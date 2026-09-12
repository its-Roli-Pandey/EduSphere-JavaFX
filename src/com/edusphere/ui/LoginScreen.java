package com.edusphere.ui;

import com.edusphere.dao.UserDAO;
import com.edusphere.model.User;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LoginScreen extends JDialog {
    private JTextField txtUsername, txtCaptchaInput;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JButton btnLogin, btnGoRegister, btnRefreshCaptcha;
    private JCheckBox chkShowPassword;
    private JLabel lblCaptchaDisplay;
    private String generatedCaptcha;

    public LoginScreen(JFrame parentFrame) {
        super(parentFrame, "EduSphere Secure Gateway", true);
        setSize(420, 520);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setLayout(null);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(15, 23, 42));
        mainPanel.setBounds(0, 0, 420, 520);
        add(mainPanel);

        JLabel lblTitle = new JLabel("SECURE ACCESS GATEWAY", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(56, 189, 248));
        lblTitle.setBounds(30, 20, 360, 25);
        mainPanel.add(lblTitle);

        int startY = 65; int spacing = 58;

        JLabel lblProfile = new JLabel("IDENTITY PROFILE:");
        lblProfile.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblProfile.setForeground(Color.LIGHT_GRAY);
        lblProfile.setBounds(40, startY, 200, 15);
        mainPanel.add(lblProfile);

        String[] roles = {"Admin System", "Faculty Teacher", "Student Portal"};
        cmbRole = new JComboBox<>(roles);
        cmbRole.setBounds(40, startY + 18, 340, 30);
        mainPanel.add(cmbRole);

        JLabel lblUser = new JLabel("USERNAME KEY:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setBounds(40, startY + spacing, 200, 15);
        mainPanel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(40, startY + spacing + 18, 340, 30);
        mainPanel.add(txtUsername);

        JLabel lblPass = new JLabel("PASSWORD LOCK:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPass.setForeground(Color.LIGHT_GRAY);
        lblPass.setBounds(40, startY + (spacing * 2), 200, 15);
        mainPanel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, startY + (spacing * 2) + 18, 340, 30);
        mainPanel.add(txtPassword);

        chkShowPassword = new JCheckBox("Show Password");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkShowPassword.setForeground(Color.LIGHT_GRAY);
        chkShowPassword.setOpaque(false);
        chkShowPassword.setBounds(40, startY + (spacing * 2) + 52, 150, 20);
        mainPanel.add(chkShowPassword);

        lblCaptchaDisplay = new JLabel("", JLabel.CENTER);
        lblCaptchaDisplay.setFont(new Font("Consolas", Font.BOLD, 18));
        lblCaptchaDisplay.setForeground(new Color(239, 68, 68));
        lblCaptchaDisplay.setBackground(new Color(30, 41, 59));
        lblCaptchaDisplay.setOpaque(true);
        lblCaptchaDisplay.setBounds(40, startY + (spacing * 3) + 20, 100, 30);
        mainPanel.add(lblCaptchaDisplay);

        btnRefreshCaptcha = new JButton("🔄");
        btnRefreshCaptcha.setBounds(145, startY + (spacing * 3) + 20, 45, 30);
        mainPanel.add(btnRefreshCaptcha);

        txtCaptchaInput = new JTextField();
        txtCaptchaInput.setHorizontalAlignment(JTextField.CENTER);
        txtCaptchaInput.setBounds(200, startY + (spacing * 3) + 20, 180, 30);
        mainPanel.add(txtCaptchaInput);

        btnLogin = new JButton("Access Terminal Session");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(34, 197, 94));
        btnLogin.setBounds(40, 360, 340, 36);
        mainPanel.add(btnLogin);

        btnGoRegister = new JButton("New User? Provision Account Gateway");
        btnGoRegister.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnGoRegister.setForeground(new Color(56, 189, 248));
        btnGoRegister.setContentAreaFilled(false); btnGoRegister.setBorderPainted(false);
        btnGoRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGoRegister.setBounds(40, 410, 340, 25);
        mainPanel.add(btnGoRegister);

        generateNewCaptcha();

        chkShowPassword.addActionListener(e -> {
            txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '•');
        });

        btnRefreshCaptcha.addActionListener(e -> generateNewCaptcha());

        btnGoRegister.addActionListener(e -> {
            this.dispose();
            new RegistrationScreen();
        });


        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String selectedRole = cmbRole.getSelectedItem().toString();
            String userCaptcha = txtCaptchaInput.getText().trim();

            if (username.isEmpty() || password.isEmpty() || userCaptcha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!userCaptcha.equals(generatedCaptcha)) {
                JOptionPane.showMessageDialog(this, "Security Violation: Invalid Captcha!", "Failed", JOptionPane.ERROR_MESSAGE);
                generateNewCaptcha(); return;
            }

            UserDAO userDAO = new UserDAO();
            User user = userDAO.validateUser(username, password);

            if (user != null) {
                String mappedDbRole = user.getRole();
                String mappedSelectedRole = selectedRole.equals("Admin System") ? "Admin" : selectedRole.equals("Faculty Teacher") ? "Teacher" : "Student";

                if (!mappedDbRole.equalsIgnoreCase(mappedSelectedRole)) {
                    JOptionPane.showMessageDialog(this, "Access Denied! Role mismatch.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SessionManager.isLoggedIn = true;
                SessionManager.currentUsername = user.getUsername();
                SessionManager.currentUserRole = mappedDbRole;

                this.dispose();

                if (!SessionManager.pendingRedirectPage.isEmpty()) {
                    if (SessionManager.pendingRedirectPage.equalsIgnoreCase("Resources")) new ResourcesPage();
                    else if (SessionManager.pendingRedirectPage.equalsIgnoreCase("Ecosystem")) new EcosystemPage();
                    else if (SessionManager.pendingRedirectPage.equalsIgnoreCase("Support")) new SupportPage();
                    SessionManager.pendingRedirectPage = "";
                } else {
                    parentFrame.dispose();
                    if (mappedDbRole.equalsIgnoreCase("Admin")) new AdminDashboard();
                    else if (mappedDbRole.equalsIgnoreCase("Teacher")) new TeacherPortal(user.getUsername());
                    else new StudentPortal(user.getUsername());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Authentication Error", JOptionPane.ERROR_MESSAGE);
                generateNewCaptcha();
            }
        });
    }

    private void generateNewCaptcha() {
        Random rand = new Random();
        generatedCaptcha = String.valueOf(1000 + rand.nextInt(9000));
        lblCaptchaDisplay.setText(generatedCaptcha.replace("", " ").trim());
    }
}
