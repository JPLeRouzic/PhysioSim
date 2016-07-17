// 
//  
// 

package utility;

public class XMLIdManager
{
    private static long xmlIdCounter;
    
    public static long incrementXMLIdDispenser() {
        return ++XMLIdManager.xmlIdCounter;
    }
    
    public static void updateXMLIdDispenser(final long idCount) {
        if (idCount >= XMLIdManager.xmlIdCounter) {
            XMLIdManager.xmlIdCounter = idCount + 1L;
        }
    }
    
    public static void updateXMLIdDispenser(final String xmlId) {
        try {
            final String[] splitString = xmlId.split("\\D");
            if (splitString.length > 0) {
                final String integerValueString = splitString[splitString.length - 1];
                final int theXMLIdInteger = Integer.parseInt(integerValueString);
                updateXMLIdDispenser(theXMLIdInteger);
            }
        }
        catch (Exception ex) {}
    }
    
    public static String getXMLId(final Class aClass) {
        String className = aClass.getName();
        try {
            final String[] splitString = className.split("\\.");
            if (splitString.length > 0) {
                className = splitString[splitString.length - 1];
            }
        }
        catch (Exception ex) {}
        return getXMLId(className);
    }
    
    public static String getXMLId(final String prefix) {
        final String xmlId = prefix + XMLIdManager.xmlIdCounter;
        incrementXMLIdDispenser();
        return xmlId;
    }
    
    static {
        XMLIdManager.xmlIdCounter = 1L;
    }
}
