// 
//  
// 

package datamodel.perturbation;

import datamodel.MeasuredConcentrationSample;
import datamodel.perturbation.Molecule;
import datamodel.type.SamplingUnitsDefinitionType;
import java.util.ArrayList;
import java.util.Collection;

public class MeasurementSet 
{

    protected Molecule molecule;

    protected SamplingLocation samplingLocation;
    protected double molesMoleculeInjected;

    protected boolean moleculeFreeStateOnly;
    protected ArrayList<MeasuredConcentrationSample> concentrations;
    protected String notes;
    
    
    public MeasurementSet() {
        this.molecule = null;
        this.molesMoleculeInjected = 0.0;
        this.moleculeFreeStateOnly = false;
        this.concentrations = new ArrayList<>();
        this.notes = "";
    }
    
    public ArrayList<MeasuredConcentrationSample> getConcentrations() {
        return this.concentrations;
    }
    
    public void setConcentrations(final ArrayList<MeasuredConcentrationSample> concentrations) {
        this.concentrations = concentrations;
    }
    
    public void addMeasuredConcentrationSample(final MeasuredConcentrationSample measuredConcentrationSample) {
        this.getConcentrations().add(measuredConcentrationSample);
    }
    
    public int getNumSamplingTimes() {
        int numSamplingTimes = 0;
        if (this.concentrations != null) {
            numSamplingTimes = this.concentrations.size();
        }
        return numSamplingTimes;
    }
    
    public void setNumSamplingTimes(final int numSamplingTimes) {
        if (this.concentrations != null) {
            if (numSamplingTimes < this.concentrations.size()) {
                final int numToDelete = this.concentrations.size() - numSamplingTimes;
                final ArrayList<MeasuredConcentrationSample> measuredConcentrationSamples = 
                        (ArrayList<MeasuredConcentrationSample>)this.getConcentrations();
                for (int i = 0; i < numToDelete; ++i) {
                    measuredConcentrationSamples.remove(measuredConcentrationSamples.size() - 1);
                }
            }
            else if (numSamplingTimes > this.concentrations.size()) {
                for (int numToAdd = numSamplingTimes - this.concentrations.size(), j = 0; j < numToAdd; ++j) {
                    this.addMeasuredConcentrationSample(new MeasuredConcentrationSample());
                }
            }
        }
    }
    
    public ArrayList<SamplingTime> getSamplingTimes() {
        final ArrayList<SamplingTime> samplingTimes = new ArrayList<>();
        for (final MeasuredConcentrationSample measuredConcentrationSample : this.concentrations) {
            final SamplingTime samplingTime = measuredConcentrationSample.getSamplingTime();
            if (samplingTime != null) {
                samplingTimes.add(samplingTime);
            }
        }
        return samplingTimes;
    }
    
    public SamplingTime[] getSamplingTimesSortedByTime() {
        final ArrayList<SamplingTime> samplingTimes = (ArrayList<SamplingTime>)this.getSamplingTimes();
        if (samplingTimes == null) {
            return null;
        }
        return SamplingTime.sortSamplingTimes(samplingTimes);
    }
    
    public Molecule getMolecule() {
        return this.molecule;
    }
    
    public void setMolecule(final Molecule molecule) {
        this.molecule = molecule;
    }
    
    public boolean isMoleculeFreeStateOnly() {
        return this.moleculeFreeStateOnly;
    }
    
    public void setMoleculeFreeStateOnly(final boolean moleculeFreeStateOnly) {
        this.moleculeFreeStateOnly = moleculeFreeStateOnly;
    }
    
    public double getMolesMoleculeInjected() {
        return this.molesMoleculeInjected;
    }
    
    public void setMolesMoleculeInjected(final double molesMoleculeInjected) {
        this.molesMoleculeInjected = molesMoleculeInjected;
    }
    
    public SamplingLocation getSamplingLocation() {
        return this.samplingLocation;
    }
    
    public void setSamplingLocation(final SamplingLocation property1) {
        this.samplingLocation = property1;
    }
    
    @Override
    public String toString() {
        if (this.samplingLocation != null) {
            return this.samplingLocation.getName();
        }
        return super.toString();
    }
    
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(final String notes) {
        this.notes = notes;
    }
    
