/*
 * 
 */
package home;

import datamodel.Tissue;
import home.MenuAction.DoDrug;
import home.MenuAction.DoNewModel;
import home.MenuAction.DoOpenModel;
import home.MenuAction.DoSaveModel;
import home.MenuAction.DoInjection;
import home.MenuAction.DoNewModel;
import java.awt.Cursor;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import home.MenuAction.RunSimulation;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class MenuDoAction {

    public final GUIMain guiMn;
    boolean startDataPath;

    public DoDrug agent;
    DoNewModel placentaliaNew;
    DoOpenModel placentaliaOpen;
    DoSaveModel placentaliaSave;
    public DoInjection injection;
    private boolean startSave;
    public RunSimulation runSimul ; 

    public MenuDoAction(GUIMain gMn) {
        guiMn = gMn;
        startDataPath = true;
        placentaliaNew = new DoNewModel();
        placentaliaOpen = new DoOpenModel();
        placentaliaSave = new DoSaveModel();
        agent = new DoDrug();
        injection = new DoInjection();
        runSimul = new RunSimulation() ;
    }

    // user hits the "About" button
    public void doAboutApplication() {
        String aboutStr = "";
        aboutStr += "PhysioSim\r\n";
        aboutStr = aboutStr + "Version " + guiMn.appVersionStr + "\r\n";
        aboutStr += "\r\n";
        JOptionPane.showMessageDialog(guiMn, aboutStr, "About", -1);
    }

    /**
     * User has selected the "File/Close" menu
     *
     */
    public void doCloseModel() {
        if ((guiMn.xmlFile.exists()) || (!guiMn.isSaved())) {
            doPromptSaveResults();
        }
        try {
            guiMn.xmlFile = new File("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(guiMn, e.getMessage(), "Error", 0);
        }
        // remove all graphics objects
        guiMn.simMain.st.removeAllObjects(guiMn, true);
//        guiMn.simMain.upperRightPane.nodes = null;
//        guiMn.simMain.upperRightPane.repaint();;
        guiMn.updateJFrameTitle();
    }

    public void doAddSamplingTimes() {
    }

    public void doEditSamplingTimes() {
    }

    public void doDeleteSamplingTime() {
    }

    public void doAddSamplingLocation() {
    }

    public void doEditSamplingLocation() {
    }

    public void doDeleteSamplingLocation() {
    }

    public void doAddLocationToSamplingLocation() {
    }

    public void doDeleteLocationToSamplingLocation() {
    }

    public void doAddMeasurementSet() {
    }

    public void doDeleteMeasurementSet() {
    }

    // validate session parameters
    public void validateSessionParameters() {
    }
    
    /**
     * Find which organs there are in this physiological system
     *
     * @param psStr
     * @param tissueList
     * @return
     */
    public ArrayList<Tissue> getTissueList(String psStr, ArrayList<Tissue> tissueList) {

        Iterator<Tissue> iterOrgan = tissueList.iterator();
        ArrayList<Tissue> newTissueList = new ArrayList<>();

        while (iterOrgan.hasNext()) {
            Tissue tiss = iterOrgan.next();
            String os = tiss.getOrganSystemStr();
            if (psStr.contentEquals(os)) {
                newTissueList.add(tiss);
            }
        }
        return newTissueList;
    }

    public int doPromptSaveResults() {
        int proceedVal = 1;
        if ((!guiMn.isSaved()) && (startSave == false)) {
            String warningText = "";
            if (guiMn.isExecuted()) {
                warningText = "Simulation results are unsaved. You saved them before proceeding.\r\n";
            } else {
                warningText = "Simulation parameters are unsaved. You saved them before proceeding.\r\n";
            }
            warningText += "Do you want to save your data first?";
            proceedVal = JOptionPane.showConfirmDialog(guiMn, warningText, "Warning", 1, 2);
            if (proceedVal == 0) {
                placentaliaSave.doSaveModel(guiMn.doMnAction);
            } else if (guiMn.isExecuted()) {
                guiMn.setExecuted(false);
            }
        }
        if (startSave == true) {
            startSave = false;
        }
        return proceedVal;
    }

    protected int doPromptCreateDirectory() {
        int proceedVal;
        if (startDataPath == true) {
            startDataPath = false;
            return -1;
        } else {
            final String displayText = "It appears that the PhysioSim model files do not exist on your system.\n\nIf you have not already downloaded the model files, please specify a directory where they can be created.\nIf you have already downloaded the model files, select the directory where they were placed on your computer.\n\n";
            proceedVal = JOptionPane.showConfirmDialog(guiMn, displayText, "Specify PhysioSim data directory", -1, 1);
            return proceedVal;
        }
    }

    protected String doSelectBaseDirectoryPath(final String defaultPath) {
        String directoryPath = null;
        if (doPromptCreateDirectory() == 0) {
            final JFileChooser chooser = guiMn.getDirectoryChooser(defaultPath);
            final int returnVal = chooser.showOpenDialog(guiMn);
            if (returnVal == 0) {
                guiMn.setCursor(new Cursor(3));
                final File chosenDirectory = chooser.getSelectedFile();
                directoryPath = chosenDirectory.getAbsolutePath();
                guiMn.setCursor(new Cursor(0));
            }
        }
        return directoryPath;
    }

    protected void doExitApplication() {
        if ((guiMn.xmlFile.exists()) || (!guiMn.isSaved())) {
            doPromptSaveResults();
        }
        guiMn.setVisible(false);
        guiMn.dispose();
        System.exit(0);
    }

    protected void doAddJTreeNode(GUIMain guiMn) {
        guiMn.simMain.st.addObject(guiMn, "node");
    }

    // add a medical device such as an insulin pump, artificial kidney...
    public void doAddMedicalDevice(final boolean usePlacentaliaNode) {
    }

    public void doMoveNodeUp() {
    }

    public void doMoveNodeDown() {
    }

    public void doAddPipe() {
    }

    public void doDeleteSurface() {
    }

    public void doDeletePipe() {
    }

    public void doDeleteConnection() {
    }

    public void doAddSurface() {
    }

    public void doSplitTissue() {
    }
}
