// 
//  
// 

package datamodel.type;

public enum TimeUnitsDefinitionType 
{
    SECONDS("", ""), 
    MINUTES("", ""), 
    HOURS("", ""), 
    DAYS("", ""), 
    WEEKS("", ""), 
    YEARS("", ""), 
    UNDEFINED("", "");
    
    private final String displayName;
    private final String description;
    
    private TimeUnitsDefinitionType(final String displayName, final String description) {
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
    
    public static double converTimeFromMinutes(final double valueInMinutes, final TimeUnitsDefinitionType timeUnits) {
        if (timeUnits == TimeUnitsDefinitionType.SECONDS) {
            return valueInMinutes * 60.0;
        }
        if (timeUnits == TimeUnitsDefinitionType.MINUTES) {
            return valueInMinutes;
        }
        if (timeUnits == TimeUnitsDefinitionType.HOURS) {
            return valueInMinutes / 60.0;
        }
        if (timeUnits == TimeUnitsDefinitionType.DAYS) {
            return valueInMinutes / 60.0 / 24.0;
        }
        if (timeUnits == TimeUnitsDefinitionType.WEEKS) {
            return valueInMinutes / 60.0 / 24.0 / 7.0;
        }
        if (timeUnits == TimeUnitsDefinitionType.YEARS) {
            return valueInMinutes / 60.0 / 24.0 / 365.0;
        }
        return -1.0;
    }
}
