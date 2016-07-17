/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser.sbml;

import java.util.ArrayList;
import ODEsolver.Tuples;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class ReactionsSBML {

    public String id;
    public String reversible;
    private String name;
    private String metaid;

    private ArrayList<SpeciesRefSBML> reactants = new ArrayList<>() ;
    private ArrayList<SpeciesRefSBML> products = new ArrayList<>() ;
    private String kineticLaw;

    // this array of Tuples is initialized with reactant initial values
    // and carries later values
    public ArrayList<Tuples> resultVector = new ArrayList<>();

    public String getIdentity() {
        return this.id;
    }

    public final void setIdentity(final String xmlId) {
        this.id = xmlId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String theName) {
        this.name = theName;
    }

    public final void setMetaid(final String str) {
        this.metaid = str;
    }

    public final String getMetaid() {
        return metaid;
    }

    public final void setKineticLaw(final String str) {
        this.kineticLaw = str;
    }

    public final String getKineticLaw() {
        return kineticLaw;
    }

    void setListOfReactants(ArrayList<SpeciesRefSBML> reactants) {
                this.reactants.addAll(reactants) ;
    }

    void setListOfProducts(ArrayList products) {
                this.products.addAll(reactants) ;
    }

}
