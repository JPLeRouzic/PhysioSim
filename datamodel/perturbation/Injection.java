// 
//  
// 

package datamodel.perturbation;

import datamodel.HostModel;
import datamodel.Connection;
import utility.XMLIdManager;
import java.util.ArrayList;
import datamodel.CompartmentSBML;
import datamodel.HumanBody;
import datamodel.StandardHuman;

public class Injection 
{
    public static final double MINIMUM_INJECTION_TOTAL_TIME_MINUTES = 0.01;
    public static final double DEFAULT_INJECTION_TOTAL_TIME_MINUTES = 0.1;
    
    protected Connection location;
    
    protected ArrayList<Molecule> molecules;
    protected String notes;
    
    protected String name;
    
    protected double startTimeInMinutes;
    
    public double totalTimeInMinutes;
    
    protected double dosageMillicuries;
    
    protected double dosage;
    
    protected DosageUnits dosageUnits;

    protected String identity;
    
    public int organ;
    public int type;
    public double rate;
    public double rate2;
    public double tbeg;
    public double tend;
    public double tend2;
    public double hn;
    public double hn2;
    public double tbreak;
    public double tstop;
    public double duration;

    public Injection() {
        this.molecules = new ArrayList<>();
        this.notes = "";
        this.name = "injection_1";
        this.startTimeInMinutes = 0.0;
        this.totalTimeInMinutes = 0.5;
        this.dosageMillicuries = 0.0;
        this.dosage = 0.0;
        this.dosageUnits = DosageUnits.MILLIGRAMS_PER_KILOGRAM_BODYMASS;
        this.setIdentity(XMLIdManager.getXMLId((Class)Injection.class));
    }
    
    public Injection(final Molecule molecule, final CompartmentSBML theVolume, final double theStartTimeInMinutes, final double theTotalTimeInMinutes, final double theDosage, final DosageUnits theDosageUnits, final double theDosageMillicuries) {
        this.molecules = new ArrayList<>();
        this.notes = "";
        this.name = "injection_1";
        this.startTimeInMinutes = 0.0;
        this.totalTimeInMinutes = 0.5;
        this.dosageMillicuries = 0.0;
        this.dosage = 0.0;
        this.dosageUnits = DosageUnits.MILLIGRAMS_PER_KILOGRAM_BODYMASS;
        this.setIdentity(XMLIdManager.getXMLId((Class)Injection.class));
        this.location = theVolume;
        this.molecules.add(molecule);
        this.startTimeInMinutes = theStartTimeInMinutes;
        this.totalTimeInMinutes = theTotalTimeInMinutes;
        this.dosage = theDosage;
        this.dosageUnits = theDosageUnits;
        this.dosageMillicuries = theDosageMillicuries;
    }
    
    public String getIdentity() {
        return this.identity;
    }
    
    public final void setIdentity(final String xmlId) {
        this.identity = xmlId;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(final String notes) {
        this.notes = notes;
    }
    
    public double getDosage() {
        return this.dosage;
    }
    
    public void setDosage(final double dosage) {
        this.dosage = dosage;
    }
    
    public DosageUnits getDosageUnits() {
        return this.dosageUnits;
    }
    
    public void setDosageUnits(final DosageUnits dosageUnits) {
        this.dosageUnits = dosageUnits;
    }
    
    public double getDosageMillicuries() {
        return this.dosageMillicuries;
    }
    
    public void setDosageMillicuries(final double dosageMillicuries) {
        this.dosageMillicuries = dosageMillicuries;
    }
    
    public double getStartTimeInMinutes() {
        return this.startTimeInMinutes;
    }
    
    public void setStartTimeInMinutes(final double startTimeInMinutes) {
        this.startTimeInMinutes = startTimeInMinutes;
    }
    
    public double getTotalTimeInMinutes() {
        if (this.totalTimeInMinutes < 0.01) {
            this.totalTimeInMinutes = 0.01;
        }
        return this.totalTimeInMinutes;
    }
    
    public void setTotalTimeInMinutes(double totalTimeInMinutes) {
        if (totalTimeInMinutes < 0.01) {
            totalTimeInMinutes = 0.01;
        }
        this.totalTimeInMinutes = totalTimeInMinutes;
    }
    
    public ArrayList<Molecule> getMolecules() {
        return this.molecules;
    }
    
    public void setMolecules(final ArrayList<Molecule> molecules) {
        this.molecules = molecules;
    }
    
    public int getNumMolecules() {
        if (this.molecules != null) {
            return this.molecules.size();
        }
        return 0;
    }
    
    public Molecule getMolecule() {
        if (this.getNumMolecules() > 0) {
            return (Molecule)(this.molecules).get(0);
        }
        return null;
    }
    
    public void setMolecule(final Molecule molecule) {
        this.molecules.clear();
        this.molecules.add(molecule);
    }
    
    public Connection getLocation() {
        return this.location;
    }
    
    public void setLocation(final CompartmentSBML location) {
        this.location = location;
    }
    
    public boolean contains(final Molecule molecule) {
        return this.getNumMolecules() > 0 && this.molecules.contains(molecule);
    }
    
    public float getEndTimeInMinutes() {
        return (float) this.getStartTimeInMinutes() + (float) this.getTotalTimeInMinutes();
    }
    
    public double getBodyMassGrams() {
        if (this.getLocation() != null) {
            final HostModel PhysioSimModel = (HostModel)this.getLocation().getRoot();
            return PhysioSimModel.getTotalMassGrams();
        }
        return 0.0;
    }
    
    public double getMolecularWeightGramsPerMole() {
        double avgMolecularWeight = 0.0;
        int n = 0;
        for (final Molecule molecule : this.getMolecules()) {
            avgMolecularWeight += molecule.getMolecularWeightGramsPerMole();
            ++n;
        }
        if (n > 0) {
            avgMolecularWeight /= n;
        }
        else {
            avgMolecularWeight = 500.0;
        }
        return avgMolecularWeight;
    }
    
    public double getDosageMoles() {
        if (this.dosageUnits == DosageUnits.MOLES) {
            return this.getDosage();
        }
        if (this.dosageUnits == DosageUnits.GRAMS) {
            return this.getDosage() / this.getMolecularWeightGramsPerMole();
        }
        if (this.dosageUnits == DosageUnits.MILLIGRAMS) {
            return this.getDosage() / 1000.0 / this.getMolecularWeightGramsPerMole();
        }
        if (this.dosageUnits == DosageUnits.MICROGRAMS) {
            return this.getDosage() / 1000000.0 / this.getMolecularWeightGramsPerMole();
        }
        if (this.dosageUnits == DosageUnits.MOLES_PER_KG_BODYMASS) {
            return this.getDosage() * (this.getBodyMassGrams() / 1000.0);
        }
        if (this.dosageUnits == DosageUnits.MILLIGRAMS_PER_KILOGRAM_BODYMASS) {
            return this.getDosage() / 1000.0 / this.getMolecularWeightGramsPerMole() * (this.getBodyMassGrams() / 1000.0);
        }
        if (this.dosageUnits == DosageUnits.MICROGRAMS_PER_KILOGRAM_BODYMASS) {
            return this.getDosage() / 1000000.0 / this.getMolecularWeightGramsPerMole() * (this.getBodyMassGrams() / 1000.0);
        }
        return -1.0;
    }
    
    public double getDosageMolesPerMinute() {
        return this.getDosageMoles() / this.getTotalTimeInMinutes();
    }
    
    public boolean isModelMatch(final Injection anotherInjection) {
        return anotherInjection != null && this.identity.equals(anotherInjection.getIdentity());
    }
}
