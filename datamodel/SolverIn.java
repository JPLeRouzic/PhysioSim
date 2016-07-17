// 
//  
// 

package datamodel;

import datamodel.perturbation.SamplingTime;
import datamodel.type.FlowEquationType;
import datamodel.type.MolecularType;
import datamodel.type.SamplingUnitsDefinitionType;
import java.util.ArrayList;
import java.util.Date;
import datamodel.type.TimeUnitsDefinitionType;
import java.util.Collection;
import java.util.Vector;

public class SolverIn 
{

    private HostModelToSolve simulationSession;
    
    protected Date creationDate;
    
    protected Date startTime;
    
    protected Date completionTime;
    
    protected boolean saved;
    
    protected boolean executed;
    
    public SolverIn() {
        this.simulationSession = new HostModelToSolve();
        this.creationDate = new Date();
        this.startTime = null;
        this.completionTime = null;
        this.saved = true;
        this.executed = false;
    }
    
    public SolverIn(
            HostModelToSolve  runPar,
            ArrayList entiti,
            ArrayList sampli,
            ArrayList flowEq,
            Date      creati,
            Date    startT,
            Date    comple,
            double    quantC,
            double    quantM,
            boolean   saved,
            boolean   execut,
            ArrayList    volumeL         
    ) {
        this.simulationSession = runPar;
        this.creationDate = creati;
        this.startTime = startT;
        this.completionTime = comple;
        this.saved = saved;
        this.executed = execut;
    }
    
    public HostModelToSolve getHostModelToSolve() {
        return this.simulationSession;
    }
    
    public void setHostModelToSolve(final HostModelToSolve modelToSolve) {
        this.simulationSession = modelToSolve;
    }
    
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public void cleanUp() {
        this.executed = false;
    }
    
    public void setUp() {
        this.getHostModelToSolve().getHostModel().sortSamplingTimes();
    }
    
    public boolean isSaved() {
        if (this.simulationSession != null) {
            this.saved = false;
        }
        return this.saved;
    }
    
    public void setSaved(final boolean saved) {
        this.saved = saved;
        if (saved) {
            if (this.simulationSession != null) {
                this.setSaved(saved);
            }
        }
        else {
            this.executed = false;
        }
    }
    
    public boolean isExecuted() {
        return this.executed;
    }
    
    public void setExecuted(final boolean property1) {
        this.executed = property1;
    }
    
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }
    
    public static double getTime(final double timeValueInMinutes, final TimeUnitsDefinitionType timeUnits) {
        if (timeUnits == TimeUnitsDefinitionType.SECONDS) {
            return timeValueInMinutes * 60.0;
        }
        if (timeUnits == TimeUnitsDefinitionType.MINUTES) {
            return timeValueInMinutes;
        }
        if (timeUnits == TimeUnitsDefinitionType.HOURS) {
            return timeValueInMinutes / 60.0;
        }
        if (timeUnits == TimeUnitsDefinitionType.DAYS) {
            return timeValueInMinutes / 60.0 / 24.0;
        }
        if (timeUnits == TimeUnitsDefinitionType.WEEKS) {
            return timeValueInMinutes / 60.0 / 24.0 / 7.0;
        }
        if (timeUnits == TimeUnitsDefinitionType.YEARS) {
            return timeValueInMinutes / 60.0 / 24.0 / 365.0;
        }
        return -1.0;
    }
    
    public double getTime(final TimeUnitsDefinitionType timeUnits, SamplingTime samplingTime) {
        return getTime(samplingTime.getTimeInMinutes(), timeUnits);
    }
    
    public double getTimeInMinutes(SamplingTime samplingTime) {
        return samplingTime.getTimeInMinutes();
    }

    public Date getCompletionTime() {
        return this.completionTime;
    }
    
    public void setCompletionTime(final Date completionTime) {
        this.completionTime = completionTime;
    }

    public void setTimeInMinutes(final double timeInMinutes, SamplingTime samplingTime) {
        samplingTime.setTimeInMinutes((int) (timeInMinutes * 10000.0) / 10000.0);
    }

}
