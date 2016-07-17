// 
//  
// 

package datamodel.type;

public enum SurfaceCompositionType 
{
    ORGANIC("", ""), 
    SOLID("", ""), 
    AQUEOUS("", ""), 
    UNDEFINED("", "");

    private final String displayName;
    private final String description;
    
    private SurfaceCompositionType(final String displayName, final String description) {
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
