/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.MenuAction;

import datamodel.FluidPipe;
import datamodel.HostModel;
import datamodel.HostModelToSolve;
import datamodel.HumanBody;
import datamodel.PhysioSystem;
import datamodel.SolverIn;
import datamodel.StandardHuman;
import datamodel.Tissue;
import datamodel.type.SimulationDefinitionType;
import home.MenuDoAction;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import leftPane.ShowConnections;
import parser.ParserMng;
import utility.CollToArrayList;
import utility.XMLIdManager;

/**
 *
 * @author jplr
 */
public class DoNewInsertOpenModel {
    
        void parseAndDraw(MenuDoAction menuDoAction, JFileChooser chooser) {

        /* Fetch and process the unique PBPK file
         menuDoAction.guiMn.xmlFile = chooser.getSelectedFile();
         menuDoAction.guiMn.lastModelDirectoryPath = chooser.getSelectedFile().getAbsolutePath();
         // test if opening was cancelled
         if("".equals(menuDoAction.guiMn.xmlFile.getPath()))
         {
         return ;
         }
         */
        // load this file in an internal representation
        menuDoAction.guiMn.simMain.gsm = new ParserMng();

        // use a local pointer for aesthetic convenience
        ParserMng gsm = menuDoAction.guiMn.simMain.gsm;

        try {
            gsm.ParseChosenFile(menuDoAction.guiMn.xmlFile);
        } catch (Exception ex) {
            // please do not remove
            Logger.getLogger(DoOpenModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // now fetch the SBML and connections files
        menuDoAction.guiMn.xmlFile = chooser.getSelectedFile();
        menuDoAction.guiMn.lastModelDirectoryPath = chooser.getSelectedFile().getAbsolutePath();
        // test if opening was cancelled
        if ("".equals(menuDoAction.guiMn.xmlFile.getPath())) {
            return;
        }

        // now parse the SBML and connections files
        try {
            gsm.ParseChosenFile(menuDoAction.guiMn.xmlFile);
        } catch (Exception ex) {
            // please do not remove
            Logger.getLogger(DoNewModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void drawTree(MenuDoAction menuDoAction) {
        // Draw tree HostModel on GUI, but with no connection at the moment
        menuDoAction.guiMn.simMain.st.drawTree(menuDoAction.guiMn, menuDoAction, this);

        // Draw connections between nodes on HostModel tree
        ShowConnections sc = new ShowConnections();
        sc.createConnectionsTree(menuDoAction.guiMn.simMain.solver, menuDoAction.guiMn);

        menuDoAction.guiMn.setCursor(new Cursor(0));
        menuDoAction.guiMn.updateJFrameTitle();
    }

    public boolean buildSolverIn(MenuDoAction menuDoAction) {

        /* test if the "open" command was cancelled */
        if (!menuDoAction.guiMn.xmlFile.isDirectory()) {
            // not a file, cancel processing
            return false;
        }
        /*
         * Let's first build a HumanBody from SBML file
         * in our voyage toward a SolverIn
         *
         * SolverIn contains a HostModelToSolve, 
         * a collection of FlowEquation, an ArrayList of CompartmentSBML
         * + other stuff
         *
         * HostModelToSolve includes HostModel + various stuff
         * HostModel includes collections of HumanBody, Pipes, Injections, etc..
         * HumanBody includes a collection of PhysioSystem + other stuff
         */
        HumanBody humanbody = new HumanBody();
        CollToArrayList deux = new CollToArrayList();

        ArrayList<Tissue> tissueList = deux.convertTissue(menuDoAction.guiMn.simMain.gsm.getMapOfTissues());

        ArrayList<String> pss = new ArrayList<>();
        Iterator<Tissue> iter = tissueList.iterator();

        // create list of physiological systems
        while (iter.hasNext()) {
            String ps = iter.next().getOrganSystemStr();
            if (!pss.contains(ps)) {
                pss.add(ps);
            }
        }

        Iterator<String> iterPS = pss.iterator();
        while (iterPS.hasNext()) {
            // find which physiological system comes next
            String psStr = iterPS.next();

            ArrayList<Tissue> newTissueList = menuDoAction.getTissueList(psStr, tissueList);

            // newTissueList now contains the Tissues that belong to this physiological system
            // s we can create a physiological system object
            PhysioSystem endocPS = new PhysioSystem(psStr, newTissueList);

            // add the newly created physiological system object in human body model
            humanbody.addOrganSystem(endocPS);
        }

        humanbody.setIdentity(XMLIdManager.getXMLId((Class) HumanBody.class));
        humanbody.setTemperatureCelcius(StandardHuman.getStandardTemperature());
        humanbody.setAgeInYears(StandardHuman.getStandardAgeInYears());
        humanbody.setGender(StandardHuman.getGender());
        humanbody.setBodyWeight(StandardHuman.getStandardBodyMassInGrams() / 1000);
        humanbody.setHeight(StandardHuman.getStandardHeightInMeters());
        humanbody.setBodyMassIndex(StandardHuman.getStandardBMI());
        /* FIXME
         humanbody.setCondition(condition);
         */
        System.out.println("Please create a XML file for age, gender, weight, condition, etc...");

        /*
         * So now we have a HumanBody from SBML file, let proceed to build
         * a HostModel
         *
         * SolverIn contains a HostModelToSolve, 
         * a collection of FlowEquation, an ArrayList of CompartmentSBML
         * + other stuff
         *
         * HostModelToSolve includes HostModel + various stuff
         * HostModel includes collections of HumanBody, Pipes, Injections, etc..
         * HumanBody includes a collection of PhysioSystem + other stuff
         */
        HostModel host = new HostModel();

        // Add HumanBody to host
        host.setPlacentalia(humanbody);

        // Add Pipes attributes to HostModel
        Iterator<FluidPipe> iter21 = menuDoAction.guiMn.simMain.gsm.getColPipes().iterator();
        while (iter21.hasNext()) {
            FluidPipe pipe = iter21.next();
            host.addPipe(pipe);
        }

        /* FIXME Missing stuff in HostModel
                    
         addInjections(null);
         addMeasurementSets(null);
         addSamplingTimes(null);
         addSamplingLocations(null);
         addDevices(null);
         addDrugs(null);
         */

        /*
         * As we have now filled a HostModel from SBML file, let proceed 
         * to build a HostModelToSolve
         *
         * SolverIn contains a HostModelToSolve, a 
         * a collection of FlowEquation, an ArrayList of CompartmentSBML
         * + other stuff
         *
         * HostModelToSolve includes HostModel + various measurement stuff
         * HostModel includes collections of HumanBody, Pipes, Injections, etc..
         * HumanBody includes a collection of PhysioSystem + other stuff
         */
        ArrayList injections = new ArrayList();
        ArrayList samplingTimes = new ArrayList();
        String author = null;
        String name = "to be done";
        ArrayList measurementSets = new ArrayList();
        Date creationDate = null;
        String notes = null;
        boolean saved = false;

        HostModelToSolve modelToSolve = new HostModelToSolve(
                host,
                injections,
                samplingTimes,
                creationDate,
                notes,
                author,
                name,
                SimulationDefinitionType.APPROXIMATE,
                measurementSets,
                saved
        );

        /* Goal: Fill in the solver input parameters in session parameters 
         *
         * SolverIn contains a HostModelToSolve, 
         * a collection of FlowEquation, an ArrayList of CompartmentSBML
         * + other stuff
         *
         * HostModelToSolve includes HostModel + various stuff
         * HostModel includes collections of HumanBody, Pipes, Injections, etc..
         * HumanBody includes a collection of PhysioSystem + other stuff
         */
        // Obtain list of all Compartments from SBML
//        Collection<CompartmentSBML> un = menuDoAction.guiMn.simMain.gsm.getCompartments();
//        ArrayList<CompartmentSBML> compList = new ArrayList<>(un);
//                    SolverIn si;
        ArrayList entiti = new ArrayList();
        ArrayList sampli = new ArrayList();
        ArrayList flowEq = new ArrayList();
        Date startT = null;
        Date comple = null;
        double quantC = 0.0;
        double quantM = 0.0;
        boolean execut = false;
        ArrayList volumeL = new ArrayList();

        // create internal representation for solver        
        menuDoAction.guiMn.simMain.solver = new SolverIn(
                modelToSolve,
                entiti,
                sampli,
                flowEq,
                creationDate,
                startT,
                comple,
                quantC,
                quantM,
                saved,
                execut,
                volumeL
        );
        return true;
    }
}
