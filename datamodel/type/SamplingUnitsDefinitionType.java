// 
//  
// 

package datamodel.type;

public enum SamplingUnitsDefinitionType 
{
    MICROGRAMS_PER_ML("", ""), 
    MILLIGRAM_PER_ML("", ""), 
    MOLAR("", ""), 
    MOLES("", ""), 
    MOLES_PER_GRAM("", ""), 
    MOLES_PER_ML("", ""), 
    NUMBER("", ""), 
    NUMBER_PER_GRAM("", ""), 
    NUMBER_PER_ML("", ""), 
    PERCENT_INJECTED_DOSE("", ""), 
    PERCENT_INJECTED_DOSE_PER_GRAM("", ""), 
    PERCENT_INJECTED_DOSE_PER_ML("", ""), 
    UNDEFINED("", "");
    
    private final String displayName;
    private final String description;
    
    private SamplingUnitsDefinitionType(final String displayName, final String description) {
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
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
    
    public String getDescription() {
        if (this.description != null && !this.description.equals("")) {
            return this.description;
        }
        return this.toString();
    }
}
