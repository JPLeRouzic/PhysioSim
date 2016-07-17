// 
//  
// 

package datamodel.perturbation;

import java.util.ArrayList;
import utility.XMLIdManager;

public class Molecule 
{
    
    Double[] logDParm = null ; 

    protected String notes;
    
    protected String smilesString;
    
    protected String name;
    
    protected double molecularWeightGramsPerMole;
    
    protected double densityGramsPerCM3;
    
    protected boolean isRadioactive;
    
    protected double plasmaProteinBindingPercentage;
    
    protected double clearanceMLperMinPerMgMicrosomalProtein;
    
    protected double membranePermeabilityMult;
    
    protected double logP;
    
    protected double logD;

    protected double[] pH;
    
    double basepKas[] ;
    int basepKaslength ;
    int acidpKaslength ;
   
    protected String identity;
    public static final double MG_MICROSOMAL_PROTEIN_PER_GRAM_LIVER = 45.0;
    public static final double MILLION_HEPATOCYTES_PER_GRAM_LIVER = 144.0;
    public static final double MG_MICROSOMAL_PROTEIN_PER_MILLION_HEPATOCYTES = 0.3125;
    public static final double INTERSTITIAL_INTRACELLULAR_DIFFUSION_CORRECTION_FACTOR = 0.2;
    public static final double MEMBRANE_DIFFUSION_CORRECTION_FACTOR = 4.2E-7;
    public static final double MEMBRANE_THICKNESS_MICRONS = 0.01;
    public static final double DEFAULT_MOLECULAR_WEIGHT_GRAMS_PER_MOLE = 500.0;
    public static final double DEFAULT_DENSITY_GRAMS_PER_CM3 = 1.0;
    public static final double DEFAULT_INCUBATION_VOLUME_ML_PER_MG_MICROSOMAL_PROTEIN = 2.9;
    private double[] acidpKas;
    
    
    public Molecule() {
        notes = "";
        smilesString = "";
        name = "molecule_1";
        molecularWeightGramsPerMole = 500.0;
        densityGramsPerCM3 = 1.0;
        isRadioactive = false;
        plasmaProteinBindingPercentage = -1.0;
        clearanceMLperMinPerMgMicrosomalProtein = -1.0;
        membranePermeabilityMult = 1.0;
        setIdentity(XMLIdManager.getXMLId((Class)Molecule.class));
    }
    
    public Molecule copy() {
        final Molecule molecule = new Molecule();
        molecule.name = name;
        molecule.isRadioactive = isRadioactive;
        molecule.densityGramsPerCM3 = densityGramsPerCM3;
        molecule.molecularWeightGramsPerMole = molecularWeightGramsPerMole;
        molecule.smilesString = smilesString;
        molecule.notes = notes;
        return molecule;
    }
    
    public String getIdentity() {
        return identity;
    }
    
    public final void setIdentity(final String xmlId) {
        identity = xmlId;
    }
    
    public void setupXmlId() {
        setIdentity(XMLIdManager.getXMLId((Class)Molecule.class));
    }
    
    public String getName() {
        return name;
    }
    
//    @Override
    public String toString() {
        return getName();
    }
    
    public void setName(final String namu) {
        name = namu;
    }
    
    public double getPH(int i) {
        return pH[i];
    }
    
    public void setPH(final double phash, int i) {
        pH[i] = phash;
    }
    
    public double getDensityGramsPerCM3() {
        return densityGramsPerCM3;
    }
    
    public void setDensityGramsPerCM3(final double dense) {
        densityGramsPerCM3 = dense;
    }
    
    public String getSmilesString() {
        return smilesString;
    }
    
    public void setSmilesString(final String smiles) {
        smilesString = smiles;
    }
    
    public boolean isRadioactive() {
        return isRadioactive;
    }
    
    public boolean getIsRadioactive() {
        return isRadioactive;
    }
    
    public void setIsRadioactive(final boolean rad) {
        isRadioactive = rad;
    }
    
    public double getMolecularWeightGramsPerMole() {
        return molecularWeightGramsPerMole;
    }
    
    public double getMolecularWeightDaltons() {
        return molecularWeightGramsPerMole;
    }
    
    public void setMolecularWeightDaltons(final double molecularWeight) {
        molecularWeightGramsPerMole = molecularWeight;
    }
    
