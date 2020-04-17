/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.information.system;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Sanele
 */
public class frmStudentAccountPayment extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmStudentAccountPayment
     */
    public frmStudentAccountPayment() {
        initComponents();
        mPopulateStudentsNamesComboBox(mFetchStudentsNamesQuery());
        mDefaultGUIControls();
    }
    //Instantiation of objects of class clsConnectToDatabase, clsSQLMethods
    //and class frmStudentRegistrations
    clsConnectToDatabase clsConnect = new clsConnectToDatabase();
    clsSQLMethods clsQueryingMethods = new clsSQLMethods();
    frmStudentRegistrations frmStudRegistration = new frmStudentRegistrations();
    
    //A method to set default controls for this form
    private void mDefaultGUIControls()
    {
        cboStudentNames.setEnabled(false);
        txtAmountInRands.setEnabled(false);
        btnProccessPayment.setEnabled(true);
        btnCancel.setEnabled(false);
    }
    
    //A method that sets GUI controls when
    //the process button is clicked.
    private void mProccessPaymentGUIControls()
    {
        cboStudentNames.setEnabled(true);
        txtAmountInRands.setEnabled(true);
        btnProccessPayment.setText("Record Payment");
        btnCancel.setEnabled(true);
    }
    
    //A method that returns as String a query
    //that fetches student names from the database
    private String mFetchStudentsNamesQuery()
    {
        return "SELECT NAME FROM tblStudents";
    }
    
    //A method that connects to the database and load data to a 
    //combo box as values.
    private void mPopulateStudentsNamesComboBox(String strQuery)
    {
        try
        {
            try (Statement stStatement = clsConnect.mConnectToXYZInformationSystem().prepareStatement(strQuery)) {
                stStatement.execute(strQuery);
                try (ResultSet rs = stStatement.getResultSet()) {
                    while(rs.next())
                    {
                        cboStudentNames.addItem(rs.getString(1));
                    }
                    stStatement.close();
                    rs.close();
                }
            }
        }
        catch(SQLException eX)
        {
            JOptionPane.showMessageDialog(null,"A technical error has been encountered\n"+eX);
        }
    }
    
    //A method that uses the object clsclsQueryingMethods
    //to get a student id from the database then return it
    private int mStudentId()
    {
        return clsQueryingMethods.mGetNumericColumn("SELECT ID FROM tblStudents WHERE Name ='"+cboStudentNames.getSelectedItem().toString()+"'");
    }
        
    //A method that retrieves data from the database and calculate
    //fees that a student is yet to pay, and return the calculated
    //result as a value of type double.
    private double mAmountAlreadyPaid()
    {
        return (double)clsQueryingMethods.mGetNumericColumn("SELECT SUM(Amount) FROM tblAccounts WHERE Student='"+mStudentId()+"'");
    }
    
    /*A method that uses the object clsclsQueryingMethods
    * to get a amount of the class a student is enrolled in,
    * then subtract the amount the student is paying from 
    * that class amount to get the amount a student is yet to
    * to pay then return that amount as a value of type double.
    */
    private double mFeesToBePaid()
    {
        return (double)(clsQueryingMethods.mGetNumericColumn("SELECT ClassAmount FROM tblClass WHERE ID='"+
                clsQueryingMethods.mGetNumericColumn("SELECT ClassEnrolledIn FROM tblStudents WHERE Name ='"+
                 cboStudentNames.getSelectedItem().toString()+"'")+"'") - mAmountAlreadyPaid() - 
                Double.parseDouble(txtAmountInRands.getText()));
    }
    
    //A method that returns a String query that inserts
    //payment details to the database
    private String mRecordPayment()
    {
        return "INSERT INTO tblAccounts(Student, PaymentDate, Amount, FeesToBePaid)"
                    + "VALUES('"+mStudentId()+"','"+frmStudRegistration.mTodayDate()+
                    "','"+txtAmountInRands.getText()+"','"+mFeesToBePaid()+"')";
    }
    
    //A method to clear a combo box
    private void mClearComboBox()
    {
        String[] arrArray = new String[0];
        javax.swing.DefaultComboBoxModel model = new javax.swing.DefaultComboBoxModel(arrArray);
        cboStudentNames.setModel(model);
    }
    
    //A method to clear GUI input fields when a payment is cancelled
    private void mCancelPayment()
    {
        txtAmountInRands.setText("");
        mClearComboBox();
        mPopulateStudentsNamesComboBox(mFetchStudentsNamesQuery());
    }
    
    //A method that returns as integer a class id
    //retrieved from the database.
    private int mGetEnrolledInClassID()
    {
        return clsQueryingMethods.mGetNumericColumn("SELECT ClassEnrolledIn FROM tblStudents WHERE ID ='"+frmLogin.strUserID+"'");
    }
    
    //A method that returns as double type a yearly amount of a class.
    private double mGetClassYearlyAmount()
    {
        return (double)(clsQueryingMethods.mGetNumericColumn("SELECT ClassAmount FROM tblClass WHERE ID='"+
                mGetEnrolledInClassID()+"'"));
    }
    
    
    //A method that returns as String a query to view student details and their fees.
    private String mViewStudentDetailsAndFeesQuery()
    {
        return "SELECT Name, DOB, Gender, Address, ClassEnrolledIn, "
                +mGetClassYearlyAmount()+" AS ClassYearlyAmount, "
                +mAmountAlreadyPaid()+" AS AmountPaid, "
                +(mGetClassYearlyAmount()- mAmountAlreadyPaid())+" AS FeesToBePaid "
                + " FROM tblStudents WHERE ID ='"+frmLogin.strUserID+"'";
    }
    
    //A method that dispalys details in a tabular format
    private void mViewStudentDetailsAndFinances(String strQuery)
    {
        clsConnectToDatabase clsConnect = new clsConnectToDatabase();
        try
        {
            try (Statement stSQLQuery = clsConnect.mConnectToXYZInformationSystem().prepareStatement(strQuery)) {
                ResultSet rs = stSQLQuery.executeQuery(strQuery);
                ResultSetMetaData rsmt = rs.getMetaData();
                int intColumnCount = rsmt.getColumnCount();
                Vector vColumn = new Vector(intColumnCount);
                for(int i = 1; i <= intColumnCount; i++)
                {
                    vColumn.add(rsmt.getColumnName(i));
                }   Vector vData = new Vector();
                Vector vRow = new Vector();
                while(rs.next())
                {
                    vRow = new Vector(intColumnCount);
                    for(int i = 1; i <= intColumnCount; i++)
                    {
                        vRow.add(rs.getObject(i));
                    }
                    vData.add(vRow);
                }   
                JPanel pnlTable = new JPanel();
                JTable tblMembers = new JTable(vData, vColumn);
                tblMembers.setFillsViewportHeight(true);
                JScrollPane jspMemberPane = new JScrollPane(tblMembers);
                tblMembers.setVisible(true);
                tblMembers.validate();
                pnlTable.setLayout(new GridLayout(1,0));
                pnlTable.add(jspMemberPane);
                this.setResizable(true);
                this.setContentPane(pnlTable);
                stSQLQuery.close();
                rs.close();
            }
        }
        catch(SQLException eX)
        {
            JOptionPane.showMessageDialog(null, "Technical Error, table cannot be displayed"+""+eX);
        }
    }
    //A public method that invokes methods that make
    //it possible for student details and fees to be displayed
    public void mViewStudentDetailsAndFees()
    {
        mViewStudentDetailsAndFinances(mViewStudentDetailsAndFeesQuery());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dsktpStudentAccountPayment = new javax.swing.JDesktopPane();
        lblHeading = new javax.swing.JLabel();
        lblStudentName = new javax.swing.JLabel();
        cboStudentNames = new javax.swing.JComboBox<>();
        lblAmountInRands = new javax.swing.JLabel();
        txtAmountInRands = new javax.swing.JTextField();
        btnProccessPayment = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        dsktpStudentAccountPayment.setBackground(new java.awt.Color(255, 204, 153));
        dsktpStudentAccountPayment.setMinimumSize(new java.awt.Dimension(445, 408));
        dsktpStudentAccountPayment.setName(""); // NOI18N

        lblHeading.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHeading.setText("Student Account Payment");

        lblStudentName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStudentName.setText("Student Name");

        lblAmountInRands.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAmountInRands.setText("Amount In Rands");

        txtAmountInRands.setToolTipText("");

        btnProccessPayment.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnProccessPayment.setText("Process Payment");
        btnProccessPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProccessPaymentActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancel.setText("Cancel Payment");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        dsktpStudentAccountPayment.setLayer(lblHeading, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpStudentAccountPayment.setLayer(lblStudentName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpStudentAccountPayment.setLayer(cboStudentNames, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpStudentAccountPayment.setLayer(lblAmountInRands, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpStudentAccountPayment.setLayer(txtAmountInRands, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpStudentAccountPayment.setLayer(btnProccessPayment, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpStudentAccountPayment.setLayer(btnCancel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dsktpStudentAccountPaymentLayout = new javax.swing.GroupLayout(dsktpStudentAccountPayment);
        dsktpStudentAccountPayment.setLayout(dsktpStudentAccountPaymentLayout);
        dsktpStudentAccountPaymentLayout.setHorizontalGroup(
            dsktpStudentAccountPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpStudentAccountPaymentLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(dsktpStudentAccountPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dsktpStudentAccountPaymentLayout.createSequentialGroup()
                        .addComponent(btnProccessPayment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel))
                    .addGroup(dsktpStudentAccountPaymentLayout.createSequentialGroup()
                        .addGroup(dsktpStudentAccountPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStudentName)
                            .addComponent(lblAmountInRands))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                        .addGroup(dsktpStudentAccountPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboStudentNames, 0, 127, Short.MAX_VALUE)
                            .addComponent(txtAmountInRands))))
                .addGap(30, 30, 30))
            .addGroup(dsktpStudentAccountPaymentLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(lblHeading)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dsktpStudentAccountPaymentLayout.setVerticalGroup(
            dsktpStudentAccountPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpStudentAccountPaymentLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblHeading)
                .addGap(74, 74, 74)
                .addGroup(dsktpStudentAccountPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStudentName)
                    .addComponent(cboStudentNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(dsktpStudentAccountPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAmountInRands)
                    .addComponent(txtAmountInRands, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(dsktpStudentAccountPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProccessPayment)
                    .addComponent(btnCancel))
                .addGap(96, 96, 96))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dsktpStudentAccountPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpStudentAccountPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProccessPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProccessPaymentActionPerformed
        if(btnProccessPayment.getText().equals("Process Payment"))
        {
            mProccessPaymentGUIControls();
        }else if(btnProccessPayment.getText().equals("Record Payment")){
            if(txtAmountInRands.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Payment amount cannot be null");
                txtAmountInRands.requestFocusInWindow();
            }else{
                double dblPaymentAmountInRands;
                try{
                    dblPaymentAmountInRands = Double.parseDouble(txtAmountInRands.getText());
                    int intOption = JOptionPane.showConfirmDialog(null, "Student Name "+cboStudentNames.getSelectedItem().toString().toUpperCase()+"\n"+
                        "Amount = R"+dblPaymentAmountInRands, "Confirm Payment Entered Details", JOptionPane.YES_OPTION);
                    if(intOption == 0){
                        clsQueryingMethods.mCreateRecord(mRecordPayment());
                        txtAmountInRands.setText("");
                        mPopulateStudentsNamesComboBox(mFetchStudentsNamesQuery());
                        btnProccessPayment.setText("Process Payment");
                        mDefaultGUIControls();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "The payment was not processed.");
                    }
                }catch(HeadlessException | NumberFormatException e){
                    JOptionPane.showMessageDialog(null, txtAmountInRands.getText()+" is Not a number!");
                    txtAmountInRands.setText("");
                    txtAmountInRands.requestFocusInWindow();
                }
            }
        }
    }//GEN-LAST:event_btnProccessPaymentActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        mCancelPayment();
        mDefaultGUIControls();
        btnProccessPayment.setText("Process Payment");
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnProccessPayment;
    private javax.swing.JComboBox<String> cboStudentNames;
    private javax.swing.JDesktopPane dsktpStudentAccountPayment;
    private javax.swing.JLabel lblAmountInRands;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblStudentName;
    private javax.swing.JTextField txtAmountInRands;
    // End of variables declaration//GEN-END:variables
}
