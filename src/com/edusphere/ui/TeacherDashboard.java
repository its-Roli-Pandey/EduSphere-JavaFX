package com.edusphere.ui;

import com.edusphere.config.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class TeacherDashboard extends JFrame {
    private JLabel lblWelcome, lblFacultyAtt, lblGlobalMetrics;
    private JTextArea txtFacultyNoticeRadar;
    private JTable tablePlatformLog;
    private DefaultTableModel modelPlatformLog;

    public TeacherDashboard(String teacherUsername) {
        setTitle("EduSphere OS - Authorized Faculty Management Workstation");
        setSize(1200, 800); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); setResizable(false); setLayout(new BorderLayout());

        JPanel navBar = new JPanel(null); navBar.setBackground(new Color(15, 23, 42)); navBar.setPreferredSize(new Dimension(1200, 60)); add(navBar, BorderLayout.NORTH);
        lblWelcome = new JLabel("👨‍🏫 FACULTY CONSOLE: " + teacherUsername.toUpperCase() + " // INTEL LOGISTICS TERMINAL");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 14)); lblWelcome.setForeground(Color.WHITE); lblWelcome.setBounds(30, 15, 600, 30); navBar.add(lblWelcome);

        JButton btnLogout = new JButton("🚪 Log Out"); btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 11)); btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(239, 68, 68)); btnLogout.setBounds(1050, 15, 110, 30); btnLogout.setFocusPainted(false); navBar.add(btnLogout);

        JPanel mainWorkspace = new JPanel(null); mainWorkspace.setBackground(new Color(10, 15, 30)); mainWorkspace.setPreferredSize(new Dimension(1160, 740));
        JScrollPane scrollPane = new JScrollPane(mainWorkspace); scrollPane.setBorder(null); add(scrollPane, BorderLayout.CENTER);

        JPanel cardAtt = createFlatCard(); cardAtt.setBounds(30, 30, 360, 90); mainWorkspace.add(cardAtt);
        JLabel lblAT = new JLabel("Faculty Duty Logs Token"); lblAT.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblAT.setForeground(new Color(148, 163, 184)); lblAT.setBounds(20, 15, 200, 15); cardAtt.add(lblAT);
        lblFacultyAtt = new JLabel("0 Assigned Logs", JLabel.LEFT); lblFacultyAtt.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblFacultyAtt.setForeground(new Color(56, 189, 248)); lblFacultyAtt.setBounds(20, 38, 300, 30); cardAtt.add(lblFacultyAtt);

        JPanel cardMeta = createFlatCard(); cardMeta.setBounds(420, 30, 360, 90); mainWorkspace.add(cardMeta);
        JLabel lblMT = new JLabel("Institutional Infrastructure State"); lblMT.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblMT.setForeground(new Color(148, 163, 184)); lblMT.setBounds(20, 15, 200, 15); cardMeta.add(lblMT);
        lblGlobalMetrics = new JLabel("CONNECTED // NODE SECURE", JLabel.LEFT); lblGlobalMetrics.setFont(new Font("Segoe UI", Font.BOLD, 18)); lblGlobalMetrics.setForeground(new Color(34, 197, 94)); lblGlobalMetrics.setBounds(20, 38, 300, 30); cardMeta.add(lblGlobalMetrics);

        JPanel cardNotice = createFlatCard(); cardNotice.setBounds(30, 150, 740, 560); mainWorkspace.add(cardNotice);
        JLabel lblNT = new JLabel("📢 ADMINISTRATIVE STRATEGIC COMMAND BROADCAST MATRIX", JLabel.LEFT);
        lblNT.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblNT.setForeground(Color.WHITE); lblNT.setBounds(20, 15, 700, 20); cardNotice.add(lblNT);
        txtFacultyNoticeRadar = new JTextArea(); txtFacultyNoticeRadar.setFont(new Font("Consolas", Font.PLAIN, 13)); txtFacultyNoticeRadar.setForeground(new Color(203, 213, 225));
        txtFacultyNoticeRadar.setBackground(new Color(15, 23, 42)); txtFacultyNoticeRadar.setEditable(false); txtFacultyNoticeRadar.setLineWrap(true); txtFacultyNoticeRadar.setWrapStyleWord(true);
        JScrollPane noticeScroll = new JScrollPane(txtFacultyNoticeRadar); noticeScroll.setBorder(BorderFactory.createLineBorder(new Color(71, 85, 105), 1)); noticeScroll.setBounds(20, 45, 700, 495); cardNotice.add(noticeScroll);

        JPanel cardLogs = createFlatCard(); cardLogs.setBounds(800, 30, 365, 680); mainWorkspace.add(cardLogs);
        JLabel lblLT = new JLabel("📂 LIVE CURRICULUM SYLLABUS DIRECTORY", JLabel.CENTER); lblLT.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblLT.setForeground(Color.WHITE); lblLT.setBounds(10, 15, 345, 20); cardLogs.add(lblLT);
        String[] cols = {"Syllabus Node ID", "Mapped Curriculum Payload Log"};
        modelPlatformLog = new DefaultTableModel(null, cols) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tablePlatformLog = new JTable(modelPlatformLog); tablePlatformLog.setFont(new Font("Segoe UI", Font.PLAIN, 12)); tablePlatformLog.setForeground(new Color(203, 213, 225)); tablePlatformLog.setBackground(new Color(15, 23, 42));
        tablePlatformLog.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11)); tablePlatformLog.getTableHeader().setBackground(new Color(24, 32, 56)); tablePlatformLog.getTableHeader().setForeground(Color.WHITE); tablePlatformLog.setRowHeight(26);
        JScrollPane logScroll = new JScrollPane(tablePlatformLog); logScroll.setBorder(null); logScroll.setBounds(15, 45, 335, 615); cardLogs.add(logScroll);

        fetchLiveFacultyWorkstationData(teacherUsername);
        btnLogout.addActionListener(e -> { this.dispose(); new ProjectIndexPortal(); });
        setVisible(true);
    }
    private void fetchLiveFacultyWorkstationData(String user) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            int attCount = 0;
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM attendance_ledger WHERE node_name LIKE ?")) {
                ps.setString(1, "%" + user + "%");
                try (ResultSet rs = ps.executeQuery()) { if (rs.next()) { attCount = rs.getInt(1); lblFacultyAtt.setText(attCount + " Logged Shifts"); } }
            }
            StringBuilder sb = new StringBuilder();
            try (PreparedStatement ps = conn.prepareStatement("SELECT alert_msg, timestamp FROM campus_notices ORDER BY id DESC LIMIT 15"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sb.append("[").append(rs.getString("timestamp")).append("]\n").append(rs.getString("alert_msg")).append("\n");
                    sb.append("--------------------------------------------------------------------------------\n");
                }
                if (sb.length() == 0) txtFacultyNoticeRadar.setText("• Master ledger connection stable. No administrative broadcast logs registered today.");
                else txtFacultyNoticeRadar.setText(sb.toString());
            }
            modelPlatformLog.setRowCount(0);
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, status_log FROM campus_syllabus ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { Vector<String> row = new Vector<>(); row.add("#SYL-" + rs.getInt("id")); row.add(rs.getString("status_log").toUpperCase()); modelPlatformLog.addRow(row); }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private JPanel createFlatCard() {
        JPanel card = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(new Color(30, 41, 59)); g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g.setColor(new Color(255, 255, 255, 12)); g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
            }
        };
        card.setOpaque(false); return card;
    }
    // ⚙️ FEATURE 1: SECURE PASSWORD CHANGE DIALOG COMPONENT
    public void triggerPasswordChangePopup(String username) {
        JDialog dialog = new JDialog(this, "Security Credential Update Console", true);
        dialog.setSize(450, 260); dialog.getContentPane().setBackground(new Color(15, 23, 42)); dialog.setLayout(null); dialog.setLocationRelativeTo(this);

        JLabel lblT = new JLabel("⚙️ SECURITY KEY UPDATE TERMINAL", JLabel.CENTER); lblT.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblT.setForeground(new Color(56, 189, 248)); lblT.setBounds(10, 20, 410, 20); dialog.add(lblT);
        JLabel lblP = new JLabel("Enter New Secure Password:"); lblP.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblP.setForeground(Color.LIGHT_GRAY); lblP.setBounds(40, 60, 250, 20); dialog.add(lblP);

        JPasswordField fieldPass = new JPasswordField(); fieldPass.setBounds(40, 85, 350, 32); fieldPass.setBackground(new Color(30, 41, 59)); fieldPass.setForeground(Color.WHITE); fieldPass.setCaretColor(new Color(56, 189, 248)); dialog.add(fieldPass);
        JButton btnSave = new JButton("Lock New Security Key"); btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12)); btnSave.setForeground(Color.WHITE); btnSave.setBackground(new Color(14, 165, 233)); btnSave.setBounds(40, 140, 350, 38); dialog.add(btnSave);

        btnSave.addActionListener(e -> {
            String newPass = new String(fieldPass.getPassword()).trim();
            if (newPass.isEmpty()) return;
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?")) {
                ps.setString(1, newPass); ps.setString(2, username.toLowerCase()); ps.executeUpdate();
                dialog.dispose(); JOptionPane.showMessageDialog(this, "Security Authentication Access Credentials Updated Live!", "Lock Verified", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) { ex.printStackTrace(); }
        });
        dialog.setVisible(true);
    }

}
