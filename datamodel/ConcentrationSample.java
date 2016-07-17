// 
//  
// 

package datamodel;

import datamodel.perturbation.SamplingTime;

public class ConcentrationSample 
{
    protected SamplingTime samplingTime;
    protected double concMolesPerML; // concentration
     
   
    public ConcentrationSample() {
        this.samplingTime = null;
        this.concMolesPerML = 0.0;
    }
    
    public ConcentrationSample(final double concMolesPerML, final SamplingTime samplingTime) {
        this.samplingTime = null;
        this.concMolesPerML = concMolesPerML;
        this.samplingTime = samplingTime;
    }
    
    public double getConcMolesPerML() {
        return this.concMolesPerML;
    }
    
    public void setConcMolesPerML(final double concMolesPerML) {
        this.concMolesPerML = concMolesPerML;
    }
    
    public SamplingTime getSamplingTime() {
        return this.samplingTime;
    }
    
    public void setSamplingTime(final SamplingTime samplingTime) {
        this.samplingTime = samplingTime;
    }
}
