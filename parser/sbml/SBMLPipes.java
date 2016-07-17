/*
 * <!ELEMENT root (pipes)*>

 <!--- Put your DTDDoc comment here. -->
 <!ELEMENT pipes (notes|pipePores)*>
 <!ATTLIST pipes
 thicknessMicrons CDATA #IMPLIED
 pipePoresHaveNegativeCharges CDATA #IMPLIED
 fractionVolumePipeArea CDATA #IMPLIED
 pipeAreaSqrCMPerScaleUnit CDATA #IMPLIED
 pipeCompositionType CDATA #IMPLIED
 scalePipeAreaDefinitionType CDATA #IMPLIED
 refVolumeForScaling CDATA #IMPLIED
 toSpaceIdRef CDATA #IMPLIED
 fromSpaceIdRef CDATA #IMPLIED
 name CDATA #IMPLIED
 xmlId CDATA #IMPLIED
 >

 <!--- Put your DTDDoc comment here. -->
 <!ELEMENT notes (#PCDATA)>

 <!--- Put your DTDDoc comment here. -->
 <!ELEMENT pipePores EMPTY>
 <!ATTLIST pipePores
 poreDiameterMicrons CDATA #IMPLIED
 poreAreaFraction CDATA #IMPLIED
 >
 */
package parser.sbml;

import parser.SBMLParserMain;
import datamodel.FluidPipe;
import datamodel.type.ReferenceVolumeType;
import datamodel.type.ScaleFlowRateDefinitionType;
import parser.ParserMng;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;

/**
 *
 * @author jplr
 */
public class SBMLPipes {

    SBMLNotes note;
    private final SBMLParserMain spm;
    ParserMng gui ;

    Map<Integer, FluidPipe> mapOfPipes = new HashMap<>();
    int indxOfPipes = 0;

    public SBMLPipes(ParserMng guiii, SBMLParserMain spmain, SBMLNotes not) {
        spm = spmain;
        note = not;
        gui = guiii ;
    }

    /**
     * <!ELEMENT root (pipes|pipeFamilies)*>
     *
     * @param pipes
     */
    public void parsePipes(Document pipes) {
        Element rootNode = null;
        List<Element> list = null;

        if (pipes != null) {
            rootNode = pipes.getRootElement();

            list = rootNode.getChildren();
        }
        for (Element un : list) {
            switch (un.getName()) {
                case "pipes":
                    FluidPipe one = pipesSBML(un);
                    gui.getColPipes().add(one);
                    break;
            }
        }
    }

    /**
     * <!ELEMENT pipes (notes)*>
     * <!ATTLIST pipes
     * flowRateMLperMin CDATA #IMPLIED
     * scaleFlowRateDefinitionType CDATA #IMPLIED
     * refVolumeForScaling CDATA #IMPLIED
     * toSpaceIdRef CDATA #IMPLIED
     * fromSpaceIdRef CDATA #IMPLIED
     * name CDATA #IMPLIED
     * xmlId CDATA #IMPLIED
     * >
     *
     * @param deux
     */
    private FluidPipe pipesSBML(Element deux) {
        FluidPipe intrnPipes = new FluidPipe();
        String quantityS;
        double quantityD;

        if (deux.getName().contentEquals("notes")) {
            note.notesSBML(deux);
        } 
        // flowRateMLperMin CDATA #IMPLIED
        if (deux.getAttribute("flowRateMLperMin") != null) {
            quantityS = deux.getAttributeValue("flowRateMLperMin");
            quantityD = Double.valueOf(quantityS);
            intrnPipes.setFlowRateMLPerMinPerScaleUnit(quantityD);
        } // scaleFlowRateDefinitionType CDATA #IMPLIED
        if (deux.getAttribute("scaleFlowRateDefinitionType") != null) {
            quantityS = deux.getAttributeValue("scaleFlowRateDefinitionType");
            ScaleFlowRateDefinitionType sfrdt = ScaleFlowRateDefinitionType.valueOf(quantityS);
            intrnPipes.setScaleFlowRateDefinitionType(sfrdt);
        } // refVolumeForScaling CDATA #IMPLIED
        if (deux.getAttribute("refVolumeForScaling") != null) {
            quantityS = deux.getAttributeValue("refVolumeForScaling");
            ReferenceVolumeType rst = ReferenceVolumeType.valueOf(quantityS);
            intrnPipes.setRefVolumeForScaling(rst);
        } 
        // toSpaceIdRef CDATA #IMPLIED
        // to volume
        if (deux.getAttribute("toSpaceIdRef") != null) {
            intrnPipes.setToVolumeStr = deux.getAttributeValue("toSpaceIdRef");
        } 
        // name CDATA #IMPLIED
        // from volume
        if (deux.getAttribute("fromSpaceIdRef") != null) {
            intrnPipes.setFromVolumeStr = deux.getAttributeValue("fromSpaceIdRef");
        } // name CDATA #IMPLIED
        if (deux.getAttribute("name") != null) {
            intrnPipes.setName(deux.getAttributeValue("name"));
        } // xmlId CDATA #IMPLIED
        if (deux.getAttribute("xmlId") != null) {
            intrnPipes.setIdentity(deux.getAttributeValue("xmlId"));
        }

        return intrnPipes;
    }
}
