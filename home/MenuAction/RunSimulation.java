/**
 * Now that we have compartments, species and reactions we can start the
 * simulation "x" is an array of "nbReactions" size, "x" contains the initial
 * value for each ODE. We need to put the ODEs in the user function
 *
 * First loop through solver's steps, then models, then reactions That way, a
 * product in one reaction which is a reactant in another, helps to provide a
 * global simulation, instead of being the result of one of many reactions which
 * do not influence each other.
 *
 * The ArrayList in return, contains for each step, the value of each ODE. So
 * ArrayList's size is equal to the number of steps and each item of the
 * ArrayList, contains as much "double" as there are ODEs.
 *
 * This uses DESSolver's code from Jens.Langner and Felix Kraemer from
 * tw-dresden.de
 */
package home.MenuAction;

import datamodel.HostModel;
import datamodel.SolverIn;
import home.SimMain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import parser.sbml.ModelSBML;
import parser.sbml.ReactionsSBML;
import ODEsolver.Tuples;
import java.util.Vector;
import javax.swing.event.ChangeEvent;
import parser.sbml.ParameterSBML;
import parser.sbml.SpeciesSBML;
import parser.sbml.SpeciesRefSBML;

/**
 *
 * @author jplr
 */
public class RunSimulation {

    // user wants to run simulation
    public void doSimulationRun(SimMain simMain) {
        SolverIn solver = simMain.solver;
        HostModel mdl = solver.getHostModelToSolve().getHostModel();

        // Do one step, first for PBPK wholebody, next for SBML cell and molecular pathways
        // in the future we will add tissue's cell population simulation
        // Loop through SBML models
        through_SBML_models(simMain);  //    FIXME              
    }

    // reactant is a substance consumed in the course of a chemical reaction
    // In SBML a reactant is a species used in a reaction
    // there are several reactants (also named reagents) for each reaction    
    private void through_SBML_models(SimMain simMain) {
        ArrayList<Tuples> resultVectar = new ArrayList<>();
        Collection<ReactionsSBML> reactionsList = new ArrayList<>();
        Collection<SpeciesSBML> speciesList = new ArrayList<>();
        Collection<SpeciesRefSBML> speciesReferenceList = new ArrayList<>();
        Collection<ParameterSBML> ParameterList = new ArrayList<>();

        // arrays to collect parts for narrative
        ArrayList alComp = new ArrayList();
        ArrayList alSpec = new ArrayList();
        ArrayList alReac = new ArrayList();
        ArrayList alPara = new ArrayList();

        /**
         * * species **
         */
        // FIXME find all species that are part of Tissue's selectedNode
        /**
         * * reactions **
         *
         * Here we can change kinetic laws
         *
         */
        // FIXME find all reactions that are part of Tissue's selectedNode
        /**
         * * parameters **
         */
        Collection colModl = simMain.gsm.getMapOfModels().values();

//
        Iterator iterModl = colModl.iterator();

        while (iterModl.hasNext()) {
            ModelSBML model = (ModelSBML) iterModl.next();

            // Now get the initial values for reaction in each model 
            // and store them in reactionsList
            // the initial amount for a species in stored SBML as in:
            //         <species compartment="cytosol" id="ES" initialAmount="0" name="ES"/>
            speciesList.addAll(model.getSpecies());

            // and also reactants and products
            //    <listOfReactants>
            //        <speciesReference species="Urine" stoichiometry="1"/>
            //    </listOfReactants>
            //    <listOfProducts>
            //        <speciesReference species="Urine" stoichiometry="1"/>
            //    </listOfProducts>
            Collection<SpeciesRefSBML> un = model.getSpeciesReference();
            speciesReferenceList.addAll(un);

            // and also listOfParameters
            Collection<ParameterSBML> deux = model.getParameters();
            ParameterList.addAll(deux);

            // Now loop through reactions (one reaction = one ODE)
            // Now loop through every reaction in each model (one reaction = one ODE)
            // and store them in reactionsList
            reactionsList.addAll(model.getReactions());
            if (!model.getReactions().isEmpty()) {
                int y = 0; // FIXME remove me if debug finished
            }
        }

        // now write the kinetic laws of reactions to the solver
        Iterator iterMoc = reactionsList.iterator();
        while (iterMoc.hasNext()) {
            // reactant is a substance consumed in the course of a chemical reaction
            // In SBML a reactant is a species used in a reaction
            // there are several reactants (also named reagents) for each reaction

            // find all reactions that are part of Tissue's selectedNode
            Vector tempstrings = new Vector();

            // create a vector (functionstrings) with all kinetic laws
            // This is a string that represent a function
            //something like "2ax + 10"
            Vector functionstrings = new Vector();

            // This is the value at which the function starts
            // for example "5", then "2ax + 10" will start with x = 5
            // FIXME
            Vector startvector = new Vector();

            Iterator un = reactionsList.iterator();
            while (un.hasNext()) {
                ReactionsSBML cinq = (ReactionsSBML) un.next();
                functionstrings.add(cinq.getKineticLaw());
            }

            // write back function on gui's textArea
            simMain.guiMn.mnPanel.funcTextA.setText(tempstrings.toString());

            // Find the initial value for those functions
            // There are two different kind of initial values for the ODE
            // The first is the initial value of the "X" dimension, it is 
            // the initial amount for a species in SBML AS IN:
            //         <species compartment="cytosol" id="ES" initialAmount="0" name="ES"/>
            // The second is the initial value of the "Y" dimension, as the function
            // works with tiny increments over previous value of the function, it has to start somewhere
            // So we have to compute the initial value of the function from the initial values of its variables
            startvector = initial_value_of_variables_of_the_function(
                    speciesList, speciesReferenceList,
                    ParameterList, functionstrings);

            // write back function on gui's textArea
            simMain.guiMn.mnPanel.funcTextA.setText(tempstrings.toString());
            simMain.guiMn.mnPanel.gPanel.setCalcMethod((String) simMain.guiMn.mnPanel.methodComboBox.getSelectedItem());
            simMain.guiMn.mnPanel.gPanel.setFunction(functionstrings, startvector);
            //  xMin width= 0.0, xMax = 30.0, yMin height = -3.0, yMax = 3.0, incr = 0.1, eps (error) = 0.1;
            simMain.guiMn.mnPanel.gPanel.setView(0, 200, -30, 30, 0.1, 0.1);

            simMain.guiMn.mnPanel.checkTabbedPane();
            simMain.guiMn.mnPanel.tabbedAction.stateChanged(new ChangeEvent(simMain.guiMn.mnPanel.tabbedPane));

        }
    }

