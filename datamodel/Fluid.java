// 
//  
// 

package datamodel;

public class Fluid 
{

    protected String name;

    public double pH;

    public double volumeFractionWater;

    public double volumeFractionProtein;

    public double volumeFractionCarbohydrate;

    public double volumeFractionLipid;

    public double volumeFractionDNA;

    public double volumeFractionRNA;

    public double volumeFractionMineral;
    
    
    public Fluid() {
        this.name = "";
        this.pH = 7.4;
        this.volumeFractionWater = 1.0;
        this.volumeFractionProtein = 0.0;
        this.volumeFractionCarbohydrate = 0.0;
        this.volumeFractionLipid = 0.0;
        this.volumeFractionDNA = 0.0;
        this.volumeFractionRNA = 0.0;
        this.volumeFractionMineral = 0.0;
    }
    
    public Fluid copy(final Object theParent) {
        final Fluid fluid = new Fluid();
        fluid.name = this.name;
        fluid.pH = this.pH;
        fluid.volumeFractionWater = this.volumeFractionWater;
        fluid.volumeFractionProtein = this.volumeFractionProtein;
        fluid.volumeFractionCarbohydrate = this.volumeFractionCarbohydrate;
        fluid.volumeFractionLipid = this.volumeFractionLipid;
        fluid.volumeFractionDNA = this.volumeFractionDNA;
        fluid.volumeFractionRNA = this.volumeFractionRNA;
        fluid.volumeFractionMineral = this.volumeFractionMineral;
        return fluid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String theName) {
        this.name = theName;
    }
    
    public double getPH() {
        return this.pH;
    }
    
    public void setPH(final double theValue) {
        this.pH = theValue;
    }
    
    public double getVolumeFractionWater() {
        return this.volumeFractionWater;
    }
    
    public void setVolumeFractionWater(final double theValue) {
        this.volumeFractionWater = theValue;
    }
    
    public void setAndScaleVolumeFractionWater(final double theValue) {
        if (this.volumeFractionWater < 1.0) {
            final double scaleFactor = (1.0 - theValue) / (1.0 - this.volumeFractionWater);
            this.volumeFractionProtein *= scaleFactor;
            this.volumeFractionCarbohydrate *= scaleFactor;
            this.volumeFractionLipid *= scaleFactor;
            this.volumeFractionDNA *= scaleFactor;
            this.volumeFractionRNA *= scaleFactor;
            this.volumeFractionMineral *= scaleFactor;
            this.volumeFractionWater = theValue;
        }
        else {
            this.volumeFractionProtein = 1.0 - theValue;
            this.volumeFractionWater = theValue;
        }
    }
    
    public double getVolumeFractionProtein() {
        return this.volumeFractionProtein;
    }
    
    public void setVolumeFractionProtein(final double theValue) {
        this.volumeFractionProtein = theValue;
    }
    
    public double getVolumeFractionCarbohydrate() {
        return this.volumeFractionCarbohydrate;
    }
    
    public void setVolumeFractionCarbohydrate(final double theValue) {
        this.volumeFractionCarbohydrate = theValue;
    }
    
    public double getVolumeFractionLipid() {
        return this.volumeFractionLipid;
    }
    
    public void setVolumeFractionLipid(final double theValue) {
        this.volumeFractionLipid = theValue;
    }
    
    public double getVolumeFractionDNA() {
        return this.volumeFractionDNA;
    }
    
    public void setVolumeFractionDNA(final double theValue) {
        this.volumeFractionDNA = theValue;
    }
    
    public double getVolumeFractionRNA() {
        return this.volumeFractionRNA;
    }
    
    public void setVolumeFractionRNA(final double theValue) {
        this.volumeFractionRNA = theValue;
    }
    
    public double getVolumeFractionMineral() {
        return this.volumeFractionMineral;
    }
    
    public void setVolumeFractionMineral(final double theValue) {
        this.volumeFractionMineral = theValue;
    }
    
    public double getVolumeFractionAir() {
        double volumeFractionAir = 1.0 - this.getFluidCompositionVolumeFractionSum();
        if (volumeFractionAir < 0.0) {
            volumeFractionAir = 0.0;
        }
        return volumeFractionAir;
    }
    
    public double getAqueousVolumeFraction() {
        double aqueousVolumeFraction = 0.0;
        aqueousVolumeFraction += this.volumeFractionWater;
        aqueousVolumeFraction += this.volumeFractionCarbohydrate;
        aqueousVolumeFraction += this.volumeFractionDNA;
        aqueousVolumeFraction += this.volumeFractionRNA;
        return aqueousVolumeFraction;
    }
    
    public double getOrganicVolumeFraction() {
        double organicVolumeFraction = 0.0;
        organicVolumeFraction += this.volumeFractionProtein;
        organicVolumeFraction += this.volumeFractionLipid;
        return organicVolumeFraction;
    }
    
    public double getFluidCompositionVolumeFractionSum() {
        double sumVolumeFractions = 0.0;
        sumVolumeFractions += this.volumeFractionWater;
        sumVolumeFractions += this.volumeFractionProtein;
        sumVolumeFractions += this.volumeFractionCarbohydrate;
        sumVolumeFractions += this.volumeFractionLipid;
        sumVolumeFractions += this.volumeFractionDNA;
        sumVolumeFractions += this.volumeFractionRNA;
        sumVolumeFractions += this.volumeFractionMineral;
        return sumVolumeFractions;
    }
}
