// 
//  A pore which leaks some fluid
// 

package datamodel;

public class SurfacePore 
{

    protected double poreAreaFraction;

    protected double poreDiameterMicrons;
    
    
    public SurfacePore() {
        this.poreAreaFraction = 0.0;
        this.poreDiameterMicrons = 0.0;
    }
    
    public SurfacePore(final double thePoreDiameterMicrons, final double thePoreAreaFraction) {
        this.poreAreaFraction = 0.0;
        this.poreDiameterMicrons = 0.0;
        this.poreAreaFraction = thePoreAreaFraction;
        this.poreDiameterMicrons = thePoreDiameterMicrons;
    }
    
    public SurfacePore copy() {
        final SurfacePore surfacePore = new SurfacePore();
        surfacePore.poreAreaFraction = this.poreAreaFraction;
        surfacePore.poreDiameterMicrons = this.poreDiameterMicrons;
        return surfacePore;
    }
    
    public double getPoreAreaFraction() {
        return this.poreAreaFraction;
    }
    
    public void setPoreAreaFraction(final double thePoreAreaFraction) {
        this.poreAreaFraction = thePoreAreaFraction;
    }
    
    public double getPoreDiameterMicrons() {
        return this.poreDiameterMicrons;
    }
    
    public void setPoreDiameterMicrons(final double thePoreDiameterMicrons) {
        this.poreDiameterMicrons = thePoreDiameterMicrons;
    }
    
    public double calcReflectionCoefficient(final boolean surfacePoresHaveNegativeCharges, final double moleculesHydrodynamicRadiusInMicrons, final double soluteCharge) {
        if (surfacePoresHaveNegativeCharges) {
            return calcGlomerularReflectionCoefficient(this.getPoreDiameterMicrons() / 2.0, moleculesHydrodynamicRadiusInMicrons, soluteCharge);
        }
        return calcTheoreticalReflectionCoefficient(this.getPoreDiameterMicrons() / 2.0, moleculesHydrodynamicRadiusInMicrons);
    }
    
    public static double calcTheoreticalReflectionCoefficient(final double poreRadius, final double soluteRadius) {
        if (poreRadius <= 0.0) {
            return 1.0;
        }
        final double lamda = soluteRadius / poreRadius;
        if (lamda >= 1.0) {
            return 1.0;
        }
        final double qty = (1.0 - lamda) * (1.0 - lamda);
        double reflectionCoef = 1.0 - qty * (2.0 - qty) * (1.0 - lamda / 3.0) / (1.0 - lamda / 3.0 + 2.0 * lamda * lamda / 3.0);
        if (reflectionCoef < 0.0) {
            reflectionCoef = 0.0;
        }
        else if (reflectionCoef > 1.0) {
            reflectionCoef = 1.0;
        }
        return reflectionCoef;
    }
    
    public static double calcGlomerularReflectionCoefficient(final double poreRadius, final double soluteRadius, final double soluteCharge) {
        if (poreRadius <= 0.0) {
            return 1.0;
        }
        final double lamda = soluteRadius / poreRadius;
        if (lamda >= 1.0) {
            return 1.0;
        }
        if (soluteCharge < -0.1) {
            // FIXME unused variables
            final double c1 = -16.0;
            final double c2 = 0.47;
            final double c3 = 0.0;
            return Math.pow(lamda, 0.0) / (1.0 + Math.exp(-16.0 * (lamda - 0.47)));
        }
        if (soluteCharge > 0.1) {
            // FIXME unused variables
            final double c1 = -12.0;
            final double c2 = 0.59;
            final double c3 = 2.0;
            return Math.pow(lamda, 2.0) / (1.0 + Math.exp(-12.0 * (lamda - 0.59)));
        }
            // FIXME unused variables
        final double c1 = -19.0;
        final double c2 = 0.59;
        final double c3 = 0.6;
        return Math.pow(lamda, c3) / (1.0 + Math.exp(-19.0 * (lamda - c2)));
    }
}
