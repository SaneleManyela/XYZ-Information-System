/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.information.system;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Sanele
 */
public class clsSQLMethods {
    //An instance of class clsConnectToDatabase
    clsConnectToDatabase clsConnect = new clsConnectToDatabase();
       
    /* A public method that check a certain record exists in the
     * database and return a value of boolean depending on 
     * the result of executing a query passed as an argument
     * to the method.
    */
    public boolean mCheckIfRecordExists(String strQuery)
    {
        boolean boolAccountExists = false;
        Statement stStatement = null;
        ResultSet rs;
        try
        {
            stStatement = clsConnect.mConnectToXYZInformationSystem().prepareStatement(strQuery);
            stStatement.execute(strQuery);
            rs = stStatement.getResultSet();
            boolAccountExists = rs.next();
            stStatement.close();
            rs.close();
        }
        catch(SQLException eX)
        {
            JOptionPane.showMessageDialog(null, "The application has encountered a technical error.");
        }
        return boolAccountExists;
    }
    
    /*A method that insert daa into a database.
     *It accepts a string query as an argument
    */
    public void mCreateRecord(String strQuery)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToXYZInformationSystem().prepareStatement(strQuery)) {
                stStatement.executeUpdate(strQuery);
                stStatement.close();
                JOptionPane.showMessageDialog(null, "Record registered");
            }
	}
        catch(SQLException eX)
        {
            JOptionPane.showMessageDialog(null, " The record could not be created\n"+eX);
        }
    }
    /* A method that gets from the database
     * numeric data. It accepts a string
     * query and returns a value of type integer.
    */  
    public int mGetNumericColumn(String strQuery)
    {
        int id = 0;
        try
        {
            try (Statement stStatement = clsConnect.mConnectToXYZInformationSystem().prepareStatement(strQuery)) {
                stStatement.execute(strQuery);
                ResultSet rs = stStatement.getResultSet();
                while(rs.next()){
                    id = rs.getInt(1);
                }
                stStatement.close();
                rs.close();
            }
	}
        catch(SQLException | NullPointerException eX){
            JOptionPane.showMessageDialog(null, "Technical error has been encounterd, cannot return required integer column\n"+eX);
        }
       return id;
    }
    
    static String[] arrRecordDetails = null; // a declaration of an array of type string
    /* A method that fetches data from the database.
     * It takes a string query and return an array of
     * type string with the retrieved data.
    */
    public String[] mFetchRecordDetails(String strQuery)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToXYZInformationSystem().prepareStatement(strQuery)) {
                stStatement.execute(strQuery);
                ResultSet rs = stStatement.getResultSet();
                ResultSetMetaData rsmt = rs.getMetaData();
                arrRecordDetails = new String[rsmt.getColumnCount()+1];
                while(rs.next())
                {
                    for(int i = 1; i < arrRecordDetails.length; i++){
                        arrRecordDetails[i] = rs.getString(i);                    
                    }
                }
                stStatement.close();
                rs.close();
                return arrRecordDetails;
            }
	}
        catch(SQLException eX)
        {
            JOptionPane.showMessageDialog(null, eX);
        }
        return null;
    }
    
    /* A method that updates data in the database,
     * it takes a query in a string type and execute
     * that query to do an update transaction.
    */
    public void mUpdateRecordDetails(String strQuery)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToXYZInformationSystem().prepareStatement(strQuery)) {
                stStatement.executeUpdate(strQuery);
                stStatement.close();
                JOptionPane.showMessageDialog(null, "Record updated successfully.");
            }
        }
        catch(HeadlessException | SQLException eX)
        {
            JOptionPane.showMessageDialog(null, "Technical error, updated transaction could not be finished\n."+eX);
        }
    }
}
