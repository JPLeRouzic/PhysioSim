/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.MenuAction;

import home.MenuDoAction;
import javax.swing.JFileChooser;
import java.io.File;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class DoNewModel extends DoNewInsertOpenModel {

    /**
     * File/New HumanBody model" was selected
     *
     * @param menuDoAction
     *
     * Goal: Fill in the solver input parameters in session parameters
     *
     * SolverIn contains a HostModelToSolve, a collection of FlowEquation, an
     * ArrayList of CompartmentSBML + other stuff
     *
     * HostModelToSolve includes HostModel + various stuff HostModel includes
     * collections of HumanBody, Pipes, Injections, etc.. HumanBody includes a
     * collection of PhysioSystem + other stuff
     */
    public void newPlacentaliaModel(MenuDoAction menuDoAction) {
        JFileChooser chooser = new JFileChooser("./data/models/sbml/");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File file = new File("./data/models/sbml/");
        chooser.setSelectedFile(file);
//        menuDoAction.guiMn.xmlFile = chooser.getSelectedFile();
        parseAndDraw(menuDoAction, chooser);

        // build SolverIn for later use
        buildSolverIn(menuDoAction);

        // Draw tree HostModel on GUI, but with no connection at the moment
        drawTree(menuDoAction);
    }
}
