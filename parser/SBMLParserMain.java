/*
 * Assemble an internal Placentalia representation from organs described in
 * SBML in a path
 */
package parser;

import datamodel.CompartmentSBML;
import datamodel.FluidPipe;
import datamodel.Tissue;
import parser.ParserMng;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.Attributes;
import parser.sbml.SBMLNotes;
import parser.sbml.SBMLPipes;
import parser.sbml.SbmlParser;
import utility.CollToArrayList;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class SBMLParserMain {

    public String SBMLversion;
    public String SBMLxmlns;
    public String SBMLlevel;

//    List<CompartmentSBML> globalReactions;
    SbmlParser us = null;

    // noote is used to share a method with other classes
    public SBMLNotes note;

    private final ParserMng gui;
    
    public SBMLParserMain(ParserMng guiii) {
        gui = guiii;
    }

    /**
     * Assemble an internal Placentalia representation from organs described in
     * SBML in a path
     *
     * @param path
     */
    public void parseSBML2Coll(final File path) {

        Attributes atts = null;
        SBMLNotes noote = new SBMLNotes();
        us = new SbmlParser(gui, this, noote);
        gui.setColPipes(new ArrayList()); 

        for (final File fileEntry : path.listFiles()) {
            if (fileEntry.isFile()) {
                FileInputStream file = null;
                try {
                    file = new FileInputStream(fileEntry);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                // File is "model.xml"
                if (fileEntry.getName().compareTo("model.xml") == 0) {
                    unmarshalModel(file);
                } else // File is "pipes.xml"
                // File is "pbpk.xml"
                if (fileEntry.getName().compareTo("pbpk.xml") == 0) {
// FIXME                    unmarshalPBPK(file);
                } else // File is "pipes.xml"
                if (fileEntry.getName().compareTo("pipes.xml") == 0) {
                    unmarshalPipes(file);
                }
            } else if (fileEntry.isDirectory()) {
                // File is "organs" folder
                if (fileEntry.getName().compareTo("organs") == 0) {
                    for (final File puella : fileEntry.listFiles()) {
                        FileInputStream fillote = null;
                        try {
                            fillote = new FileInputStream(puella);
                        } catch (FileNotFoundException ex) {
                            // logger.getLogger(Xml2Class.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    //  no knowledge about file names or Organ system
                    us.parseSBML(fillote);
                    }
                }
            } else {
                System.out.println("Entry is not a file: " + fileEntry.getName());
            }
        }

        // This will fill the "partOf" field with a pointer to the "Tissue" object, 
        // based on the "partOfString" field
        adjustPartOf(gui.getMapOfTissues());

        // this replaces the string pointing to a compartment in a class extending Connection
        // with a pointer to the actual compartment
        adjustCnxEndPoints();
    }

    /**
     * Process generalities about model
     *
     * @param file
     */
    private Document unmarshalModel(FileInputStream file) {
        Document doc = null;

        try {
            // now load this file
            /* we take the SAXBuilder for building a model out of a XML-File */
            SAXBuilder builder = new SAXBuilder();
            /* create a new JDOM Document with the SAXBuilder and the given file */
            doc = builder.build(file);

        } catch (JDOMException y) {
        }
        return doc;
    }

    private Document unmarshalPipes(
            FileInputStream file) {
        Document doc = null;

        try {
            // now load this file
            /* we take the SAXBuilder for building a model out of a XML-File */
            SAXBuilder builder = new SAXBuilder();
            /* create a new JDOM Document with the SAXBuilder and the given file */
            doc = builder.build(file);

            // Parsing of pipe file
            SBMLPipes pipes = new SBMLPipes(gui, this, note);

            // parsePipes will create both Collections of pipes and pipesFamilies
            pipes.parsePipes(doc);

        } catch (JDOMException y) {
        }
        return doc;
    }

    // This will fill the "partOf" field with a pointer to the "Tissue" object, 
    // based on the "partOfString" field
    private void adjustPartOf(Map<Integer, Tissue> mapOf) {
        CollToArrayList deux = new CollToArrayList();
        Tissue tiss = new Tissue();

        ArrayList<Tissue> tissArray = deux.convertTissue(mapOf);

        Iterator<Tissue> iter = tissArray.iterator();

        // for each Tissue in ArrayList
        while (iter.hasNext()) {
            tiss = iter.next();

            // if that Tissue is part of another Tissue, use the name of container
            // to find a pointer on that container object
            if (tiss.partOfString != null) {
                Iterator<Tissue> iter2 = tissArray.iterator();
                // iterate through all Tissues
                while (iter2.hasNext()) {
                    Tissue tiss2 = iter2.next();
                    // if both names are similar, then fill "tiss.partOf" with 
                    // a pointer to the container
                    if (tiss2.getIdentity().contentEquals(tiss.partOfString)) {
                        tiss.partOf = tiss2;
                    }
                }
            }
        }
    }

    // this replaces the string pointing to a compartment in a class extending Connection
    // with a pointer to the actual compartment
    private void adjustCnxEndPoints() {
        // process pipes
        adjustPipeEndPoints(gui.getColPipes());
    }

    // fill in FluidPipe's end points with pointers to the right compartment
    private void adjustPipeEndPoints(ArrayList<FluidPipe> tissArray) {
        CompartmentSBML comp = new CompartmentSBML(null);
        FluidPipe pipe;

        Iterator<FluidPipe> pipeIterator = tissArray.iterator();

        CollToArrayList deux = new CollToArrayList();
        ArrayList<CompartmentSBML> compArray = gui.getCompartments();

        // for each FluidPipe in ArrayList
        while (pipeIterator.hasNext()) {
            pipe = pipeIterator.next();

            // if that FluidPipe has any connection to another compartment, use the 
            // name of end point to find a pointer on that end point object
            if ((pipe.setFromVolumeStr != null) || (pipe.setToVolumeStr != null)) {
                // iterate through all Tissues
                Iterator<CompartmentSBML> compIterator = compArray.iterator();
                while (compIterator.hasNext()) {
                    comp = compIterator.next();
                    /* debug
                     if (comp.getIdentity().contentEquals("Space4842")) {
                     int y = 0;
                     } */
                    // if both names are similar, then fill "tiss.partOf" with 
                    // a pointer to the container
                    if (pipe.setFromVolumeStr.contentEquals(comp.getIdentity())) {
                        pipe.setFromVolume(comp);
                    }
                    if (pipe.setToVolumeStr.contentEquals(comp.getIdentity())) {
                        pipe.setToVolume(comp);
                    }
                    if ((pipe.getFromVolume() != null) && (pipe.getToVolume() != null)) {
                        // both end points are found, go for next pipe
                        break;
                    }
                    // Job done so go to next item
                }
                /* FIXME debug 
                if ((pipe.getFromVolume() == null) || (pipe.getToVolume() == null)) {
                    System.out.println(pipe.getIdentity()) ; 
                    System.out.println(pipe.getName()) ; 
                    System.out.println(pipe.getFromVolume()) ; 
                    System.out.println(pipe.getToVolume()) ; 
                    throw new UnsupportedOperationException("SBMLParserMain: Something wrong with FluidPipe connections.");
                } */
            }
        }
    }

    private void unmarshalPBPK(FileInputStream file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
