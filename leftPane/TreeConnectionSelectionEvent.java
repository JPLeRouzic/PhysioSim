// 
//  
// 

package leftPane;

import java.util.EventObject;

public class TreeConnectionSelectionEvent extends EventObject
{
    public Object connectionObject;
    private static final long serialVersionUID = 65020000001L; // JPLR
    
    public TreeConnectionSelectionEvent(final Object source, final Object connectionObject) {
        super(source);
        this.connectionObject = connectionObject;
    }
    
    public Object getConnectionObject() {
        return this.connectionObject;
    }
}
