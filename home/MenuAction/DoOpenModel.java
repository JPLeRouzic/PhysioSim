/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.MenuAction;

import home.MenuDoAction;
import java.awt.Cursor;
import javax.swing.JFileChooser;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class DoOpenModel extends DoNewInsertOpenModel {

    /**
     * File/Open HumanBody model" was selected Choose the folder where a
     * Placentalia is described
     *
     * @param menuDoAction
     */
    public void doOpenModel(MenuDoAction menuDoAction) {

        // clear previous tree
        menuDoAction.guiMn.simMain.st.removeAllObjects(menuDoAction.guiMn, true);

        // Fetch and process the SBML files
        String absolutePath = menuDoAction.guiMn.xmlFile.getAbsolutePath();
        final JFileChooser chooser2 = menuDoAction.guiMn.getJFileChooser(absolutePath);
        // only accept folders
        chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser2.showOpenDialog(menuDoAction.guiMn);

        // A directory was chosen
        menuDoAction.guiMn.setCursor(new Cursor(3));

        parseAndDraw(menuDoAction, chooser2);

        // build SolverIn for later use
        boolean files = buildSolverIn(menuDoAction);

        if (files == false) {
            return;
        }

        // Draw tree HostModel on GUI, but with no connection at the moment
        drawTree(menuDoAction);
    }

}
