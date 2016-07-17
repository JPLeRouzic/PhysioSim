/*
 * This process the "organs" SBML files.
 * the goal is to create a "Tissue" object with each organ file
 * We use XML annotations to get more information ("physioSim")
 */
package parser.sbml;

import parser.SBMLParserMain;
import datamodel.CellType;
import datamodel.CompartmentSBML;
import datamodel.Fluid;
import datamodel.Tissue;
import datamodel.type.CellTypeMemberOfDefinitionType;
import parser.ParserMng;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import static MathML.MathMLReader.MathMLXMLToText;
import utility.CollToArrayList;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class SbmlParser {

    SBMLParserMain spm;
    private final ParserMng gui;
    ModelSBML currentModel;

    public SbmlParser(ParserMng guiii, SBMLParserMain spmain, SBMLNotes not) {

        gui = guiii;
        spm = spmain;

        spm.note = not;

        guiii.setMapOfModels(new HashMap<>());
        guiii.setMapOfTissueCompartments(new HashMap<>());

        // initialize indexes of listOf...
        guiii.setIndxOfModels(0);
    }

    /**
     * Load a SBML currentModel in a SBML object
     *
     * <!ELEMENT sbml (comp:ListOfSubmodels|listOfModelDefinitions|currentModel)*>
     * <!ATTLIST sbml
     * level CDATA #IMPLIED
     * version CDATA #IMPLIED
     * xmlns CDATA #IMPLIED
     * >
     *
     * @param file
     */
    public void parseSBML(FileInputStream file) {
        Document doc = null;
        Element rootNode = null;
        List<Element> list = null;

        try {
            // now load this file
            /* we take the SAXBuilder for building a currentModel out of a XML-File */
            SAXBuilder builder = new SAXBuilder();
            /* create a new JDOM Document with the SAXBuilder and the given file */
            doc = builder.build(file);

        } catch (JDOMException y) {
        }

        if (doc != null) {
            rootNode = doc.getRootElement();

            list = rootNode.getChildren();
        } else {
            System.out.println("Can't create a new JDOM Document with the SAXBuilder and the given file???");
            return;
        }
        for (Element list1 : list) {

            // comp:ListOfSubmodels
            if (list1.getName().contentEquals("ListOfSubmodels")) {
                listOfSubmodels(list1);
            } // currentModel
            if (list1.getName().contentEquals("model")) {
                modelSBML(list1);
            }

            // level CDATA #IMPLIED
            if (list1.getAttribute("level") != null) {
                spm.SBMLlevel = list1.getAttributeValue("level");
            }  // version CDATA #IMPLIED
            if (list1.getAttribute("version") != null) {
                spm.SBMLversion = list1.getAttributeValue("version");
            }  // xmlns CDATA #IMPLIED
            // version CDATA #IMPLIED
            if (list1.getAttribute("xmlns") != null) {
                spm.SBMLxmlns = list1.getAttributeValue("xmlns");
            }

        }
    }

    /**
     * To be done later
     *
     * <!ELEMENT listOfModelDefinitions (modelDefinition)*>
     * <!--- Put your DTDDoc comment here. -->
     * <!ELEMENT modelDefinition (#PCDATA)>
     * <!ATTLIST modelDefinition
     * id CDATA #IMPLIED
     * >
     */
    /**
     * There is only one list of models in each files, it is actually a organ
     *
     * <!ELEMENT comp:ListOfSubmodels (notes|comp:ListOfSubmodels|currentModel)*>
     *
     * @param doc
     */
    private void listOfSubmodels(Element doc) {
        List<Element> list = doc.getChildren(); // "comp:ListOfSubmodels");
        for (Element list1 : list) {

            switch (list1.getName()) {
                case "comp:ListOfSubmodels":
                    listOfSubmodels(list1);
                    break;
                case "model":
                    modelSBML(list1);
                    break;
                case "notes":
                    spm.note.notesSBML(list1);
                    break;
            }
        }
    }

    /**
     * <!ELEMENT currentModel (notes|compartment)*>
     * <!ELEMENT currentModel (notes|annotation|listOfUnitDefinitions|listOfParameters|listOfRules|listOfCompartments|listOfSpecies|listOfReactions)*>
     * <!ATTLIST currentModel
     * id CDATA #IMPLIED
     * >
     *
     */
    private void modelSBML(Element doc) {
        currentModel = new ModelSBML();
        // id="Tissue8393" name="Caecum" >
        // fill in Tissue
        // id CDATA #IMPLIED
        if (doc.getAttribute("id") != null) {

            String un = doc.getAttributeValue("id");
            if ("".contentEquals(un)) {
                System.out.println("No id in model: " + currentModel.getName());
                currentModel.setIdentity("no id");
            }
            currentModel.setIdentity(un);
        } else {
            System.out.println("No id in model: " + currentModel.getName());
            currentModel.setIdentity("no id");
        }

        // name CDATA #IMPLIED
        if (doc.getAttribute("name") != null) {
            currentModel.setName(doc.getAttributeValue("name"));
        } else {
            System.out.println("No name in model: " + currentModel.getName());
            currentModel.setName(currentModel.getIdentity());
        }

        // name CDATA #IMPLIED
        if (doc.getAttribute("metaid") != null) {
            currentModel.setMetaid(doc.getAttributeValue("metaid"));
        }

        // organSystem CDATA #IMPLIED
        if (doc.getAttribute("organSystem") != null) {
            currentModel.setOrganSystemStr(doc.getAttributeValue("organSystem"));
        } else {
            System.out.println("No organSystem in model: " + currentModel.getName());
            currentModel.setOrganSystemStr("no organSystem");
        }

        // memberOf CDATA #IMPLIED
        if (doc.getAttribute("memberOf") != null) {
            String mof = doc.getAttributeValue("memberOf");
            currentModel.setPartOfStr(mof);
        }

        // Now proceed to treat the lower level elements (XML content)       
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            /**
             * to be done
             */
            // list of function definitions (optional)
            // list of unit definitions (optional)
            // this is not SBML
            switch (list1.getName()) {
                // listOfCompartments
                case "compartment":
                    oneCompartment(list1); // ok
                    break;
                /**
                 * Other SBML features to be implemented
                 *
                 * list of initial assignments (optional) list of constraints
                 * (optional) list of events (optional)
                 */
                /* this is not SBML
                 if (list1.getName().contentEquals("notes")) {
                 String naute = spm.note.notesSBML(list1);
                 currentModel.setNotes(naute);
                 }
                 */
                // ???
                case "notes":
                    if (list1.getName().contentEquals("notes")) {
                        spm.note.notesSBML(list1);
                    }
                    break;
                // "annotation" is processed as "notes" for now
                case "annotation":
                    if (list1.getName().contentEquals("annotation")) {
                        spm.note.notesSBML(list1);
                    }
                    break;
                // ???
                case "listOfUnitDefinitions":
                    listOfUnitDefinitions(list1); // ok
                    break;
                // listOfSpecies
                case "listOfCompartments":
                    listOfCompartments(list1); // ok
                    break;
                // listOfParameters
                case "listOfSpecies":
                    listOfSpecies(list1); // ok
                    break;
                // listOfRules
                case "listOfParameters":
                    listOfParameters(list1); // ok
                    break;
                // listOfReactions
                case "listOfRules":
                    listOfRules(list1); // ok
                    break;
                case "listOfReactions":
                    listOfReactions(list1); // ok
                    break;
            }

        }

        // manage a multiplicity of Models
        gui.getMapOfModels().put(gui.getIndxOfModels(), currentModel);
        gui.incIndxOfModels();

        /* 
         Build the organ (Tissue) from ModelSBML
        
         ModelSBML              Tissue
         volume;             => volume
         partOf;             => partOf, partOfString
         identity;           => identity (Connection)
         name;               => tissueName
         parentNode;         => OrganSystem
         compartmentSBMLs    => ArrayList<CompartmentSBML> volumes; 
         */
        Tissue organ = new Tissue();

        // Load compartmentsSBML in organ.volumes
        ArrayList<CompartmentSBML> tissueCompartments;
        CollToArrayList conv = new CollToArrayList();
        tissueCompartments = conv.convertComp(gui.getMapOfTissueCompartments());
        // clear it to be ready for next Tissue
        gui.getMapOfTissueCompartments().clear();

        organ.setInternVolumes(tissueCompartments);
        organ.setTotalVolumeML(currentModel.getTotalVolumeML());       // ok
        organ.setPartOfStr(currentModel.getPartOfStr());               // ok
        organ.setOrganSystemStr(currentModel.getOrganSystemStr());               // ok
        organ.setIdentity(currentModel.getIdentity());
        organ.setName(currentModel.getName());

        gui.getMapOfTissues().put(gui.getIndxOfTissues(), organ);
        gui.incIndxOfTissues();
    }

    /**
     *
     * <!ELEMENT listOfCompartments (compartment)*>
     *
     */
    private void listOfCompartments(Element doc) {
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            // Get a compartment
            if (list1.getName().contentEquals("compartment")) {
                oneCompartment(list1); // ok
            }

        }
    }

    /**
     * Legal SBML: <!ELEMENT compartment EMPTY>      <!ATTLIST compartment
     *
     * CDATA #IMPLIED metaid (2.4)
     * CDATA #IMPLIED id
     * CDATA #IMPLIED name (2.4)
     * CDATA #IMPLIED spatialDimensions (2.4)
     * CDATA #IMPLIED sizeb
     * CDATA #IMPLIED constant
     * CDATA #IMPLIED outside
     * CDATA #IMPLIED sboTerm (2.4)
     *
     * A SBML compartment without any outside connections is an internal compartment.
     * It is represented internally by the FluidCompartment class.
     *
     * A compartment with one outside connection is a compartment which is included
     * in another compartment.
     *
     * A compartment with two outside connections is either a membrane or a point to point connection.
     * A FluidPipe class has a length which is much longer than width.
     * The outside connections are the two ends of the pipe.
     * Because SBML has no geometry information, we use the "spatialDimensions" attribute
     * in a distorted way, if spatialDimensions="1", then the compartment is a FluidPipe
     *
     * A ExtendedAreaConn is a membrane separating two compartments.
     * Again the two outside connections represent the connections to the two containers.
     * If spatialDimensions="2", then the compartment is an ExtendedAreaConn
     *
     * Other Compartments have spatialDimensions="3"
     *
     * Compartments may also have arbitrary connections but not in SBML standard
     *
     * >
     */
    private void oneCompartment(Element list1) {

        CompartmentSBML comp = new CompartmentSBML(currentModel);
        Fluid fluid = new Fluid();
        CellType ct = new CellType();
        boolean flag = false;
        String namu = null;

        // CDATA #IMPLIED metaid (2.4)
        if (list1.getAttribute("metaid") != null) {
            comp.setMetaid(list1.getAttributeValue("metaid"));
        }

        // id CDATA #IMPLIED
        if (list1.getAttribute("id") != null) {
            comp.setIdentity(list1.getAttributeValue("id"));
        } else {
            flag = true;
            System.out.println("No id in compartment: " + comp.getName());
            comp.setIdentity("no id");
        }

        // name CDATA #IMPLIED
        if (list1.getAttribute("name") != null) {
            namu = list1.getAttributeValue("name");
            comp.setName(namu);
            // Maybe it is a Fluid
            fluid.setName(namu);
        } else { // It is not mandatory
//            System.out.println("No name in compartment: " + comp.getName());
            comp.setIdentity(comp.getIdentity());
        }

        // spatialDimensions CDATA #IMPLIED
        if (list1.getAttribute("spatialDimensions") != null) {
            comp.spatialDimensions = list1.getAttributeValue("spatialDimensions");
        }

        // parentOrgan CDATA #IMPLIED
        if (list1.getAttribute("parentOrgan") != null) {
            comp.outside = list1.getAttributeValue("parentOrgan");
        }
        // outside CDATA #IMPLIED
        // it exists only in SBML level 1 & 2
        if (list1.getAttribute("outside") != null) {
            comp.outside = list1.getAttributeValue("outside");
        }

        // memberOf CDATA #IMPLIED
        // only for cells
        if (list1.getAttribute("memberOf") != null) {
            String mof = list1.getAttributeValue("memberOf");
            ct.memberOf = CellTypeMemberOfDefinitionType.valueOf(mof);
        }
        /**
         * size CDATA #IMPLIED
         *
         * A compartment’s volume is set by its "size" attribute in units
         * specified by the attribute "units". If the compartment’s attribute
         * "constant" has the value “ true ”, then the compartment’s size is
         * fixed and cannot be changed.
         */
        if (list1.getAttribute("size") != null) {
            comp.size = list1.getAttributeValue("size");
        }
        // CDATA #IMPLIED sboTerm (2.4) 
        if (list1.getAttribute("sboTerm") != null) {
            comp.sboTerm = list1.getAttributeValue("sboTerm");
        }

        // constant CDATA #IMPLIED
        if (list1.getAttribute("constant") != null) {
            comp.constant = list1.getAttributeValue("constant");
        } // units CDATA #IMPLIED
        if (list1.getAttribute("units") != null) {
            comp.units = list1.getAttributeValue("units");
        } // this is not SBML
        if (list1.getName().contentEquals("notes")) {
            spm.note.notesSBML(list1);
        }

        /* FIXME
         * Is it a Tissue, a CompartmentSBML, a Fluid, a CellType?
         * Is it a Surface, a SurfacePore or a FluidPipe?
         * We will know only at the end by confronting compartments, species and reactions
         */
        // In any case register it as a compartment
        Map<String, CompartmentSBML> un = currentModel.getMapOfCompartments();
        un.put(comp.getIdentity(), comp);

        // Register it also as a compartment from the current Tissue
        gui.getMapOfTissueCompartments().put(comp.getIdentity(), comp);
    }

    /**
     *
     * <!ELEMENT listOfSpecies (species)*>
     */
    private void listOfSpecies(Element doc) {
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            // Get a compartment
            if (list1.getName().contentEquals("species")) {
                species(list1);
            }
        }
    }

    /* <!ELEMENT species EMPTY>
     * <!ATTLIST species
     * id CDATA #IMPLIED
     * charge CDATA #IMPLIED
     * name CDATA #IMPLIED
     * metaid CDATA #IMPLIED
     * initialConcentration CDATA #IMPLIED
     * compartment CDATA #IMPLIED
     * boundaryCondition CDATA #IMPLIED
     * constant CDATA #IMPLIED
     * >
     */
    private void species(Element list1) {

        SpeciesSBML species = new SpeciesSBML();

        // CDATA #IMPLIED metaid (2.4)
        if (list1.getAttribute("metaid") != null) {
            species.setMetaid(list1.getAttributeValue("metaid"));
        }
        // id CDATA #IMPLIED
        if (list1.getAttribute("id") != null) {
            species.id = list1.getAttributeValue("id");
        }
        // id CDATA #IMPLIED
        if (list1.getAttribute("name") != null) {
            species.name = list1.getAttributeValue("name");
        } else {
            species.setName(species.getIdentity());
        }
        /*        
         // id CDATA #IMPLIED
         if (list1.getAttribute("charge") != null) {
         species.charge = list1.getAttributeValue("charge");
         }
         */
        // initialConcentration CDATA #IMPLIED
        // A species’ initial quantity is set by the initialAmount or initialConcentration attributes 
        // exactly once.
        // If the constant attribute is “ true ”, then the value of the species’ quantity is fixed and 
        // cannot be changed except by an InitialAssignment . 
        // These methods differ in that the initialAmount and initialConcentration
        // attributes can only be used to set the species quantity to a literal floating-point number, 
        // whereas the use of an InitialAssignment object allows the value to be set using 
        // an arbitrary mathematical expression
        if (list1.getAttribute("initialAmount") != null) {
            species.initialAmount = list1.getAttributeValue("initialAmount");
        }
        if (list1.getAttribute("initialConcentration") != null) {
            species.initialConcentration = list1.getAttributeValue("initialConcentration");
        } // compartment CDATA #IMPLIED
        if (list1.getAttribute("compartment") != null) {
            species.compartment = list1.getAttributeValue("compartment");
        }
        // boundaryCondition CDATA #IMPLIED
        if (list1.getAttribute("boundaryCondition") != null) {
            species.boundaryCondition = list1.getAttributeValue("boundaryCondition");
        } // constant CDATA #IMPLIED
        if (list1.getAttribute("constant") != null) {
            species.constant = list1.getAttributeValue("constant");
        }
        // CDATA #IMPLIED sboTerm (2.4) 
        if (list1.getAttribute("sboTerm") != null) {
            species.sboTerm = list1.getAttributeValue("sboTerm");
        }

        // manage a multiplicity of SBML Species
        currentModel.getMapOfSpecies().put(currentModel.getIndxOfSpecies(), species);
        currentModel.incIndxOfSpecies();
    }

    private void listOfUnitDefinitions(Element doc) {
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            // Get a UnitDefinition
            if (list1.getName().contentEquals("unitDefinition")) {
                unitDefinition(list1);
            }
        }
    }

    /*
     <!--- Put your DTDDoc comment here. -->
     <!ELEMENT unitDefinition (listOfUnits)*>
     <!ATTLIST unitDefinition
     id CDATA #IMPLIED
     >
     */
    private void unitDefinition(Element doc) {
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            if (list1.getAttribute("listOfUnits") != null) {
                listOfUnits(list1);
            }
        }
    }

    /*
     <!ELEMENT unit EMPTY>
     <!ATTLIST unit
     kind CDATA #IMPLIED
     scale CDATA #IMPLIED
     multiplier CDATA #IMPLIED
     exponent CDATA #IMPLIED
     >
     */
    private void listOfUnits(Element doc) {
        UnitSBML unit = new UnitSBML();
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            if (list1.getAttribute("kind") != null) {
                unit.kind = list1.getAttributeValue("kind");
            }
            if (list1.getAttribute("scale") != null) {
                unit.scale = list1.getAttributeValue("scale");
            }
            if (list1.getAttribute("multiplier") != null) {
                unit.multiplier = list1.getAttributeValue("multiplier");
            }
            if (list1.getAttribute("exponent") != null) {
                unit.exponent = list1.getAttributeValue("exponent");
            }

            // manage a multiplicity of UnitSBML
            currentModel.getMapOfUnits().put(currentModel.getIndxOfUnits(), unit);
            currentModel.incIndxOfUnits();
        }
    }

    /*
     <!ELEMENT listOfParameters (parameter)*>
     */
    private void listOfParameters(Element doc) {
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            // Get a parameter
            if (list1.getName().contentEquals("parameter")) {
                parameter(list1);
            }
        }
    }

    /* <!ELEMENT parameter EMPTY>
     * <!ATTLIST parameter
     * id CDATA #IMPLIED
     * value CDATA #IMPLIED
     * >
     */
    private void parameter(Element list1) {

        ParameterSBML param = new ParameterSBML();
        
        if (list1.getAttribute("id") != null) {
            param.id = list1.getAttributeValue("id");
        } 
        if (list1.getAttribute("name") != null) {
            param.id = list1.getAttributeValue("name");
        } 
        if (list1.getAttribute("value") != null) {
            param.value = list1.getAttributeValue("value");
        }

        // manage a multiplicity of Parameters
        currentModel.getMapOfParameters().put(currentModel.getIndxOfParameters(), param);
        currentModel.incIndxOfParameters();
    }

    /*
     <!ELEMENT listOfRules (rateRule)*>
     */
    private void listOfRules(Element doc) {
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            // Get a compartment
            if (list1.getName().contentEquals("rateRule")) {
                rateRule(list1); // ok
            }

        }
    }

    /* <!ELEMENT rateRule (math)*>
     * <!ATTLIST rateRule
     * variable CDATA #IMPLIED
     * >
     */
    private void rateRule(Element list1) {

        RulesSBML param = new RulesSBML();

        // value CDATA #IMPLIED
        if (list1.getAttribute("variable") != null) {
            param.variable = list1.getAttributeValue("variable");
        }

        // <!ATTLIST rateRule
        List<Element> list = list1.getChildren();
        for (Element list2 : list) {

            // Go to a rateRule
            if (list2.getName().contentEquals("math")) {
                math(list2);
            }
        }
        // manage a multiplicity of Species
        currentModel.getMapOfRule().put(currentModel.getIndxOfRules(), param);
        currentModel.incIndxOfRules();
    }

    /*
     <!ELEMENT math (apply)*>
     * <!ATTLIST math
     * xmlns CDATA #IMPLIED
     * >
     */
    private String math(Element doc) {
        String str = "";

        str = subtree("", doc);
        // returns a string representing the MathML formula
        return MathMLXMLToText(str);
    }

    String subtree(String str1, Element doc) {
        String str2 = "", str3 = "";
        List<Element> list0 = doc.getChildren();
        for (Element list1 : list0) {
            String name = list1.getName();
//            str2 = str2 + "<" + name + ">";
            if ((name.contentEquals("ci")) || (name.contentEquals("cn"))) {
                if (name.contentEquals("ci")) {
                    str2 = str2 + "<ci>";
                } else {
                    str2 = str2 + "<cn>";
                }

                List content = list1.getContent();
                Iterator iter = content.iterator();
                while (iter.hasNext()) {
                    org.jdom.Text un = (org.jdom.Text) iter.next();
                    String necht = un.getText();
                    str2 = str2 + necht;
                }

                if (name.contentEquals("ci")) {
                    str2 = str2 + "</ci>";
                } else {
                    str2 = str2 + "</cn>";
                }

            }
            if (list1.hasChildren()) {
                if (list1 instanceof List) {
                    str3 = subtree(str2, list1);
                }
            }
            str2.concat(str3);
        }
        return str1.concat(str2);
    }

    /*
     <!ELEMENT listOfReactions (reaction)*>
     */
    private void listOfReactions(Element doc) {
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            // Get a compartment
            if (list1.getName().contentEquals("reaction")) {
                reaction(list1);
            }

        }
    }

    /*
     <!ELEMENT reaction (listOfReactants|listOfProducts|kineticLaw)*>
     * <!ATTLIST reaction
     * id CDATA #IMPLIED
     * reversible CDATA #IMPLIED
     * metaid CDATA #IMPLIED
     * name CDATA #IMPLIED
     * sboTerm CDATA #IMPLIED
     * >
     */
    private void reaction(Element list1) {

        ReactionsSBML react = new ReactionsSBML();

        // id CDATA #IMPLIED
        if (list1.getAttribute("id") != null) {
            react.id = list1.getAttributeValue("id");
        }
        // value CDATA #IMPLIED
        if (list1.getAttribute("reversible") != null) {
            react.reversible = list1.getAttributeValue("reversible");
        }
        // CDATA #IMPLIED metaid (2.4)
        if (list1.getAttribute("metaid") != null) {
            react.setMetaid(list1.getAttributeValue("metaid"));
        }
        // name CDATA #IMPLIED
        if (list1.getAttribute("name") != null) {
            String namu = list1.getAttributeValue("name");
            react.setName(namu);
        } else {
            react.setName(react.getIdentity());
        }
        // id CDATA #IMPLIED
        if (list1.getAttribute("sboTerm") != null) {
            react.setIdentity(list1.getAttributeValue("sboTerm"));
        }

        List<Element> list2 = list1.getChildren();
        for (Element list3 : list2) {

            // Get a listOfReactants
            switch (list3.getName()) {
                case "listOfReactants":
                    ArrayList reactants = listOfReactants(list3);
                    react.setListOfReactants(reactants);
                    break;
                case "listOfProducts":
                    ArrayList products = listOfProducts(list3);
                    react.setListOfProducts(products);
                    break;
                case "listOfModifiers":
                    listOfModifiers(list3);
                    break;
                case "kineticLaw":
                    // store kinetic law to retrieve it when editing or running the simulation
                    String law = kineticLaw(list3);
                    react.setKineticLaw(law);
                    break;
            }

        }

        // manage a multiplicity of Reactions
        currentModel.getMapOfReactions().put(currentModel.getIndxOfReactions(), react);
        currentModel.incIndxOfReactions();
    }

    /*
     * <!ELEMENT listOfReactants (speciesReference)*>
     */
    private ArrayList listOfReactants(Element doc) {
        List<Element> list = doc.getChildren();
        SpeciesRefSBML reactant = null ;
        ArrayList reactants = new ArrayList() ;
        
        for (Element list1 : list) {

            // Get a compartment
            if (list1.getName().contentEquals("speciesReference")) {
                reactant = speciesReference(list1);
                currentModel.getMapOfReactants().put(currentModel.getIndxOfReactants(), reactant);
                reactants.add(reactant) ;
            }
        }
        // manage a multiplicity of Reactants
        currentModel.incIndxOfReactants();
        
        return reactants;
    }

    /*
     <!ELEMENT speciesReference EMPTY>
     * <!ATTLIST speciesReference
     * species CDATA #IMPLIED
     * stoichiometry CDATA #IMPLIED
     * >
     */
    private SpeciesRefSBML speciesReference(Element list1) {

        SpeciesRefSBML param = new SpeciesRefSBML();

        // species CDATA #IMPLIED
        if (list1.getAttribute("species") != null) {
            param.species = list1.getAttributeValue("species");
        }

        // species CDATA #IMPLIED
        if (list1.getAttribute("stoichiometry") != null) {
            param.stoichiometry = list1.getAttributeValue("stoichiometry");
        }

        return param ;
    }

    /*
     <!ELEMENT listOfProducts (speciesReference)*>
     */
    private ArrayList listOfProducts(Element doc) {
        List<Element> list = doc.getChildren();
        ArrayList products = new ArrayList() ;
        for (Element list1 : list) {

            // Get a compartment
            if (list1.getName().contentEquals("speciesReference")) {
                SpeciesRefSBML un = speciesReference(list1);
                products.add(un) ;
            }
        }
        return products;
    }

    /*
     <!ELEMENT listOfModifiers (speciesReference)*>
     */
    private void listOfModifiers(Element doc) {
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            // Get a compartment
            if (list1.getName().contentEquals("modifierSpeciesReference")) {
                modifierSpeciesReference(list1);
            }

        }
    }

    /*
     <!ELEMENT modifierSpeciesReference EMPTY>
     * <!ATTLIST speciesReference
     * species CDATA #IMPLIED
     * >
     */
    private void modifierSpeciesReference(Element list1) {

        SpeciesRefSBML param = new SpeciesRefSBML();

        // species CDATA #IMPLIED
        if (list1.getAttribute("species") != null) {
            param.species = list1.getAttributeValue("species");
        }

        // manage a multiplicity of Species
        currentModel.getMapOfspeciesRef().put(currentModel.getIndxOfspeciesRef(), param);
        currentModel.incIndxOfspeciesRef();
    }

    /*     
     <!ELEMENT kineticLaw (math|listOfParameters)*>
     <!ELEMENT kineticLaw (math|listOfLocalParameters|notes|annotation)*>
     <!ATTLIST kineticLaw
     sboTerm CDATA #IMPLIED
     metaid CDATA #IMPLIED
     xmlns:sbml CDATA #IMPLIED
     >
     */
    private String kineticLaw(Element doc) {
        String law = "";
        
        if (doc.getAttribute("formula") != null) {
            law = doc.getAttributeValue("formula");
        }
        
        List<Element> list = doc.getChildren();
        for (Element list1 : list) {

            // Get only one math bloc.
            if (list1.getName().contentEquals("math")) {
                law = math(list1);
            }

            // Get parameters
            if (list1.getName().contentEquals("listOfParameters")) {
                listOfParameters(list1);
            }
            // same as listOfParameters?
            if (list1.getName().contentEquals("listOfLocalParameters")) {
                listOfParameters(list1);
            }
        }
        // FIXME what about parameters?
        return law;
    }
}
