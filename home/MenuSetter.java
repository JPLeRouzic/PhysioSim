/*
 * This is the menu of the simulator
 */
package home;

import leftPane.ShowTree;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public final class MenuSetter {
    
    public JMenuBar menuBar;


    
//    SimMain sm ;
    GUIMain guiMn ;
    private ShowTree tree; // FIXME not used?

    public MenuSetter(GUIMain guiMain, String path) throws Exception {
        guiMn = guiMain ;
        setupIcons(path);
        menuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        (guiMn.newPlacentaliaModelMenuItem = fileMenu.add("New Model organism")).setMnemonic('N');
        guiMn.newPlacentaliaModelMenuItem.setAccelerator(KeyStroke.getKeyStroke(78, 2, false));
        guiMn.newPlacentaliaModelMenuItem.addActionListener(guiMn);
        fileMenu.addSeparator();
        (guiMn.openModelMenuItem = fileMenu.add("Open...")).setMnemonic('O');
        guiMn.openModelMenuItem.setAccelerator(KeyStroke.getKeyStroke(79, 2, false));
        guiMn.openModelMenuItem.addActionListener(guiMn);
        (guiMn.closeModelMenuItem = fileMenu.add("Close")).setMnemonic('C');
        guiMn.closeModelMenuItem.addActionListener(guiMn);
        (guiMn.saveModelMenuItem = fileMenu.add("Save")).setMnemonic('S');
        guiMn.saveModelMenuItem.setAccelerator(KeyStroke.getKeyStroke(83, 2, false));
        guiMn.saveModelMenuItem.addActionListener(guiMn);
//        guiMn.saveModelMenuItem.setEnabled(false);
        (guiMn.saveAsMenuItem = fileMenu.add("Save As...")).setMnemonic('A');
        guiMn.saveAsMenuItem.addActionListener(guiMn);
        fileMenu.addSeparator();
        (guiMn.exitMenuItem = fileMenu.add("Exit")).setMnemonic('X');
        guiMn.exitMenuItem.addActionListener(guiMn);
        menuBar.add(fileMenu);
        
        final JMenu modelMenu = new JMenu("Model");
        modelMenu.setMnemonic('M');
        (guiMn.addDeviceNodeMenuItem = modelMenu.add("Add Device")).setMnemonic('V');
        guiMn.addDeviceNodeMenuItem.addActionListener(guiMn);
        modelMenu.addSeparator();
        (guiMn.addPipeMenuItem = modelMenu.add("Add Pipe")).setMnemonic('E');
        guiMn.addPipeMenuItem.addActionListener(guiMn);
        (guiMn.addTissueMenuItem = modelMenu.add("Add Tissue")).addActionListener(guiMn);
        guiMn.addTissueMenuItem.setMnemonic('N');
        (guiMn.addCompartmentMenuItem = modelMenu.add("Add Compartment")).addActionListener(guiMn);
        guiMn.addCompartmentMenuItem.setMnemonic('U');
        modelMenu.addSeparator();
        (guiMn.splitTissueMenuItem = modelMenu.add("Split Tissue")).setMnemonic('L');
        guiMn.splitTissueMenuItem.addActionListener(guiMn);
        (guiMn.deleteInModelMenuItem = modelMenu.add("Delete Node")).setMnemonic('D');
        guiMn.deleteInModelMenuItem.addActionListener(guiMn);
        menuBar.add(modelMenu);
        
        final JMenu agentMenu = new JMenu("Disturbance");
        agentMenu.setMnemonic('D');
        (guiMn.addInjectionMenuItem = agentMenu.add("Add Injection site")).addActionListener(guiMn);
        guiMn.addInjectionMenuItem.setMnemonic('I');
        (guiMn.addDrugMenuItem = agentMenu.add("Add Drug")).addActionListener(guiMn);
        guiMn.addDrugMenuItem.setMnemonic('A');
        (guiMn.insertMoleculeMenuItem = agentMenu.add("Add Molecule")).addActionListener(guiMn);
        (guiMn.insertBacteriaMenuItem = agentMenu.add("Add Bacteria")).addActionListener(guiMn);
        (guiMn.insertVirusMenuItem = agentMenu.add("Add Virus")).addActionListener(guiMn);
        (guiMn.insertToxinMenuItem = agentMenu.add("Add Toxin")).addActionListener(guiMn);
        (guiMn.addTumorMenuItem = agentMenu.add("Add Tumor")).addActionListener(guiMn);
        agentMenu.addSeparator();
        (guiMn.editMoleculeLiverClearanceRateMenuItem = agentMenu.add("Edit Liver Clearance Rate")).addActionListener(guiMn);
        guiMn.editMoleculeLiverClearanceRateMenuItem.setMnemonic('L');
        (guiMn.importDrugMenuItem = agentMenu.add("Import Drug")).addActionListener(guiMn);
        guiMn.importDrugMenuItem.setMnemonic('P');
        (guiMn.exportDrugMenuItem = agentMenu.add("Export Drug")).addActionListener(guiMn);
        guiMn.exportDrugMenuItem.setMnemonic('E');
        menuBar.add(agentMenu);
        
        final JMenu simulationMenu = new JMenu("Simulation");
        simulationMenu.setMnemonic('S');
//        simulationMenu.addSeparator();
        (guiMn.validateSessionMenuItem = simulationMenu.add("Validate Simulation Parameters")).setMnemonic('V');
        guiMn.validateSessionMenuItem.addActionListener(guiMn);
        simulationMenu.addSeparator();
        (guiMn.runSimulationMenuItem = simulationMenu.add("Run Simulation")).setMnemonic('s');
        guiMn.runSimulationMenuItem.setAccelerator(KeyStroke.getKeyStroke(82, 2, false));
        guiMn.runSimulationMenuItem.addActionListener(guiMn);
  //      simulationMenu.addSeparator();
        menuBar.add(simulationMenu);
        
        final JMenu samplingMenu = new JMenu("Measurements");
        samplingMenu.setMnemonic('M');
        (guiMn.addSamplingTimesMenuItem = samplingMenu.add("Add Sampling Times")).addActionListener(guiMn);
        guiMn.addSamplingTimesMenuItem.setMnemonic('S');
        (guiMn.editSamplingsTimeMenuItem = samplingMenu.add("Edit Sampling Times")).addActionListener(guiMn);
        guiMn.editSamplingsTimeMenuItem.setMnemonic('T');
        samplingMenu.addSeparator();
        (guiMn.addSamplingLocationMenuItem = samplingMenu.add("Add Sampling Location")).addActionListener(guiMn);
        guiMn.addSamplingLocationMenuItem.setMnemonic('L');
        guiMn.addSamplingLocationMenuItem.setDisplayedMnemonicIndex(13);
        (guiMn.editSamplingLocationMenuItem = samplingMenu.add("Edit Sampling Location")).addActionListener(guiMn);
        guiMn.editSamplingLocationMenuItem.setMnemonic('E');
        samplingMenu.addSeparator();
        menuBar.add(samplingMenu);
        
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        (guiMn.aboutMenuItem = helpMenu.add("About")).setMnemonic('A');
        guiMn.aboutMenuItem.addActionListener(guiMn);
        menuBar.add(helpMenu);
               
        guiMn.updateJFrameTitle();
    }

    public void menuActionPerformed(GUIMain guiMn, final ActionEvent e) {
        
        if (e.getSource() == guiMn.addDeviceNodeMenuItem) {
            guiMn.doMnAction.doAddMedicalDevice(false);
        } else if (e.getSource() == guiMn.addPipeMenuItem) {
            guiMn.doMnAction.doAddPipe();
        } else if (e.getSource() == guiMn.addDrugMenuItem) {
            guiMn.doMnAction.agent.doAddDrug(guiMn, guiMn.doMnAction);
        } else if (e.getSource() == guiMn.importDrugMenuItem) {
            guiMn.doMnAction.agent.doImportDrug(guiMn.doMnAction);
        } else if (e.getSource() == guiMn.exportDrugMenuItem) {
            guiMn.doMnAction.agent.doExportDrug(guiMn.doMnAction);
        } else if (e.getSource() == guiMn.addInjectionMenuItem) {
            guiMn.doMnAction.injection.doAddInjection(guiMn.doMnAction);
        } else if (e.getSource() == guiMn.addSamplingTimesMenuItem) {
            guiMn.doMnAction.doAddSamplingTimes();
        } else if (e.getSource() == guiMn.editSamplingsTimeMenuItem) {
            guiMn.doMnAction.doEditSamplingTimes();
        } else if (e.getSource() == guiMn.addSamplingLocationMenuItem) {
            guiMn.doMnAction.doAddSamplingLocation();
        } else if (e.getSource() == guiMn.editSamplingLocationMenuItem) {
            guiMn.doMnAction.doEditSamplingLocation();
        } else if (e.getSource() == guiMn.closeModelMenuItem) {
            guiMn.doMnAction.doCloseModel();
        } else if (e.getSource() == guiMn.openModelMenuItem) {
            guiMn.doMnAction.placentaliaOpen.doOpenModel(guiMn.doMnAction);
        } else if (e.getSource() == guiMn.saveModelMenuItem) {
            guiMn.doMnAction.placentaliaSave.doSaveModel(guiMn.doMnAction);
        } else if (e.getSource() == guiMn.saveAsMenuItem) {
            guiMn.doMnAction.placentaliaSave.doSaveModelAs(guiMn.doMnAction);
        } else if (e.getSource() == guiMn.exitMenuItem) {
            guiMn.doMnAction.doExitApplication();
        } else if (e.getSource() == guiMn.validateSessionMenuItem) {
            guiMn.doMnAction.validateSessionParameters();
        } else if (e.getSource() == guiMn.runSimulationMenuItem) {
            guiMn.doMnAction.runSimul.doSimulationRun(guiMn.simMain);
        } else if (e.getSource() == guiMn.newPlacentaliaModelMenuItem) {
            guiMn.doMnAction.placentaliaNew.newPlacentaliaModel(guiMn.doMnAction);
        } else if (e.getSource() == guiMn.aboutMenuItem) {
            guiMn.doMnAction.doAboutApplication();
        }
    }


    private void setupIcons(String incpath) {
        int pathlength = incpath.length() ;
        String path = incpath.substring(0, pathlength -1 ) ;

    }

    public void checkPopup(final MouseEvent e, GUIMain guiMain) {
}
}
