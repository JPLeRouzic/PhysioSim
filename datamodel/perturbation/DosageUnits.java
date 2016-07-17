// 
//  
// 

package datamodel.perturbation;

import java.util.Locale;

public enum DosageUnits 
{
    MOLES("", ""), 
    GRAMS("", ""), 
    MILLIGRAMS("", ""), 
    MICROGRAMS("", ""), 
    MOLES_PER_KG_BODYMASS("", ""), 
    MILLIGRAMS_PER_KILOGRAM_BODYMASS("", ""), 
    MICROGRAMS_PER_KILOGRAM_BODYMASS("", "");
    
    private final String displayName;
    private final String description;
    
    private DosageUnits(final String displayName, final String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getName() {
        return super.toString();
    }
    
    @Override
    public String toString() {
        if (this.displayName != null && !this.displayName.equals("")) {
            return this.displayName;
        }
        String s = super.toString();
        s = s.replace('_', ' ');
        return s.toLowerCase(Locale.ENGLISH);
    }
    
    public String getDescription() {
        if (this.description != null && !this.description.equals("")) {
            return this.description;
        }
        return this.toString();
    }
}
