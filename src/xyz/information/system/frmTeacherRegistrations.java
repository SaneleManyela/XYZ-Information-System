/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.information.system;

import java.awt.GridLayout;
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
public class frmTeacherRegistrations extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmTeacherRegistrations
     * @param strRegisterWho
     */
    public frmTeacherRegistrations(String strRegisterWho) {
        initComponents();
        txtNames.requestFocusInWindow();
        switch (strRegisterWho) {
            case "registrar":
                lblHeading.setText("Register Registrars");
                txtPassword.setText("r");
                break;
            case "teacher":
                txtPassword.setText("t");
                break;
            case "bursar":
                lblHeading.setText("Register Bursars");
                txtPassword.setText("b");
                break;
        }
    }
    
    boolean boolAccountExists = false; //Declaration of a boolean variable
    clsSQLMethods clsQueryingMethods = new clsSQLMethods();
    public String strRegisterWhichUser; 
    
    //A method that accepts as an argument a table name
    //to be used in the query that will be used to isert
    // a record to the database and is returned as a string
    private String mRegisterAccountQuery(String tblTable)
    {
        return "INSERT INTO "+tblTable+"(Name, Password)"
                    + "VALUES('"+txtNames.getText().toLowerCase()+"','"+txtPassword.getText().toLowerCase()+"')";
    }
    
    //A method that accepts as an argument a table name
    //to be used in the query that will be used to check
    // a record exists in the database and is returned as a string
    private String mCheckIfRegistrationExistQuery(String tblTable)
    {
        return "SELECT * FROM "+tblTable+" WHERE Name='"+txtNames.getText().toLowerCase()+"' AND Password='"+
                txtPassword.getText().toLowerCase()+"'";
    }
    
    private void mClearTextFields()
    {
        txtNames.setText(null);
        txtPassword.setText(null);
    }
    
    private double mGetClassAmount()
    {
        return (double)clsQueryingMethods.mGetNumericColumn("SELECT ClassAmount FROM tblClass WHERE ClassTeacher ='"+frmLogin.strUserID+"'");
    }
    
    //A method to display student enrolled in a teacher's class
    private String mViewStudentEnrolledInClassOfTeacherXQuery()
    {
        return "SELECT Name, DOB, Gender, Address, "+mGetClassAmount()+" AS ClassAmount,"
                + "(SELECT SUM(Amount) FROM tblAccounts, tblClass "
                + "WHERE Student = tblStudents.ID AND tblClass.ID = tblStudents.ClassEnrolledIn) AS AmountPaid, "
                + "("+mGetClassAmount()+" - (SELECT SUM(Amount) FROM tblAccounts, tblClass "
                + "WHERE Student = tblStudents.ID AND tblClass.ID = tblStudents.ClassEnrolledIn)) AS YetToPay"
                + " FROM tblStudents WHERE ClassEnrolledIn='"+
                clsQueryingMethods.mGetNumericColumn("SELECT ID FROM tblClass WHERE ClassTeacher='"+
                frmLogin.strUserID+"'")+"'";
    }
    
    //A method that takes as an argument a query and display
    //student details and financial standings.
    private void mViewStudentsDetailsAndFinances(String strQuery)
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
    
    //A method that takes a query as argument
    //and decide what information to display 
    //in a tabular format. 
    public void mViewStudentsDetails(String strQuery)
    {
        switch(frmMain.strUserIdentifier){
            case "teacher":
                mViewStudentsDetailsAndFinances(mViewStudentEnrolledInClassOfTeacherXQuery());
                break;
            case "bursar":
                mViewStudentsDetailsAndFinances(strQuery);
                break;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dsktpTeacherRegistration = new javax.swing.JDesktopPane();
        lblHeading = new javax.swing.JLabel();
        lblNames = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtNames = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        dsktpTeacherRegistration.setBackground(new java.awt.Color(255, 204, 153));

        lblHeading.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHeading.setText("Register Teachers");

        lblNames.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNames.setText("Names");

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPassword.setText("Password");

        btnRegister.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClose.setText("Close");
        btnClose.setPreferredSize(new java.awt.Dimension(89, 25));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setPreferredSize(new java.awt.Dimension(89, 25));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        dsktpTeacherRegistration.setLayer(lblHeading, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTeacherRegistration.setLayer(lblNames, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTeacherRegistration.setLayer(lblPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTeacherRegistration.setLayer(txtNames, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTeacherRegistration.setLayer(txtPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTeacherRegistration.setLayer(btnRegister, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTeacherRegistration.setLayer(btnClose, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpTeacherRegistration.setLayer(btnClear, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dsktpTeacherRegistrationLayout = new javax.swing.GroupLayout(dsktpTeacherRegistration);
        dsktpTeacherRegistration.setLayout(dsktpTeacherRegistrationLayout);
        dsktpTeacherRegistrationLayout.setHorizontalGroup(
            dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpTeacherRegistrationLayout.createSequentialGroup()
                .addGroup(dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dsktpTeacherRegistrationLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dsktpTeacherRegistrationLayout.createSequentialGroup()
                                .addComponent(btnRegister)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dsktpTeacherRegistrationLayout.createSequentialGroup()
                                .addGroup(dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNames)
                                    .addComponent(lblPassword))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNames, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(dsktpTeacherRegistrationLayout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 125, Short.MAX_VALUE)))
                .addGap(40, 40, 40))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dsktpTeacherRegistrationLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblHeading)
                .addGap(133, 133, 133))
        );
        dsktpTeacherRegistrationLayout.setVerticalGroup(
            dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpTeacherRegistrationLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblHeading)
                .addGap(83, 83, 83)
                .addGroup(dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNames)
                    .addComponent(txtNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(dsktpTeacherRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpTeacherRegistration)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpTeacherRegistration)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        if(txtNames.getText().equals("")){
            JOptionPane.showMessageDialog(null, "The Names field cannot be left empty.");
            txtNames.requestFocusInWindow();
        }
        else if(txtPassword.getText().equals("")){
            JOptionPane.showMessageDialog(null, "The Password field cannot be left empty.");
            txtPassword.requestFocusInWindow();
        }
        else{
            switch(strRegisterWhichUser){
                case "teacher":
                    if(txtPassword.getText().startsWith("t")){
                        boolAccountExists = clsQueryingMethods.mCheckIfRecordExists(mCheckIfRegistrationExistQuery("tblTeachers"));
                        if(boolAccountExists){
                            JOptionPane.showMessageDialog(null, "This teacher is already registered.");
                        }else{
                            clsQueryingMethods.mCreateRecord(mRegisterAccountQuery("tblTeachers"));
                            mClearTextFields();
                            txtPassword.setText("t");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Teacher passwords must be prefixed with 't'");
                        txtPassword.setText("t");
                    }
                    break;
                case "registra":
                    if(txtPassword.getText().startsWith("r")){
                        boolAccountExists = clsQueryingMethods.mCheckIfRecordExists(mCheckIfRegistrationExistQuery("tblRegistrars"));
                        if(boolAccountExists){
                            JOptionPane.showMessageDialog(null, "This registrar is already registered.");
                        }else{
                            clsQueryingMethods.mCreateRecord(mRegisterAccountQuery("tblRegistrars"));
                            mClearTextFields();
                            txtPassword.setText("r");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Registra passwords must be prifixed with 'r'");
                        txtPassword.setText("r");
                    }
                    break;
            }
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        mClearTextFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.hide();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRegister;
    private javax.swing.JDesktopPane dsktpTeacherRegistration;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblNames;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JTextField txtNames;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