    public void addNote(final String note) {
        if (this.getNotes().length() > 0) {
            this.setNotes(this.getNotes() + "\r\n" + note);
        }
        else {
            this.setNotes(note);
        }
    }
    
    public MeasuredConcentrationSample getConcentration(final SamplingTime samplingTime) {
        for (final MeasuredConcentrationSample measuredConcentrationSample : this.getConcentrations()) {
            if (measuredConcentrationSample.getSamplingTime() == samplingTime) {
                return measuredConcentrationSample;
            }
        }
        return null;
    }
    
    public void deleteConcentrationSampleTimeReferences(final SamplingTime referenceTime) {
        final ArrayList<MeasuredConcentrationSample> deletionList = new ArrayList<>();
        for (final MeasuredConcentrationSample concentrationSample : this.concentrations) {
            if (concentrationSample.getSamplingTime() == referenceTime) {
                deletionList.add(concentrationSample);
            }
        }
        if (deletionList.size() > 0) {
            this.concentrations.removeAll(deletionList);
        }
    }
    
    public double getMolesPerML(final SamplingTime samplingTime) throws Exception {
        final MeasuredConcentrationSample measuredConcentrationSample = this.getConcentration(samplingTime);
        if (measuredConcentrationSample == null) {
            throw new Exception("Measurement not found.");
        }
        return measuredConcentrationSample.getConcMolesPerML();
    }
    
    public double getMolarConcentration(final SamplingTime samplingTime) throws Exception {
        return 1000.0 * this.getMolesPerML(samplingTime);
    }
    
    public double getMilligramsPerML(final SamplingTime samplingTime) throws Exception {
        return 1000.0 * this.molecule.getMolecularWeightGramsPerMole() * this.getMolesPerML(samplingTime);
    }
    
    public double getMicrogramsPerML(final SamplingTime samplingTime) throws Exception {
        return 1000000.0 * this.molecule.getMolecularWeightGramsPerMole() * this.getMolesPerML(samplingTime);
    }
    
    public double getStDevMolesPerML(final SamplingTime samplingTime) throws Exception {
        final MeasuredConcentrationSample measuredConcentrationSample = this.getConcentration(samplingTime);
        if (measuredConcentrationSample == null) {
            throw new Exception("Measurement not found.");
        }
        return measuredConcentrationSample.getStdDevConcMolesPerML();
    }
    
    public double getStDevMolarConcentration(final SamplingTime samplingTime) throws Exception {
        return 1000.0 * this.getStDevMolesPerML(samplingTime);
    }
    
    public double getStDevMilligramsPerML(final SamplingTime samplingTime) throws Exception {
        return 1000.0 * this.molecule.getMolecularWeightGramsPerMole() * this.getStDevMolesPerML(samplingTime);
    }
    
    public double getStDevMicrogramsPerML(final SamplingTime samplingTime) throws Exception {
        return 1000000.0 * this.molecule.getMolecularWeightGramsPerMole() * this.getStDevMolesPerML(samplingTime);
    }
    
    public double getMeanValue(final SamplingTime samplingTime, final SamplingUnitsDefinitionType samplingUnitsDefinitionType) throws Exception {
        if (samplingUnitsDefinitionType == SamplingUnitsDefinitionType.MICROGRAMS_PER_ML) {
            return this.getMicrogramsPerML(samplingTime);
        }
        if (samplingUnitsDefinitionType == SamplingUnitsDefinitionType.MILLIGRAM_PER_ML) {
            return this.getMilligramsPerML(samplingTime);
        }
        if (samplingUnitsDefinitionType == SamplingUnitsDefinitionType.MOLAR) {
            return this.getMolarConcentration(samplingTime);
        }
        if (samplingUnitsDefinitionType == SamplingUnitsDefinitionType.MOLES_PER_ML) {
            return this.getMolesPerML(samplingTime);
        }
        throw new Exception("Unknown Sampling Units Definition Type");
    }
    
