// 
//  
// 

package datamodel;

import datamodel.perturbation.SamplingTime;

public class MeasuredConcentrationSample extends ConcentrationSample
{

    protected double stdDevConcMolesPerML;

    protected int numSamples;
    
    
    public MeasuredConcentrationSample() {
        this.stdDevConcMolesPerML = 0.0;
        this.numSamples = 1;
    }
    
    public MeasuredConcentrationSample(final double concentrationMolesPerML, final SamplingTime samplingTime, final double stdDevConcMolesPerML, final int numSamples) {
        this.stdDevConcMolesPerML = 0.0;
        this.numSamples = 1;
        this.concMolesPerML = concentrationMolesPerML;
        this.samplingTime = samplingTime;
        this.stdDevConcMolesPerML = stdDevConcMolesPerML;
        this.numSamples = numSamples;
    }
    
    public int getNumSamples() {
        return this.numSamples;
    }
    
    public void setNumSamples(final int numSamples) {
        this.numSamples = numSamples;
    }
    
    public double getStdDevConcMolesPerML() {
        return this.stdDevConcMolesPerML;
    }
    
    public void setStdDevConcMolesPerML(final double stdDevConcMolesPerML) {
        this.stdDevConcMolesPerML = stdDevConcMolesPerML;
    }
}
