// 
//  
// 

package datamodel.type;

import datamodel.Connection;
import utility.XMLIdManager;

public class MolecularInstance 
{
    protected MolecularType type;
    private static final long serialVersionUID = 65020000001L; // JPLR
    private Connection locationNode;
    
    public MolecularInstance() {
        this.setXmlId(XMLIdManager.getXMLId((Class)MolecularInstance.class));
    }
    
    public MolecularInstance(final MolecularType molecularType, final Connection locationNode) {
        this.setXmlId(XMLIdManager.getXMLId((Class)MolecularInstance.class));
        this.type = molecularType;
        this.locationNode = locationNode;
    }
    
    public void setupXmlId() {
        this.setXmlId(XMLIdManager.getXMLId((Class)MolecularInstance.class));
    }
    
    public MolecularType getType() {
        return this.type;
    }
    
    public void setType(final MolecularType type) {
        this.type = type;
    }
    
    private void setXmlId(String xmlId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getConcentrationMolesPerML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
