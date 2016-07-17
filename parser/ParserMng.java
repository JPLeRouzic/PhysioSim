// 
//  
// 
package parser;

import datamodel.FluidPipe;
import datamodel.Tissue;
import datamodel.perturbation.Drug;
import datamodel.SolverIn;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import datamodel.CompartmentSBML;
import java.util.Collection;
import java.util.Iterator;
import parser.sbml.ModelSBML;
import parser.sbml.ParameterSBML;
import parser.sbml.ReactionsSBML;
import parser.sbml.SpeciesSBML;

public class ParserMng {

    // Map to create a list of Tissues in a Placentalia model
    private Map<Integer, Tissue> mapOfTissues = new HashMap<>();
    private int indxOfTissues = 0;

    /* this is about SBML elements only */
    private int indxOfModels = 0;
    private Map<Integer, ModelSBML> mapOfModels;
    
    // This is used to store temporally the current Tissue list of compartments
    private Map<String, CompartmentSBML> mapOfTissueCompartments;

    /* Intermediate objects betwween SBML and internal representation */
    private ArrayList<FluidPipe> colPipes;
    
    // Command File/Open has just been executed and a folder was chosen
    // we parse the file
    public void ParseChosenFile(final File path) throws Exception {

        if (path.exists() && path.canRead()) {
            final SBMLParserMain umu = new SBMLParserMain(this);

            // SBML parser
            umu.parseSBML2Coll(path);
        }
    }

    public static void saveSolvedModel(final File outputFile, final SolverIn solverOutput) throws Exception {
        if (solverOutput != null) {
            solverOutput.cleanUp();
            solverOutput.setSaved(true);
        }
    }

    public static Drug loadDrug(final File inputFile) throws Exception {
        final Drug agent = null;
        if (agent == null) {
            throw new Exception("Problem loading file " + inputFile.getName());
        }
        return agent;
    }

    public static void saveDrug(final File outputFile, final Drug agent) throws Exception {
//        if (false) {
            agent.setSaved(true);
//        }
    }

    /**
     * Return all compartments
     *
     * @return
     */
    public ArrayList<CompartmentSBML> getCompartments() {
        ArrayList<CompartmentSBML> deux = new ArrayList<>() ;

        Collection<ModelSBML> modls = mapOfModels.values();
        Iterator<ModelSBML> iterModls = modls.iterator();
        while(iterModls.hasNext())
        {
            ModelSBML modl = iterModls.next();
            deux.addAll(modl.getMapOfCompartments().values()) ;
        }
        return deux;
    }

    public ArrayList getColPipes() {
        return this.colPipes  ;
    }

    public void setColPipes(ArrayList arrayList) {
        this.colPipes = arrayList ;
    }

    public Map<Integer, Tissue> getMapOfTissues() {
        return this.mapOfTissues ;
    }

    public void setMapOfTissues(Map<Integer, Tissue> map) {
        this.mapOfTissues = map ;
    }

    public Map<Integer, ModelSBML> getMapOfModels() {
        return this.mapOfModels ;
    }

    public void setMapOfModels(HashMap<Integer, ModelSBML> hashMap) {
        this.mapOfModels = hashMap ;
    }

    public Map<String, CompartmentSBML> getMapOfTissueCompartments() {
        return this.mapOfTissueCompartments ;
    }

    public void setMapOfTissueCompartments(HashMap<String, CompartmentSBML> hashMap) {
        this.mapOfTissueCompartments = hashMap ;
    }

    public int getIndxOfModels() {
        return this.indxOfModels ;
    }

    public void setIndxOfModels(int i) {
        this.indxOfModels = i ;
    }

    public void incIndxOfModels() {
        this.indxOfModels++ ;
    }

    public Integer getIndxOfTissues() {
        return this.indxOfTissues ;
    }
    
    public void setIndxOfTissues(int i) {
        this.indxOfTissues = i ;
    }
    public void incIndxOfTissues() {
        this.indxOfTissues++ ;
    }

}