    private Vector initial_value_of_variables_of_the_function(
            Collection<SpeciesSBML> speciesList, Collection<SpeciesRefSBML> speciesReferenceList,
            Collection<ParameterSBML> parameterList, Vector functionstrings) {

        // for each kinetic law, we search which variables it has
        // from the variable, we find its initial value,
        // then we compute the kinetic law initial value
        // and put it in the result vector 
        // make a global collection of all variables
        ArrayList allVars = new ArrayList();
        Vector initValues = new Vector();

        Iterator iterspecies = speciesList.iterator();
        while (iterspecies.hasNext()) {
            SpeciesSBML var = (SpeciesSBML) iterspecies.next();
            allVars.add(var.id);
        }

        Iterator iterrefspecies = speciesReferenceList.iterator();
        while (iterrefspecies.hasNext()) {
            SpeciesRefSBML var = (SpeciesRefSBML) iterrefspecies.next();
            allVars.add(var.species);
        }

        Iterator iterparam = parameterList.iterator();
        while (iterparam.hasNext()) {
            ParameterSBML var = (ParameterSBML) iterparam.next();
            allVars.add(var.id);
        }

        Vector initValFunc = new Vector() ;
        Iterator un = functionstrings.iterator();
        while (un.hasNext()) {
            String function = (String) un.next();

            Iterator varIter = allVars.iterator();
            while (varIter.hasNext()) {
                String one = (String) varIter.next();
                // for each kinetic law, we search which variables it has
                if (function.contains(one)) {
                    String value = fuhhh();
                    initValues.add(value);
                }
            }
            // obtain initial value for function, from initial values of kinetic law
            initValFunc.add(fiiihh()) ;
        }
        return initValFunc;
    }
}
