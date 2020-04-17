/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.information.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sanele
 */
public class clsConnectToDatabase {
    /*A method that connects the user to the
    * database using the user's details
    * then returns that connection
    */
    public Connection mConnectToXYZInformationSystem()
    {
        String strDBConnectionString = "jdbc:mysql://localhost:3306/FutureLeaders";
    	String strDBUser = "root";
        String strDBPassword = "password";
        java.sql.Connection conMySQLConnectionString = null;
        try
        {
            conMySQLConnectionString = DriverManager.getConnection(strDBConnectionString, strDBUser, strDBPassword);
        }
        catch(SQLException eX)
        {
            JOptionPane.showMessageDialog(null, eX);
        }
        return conMySQLConnectionString;
    }
}
