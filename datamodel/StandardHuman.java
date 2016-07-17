/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import datamodel.type.GenderDefinitionType;

/**
 *
 * @author jplr
 */
public class StandardHuman {  
    
    private static GenderDefinitionType stdGender;
    private static double stdTemperature ;
    private static double stdAge ;
    private static double stdWeight ;
    private static double stdHeight ;
    private static int stdBMI ;
    public int NCOLOR;
    public static int NDISP = 2;
    public static int MAXINPUT;
    public static int MAXVARX = 8;
    public static int MAXDATAPLOT = 3 ;
    public static int NORGAN = 12;
    public static int NENZ;
    public int MAXNLUNG;
    public int MAXNEXTRA;
    public static int MAXADJ = 3;
    public static int NEXTRA = 3;
    public static int MAXEXP;
    public static int N;
    public static int MAXSOLUTE = 8 ;
    public static int MAXSEQUENCE = 8 ;
    public static int MAXORGAN = 12 ;
    public int MAXSTEP;
    public int N1dim;
    
    public StandardHuman() {
        stdGender = GenderDefinitionType.FEMALE ;
        stdTemperature = 37.5 ;
        stdAge = 40.0 ;
        
        // weight
        stdBMI = getStandardBMI();
        stdHeight = getStandardHeightInMeters();
        stdWeight = 1000.0 * stdBMI * stdHeight * stdHeight;

        this.MAXSOLUTE = 5;
        this.MAXINPUT = 11;
        this.MAXADJ = 6;
        this.MAXVARX = 15;
        this.MAXORGAN = 12;
        this.NCOLOR = 10;
        this.NENZ = 2;
        this.MAXNEXTRA = 4;
        this.NEXTRA = -1;
        this.NEXTRA = 2;
        this.N = this.MAXORGAN;
        this.N1dim = -1;
        this.MAXNLUNG = 16;
        this.NORGAN = this.MAXORGAN + this.MAXNEXTRA + this.MAXNLUNG + 10;
        this.MAXSTEP = 100;
        this.MAXDATAPLOT = 3;
        this.MAXSEQUENCE = 11;
        this.MAXEXP = 4;
    }

    public static double getStandardAgeInYears() {
        return stdAge;
    }
    
    public static double getStandardTemperature() {
        return stdTemperature ;
    }
    
    public static int getStandardBMI() {
        if (stdGender == GenderDefinitionType.MALE) {
            return 23;
        }
        return 22;
    }
    
    public static double getStandardHeightInMeters() {
        if (stdGender == GenderDefinitionType.MALE) {
            return 1.77;
        }
        return 1.63;
    }
    
    public static double getStandardBodyMassInGrams() {
        return stdWeight ;
    }

    public static GenderDefinitionType getGender() {       
        return stdGender ;
    }
    
    public double getBodyHeightInMeters() {
        return 170 ;
    }        
}