    public void setMolecularWeightGramsPerMole(final double mol) {
        molecularWeightGramsPerMole = mol;
    }
    
    public double getClearanceMLperMinPerMgMicrosomalProtein() {
        return clearanceMLperMinPerMgMicrosomalProtein;
    }
    
    public void setClearanceMLperMinPerMgMicrosomalProtein(final double clear) {
        clearanceMLperMinPerMgMicrosomalProtein = clear;
    }
    
    public static double calcClearanceMLperMinPerMgMicrosomalProtein(final double halfLifeMinutes, final double incubationMLPerMGofMicrosomalProtein) {
        return 0.693 / halfLifeMinutes * incubationMLPerMGofMicrosomalProtein;
    }
    
    public static double calcClearanceHalfLifeMinutes(final double clearanceMLperMinPerMgMicrosomalProtein, final double incubationMLPerMGofMicrosomalProtein) {
        return 0.693 / clearanceMLperMinPerMgMicrosomalProtein * incubationMLPerMGofMicrosomalProtein;
    }
    
    public double getClearanceMLperMinPerGramLiver() {
        return getClearanceMLperMinPerMgMicrosomalProtein() * 45.0;
    }
    
    public void setClearanceMLperMinPerGramLiver(final double clearanceMLperMinPerGramLiver) {
        setClearanceMLperMinPerMgMicrosomalProtein(clearanceMLperMinPerGramLiver / 45.0);
    }
    
    public double getPlasmaProteinBindingPercentage() {
        return plasmaProteinBindingPercentage;
    }
    
    public void setPlasmaProteinBindingPercentage(final double plasma) {
        plasmaProteinBindingPercentage = plasma;
    }
    
    public double getMembranePermeabilityMult() {
        return membranePermeabilityMult;
    }
    
