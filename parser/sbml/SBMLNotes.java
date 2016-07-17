/*
 * This is simply about notes in the SBML flow.
 */
package parser.sbml;

import java.util.List;
import org.jdom.Element;

/**
 *
 * @author jplr
 */
public class SBMLNotes {

    public String notesSBML(Element doc) {
        String res1 = "";
        
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {
            if (list1.getName().contentEquals("notes")) {
                Element list2 = list1.getChild("notes");
                res1 += notesSBML(list2);
            }
        } 
        return res1 ;
    }
        
}
