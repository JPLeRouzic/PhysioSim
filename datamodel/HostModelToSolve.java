/* 
The top-level element/object class is called HostModelToSolve. 
It defines some descriptive attributes useful for categorizing simulation input parameter sets, 
such as the name used to identify this set of simulation inputs, the name of the author 
who created them, and the date the data was created or last modified. 

The HostModelToSolve element/class encompasses all the data inputs into the simulation. 
It contains:
    an placentalia model, 
    a collection of Injections, 
    a collection of SamplingTimes, 
    a collection of MeasurementSet objects, the latter representing a set of measurement data that can be used to verify/validate a simulation model.

The internal Model defines the data needed to model the hosts, pathogens, and agents fed into
the simulation.
*/ 

package datamodel;

import datamodel.perturbation.Drug;
import datamodel.perturbation.Injection;
import datamodel.Connection;
import datamodel.perturbation.MeasurementSet;
import datamodel.perturbation.Molecule;
import datamodel.HostModel;
import datamodel.perturbation.SamplingLocation;
import datamodel.perturbation.SamplingTime;
import datamodel.type.SimulationDefinitionType;
import java.util.ArrayList;
import java.util.Date;
import datamodel.CompartmentSBML;

public class HostModelToSolve 
{
    // The host model
    protected HostModel physioSM;
        
    // administratif stuff
    protected String notes;
    protected String name;
    SimulationDefinitionType simulationDefinitionType;
    
    
    public HostModelToSolve() {
        this.physioSM = new HostModel();
        physioSM.injections = new ArrayList();
        physioSM.samplingTimes = new ArrayList();
        this.notes = "";
        this.name = "New Run";
        this.simulationDefinitionType = SimulationDefinitionType.APPROXIMATE;
        physioSM.measurementSets = new ArrayList();
    }
    
    public HostModelToSolve(
            HostModel physioSM, 
            ArrayList injections, 
            ArrayList samplingTimes, 
            Date creationDate, 
            String notes, 
            String author, 
            String name, 
            SimulationDefinitionType simulationDefinitionType, 
            ArrayList measurementSets, 
            boolean saved) {
        this.physioSM = physioSM;
        physioSM.injections = injections;
        physioSM.samplingTimes = samplingTimes;
        this.notes = notes ;
        this.name = name ;
        this.simulationDefinitionType = simulationDefinitionType;
        physioSM.measurementSets = measurementSets;
//        this.runSimulationAfterWizard = runSimulationAfterWizard;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
            
    public HostModel getHostModel() {
        return this.physioSM;
    }
    
    public void setPhysioSimModel(final HostModel PhysioSimModel) {
        this.physioSM = PhysioSimModel;
    }

    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(final String notes) {
        this.notes = notes;
    }
    
    public SimulationDefinitionType getSimulationDefinitionType() {
        return this.simulationDefinitionType;
    }
    
    public void setSimulationDefinitionType(final SimulationDefinitionType simulationDefinitionType) {
        this.simulationDefinitionType = simulationDefinitionType;
    }
}
