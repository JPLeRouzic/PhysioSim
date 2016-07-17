/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import leftPane.ShowTree;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.File;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import parser.ParserMng;
import datamodel.SolverIn;

/**
 *
 * @author jplr
 */
public class SimMain {

    public String appVersionStr;
    public MenuSetter mainMenu;
    private static SimMain locationView = null;
    public GUIMain guiMn = null;

    public SolverIn solver;

    JPanel rightPane = new JPanel();

    // The  whole body Tree on GUI
    public ShowTree st;

    // The MutableTreeNode Tree where biological models are displayed
    public DefaultMutableTreeNode rootNode;
    public DefaultTreeModel treeModel;
    
    // the file parser
    public ParserMng gsm ;

    public static void main(String[] args) {

        Locale.setDefault(new Locale("en", "US"));
        try {
            File currentDir = new File(".");
            String path = currentDir.getAbsolutePath();
            System.out.println(path);
            locationView = new SimMain();
            locationView.startGUI(path);
        } catch (Exception e) {
            // dot not remove
            e.printStackTrace();
        }

    }

    public SimMain() {
        // Case where the NarrationPanel is shown at startup 
        
    }

    public void startGUI(String path) throws Exception {

        // guiMn extends JFrame, so it can be used to draw graphics
        guiMn = new GUIMain(this, path);

        // create substrate frame for GUI
        guiMn.frame = new JFrame();

        // Create myTree       
        st = new ShowTree(guiMn);

        // create split panels (left and right)
        splitPanel(guiMn);

        // There are two panels sharing the frame horizontally
        JScrollPane leftPane = new JScrollPane(guiMn.myTree,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leftPane.setPreferredSize(new Dimension(500, 600));

//        rightPane.add(guiMn.scd.text);
        rightPane.add(guiMn.mnPanel) ;

        guiMn.horizontalJSplitPane.setLeftComponent(leftPane);
        guiMn.horizontalJSplitPane.setRightComponent(rightPane);

        // Create menu and change it to frame
        guiMn.mainMenu = new MenuSetter(guiMn, path);
        guiMn.frame.add(guiMn.mainMenu.menuBar);

        // change split panel to substrate frame
        guiMn.frame.add(guiMn.horizontalJSplitPane);

        guiMn.frame.setJMenuBar(guiMn.mainMenu.menuBar);
        guiMn.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiMn.frame.setPreferredSize(new Dimension(1200, 600));
        guiMn.frame.pack();
        guiMn.frame.setLocationRelativeTo(null);
        guiMn.frame.setVisible(true);
    }

    /**
     * It separates the guiMn in two panels, one to the left, one to the right.
     * The one to the left supports the myTree view 
     * The one to the right is, (to be done)
     */
    void splitPanel(GUIMain guiMain) {
        guiMain.horizontalJSplitPane = new JSplitPane();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = 1;
    }

}
