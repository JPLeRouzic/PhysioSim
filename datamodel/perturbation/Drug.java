// 
//  
// 

package datamodel.perturbation;

import utility.XMLIdManager;
import java.util.ArrayList;
import java.util.Date;

public final class Drug 
{
    private ArrayList<Molecule> molecules;
    private Date creationDate;
    private String notes;

    private String name;
    
    private String identity;
   
    private String author;
   
    private boolean saved;
    
    
    
    public Drug() {
        this.molecules = new ArrayList<>();
        this.creationDate = new Date();
        this.notes = "";
        this.name = "agent_1";
        this.author = "";
        this.saved = true;
        this.setIdentity(XMLIdManager.getXMLId((Class)Drug.class));
    }
    
    public String getIdentity() {
        return this.identity;
    }
    
    public void setIdentity(final String xmlId) {
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
    
    public void addMolecule(final Molecule molecule) {
        final String theOrigName = molecule.getName();
        int indx = 1;
        while (this.getMolecule(molecule.getName()) != null) {
            molecule.setName(theOrigName + "_" + indx++);
        }
        this.molecules.add(molecule);
    }
    
    public Molecule getMolecule(final int moleculeIndx) {
        if (this.getNumMolecules() > moleculeIndx) {
            return (Molecule) (this.molecules).get(moleculeIndx);
        }
        return null;
    }
    
    public Molecule getMolecule(final String theName) {
        for (final Molecule molecule : this.molecules) {
            if (molecule.getName().equalsIgnoreCase(theName)) {
                return molecule;
            }
        }
        return null;
    }
    
    public boolean containsMolecule(final Molecule molecule) {
        return this.molecules != null && this.molecules.contains(molecule);
    }
    
    public void setupXmlId() {
        this.setIdentity(XMLIdManager.getXMLId((Class)Drug.class));
        for (final Molecule molecule : this.molecules) {
            molecule.setupXmlId();
        }
    }
    
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(final String notes) {
        this.notes = notes;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(final String author) {
        this.author = author;
    }
    
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public boolean isSaved() {
        return this.saved;
    }
    
    public void setSaved(final boolean saved) {
        this.saved = saved;
    }
}
