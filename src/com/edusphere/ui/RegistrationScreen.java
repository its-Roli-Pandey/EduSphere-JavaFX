package com.edusphere.ui;

import com.edusphere.config.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class RegistrationScreen extends JFrame {
    private JTextField txtNewUser, txtNewEmail;
    private JPasswordField txtNewPass;
    private JComboBox<String> cmbNewRole;
    private JButton btnRegister, btnCancel;
    private JCheckBox chkShowPassword;

    public RegistrationScreen() {
        setTitle("EduSphere OS - Secure Account Provisioning Gateway");
        setSize(460, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 23, 42), getWidth(), getHeight(), new Color(30, 41, 59));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(25, 25, 395, 465, 15, 15);
                g2d.setColor(new Color(255, 255, 255, 18));
                g2d.drawRoundRect(25, 25, 395, 465, 15, 15);
            }
        };

        JLabel lblTitle = new JLabel("SECURED ACCOUNT REGISTRY MODULE", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(56, 189, 248));
        lblTitle.setBounds(30, 45, 390, 25);
        mainPanel.add(lblTitle);

        JLabel lblRole = new JLabel("ASSIGN INTENDED SYSTEM PROFILE:");
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblRole.setForeground(new Color(148, 163, 184));
        lblRole.setBounds(45, 95, 250, 15);
        mainPanel.add(lblRole);

        String[] roles = {"Faculty Teacher", "Student Portal"};
        cmbNewRole = new JComboBox<>(roles);
        cmbNewRole.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbNewRole.setBounds(45, 118, 355, 34);
        mainPanel.add(cmbNewRole);

        JLabel lblUserKey = new JLabel("DESIRED IDENTITY TERMINAL KEY (USERNAME):");
        lblUserKey.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblUserKey.setForeground(new Color(148, 163, 184));
        lblUserKey.setBounds(45, 175, 300, 15);
        mainPanel.add(lblUserKey);

        txtNewUser = createPlaceholderField("Enter unique username key...");
        txtNewUser.setBounds(45, 198, 355, 34);
        mainPanel.add(txtNewUser);

        JLabel lblEmail = new JLabel("SECURED SECULAR EMAIL LOG DIRECTORY:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEmail.setForeground(new Color(148, 163, 184));
        lblEmail.setBounds(45, 255, 300, 15);
        mainPanel.add(lblEmail);

        txtNewEmail = createPlaceholderField("Enter valid email record...");
        txtNewEmail.setBounds(45, 278, 355, 34);
        mainPanel.add(txtNewEmail);

        JLabel lblPass = new JLabel("CRYPTOGRAPHIC PASS KEY (PASSWORD):");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPass.setForeground(new Color(148, 163, 184));
        lblPass.setBounds(45, 335, 250, 15);
        mainPanel.add(lblPass);

        txtNewPass = new JPasswordField();
        txtNewPass.setForeground(Color.WHITE);
        txtNewPass.setBackground(new Color(30, 41, 59));
        txtNewPass.setCaretColor(new Color(56, 189, 248));
        txtNewPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(71, 85, 105), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtNewPass.setBounds(45, 358, 355, 34);
        mainPanel.add(txtNewPass);

        chkShowPassword = new JCheckBox("👁 Show Password");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkShowPassword.setForeground(Color.LIGHT_GRAY);
        chkShowPassword.setOpaque(false);
        chkShowPassword.setBounds(45, 398, 150, 20);
        mainPanel.add(chkShowPassword);

        btnRegister = new JButton("Commit Save User");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBackground(new Color(34, 197, 94));
        btnRegister.setBounds(45, 435, 170, 38);
        mainPanel.add(btnRegister);

        btnCancel = new JButton("Cancel Node");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(71, 85, 105));
        btnCancel.setBounds(230, 435, 170, 38);
        mainPanel.add(btnCancel);

        add(mainPanel, BorderLayout.CENTER);

        chkShowPassword.addActionListener(e -> {
            txtNewPass.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '•');
        });

        btnRegister.addActionListener(e -> {
            String username = txtNewUser.getText().trim();
            String email = txtNewEmail.getText().trim();
            String password = new String(txtNewPass.getPassword()).trim();
            String rawRole = cmbNewRole.getSelectedItem().toString();
            String finalDbRole = rawRole.equals("Faculty Teacher") ? "Teacher" : "Student";

            if (username.isEmpty() || username.contains("Enter unique") || email.isEmpty() || email.contains("Enter valid") || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 🔐 2FA CORE GATEWAY: GENERATE 4-DIGIT VERIFICATION CODE
            Random rand = new Random();
            String secureOtp = String.valueOf(1000 + rand.nextInt(9000));

            // SIMULATED TERMINAL TRANSMISSION REPORT FOR INTERVIEW PRESENTATION
            System.out.println("=================================================");
            System.out.println("[TELEMETRY RELAY]: Sending OTP to -> " + email);
            System.out.println("[SECURE BLOCK TOKEN]: TARGET OTP GENERATED -> " + secureOtp);
            System.out.println("=================================================");

            JOptionPane.showMessageDialog(this,
                    "Two-Factor Authentication Triggered!\nA 4-digit verification secure OTP has been transmitted to:\n" + email +
                            "\n\n(Hint for Testing: Check IntelliJ Output Terminal Logs!)",
                    "System Transmission Active", JOptionPane.INFORMATION_MESSAGE);

            String userVerificationInput = JOptionPane.showInputDialog(this,
                    "Enter the 4-digit Cryptographic Security OTP token:",
                    "Identity Authentication Gateway", JOptionPane.QUESTION_MESSAGE);

            if (userVerificationInput == null) return;

            if (!userVerificationInput.trim().equals(secureOtp)) {
                JOptionPane.showMessageDialog(this, "Security Authentication Failed: Invalid OTP Key Node!", "Access Denied", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, finalDbRole);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "OTP Verified! Account Provisioned Successfully in Database Matrix Ledger!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Security Alert: Username key already allocated inside database arrays!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCancel.addActionListener(e -> this.dispose());
        setVisible(true);
    }

    private JTextField createPlaceholderField(String hint) {
        JTextField field = new JTextField(hint);
        field.setForeground(Color.GRAY);
        field.setBackground(new Color(30, 41, 59));
        field.setCaretColor(new Color(56, 189, 248));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(71, 85, 105), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(hint)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(hint);
                    field.setForeground(Color.GRAY);
                }
            }
        });
        return field;
    }
}