    public double getStDevValue(final SamplingTime samplingTime, final SamplingUnitsDefinitionType samplingUnitsDefinitionType) throws Exception {
        if (samplingUnitsDefinitionType == SamplingUnitsDefinitionType.MICROGRAMS_PER_ML) {
            return this.getStDevMicrogramsPerML(samplingTime);
        }
        if (samplingUnitsDefinitionType == SamplingUnitsDefinitionType.MILLIGRAM_PER_ML) {
            return this.getStDevMilligramsPerML(samplingTime);
        }
        if (samplingUnitsDefinitionType == SamplingUnitsDefinitionType.MOLAR) {
            return this.getStDevMolarConcentration(samplingTime);
        }
        throw new Exception("Unknown Sampling Units Definition Type");
    }
    
    public void setMeanValue(final SamplingTime samplingTime, final double meanValue, final SamplingUnitsDefinitionType samplingUnitsDefinitionType) throws Exception {
        double concentrationMolesPerML = -1.0;
        if (SamplingUnitsDefinitionType.PERCENT_INJECTED_DOSE_PER_ML == samplingUnitsDefinitionType) {
            concentrationMolesPerML = meanValue / 100.0 * this.molesMoleculeInjected;
        }
        else if (SamplingUnitsDefinitionType.MOLES_PER_ML == samplingUnitsDefinitionType) {
            concentrationMolesPerML = meanValue;
        }
        else if (SamplingUnitsDefinitionType.MICROGRAMS_PER_ML == samplingUnitsDefinitionType) {
            concentrationMolesPerML = meanValue / 1000000.0 / this.molecule.getMolecularWeightGramsPerMole();
        }
        else if (SamplingUnitsDefinitionType.MILLIGRAM_PER_ML == samplingUnitsDefinitionType) {
            concentrationMolesPerML = meanValue / 1000.0 / this.molecule.getMolecularWeightGramsPerMole();
        }
        else if (SamplingUnitsDefinitionType.MOLAR == samplingUnitsDefinitionType) {
            concentrationMolesPerML = meanValue / 1000.0;
        }
        else if (SamplingUnitsDefinitionType.NUMBER_PER_ML == samplingUnitsDefinitionType) {
            concentrationMolesPerML = meanValue / 6.0221415E23;
        }
        if (samplingTime != null) {
            final MeasuredConcentrationSample measuredConcentrationSample = this.getConcentration(samplingTime);
            if (measuredConcentrationSample == null) {
                throw new Exception("Measurement not found.");
            }
            measuredConcentrationSample.setConcMolesPerML(concentrationMolesPerML);
        }
    }
    
    public void setStDevValue(final SamplingTime samplingTime, final double stdevValue, final SamplingUnitsDefinitionType samplingUnitsDefinitionType) throws Exception {
        double stdDevConcentrationMolesPerML = -1.0;
        if (SamplingUnitsDefinitionType.PERCENT_INJECTED_DOSE_PER_ML == samplingUnitsDefinitionType) {
            stdDevConcentrationMolesPerML = stdevValue / 100.0 * this.molesMoleculeInjected;
        }
        else if (SamplingUnitsDefinitionType.MOLES_PER_ML == samplingUnitsDefinitionType) {
            stdDevConcentrationMolesPerML = stdevValue;
        }
        else if (SamplingUnitsDefinitionType.MICROGRAMS_PER_ML == samplingUnitsDefinitionType) {
            stdDevConcentrationMolesPerML = stdevValue / 1000000.0 / this.molecule.getMolecularWeightGramsPerMole();
        }
        else if (SamplingUnitsDefinitionType.MILLIGRAM_PER_ML == samplingUnitsDefinitionType) {
            stdDevConcentrationMolesPerML = stdevValue / 1000.0 / this.molecule.getMolecularWeightGramsPerMole();
        }
        else if (SamplingUnitsDefinitionType.MOLAR == samplingUnitsDefinitionType) {
            stdDevConcentrationMolesPerML = stdevValue / 1000.0;
        }
        else if (SamplingUnitsDefinitionType.NUMBER_PER_ML == samplingUnitsDefinitionType) {
            stdDevConcentrationMolesPerML = stdevValue / 6.0221415E23;
        }
        final MeasuredConcentrationSample measuredConcentrationSample = this.getConcentration(samplingTime);
        if (measuredConcentrationSample == null) {
            throw new Exception("Measurement not found.");
        }
        measuredConcentrationSample.setStdDevConcMolesPerML(stdDevConcentrationMolesPerML);
    }
}
