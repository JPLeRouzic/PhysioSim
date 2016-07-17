// 
//  
// 

package datamodel.type;

import javax.swing.tree.TreePath;

public enum AdministrationMethodDefinitionType 
{
    INJECTION_INTRAVENOUS("", ""), 
    INJECTION_SUBCUTANEOUS("", ""), 
    INJECTION_INTRAMUSCULAR("", ""), 
    ORAL("", "");
    
    private final String displayName;
    private final String description;
    
    private AdministrationMethodDefinitionType(final String displayName, final String description) {
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
    
    public static AdministrationMethodDefinitionType getValueOf(final String value) {
        return valueOf(value.toUpperCase().replace(' ', '_'));
    }
}