    public void setMembranePermeabilityMult(final double mpm) {
        membranePermeabilityMult = mpm;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(final String notees) {
        notes = notees;
    }
    
    public double getDistributionCoefficient(final double pH) {
        final double logD = getLogD(pH);
        return Math.pow(10.0, logD);
    }
    
    public double getLogD(final double pH) {
        if (logDParm.length > 0) {
            for (int i = 0; i < logDParm.length - 1; ++i) {
                if ((pH >= getPH(i) && pH <= getPH(i + 1)) || (pH <= getPH(i) && pH >= getPH(i + 1))) {
                    double slope = 0.0;
                    final double denom = getPH(i + 1) - getPH(i);
                    if (denom != 0.0) {
                        slope = (getLogD(i + 1) - getLogD(i)) / denom;
                    }
                    logD = getLogD(i) + slope * (pH - getPH(i));
                    break;
                }
            }
        }
        else if (acidpKaslength > 0 || basepKaslength > 0) {
            double qkph = 0.0;
            for (int j = 0; j < basepKaslength; ++j) {
                qkph += basepKas[j] - pH;
            }
            for (int j = 0; j < acidpKaslength; ++j) {
                qkph += pH - acidpKas[j];
            }
            logD = logP - calcLog10(1.0 + Math.pow(10.0, qkph));
        }
        return logD;
    }
    
    public static double calcLog10(final double value) {
        return Math.log(value) / Math.log(10.0);
    }
    
    public double getNetCharge(final double pH) {
        final double netCharge = basepKaslength - acidpKaslength;
        return netCharge;
    }
    
    public boolean canBindToPlasmaProteins() {
        return plasmaProteinBindingPercentage > -1.0;
    }
    
    public boolean isMetabolizedByLiver() {
        return clearanceMLperMinPerMgMicrosomalProtein > -1.0;
    }
    
    public boolean isIonizable() {
        if (basepKas != null && basepKaslength > 0) {
            return true;
        }
        if (acidpKas != null && acidpKaslength > 0) {
            return true;
        }
        return false;
    }
    
    public static double calcDiffusionOnlyPermeabilityCMperMin(final double distanceTraveledInMicrons, final double waterDiffusionCoefficientCM2perSec, final double unstirredLayerCorrectionFactor) {
        final double unstirredLayerPermeabilityCMperMin = 60.0 * waterDiffusionCoefficientCM2perSec * unstirredLayerCorrectionFactor / (distanceTraveledInMicrons * 1.0E-4);
        return unstirredLayerPermeabilityCMperMin;
    }
    
    public static double calcMembraneOnlyPermeabilityCMperMin(final double distanceTraveledInMicrons, final double waterDiffusionCoefficientCM2perSec, final double distributionCoefficient, final double membraneLayerCorrectionFactor) {
        final double membranePermeabilityCMperMin = 60.0 * waterDiffusionCoefficientCM2perSec * membraneLayerCorrectionFactor * distributionCoefficient / (distanceTraveledInMicrons * 1.0E-4);
        return membranePermeabilityCMperMin;
    }
    
    public static double calcMembraneEffectivePermeabilityCMperMin(final double unstirredLayerDistanceMicrons, final double membraneThicknessMicrons, final double waterDiffusionCoefficientCM2perSec, final double distributionCoefficient, final double unstirredLayerCorrectionFactor, final double membraneLayerCorrectionFactor) {
        if (distributionCoefficient > 0.0) {
            final double unstirredLayerPermeabilityCMperMin = calcDiffusionOnlyPermeabilityCMperMin(unstirredLayerDistanceMicrons, waterDiffusionCoefficientCM2perSec, unstirredLayerCorrectionFactor);
            final double membranePermeabilityCMperMin = calcMembraneOnlyPermeabilityCMperMin(membraneThicknessMicrons, waterDiffusionCoefficientCM2perSec, distributionCoefficient, membraneLayerCorrectionFactor);
            final double effectivePermeabilityCMperMin = 1.0 / (1.0 / unstirredLayerPermeabilityCMperMin + 1.0 / membranePermeabilityCMperMin);
            return effectivePermeabilityCMperMin;
        }
        return 0.0;
    }
    
    public double getMembranePermeabilityCMperMin(final double totalDistanceMicrons, final double membraneThicknessMicrons, final double tempDegreesC, final double pH) {
        return membranePermeabilityMult * calcMembraneEffectivePermeabilityCMperMin(totalDistanceMicrons, membraneThicknessMicrons, getDiffusionCoefficient(tempDegreesC), getDistributionCoefficient(pH), 0.2, 4.2E-7);
    }
    
    public double calcDiffusionOnlyEffectivePermeabilityCMperMin(final double totalDistanceMicrons, final double tempDegreesC) {
        return calcDiffusionOnlyPermeabilityCMperMin(totalDistanceMicrons, getDiffusionCoefficient(tempDegreesC), 0.2);
    }
    
    public static double calcHydrodynamicRadiusInMicrons(final double molecularWeightGramsPerMole, final double densityGramsPerCM3) {
        return 10000.0 * Math.pow(0.238732414637843 * molecularWeightGramsPerMole / densityGramsPerCM3 / 6.02E23, 0.3333333);
    }
    
    public static double calcMolecularWeightFromHydrodynamicRadiusInMicrons(final double radiusInMicrons, final double densityGramsPerCM3) {
        return Math.pow(radiusInMicrons / 10000.0, 3.0) / (0.238732414637843 / densityGramsPerCM3 / 6.02E23);
    }
    
    public double getHydrodynamicRadiusInMicrons() {
        return calcHydrodynamicRadiusInMicrons(molecularWeightGramsPerMole, densityGramsPerCM3);
    }
    
    public void setHydrodynamicRadiusInMicrons(final double radiusInMicrons) {
        molecularWeightGramsPerMole = calcMolecularWeightFromHydrodynamicRadiusInMicrons(radiusInMicrons, densityGramsPerCM3);
    }
    
    public static double calcDiffusionCoefficient(final double molecularWeight, final double density, final double tempCelcius) {
        final double waterViscosity = (1.63355 - 0.039 * tempCelcius + 3.73E-4 * tempCelcius * tempCelcius) * 1.0E-6;
        final double diffusionCoefficient = 1.38E-20 * (273.15 + tempCelcius) / 6.0 / 3.141592653589793 / waterViscosity / (calcHydrodynamicRadiusInMicrons(molecularWeight, density) * 1.0E-4);
        return diffusionCoefficient;
    }
    
    public double getDiffusionCoefficient(final double tempCelcius) {
        return calcDiffusionCoefficient(molecularWeightGramsPerMole, densityGramsPerCM3, tempCelcius);
    }
    
    public boolean isModelMatch(final Molecule anotherMolecule) {
        return anotherMolecule != null && (identity.equals(anotherMolecule.getIdentity()) || smilesString.equalsIgnoreCase(anotherMolecule.getSmilesString()));
    }
}
