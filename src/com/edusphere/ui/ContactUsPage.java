package com.edusphere.ui;

import com.edusphere.config.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactUsPage extends JFrame {
    private JTextField txtFirst, txtLast, txtEmail, txtPhone;
    private JTextArea txtMessage;
    private JButton btnSubmit, btnClose;

    public ContactUsPage() {
        setTitle("EduSphere Live - Secured Contact Node");
        setSize(850, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 15, 30), getWidth(), getHeight(), new Color(20, 30, 48));
                g2d.setPaint(gp); g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(255, 255, 255, 6));
                g2d.fillRoundRect(430, 40, 380, 450, 15, 15);
                g2d.setColor(new Color(14, 165, 233, 40));
                g2d.drawRoundRect(430, 40, 380, 450, 15, 15);
            }
        };
        mainPanel.setBounds(0, 0, 850, 580);
        add(mainPanel);

        JLabel lblHeader = new JLabel("Contact Us");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBounds(50, 80, 300, 45);
        mainPanel.add(lblHeader);

        JLabel lblSub = new JLabel("<html>We'd love to hear from you.<br>Please fill out the secure form.</html>");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(148, 163, 184));
        lblSub.setBounds(50, 140, 350, 40);
        mainPanel.add(lblSub);

        // 🎨 SCREENSHOT 3D GRAPHIC PLACEHOLDER
        JLabel lblGlobe = new JLabel("🌐 [EduSphere Orbit Matrix]", JLabel.CENTER);
        lblGlobe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGlobe.setForeground(new Color(56, 189, 248, 120));
        lblGlobe.setBorder(BorderFactory.createLineBorder(new Color(56, 189, 248, 30), 1));
        lblGlobe.setBounds(50, 220, 320, 220);
        mainScrollPanelAddOverride(mainPanel, lblGlobe);

        int startY = 60; int spacing = 65; int fieldW = 165;

        txtFirst = createField("First Name"); txtFirst.setBounds(460, startY + 18, fieldW, 30); mainPanel.add(txtFirst);
        txtLast = createField("Last Name"); txtLast.setBounds(640, startY + 18, fieldW, 30); mainPanel.add(txtLast);

        txtEmail = createField("Email Address"); txtEmail.setBounds(460, startY + spacing + 18, 345, 30); mainPanel.add(txtEmail);
        txtPhone = createField("Phone Number"); txtPhone.setBounds(460, startY + (spacing * 2) + 18, 345, 30); mainPanel.add(txtPhone);

        txtMessage = new JTextArea("Your message...");
        txtMessage.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMessage.setForeground(Color.GRAY);
        txtMessage.setBackground(new Color(30, 41, 59));
        txtMessage.setCaretColor(new Color(56, 189, 248));
        txtMessage.setLineWrap(true); txtMessage.setWrapStyleWord(true);
        txtMessage.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(71, 85, 105), 1), BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtMessage.setBounds(460, startY + (spacing * 3) + 18, 345, 90);

        txtMessage.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { if (txtMessage.getText().equals("Your message...")) { txtMessage.setText(""); txtMessage.setForeground(Color.WHITE); } }
            @Override
            public void focusLost(FocusEvent e) { if (txtMessage.getText().isEmpty()) { txtMessage.setText("Your message..."); txtMessage.setForeground(Color.GRAY); } }
        });
        mainPanel.add(txtMessage);

        btnSubmit = new JButton("Submit Message Link ->");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSubmit.setForeground(Color.WHITE); btnSubmit.setBackground(new Color(34, 197, 94));
        btnSubmit.setBounds(460, 435, 180, 34); mainPanel.add(btnSubmit);

        btnClose = new JButton("Close");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClose.setForeground(Color.WHITE); btnClose.setBackground(new Color(71, 85, 105));
        btnClose.setBounds(655, 435, 150, 34); mainPanel.add(btnClose);

        createContactTableIfNotExists();

        btnClose.addActionListener(e -> this.dispose());
        btnSubmit.addActionListener(e -> dispatchContactMessage());

        setVisible(true);
    }
    private void dispatchContactMessage() {
        String fName = txtFirst.getText().trim(); String lName = txtLast.getText().trim();
        String email = txtEmail.getText().trim(); String phone = txtPhone.getText().trim();
        String msg = txtMessage.getText().trim();

        if (fName.contains("First Name") || email.contains("Email") || msg.contains("Your message")) {
            JOptionPane.showMessageDialog(this, "Please fulfill all required contact matrices!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO contact_logs (first_name, last_name, email, phone, message) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fName); pstmt.setString(2, lName);
            pstmt.setString(3, email); pstmt.setString(4, phone); pstmt.setString(5, msg);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Message securely dispatched and archived in SQLite Relational Arrays!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private JTextField createField(String hint) {
        JTextField field = new JTextField(hint);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13)); field.setForeground(Color.GRAY);
        field.setBackground(new Color(30, 41, 59)); field.setCaretColor(new Color(56, 189, 248));
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(71, 85, 105), 1), BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { if (field.getText().equals(hint)) { field.setText(""); field.setForeground(Color.WHITE); } }
            @Override
            public void focusLost(FocusEvent e) { if (field.getText().isEmpty()) { field.setText(hint); field.setForeground(Color.GRAY); } }
        });
        return field;
    }

    private void createContactTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS contact_logs (id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT, email TEXT, phone TEXT, message TEXT)";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) { stmt.execute(sql); } catch (SQLException e) { e.printStackTrace(); }
    }

    private void mainScrollPanelAddOverride(JPanel p, JLabel l) {
        p.add(l);
    }
}
