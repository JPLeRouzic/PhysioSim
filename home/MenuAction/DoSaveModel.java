/*
 * 
 */
package home.MenuAction;

import datamodel.SolverIn;
import java.io.File;
import javax.swing.JOptionPane;
import home.MenuDoAction;
import java.awt.Cursor;
import javax.swing.JFileChooser;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class DoSaveModel extends DoNewInsertOpenModel {

    public void doSaveModel(MenuDoAction menuDoAction) {
        if (menuDoAction.guiMn.xmlFile.exists()) {
            System.out.println("'" + menuDoAction.guiMn.xmlFile.getName().length() + "'");
            final int proceedVal = JOptionPane.showConfirmDialog(menuDoAction.guiMn, "Do you want to overwrite existing file?", "Warning", 0, 2);
            if (proceedVal == 0) {
                menuDoAction.guiMn.setCursor(new Cursor(3));
                try {
// FIXME                          
                    // now read SolverIn and write it back on file in SBML format
                    // without missing any information
//                    SolverIn si = menuDoAction.guiMn.simMain.solver ; !!!
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(menuDoAction.guiMn, e.getMessage(), "Error", 0);
                }
                menuDoAction.guiMn.setCursor(new Cursor(0));
            }
        } else {
            doSaveModelAs(menuDoAction);
        }
    }

    public void doSaveModelAs(MenuDoAction menuDoAction) {
        String absolutePath = menuDoAction.guiMn.lastModelDirectoryPath;
        if (menuDoAction.guiMn.xmlFile.exists()) {
            absolutePath = menuDoAction.guiMn.xmlFile.getAbsolutePath();
        }
        final JFileChooser chooser = menuDoAction.guiMn.getJFileChooser(absolutePath);
        if (menuDoAction.guiMn.xmlFile.getName().length() > 0) {
            chooser.setSelectedFile(menuDoAction.guiMn.xmlFile);
        }
        final int returnVal = chooser.showSaveDialog(menuDoAction.guiMn);
        if (returnVal == 0) {
            File selectedFile = chooser.getSelectedFile();
            menuDoAction.guiMn.lastModelDirectoryPath = chooser.getSelectedFile().getAbsolutePath();
            if (!selectedFile.getName().contains(".")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".xml");
            }
            menuDoAction.guiMn.setCursor(new Cursor(3));
            try {
                int proceedVal = 0;
                if (selectedFile.exists()) {
                    proceedVal = JOptionPane.showConfirmDialog(menuDoAction.guiMn, "File already exists. Do you want to overwrite?", "Warning", 0, 2);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(menuDoAction.guiMn, e.getMessage(), "Error", 0);
            }
            menuDoAction.guiMn.setCursor(new Cursor(0));
        }
        menuDoAction.guiMn.updateJFrameTitle();
    }
}


