/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.information.system;

import static java.awt.Frame.MAXIMIZED_BOTH;

/**
 *
 * @author Sanele
 */
public class XYZInformationSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*This is where the system begins.
        * An object of frmLogin is instantiated
        * and displayed.
        */ 
        frmLogin frmlogin = new frmLogin();
        frmlogin.setExtendedState(MAXIMIZED_BOTH);
        frmlogin.setTitle("XYZ Information System Login Screen");
        frmlogin.show();
    }
    
}
