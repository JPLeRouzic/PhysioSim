// 
// Is a natural or artificial connection between two points with fluid migrating 
// from one to the other
//
// A compartment with two outside connections is either a surface or a point to point connection.
// A FluidPipe class has a length which is much longer than width. 
// 

package datamodel;

import datamodel.type.ScaleFlowRateDefinitionType;
import utility.XMLIdManager;

public class FluidPipe extends Connection
{
//    public String xmlId;
 
    protected ScaleFlowRateDefinitionType scaleFlowRateDefinitionType;
    protected double flowRateMLPerMinPerScaleUnit;
    
    public FluidPipe() {
        this.scaleFlowRateDefinitionType = ScaleFlowRateDefinitionType.PER_GRAM_TISSUE;
        this.flowRateMLPerMinPerScaleUnit = 0.0;
        this.setIdentity(XMLIdManager.getXMLId((Class)FluidPipe.class));
    }
    
    public FluidPipe(final String theName, 
            final CompartmentSBML theFromVolume, final CompartmentSBML theToVolume) {
        super(theName, theFromVolume, theToVolume);
        this.scaleFlowRateDefinitionType = ScaleFlowRateDefinitionType.PER_GRAM_TISSUE;
        this.flowRateMLPerMinPerScaleUnit = 0.0;
        this.setIdentity(XMLIdManager.getXMLId((Class)FluidPipe.class));
    }
    
    public FluidPipe copy() {
        final FluidPipe pipe = new FluidPipe();
        pipe.setIdentity(XMLIdManager.getXMLId((Class)FluidPipe.class));
        pipe.setName(this.getName());
        pipe.fromVolume = this.fromVolume;
        pipe.toVolume = this.toVolume;
        pipe.notes = this.notes;
        pipe.setRefVolumeForScaling(this.getRefVolumeForScaling());
        pipe.scaleFlowRateDefinitionType = this.scaleFlowRateDefinitionType;
        pipe.flowRateMLPerMinPerScaleUnit = this.flowRateMLPerMinPerScaleUnit;
        return pipe;
    }
    
    public boolean contains(final CompartmentSBML volume1, final CompartmentSBML volume2) {
        return volume1 != null && volume2 != null && volume1 == this.getFromVolume() && volume2 == this.getToVolume();
    }
    
    public ScaleFlowRateDefinitionType getScaleFlowRateDefinitionType() {
        return this.scaleFlowRateDefinitionType;
    }
    
    public void setScaleFlowRateDefinitionType(final ScaleFlowRateDefinitionType theScaleFlowRateDefinitionType) {
        this.scaleFlowRateDefinitionType = theScaleFlowRateDefinitionType;
    }
    
    public double getFlowRateMLPerMinPerScaleUnit() {
        return this.flowRateMLPerMinPerScaleUnit;
    }
    
    public void setFlowRateMLPerMinPerScaleUnit(final double theFlowRateMLPerMinPerScaleUnit) {
        this.flowRateMLPerMinPerScaleUnit = theFlowRateMLPerMinPerScaleUnit;
    }
    
    public double getFlowRateMLPerMin() throws Exception {
        double flowRateMLPerMin = 0.0;
        // FIXME
        return flowRateMLPerMin;
    }
    
    @Override
    public String toString() {
        return this.getName() + " : " + this.getFromVolume().getIdentity() + " => " + this.getToVolume().getIdentity();
    }
}
