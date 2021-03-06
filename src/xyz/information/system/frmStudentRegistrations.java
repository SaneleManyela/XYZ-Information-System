/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.information.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Sanele
 */
public class frmStudentRegistrations extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmStudentRegistrations
     */
    public frmStudentRegistrations() {
        initComponents();
        txtNames.requestFocusInWindow();
        txtDateOfBirth.setText(mTodayDate().replace("/", "-"));
    }
    
    boolean boolAccountExists = false; //Declaration of a boolean variable
    clsSQLMethods clsQueryingMethods = new clsSQLMethods();
    
    //A public method that returns current date
    //in string format
    public static String mTodayDate()
    {
        Date dt = new Date();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        return sm.format(dt);
    }
      
    //A method that returns a String query that 
    //stores student details in the database
    private String mRegisterStudentAccountQuery()
    {
        return "INSERT INTO tblStudents(Name, DOB, Gender, Password, Address, ClassEnrolledIn, RegisteredBy)"
                    + "VALUES('"+txtNames.getText().toLowerCase()+"','"+txtDateOfBirth.getText()+
                    "','"+cboGender.getSelectedItem().toString()+"','"+txtPassword.getText().toLowerCase()+"','"+txtAddress.getText()+
                "','"+clsQueryingMethods.mGetNumericColumn("SELECT ID FROM tblClass WHERE Class='"+cboClassToEnrollIn.getSelectedItem().toString()+"'")+
                "','"+frmLogin.strUserID+"')";
    }
    
    //A method that checks if student has already been registered
    private String mCheckIfStudentRegistrationExistQuery()
    {
        return "SELECT * FROM tblStudents WHERE Name='"+txtNames.getText().toLowerCase()+"' AND DOB='"+
                txtDateOfBirth.getText()+"' AND Address='"+txtAddress.getText()+"'";
    }
    
    //A method that clear GUI text boxes
    private void mClearTextFields()
    {
        txtNames.setText("");
        txtPassword.setText("");
        txtAddress.setText("");
    }
    
    //A method that returns as String a query
    //that fetches student details from the database
    private String mFetchStudentDetailsQuery()
    {
        return "SELECT Name, DOB, Gender, Password, Address FROM tblStudents WHERE ID='"+frmLogin.strUserID+"'";
    }
    
    //A method that set student details to the GUI
    private void mSetGUIValues()
    {
        String[] arrStudentDetails = clsQueryingMethods.mFetchRecordDetails(mFetchStudentDetailsQuery());
        txtNames.setText(arrStudentDetails[1]);
        txtDateOfBirth.setText(arrStudentDetails[2]);
        cboGender.setSelectedItem(arrStudentDetails[3]);
        txtPassword.setText(arrStudentDetails[4]);
        txtAddress.setText(arrStudentDetails[5]);
        
        String[] arrClassEnrolledIn = clsQueryingMethods.mFetchRecordDetails("SELECT Class FROM tblClass WHERE ID='"+
                clsQueryingMethods.mGetNumericColumn("SELECT ClassEnrolledIn FROM tblStudents WHERE ID='"+frmLogin.strUserID+"'")+"'");
        cboClassToEnrollIn.setSelectedItem(arrClassEnrolledIn[1]);
        cboClassToEnrollIn.setEnabled(false);
    }
    
    //A method that returns as string, a query that updates student details
    private String mUpdateStudentDetailsQuery()
    {
        return "UPDATE tblStudents SET Name ='"+txtNames.getText().toLowerCase()+"', DOB='"+
                txtDateOfBirth.getText()+"', Gender='"+cboGender.getSelectedItem().toString()+"', Password='"+
                txtPassword.getText().toLowerCase()+"', Address='"+txtAddress.getText()+"', ClassEnrolledIn='"+
                clsQueryingMethods.mGetNumericColumn("SELECT ID FROM tblClass WHERE Class='"+
                cboClassToEnrollIn.getSelectedItem().toString()+"'")+"' WHERE ID='"+frmLogin.strUserID+"'";        
    }
    
    private void mSetAccountManagementDefaultGUIControls()
    {
        cboClassToEnrollIn.setEnabled(false);
        btnRegister.setText("Update");
        btnRegister.setEnabled(true);
        btnClear.setText("Save");
        btnClear.setEnabled(false);
        btnClose.setEnabled(true);
        btnClose.setText("Close");
    }
    
    public void mSetAccountManagementGUI()
    {
        lblStudentRegistration.setText("Manage Your Details");
        mSetAccountManagementDefaultGUIControls();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dsktpRegisterStudents = new javax.swing.JDesktopPane();
        lblStudentRegistration = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtNames = new javax.swing.JTextField();
        lblDateOfBirth = new javax.swing.JLabel();
        txtDateOfBirth = new javax.swing.JTextField();
        lblGender = new javax.swing.JLabel();
        cboGender = new javax.swing.JComboBox<>();
        lblPassword = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        jspAddressPane = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        lblClassToEnrollIn = new javax.swing.JLabel();
        cboClassToEnrollIn = new javax.swing.JComboBox<>();
        txtPassword = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        dsktpRegisterStudents.setBackground(new java.awt.Color(255, 204, 153));

        lblStudentRegistration.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblStudentRegistration.setText("Student Registration");

        lblName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblName.setText("Name(s)");

        lblDateOfBirth.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDateOfBirth.setText("Date Of Birth");

        lblGender.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGender.setText("Gender");

        cboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPassword.setText("Password");

        lblAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAddress.setText("Address");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jspAddressPane.setViewportView(txtAddress);

        lblClassToEnrollIn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblClassToEnrollIn.setText("Class To Enroll In ");

        cboClassToEnrollIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grade 1 A", "Grade 1 B", "Grade 2 A", "Grade 2 B", "Grade 3 A", "Grade 3 B", "Grade 4 A", "Grade 4 B", "Grade 5 A", "Grade 5 B", "Grade 6 A", "Grade 6 B", "Grade 7 A", "Grade 7 B", "Grade 8 A", "Grade 8 B", "Grade 9 A", "Grade 9 B" }));

        btnRegister.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        dsktpRegisterStudents.setLayer(lblStudentRegistration, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(lblName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(txtNames, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(lblDateOfBirth, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(txtDateOfBirth, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(lblGender, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(cboGender, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(lblPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(lblAddress, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(jspAddressPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(lblClassToEnrollIn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(cboClassToEnrollIn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(txtPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(btnRegister, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(btnClear, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpRegisterStudents.setLayer(btnClose, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dsktpRegisterStudentsLayout = new javax.swing.GroupLayout(dsktpRegisterStudents);
        dsktpRegisterStudents.setLayout(dsktpRegisterStudentsLayout);
        dsktpRegisterStudentsLayout.setHorizontalGroup(
            dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dsktpRegisterStudentsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblStudentRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(196, 196, 196))
            .addGroup(dsktpRegisterStudentsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dsktpRegisterStudentsLayout.createSequentialGroup()
                        .addComponent(lblPassword)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dsktpRegisterStudentsLayout.createSequentialGroup()
                        .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClassToEnrollIn)
                            .addComponent(lblAddress))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dsktpRegisterStudentsLayout.createSequentialGroup()
                        .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(dsktpRegisterStudentsLayout.createSequentialGroup()
                                .addComponent(btnRegister)
                                .addGap(140, 140, 140)
                                .addComponent(btnClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                                .addComponent(btnClose))
                            .addGroup(dsktpRegisterStudentsLayout.createSequentialGroup()
                                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDateOfBirth)
                                    .addComponent(lblGender)
                                    .addComponent(lblName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNames)
                                    .addComponent(jspAddressPane)
                                    .addComponent(txtDateOfBirth)
                                    .addComponent(cboGender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPassword)
                                    .addComponent(cboClassToEnrollIn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(40, 40, 40))))
        );
        dsktpRegisterStudentsLayout.setVerticalGroup(
            dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpRegisterStudentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStudentRegistration)
                .addGap(40, 40, 40)
                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateOfBirth)
                    .addComponent(txtDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGender)
                    .addComponent(cboGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress)
                    .addComponent(jspAddressPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClassToEnrollIn)
                    .addComponent(cboClassToEnrollIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(dsktpRegisterStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister)
                    .addComponent(btnClear)
                    .addComponent(btnClose))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpRegisterStudents)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpRegisterStudents)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        if(btnRegister.getText().equals("Register")){
            if(txtNames.getText().equals("")){
                JOptionPane.showMessageDialog(null, "The Names field cannot be left empty.");
                txtNames.requestFocusInWindow();
            }
            else if(txtDateOfBirth.getText().equals("")){
                JOptionPane.showMessageDialog(null, "The date of birth field cannot be left empty.");
                txtDateOfBirth.requestFocusInWindow();
            }
            else if(txtPassword.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Password field cannot be left empty");
                txtPassword.requestFocusInWindow();
            }
            else if(txtAddress.getText().equals("")){
                JOptionPane.showMessageDialog(null, "An address of a student is required");
                txtAddress.requestFocusInWindow();
            }
            else{
                boolAccountExists = clsQueryingMethods.mCheckIfRecordExists(mCheckIfStudentRegistrationExistQuery());
                if(boolAccountExists){
                    JOptionPane.showMessageDialog(null, "This student is already registered.");
                }else{
                    clsQueryingMethods.mCreateRecord(mRegisterStudentAccountQuery());
                    mClearTextFields();
                }
            }
        }else
        {
            mSetGUIValues();
            btnRegister.setEnabled(false);
            btnClear.setEnabled(true);
            btnClose.setText("Cancel");
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        if(btnClear.getText().equals("Clear")){
            mClearTextFields();
        }else{
            if(txtNames.getText().equals("")){
                JOptionPane.showMessageDialog(null, "The Names field cannot be left empty.");
                txtNames.requestFocusInWindow();
            }
            else if(txtDateOfBirth.getText().equals("")){
                JOptionPane.showMessageDialog(null, "The date of birth field cannot be left empty.");
                txtDateOfBirth.requestFocusInWindow();
            }
            else if(txtPassword.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Password field cannot be left empty");
                txtPassword.requestFocusInWindow();
            }
            else if(txtAddress.getText().equals("")){
                JOptionPane.showMessageDialog(null, "An address of a student is required");
                txtAddress.requestFocusInWindow();
            }
            else{
                clsQueryingMethods.mUpdateRecordDetails(mUpdateStudentDetailsQuery());
                mClearTextFields();
                mSetAccountManagementDefaultGUIControls();
            }
        }
        
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        if (btnClose.getText().equals("Close")) {
            this.hide();
        }else{
            mClearTextFields();
            mSetAccountManagementDefaultGUIControls();
        }
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRegister;
    private javax.swing.JComboBox<String> cboClassToEnrollIn;
    private javax.swing.JComboBox<String> cboGender;
    private javax.swing.JDesktopPane dsktpRegisterStudents;
    private javax.swing.JScrollPane jspAddressPane;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblClassToEnrollIn;
    private javax.swing.JLabel lblDateOfBirth;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblStudentRegistration;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtDateOfBirth;
    private javax.swing.JTextField txtNames;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
