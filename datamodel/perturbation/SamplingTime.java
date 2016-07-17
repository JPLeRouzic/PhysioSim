// 
//  
// 

package datamodel.perturbation;

import datamodel.type.TimeUnitsDefinitionType;
import java.util.ArrayList;
import utility.XMLIdManager;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Vector;

public class SamplingTime 
{
    public static final DecimalFormat formatSamplingTimeMinutes;
    public static final double MINIMUM_SAMPLING_TIME_STEP_MINUTES = 1.0E-5;
    public static final double INFINITY_VALUE = 1.0E16;
    private static final String INFINITY_STRING = "Infinity";

    static SamplingTime[] sortSamplingTimes(ArrayList<SamplingTime> samplingTimes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static SamplingTime[] sortSamplingTimes(Vector<SamplingTime> localSamplingTimes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected String identity;

    protected double timeInMinutes;
    
    public SamplingTime() {
        this.timeInMinutes = 0.0;
        this.setIdentity(XMLIdManager.getXMLId((Class)SamplingTime.class));
    }
    
    public SamplingTime(final double timeInMinutes) {
        this.timeInMinutes = 0.0;
        this.setIdentity(XMLIdManager.getXMLId((Class)SamplingTime.class));
        if (timeInMinutes < 2.147483647E9) {
            this.timeInMinutes = (int)(timeInMinutes * 10000.0) / 10000.0;
        }
        else {
            this.timeInMinutes = timeInMinutes;
        }
    }
    
    public String getIdentity() {
        return this.identity;
    }
    
    public final void setIdentity(final String xmlId) {
        this.identity = xmlId;
    }
        
    public static double[] getDefaultSamplingTimes() {
        final double[] samplingTimeInMinutes = { 0.0, 1.0, 2.0, 5.0, 10.0, 15.0, 20.0, 30.0, 45.0, 60.0, 75.0, 90.0, 120.0, 180.0, 240.0, 480.0, 720.0, 1440.0 };
        return samplingTimeInMinutes;
    }
    
    static {
        formatSamplingTimeMinutes = new DecimalFormat("0.###");
    }

    public double getTimeInMinutes() {
        return timeInMinutes ;
    }

    public void setTimeInMinutes(double d) {
        timeInMinutes = d ;
    }
}
