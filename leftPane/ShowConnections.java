/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leftPane;

import datamodel.FluidPipe;
import datamodel.HostModel;
import datamodel.perturbation.Injection;
import home.GUIMain;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import datamodel.SolverIn;

/**
 *
 * @author jplr
 */
public class ShowConnections extends JPanel {

    public JScrollPane areaScrollPane;

    public ShowConnections() {
        areaScrollPane = new JScrollPane();
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        areaScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Connections"),
                areaScrollPane.getBorder())
        );

    }

    /**
     * This creates connections between tissues, compartments. it gathers list
     * of pipes, surfaces, injection before calling setConnectionData.
     *
     * @param solver
     * @param guiMain
     */
    public void createConnectionsTree(SolverIn solver, GUIMain guiMain) {
        // locationJTree was built in SimMain
        guiMain.myTree.setConnectionData(guiMain, null);

        HostModel host = solver.getHostModelToSolve().getHostModel();
        if (host != null) {
            final ArrayList objects = new ArrayList();

            ArrayList<FluidPipe> j = host.getPipes();
            objects.addAll(j);

            ArrayList<Injection> l = host.getInjections();
            objects.addAll(l);

            if (objects.size() > 0) {
                // now as objects list is filled in, draw tree
                guiMain.myTree.setConnectionData(guiMain, objects);
            }

        }
    }
}
