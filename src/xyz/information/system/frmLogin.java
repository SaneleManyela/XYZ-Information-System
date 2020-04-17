/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.information.system;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sanele
 */
public class frmLogin extends javax.swing.JFrame {

    /**
     * Creates new form frmLogin
     */
    public frmLogin() {
        initComponents();
        txtUsername.requestFocusInWindow(); // This requests a cursor to on the textbox
    }
    public static String strUserID; // a declaration of a public static string variable.
    // A declation of two private string variables
    String strAccountName;
    String strAccountPassword;
    
    //An instantiation of an object of type clsSQLMethods
    clsSQLMethods clsQueryingMethods = new clsSQLMethods();
    
    //A method that returns a string, a query that authenticates students
    private String mAuthenticateStudent()
    {
        return "SELECT ID, Name, Password FROM tblStudents WHERE Name ='"
                +txtUsername.getText().toLowerCase()+"' AND Password='"
                +txtPassword.getText().toLowerCase()+"'";
    }
    //A method that returns a string, a query that authenticates teachers
    private String mAuthenticateTeacher()
    {
        return "SELECT ID, Name, Password FROM tblTeachers WHERE Name ='"
                +txtUsername.getText().toLowerCase()+"' AND Password='"
                +txtPassword.getText().toLowerCase()+"'";
    }
    //A method that returns a string, a query that authenticates registrars
    private String mAuthenticateRegistrar()
    {
        return "SELECT ID, Name, Password FROM tblRegistrars WHERE Name ='"
                +txtUsername.getText().toLowerCase()+"' AND Password='"
                +txtPassword.getText().toLowerCase()+"'";
    }    
    // A private method to authenticate users who are bursars. It returns a string.
    private String mAuthenticateBursar()
    {
        return "SELECT ID, Name, Password FROM tblBursar WHERE Name ='"
                +txtUsername.getText().toLowerCase()+"' AND Password='"
                +txtPassword.getText().toLowerCase()+"'";
    }
    /*A method that uses the previously intantiated object, clsQueryingMethods,
     *to query the database for data that is assigned to string varables.
    */
    private void mAssignVariables(String strQuery)
    {
        String[] arrLoginDetails = clsQueryingMethods.mFetchRecordDetails(strQuery);      
        frmLogin.strUserID = arrLoginDetails[1];
        this.strAccountName = arrLoginDetails[2];
        this.strAccountPassword = arrLoginDetails[3];
    }
    /*A method that logs in a user. It first check if 
    * the text boxes are passed input, if so it checks
    * enters password in order to identify the user.
    * Depending on the user an object of type frmMain
    * is instantiated and a method defined in frmMain
    * is accessed to set a user's suitable priveleges
    * in the system. A user can then access the inner
    * system given their login credentials are correct.
    */
    private void mLogin()
    {
        try
        {
            if(txtUsername.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Username is required");
                txtUsername.requestFocusInWindow();
            }
            else if(txtPassword.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Password is required");
                txtPassword.requestFocusInWindow();
            }
            else
            {
                String strIdentifier;
                if(txtPassword.getText().equals("71004857")){
                    strIdentifier = "registrar";
                }else if(txtPassword.getText().toLowerCase().startsWith("t")){
                    strIdentifier = "teacher";
                }else if(txtPassword.getText().toLowerCase().startsWith("r")){
                    strIdentifier = "registrar";
                }else if(txtPassword.getText().toLowerCase().startsWith("b")){
                    strIdentifier = "bursar";
                }else{
                    strIdentifier = "student";
                }
                
                if(txtUsername.getText().toUpperCase().equals("NOKUBONGA") &&
                                txtPassword.getText().equals("71004857"))
                {
                    frmMain frmMn = new frmMain();
                    frmMain.strUserIdentifier = "registrar";
                    frmMn.mXYZInformationSystemUserAccessControl();
                    frmMn.setTitle("XYZ Information System");
                    frmMn.setVisible(true);
                    this.setVisible(false);
                    frmMn.setExtendedState(MAXIMIZED_BOTH);
                }else{
                    switch(strIdentifier){
                        case "student":
                            mAssignVariables(mAuthenticateStudent());
                            if(strAccountName.equals(txtUsername.getText().toLowerCase()) && 
                                strAccountPassword.equals(txtPassword.getText().toLowerCase()))
                            {
                                frmMain frmMn = new frmMain();
                                frmMain.strUserIdentifier = "student";
                                frmMn.mXYZInformationSystemUserAccessControl();
                                frmMn.setTitle("XYZ Information System");
                                frmMn.setVisible(true);
                                this.setVisible(false);
                                frmMn.setExtendedState(MAXIMIZED_BOTH);
                            }else{
                                JOptionPane.showMessageDialog(null, "Access Denied!! Check account name or password");
                            }
                            break;
                        case "teacher":
                            mAssignVariables(mAuthenticateTeacher());
                            if(strAccountName.equals(txtUsername.getText().toLowerCase()) && 
                                strAccountPassword.equals(txtPassword.getText().toLowerCase()))
                            {
                                frmMain frmMn = new frmMain();
                                frmMain.strUserIdentifier = "teacher";
                                frmMn.mXYZInformationSystemUserAccessControl();
                                frmMn.setTitle("XYZ Information System");
                                frmMn.setVisible(true);
                                this.setVisible(false);
                                frmMn.setExtendedState(MAXIMIZED_BOTH);
                            }
                            break;
                        case "bursar":
                            mAssignVariables(mAuthenticateBursar());
                            if(strAccountName.equals(txtUsername.getText().toLowerCase()) && 
                                strAccountPassword.equals(txtPassword.getText().toLowerCase()))
                            {
                                frmMain frmMn = new frmMain();
                                frmMain.strUserIdentifier = "bursar";
                                frmMn.mXYZInformationSystemUserAccessControl();
                                frmMn.setTitle("XYZ Information System");
                                frmMn.setVisible(true);
                                this.setVisible(false);
                                frmMn.setExtendedState(MAXIMIZED_BOTH);
                            }
                            break;
                        case "registrar":
                            mAssignVariables(mAuthenticateRegistrar());
                            if(strAccountName.equals(txtUsername.getText().toLowerCase()) && 
                                strAccountPassword.equals(txtPassword.getText().toLowerCase()))
                            {
                                frmMain frmMn = new frmMain();
                                frmMain.strUserIdentifier = "registrar";
                                frmMn.mXYZInformationSystemUserAccessControl();
                                frmMn.setTitle("XYZ Information System");
                                frmMn.setVisible(true);
                                this.setVisible(false);
                                frmMn.setExtendedState(MAXIMIZED_BOTH);
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Account does not exist");
                    }
                }
            }
        }
        catch(HeadlessException | NullPointerException eX)
        {
            JOptionPane.showMessageDialog(null, "Access Denied!! Check account name or password");
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

        dsktpLogin = new javax.swing.JDesktopPane();
        lblLoginHeading = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtUsername = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dsktpLogin.setBackground(new java.awt.Color(255, 204, 153));

        lblLoginHeading.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblLoginHeading.setText("XYZ Secondary Login");
        dsktpLogin.add(lblLoginHeading);
        lblLoginHeading.setBounds(520, 30, 290, 29);

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblUsername.setText("Username");
        dsktpLogin.add(lblUsername);
        lblUsername.setBounds(270, 200, 92, 22);

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPassword.setText("Password");
        dsktpLogin.add(lblPassword);
        lblPassword.setBounds(270, 350, 87, 22);

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        dsktpLogin.add(txtPassword);
        txtPassword.setBounds(870, 340, 290, 40);
        dsktpLogin.add(txtUsername);
        txtUsername.setBounds(870, 190, 290, 41);

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        dsktpLogin.add(btnLogin);
        btnLogin.setBounds(500, 540, 300, 31);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 1189, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        mLogin(); //This button event invokes the mLogin method explained above
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JDesktopPane dsktpLogin;
    private javax.swing.JLabel lblLoginHeading;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
