/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser.sbml;

import datamodel.CompartmentSBML;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class ModelSBML {

    private double totalVolume;
    private String partOf;
    private String identity;
    private String name;
    private String organSystem;
    private String metaid;

    private final Map<Integer, timesSBML> mapOfTimes;
    private int indxOftimes = 0;

    private final Map<Integer, SpeciesSBML> mapOfSpecies;
    private int indxOfSpecies = 0;

    private final Map<Integer, RulesSBML> mapOfRule;
    private int indxOfRules = 0;

    int indxOfCi = 0;
    private final Map<Integer, ciSBML> mapOfCi;

    private int indxOfMath = 0;
    private final Map<Integer, mathSBML> mapOfMath;

    private final Map<Integer, ReactionsSBML> mapOfReactions;
    private int indxOfReactions;

    private int indxOfParameters = 0;
    private final Map<Integer, ParameterSBML> mapOfParameters;

    private Map<Integer, UnitSBML> mapOfUnits;
    private int indxOfUnits = 0;

    // add this compartment to the global list of internal compartment
    // We do not use indxOfCompartments, but comp.identity
    private final Map<String, CompartmentSBML> mapOfCompartments;

    private final Map<Integer, SpeciesRefSBML> mapOfspeciesRef;
    private Integer indxOfspeciesRef = 0;

    /**
     * constructor
     */
    public ModelSBML() {
        mapOfCompartments = new HashMap<>();

        mapOfSpecies = new HashMap<>();
        mapOfParameters = new HashMap<>();
        mapOfRule = new HashMap<>();
        mapOfReactions = new HashMap<>();
        mapOfspeciesRef = new HashMap<>();
        mapOfMath = new HashMap<>();
        mapOfCi = new HashMap<>();
        mapOfTimes = new HashMap<>();

        indxOfSpecies = 0;
        indxOfParameters = 0;
        indxOfRules = 0;
        indxOfReactions = 0;
        indxOfspeciesRef = 0;
        indxOfMath = 0;
        indxOfCi = 0;
        indxOftimes = 0;
    }

    void setTotalVolumeML(double parseDouble) {
        totalVolume = parseDouble;
    }

    public double getTotalVolumeML() {
        return totalVolume;
    }

    public void setPartOfStr(String mof) {
        partOf = mof;
    }

    public String getPartOfStr() {
        return partOf;
    }

    public void setOrganSystemStr(String attributeValue) {
        organSystem = attributeValue;
    }

    public String getOrganSystemStr() {
        return organSystem;
    }

    public void setName(String attributeValue) {
        name = attributeValue;
    }

    public String getName() {
        return name;
    }

    public void setIdentity(String attributeValue) {
        identity = attributeValue;
    }

    public String getIdentity() {
        return identity;
    }

    public void setMetaid(String attributeValue) {
        metaid = attributeValue;
    }

    String getMetaid() {
        return metaid;
    }

    public Map<Integer, SpeciesSBML> getMapOfSpecies() {
        return this.mapOfSpecies;
    }

    public void incIndxOfSpecies() {
        this.indxOfSpecies++;
    }

    public Map<String, CompartmentSBML> getMapOfCompartments() {
        return this.mapOfCompartments;
    }

    public Map<Integer, SpeciesRefSBML> getMapOfspeciesRef() {
        return this.mapOfspeciesRef;
    }

    public void incIndxOfspeciesRef() {
        this.indxOfspeciesRef++;
    }

    Map<Integer, ciSBML> getMapOfCi() {
        return this.mapOfCi;
    }

    Map<Integer, mathSBML> getMapOfMath() {
        return this.mapOfMath;
    }

    void incIndxOfMath() {
        this.indxOfMath++;
    }

    public Map<Integer, RulesSBML> getMapOfRule() {
        return this.mapOfRule;
    }

    public void incIndxOfRules() {
        this.indxOfRules++;
    }

    public void incIndxOfParameters() {
        this.indxOfParameters++;
    }

    public Map<Integer, ParameterSBML> getMapOfParameters() {
        return this.mapOfParameters;
    }

    public Map<Integer, UnitSBML> getMapOfUnits() {
        return this.mapOfUnits;
    }

    public Integer getIndxOfSpecies() {
        return this.indxOfSpecies;
    }

    public Integer getIndxOfRules() {
        return this.indxOfRules;
    }

    public void incIndxOfUnits() {
        this.indxOfUnits++;
    }

    public Integer getIndxOfUnits() {
        return this.indxOfUnits;
    }

    public Integer getIndxOfParameters() {
        return this.indxOfParameters;
    }

    Integer getIndxOfMath() {
        return this.indxOfMath;
    }

    Map<Integer, timesSBML> getMapOfTimes() {
        return this.mapOfTimes;
    }

    Integer getIndxOftimes() {
        return indxOftimes;
    }

    void incIndxOftimes() {
        this.indxOftimes++;
    }

    public Integer getIndxOfspeciesRef() {
        return this.indxOfspeciesRef;
    }

    public Map<Integer, ReactionsSBML> getMapOfReactions() {
        return this.mapOfReactions;
    }

    public Integer getIndxOfReactions() {
        return this.indxOfReactions;
    }

    public void incIndxOfReactions() {
        this.indxOfReactions++;
    }

    public Collection getReactions() {
        return this.mapOfReactions.values();
    }

    public Collection<SpeciesSBML> getSpecies() {
        return this.mapOfSpecies.values();
    }

    public Collection<SpeciesRefSBML> getSpeciesReference() {
        return this.mapOfspeciesRef.values();
    }

    public Collection<ParameterSBML> getParameters() {
        return this.mapOfParameters.values();
    }
    
    public Map<Integer, SpeciesRefSBML> getMapOfReactants() {
        return this.mapOfspeciesRef;
    }

    int getIndxOfReactants() {
        return this.indxOfspeciesRef;
    }

    void incIndxOfReactants() {
        this.indxOfspeciesRef++ ;
    }

}
