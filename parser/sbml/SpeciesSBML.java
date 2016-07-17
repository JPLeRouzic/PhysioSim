/*
 * The species element in SBML is used to represent entities such as ions 
 * and molecules that participate in reactions. 
 */
package parser.sbml;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class SpeciesSBML {
    public String id;
    public String initialConcentration;
    public String compartment;
    public String boundaryCondition;
    public String constant;
    public String sboTerm;
    private String metaid;
//    public String charge;
    public String name;
    String initialAmount;

    /**
     * 
     * @param i_metaid // metaid="metaid_M_m00025c"
     * @param i_id // id="M_m00025c"
     * @param i_name // name="(15Z)-tetracosenoyl-CoA"
     * @param i_comp // compartment="C_c"
     * @param i_amount // initialAmount="0"
     * @param b // boundaryCondition="false"
     * @param i_sboTerm // sboTerm="SBO:0000299"
     * @param i_const ;
     */
    public SpeciesSBML(String i_metaid, String i_id, String i_name, String i_comp, 
            String i_amount, String b, String i_sboTerm, String i_const) {
    id = i_id ;
    initialConcentration = "none" ;
    compartment = i_comp ;
    boundaryCondition = b ;
    constant = i_const ;
    sboTerm = i_sboTerm ;
    metaid = i_metaid;
    name = i_name ;
    initialAmount = i_amount ;
    }

    SpeciesSBML() {
    id = "" ;
    initialConcentration = "none" ;
    compartment = "" ;
    boundaryCondition = "" ;
    constant = "" ;
    sboTerm = "" ;
    metaid = "";
    name = "" ;
    initialAmount = "" ;
    }
    
    public String getCompartment() 
    {
        return compartment ;
    }
    
    public String getId() 
    {
        return id ;
    }

        public final void setMetaid(final String str) {
        this.metaid = str;
    }
    
        public final String getMetaid() {
        return metaid ;
    }
    
        public final void setIdentity(final String str) {
        this.id = str;
    }
    
        public final String getIdentity() {
        return id ;
    }
    
        public final void setName(final String str) {
        this.name = str;
    }
    
        public final String getName() {
        return name ;
    }

    public void setInitialAmount(String val) {
        initialAmount = val ;
    }
    
    public String getInitialAmount() {
        return initialAmount ;
    }
    
}
