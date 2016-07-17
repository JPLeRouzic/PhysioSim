// 
//  
// 

package datamodel.type;

public enum ScaleFlowRateDefinitionType 
{
    CONSTANT("", ""), 
    PER_GRAM_TISSUE("", ""), 
    PER_GRAM_CELLS("", ""), 
    PER_GRAM_SPACE("", ""), 
    PER_ML_TISSUE("", ""), 
    PER_ML_CELLS("", ""), 
    PER_ML_SPACE("", ""), 
    PER_CELL("", ""), 
    PER_SPACE_UNIT("", ""), 
    PER_REFERENCE_SPACE_FLOW("", ""), 
    UNDEFINED("", "");
    
    private String displayName;
    private String description;
    
    ScaleFlowRateDefinitionType(final String displayName, final String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getName() {
        return super.toString();
    }
    
    public void setName(String namu) {
        displayName = namu ;
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
