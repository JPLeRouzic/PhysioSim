// 
// Create basic GUI panels (without menus) 
// 
// 
//  
// 
package home;

import leftPane.NodeClickManager;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import java.io.File;
import javax.swing.JList;
import java.awt.event.MouseListener;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import leftPane.ConnectionJTree;
import rightPane.MainPanel;

public class GUIMain extends JFrame implements ActionListener,
        ListSelectionListener, MouseListener {

    public SimMain simMain;
    public MenuDoAction doMnAction;
    public MenuSetter mainMenu;
    
    //    JTree myTree;
    public ConnectionJTree myTree;

    // create SBML compartment manager
    public NodeClickManager scd ;
        
    private final String path;

    /**
     * The panel which holds all other panels
     *
     */
    public JFrame frame;

    public String appVersionStr;

    public JList errorJList;
    
    public File xmlFile;

//    items for menues
    protected JMenuItem newPlacentaliaModelMenuItem;
    protected JMenuItem openModelMenuItem;
    protected JMenuItem closeModelMenuItem;
    protected JMenuItem saveModelMenuItem;
    protected JMenuItem saveAsMenuItem;
    protected JMenuItem exitMenuItem;
    protected JMenuItem addConnectionMenuItem;
    protected JMenuItem addDeviceNodeMenuItem;
    protected JMenuItem splitTissueMenuItem;
    protected JMenuItem addTumorMenuItem;
    protected JMenuItem addSurfaceMenuItem;
    protected JMenuItem addPipeMenuItem;
    protected JMenuItem addDrugMenuItem;
    protected JMenuItem addMoleculeMenuItem;
    protected JMenuItem editMoleculeLiverClearanceRateMenuItem;
    protected JMenuItem addInjectionMenuItem;
    protected JMenuItem importDrugMenuItem;
    protected JMenuItem exportDrugMenuItem;
    protected JMenuItem addTissueMenuItem;
    protected JMenuItem addCompartmentMenuItem;
    protected JMenuItem deleteInModelMenuItem;
    protected JMenuItem addSamplingTimesMenuItem;
    protected JMenuItem editSamplingsTimeMenuItem;
    protected JMenuItem addSamplingLocationMenuItem;
    protected JMenuItem editSamplingLocationMenuItem;

    protected JMenuItem insertBacteriaMenuItem;
    protected JMenuItem insertVirusMenuItem;
    protected JMenuItem insertToxinMenuItem;
    protected JMenuItem insertMoleculeMenuItem;

    protected JMenuItem validateSessionMenuItem;
    protected JMenuItem runSimulationMenuItem;

    protected JMenuItem aboutMenuItem;

    public String lastModelDirectoryPath;
    public String lastDrugDirectoryPath;
    public JFileChooser fileChooser;
    private boolean executed;

    JSplitPane horizontalJSplitPane;
    JSplitPane verticalJSplitPane;
    public MainPanel mnPanel ;
    
    public GUIMain(SimMain sm, String pass) throws Exception {
        appVersionStr = "0.0.1";

        // Usefull pointers
        simMain = sm;
        path = pass;

        xmlFile = new File("");
        fileChooser = null;

        doMnAction = new MenuDoAction(this);
        
        scd = new NodeClickManager(this) ;
        
        mnPanel = new MainPanel() ;

    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getSource() == errorJList) {
            valueChanged_errorJList();
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
    }

    protected void valueChanged_connectionJList() {
    }

    protected void valueChanged_errorJList() {
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        mainMenu.menuActionPerformed(this, e);
    }

    @Override
    protected void processWindowEvent(final WindowEvent e) {
        if (e.getID() == 201) {
            doMnAction.doExitApplication();
        }
    }

    protected String getClassName(final Class theClass) {
        final String fullClassName = theClass.getName();
        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    }

    public String getClassName(final Object object) {
        if (object != null) {
            final String fullClassName = object.getClass().getName();
            return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        }
        return "";
    }

    public void updateJFrameTitle() {
        if (xmlFile != null && xmlFile.exists()) {
            setTitle("PhysioSim - [" + xmlFile.getAbsolutePath() + "]");
        } else {
            setTitle("PhysioSim (version " + appVersionStr + ") ");
        }
    }

    public JFileChooser getJFileChooser(final String absoluteDirectoryPath) {
        if (fileChooser == null) {
            setCursor(new Cursor(3));
            (fileChooser = new JFileChooser(lastModelDirectoryPath)).setMultiSelectionEnabled(false);
            setCursor(new Cursor(0));
        }
        fileChooser.setCurrentDirectory(new File(absoluteDirectoryPath));
        fileChooser.setSelectedFile(new File(""));
        return fileChooser;
    }

    protected JFileChooser getDirectoryChooser(final String absoluteDirectoryPath) {
        final JFileChooser dirChooser = new JFileChooser(absoluteDirectoryPath);
        dirChooser.setDialogTitle("Select the directory used to store PhysioSim models");
        dirChooser.setMultiSelectionEnabled(false);
        dirChooser.setFileSelectionMode(1);
        dirChooser.setAcceptAllFileFilterUsed(false);
        setCursor(new Cursor(0));
        return dirChooser;
    }

    boolean isSaved() {
        return false;
    }

    public boolean isExecuted() {
        return executed;
    }

    void setExecuted(boolean b) {
        executed = b;
    }

}
