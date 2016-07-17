/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.MenuAction;

import home.GUIMain;
import home.MenuDoAction;

/**
 *
 * @author Jean-Pierre Le Rouzic https://padiracinnovation.org/feedback/
 */
public class DoDrug {

    public DoDrug() {
       
    }

     // user hits the "Add Drug" button
   public void doAddDrug(GUIMain guiMn, MenuDoAction menuAction) {
        guiMn.simMain.st.addObject(guiMn, "node") ;
    }    

     // user hits the "Export Drug" button
    public void doExportDrug(MenuDoAction doMenuAction) {
    }

     // user hits the "Delete Drug" button
    public void doDeleteDrug(MenuDoAction doMenuAction) {
    }

     // user hits the "Import Drug" button
    public boolean doImportDrug(MenuDoAction doMenuAction) {
        return false;
    }
    
}
