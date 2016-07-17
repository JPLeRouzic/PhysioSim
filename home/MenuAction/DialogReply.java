// 
// This bear the answer to the "File/New model organism" box, where the user must adapt some parameters 
// of the selected proposed model.
// 

package home.MenuAction;

public class DialogReply
{
    public Object value;
    public boolean ok;
    
    public DialogReply() {
        this.ok = false;
    }
    
    public DialogReply(final Object value) {
        this.ok = false;
        this.value = value;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public String getString() {
        if (this.value != null) {
            return this.value.toString();
        }
        return "";
    }
    
    @Override
    public String toString() {
        return this.getString();
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
    
    public boolean isOk() {
        return this.ok;
    }
    
    public void setOk(final boolean ok) {
        this.ok = ok;
    }
}
