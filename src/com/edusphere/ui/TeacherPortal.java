package com.edusphere.ui;

import com.edusphere.config.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class TeacherPortal extends JFrame {
    private JLabel lblWelcome, lblFacultyAtt, lblGlobalMetrics;
    private JTextArea txtFacultyNoticeRadar;
    private JTable tablePlatformLog;
    private DefaultTableModel modelPlatformLog;
    private String activeFacultyUser;

    public TeacherPortal(String teacherUsername) {
        this.activeFacultyUser = teacherUsername;
        setTitle("EduSphere OS - Faculty Workstation");
        setSize(1200, 850); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); setResizable(false); setLayout(new BorderLayout());

        JPanel navBar = new JPanel(null); navBar.setBackground(new Color(15, 23, 42)); navBar.setPreferredSize(new Dimension(1200, 60)); add(navBar, BorderLayout.NORTH);
        lblWelcome = new JLabel("👨‍🏫 FACULTY CONSOLE: " + teacherUsername.toUpperCase() + " // WORKSTATION TERMINAL CORE");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 14)); lblWelcome.setForeground(Color.WHITE); lblWelcome.setBounds(30, 15, 600, 30); navBar.add(lblWelcome);

        JButton btnLogout = new JButton("🚪 Log Out"); btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 11)); btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(239, 68, 68)); btnLogout.setBounds(1050, 15, 110, 30); btnLogout.setFocusPainted(false); navBar.add(btnLogout);

        JPanel navSubGrid = new JPanel(new GridLayout(1, 4, 10, 0)); navSubGrid.setBackground(new Color(30, 41, 59)); navSubGrid.setPreferredSize(new Dimension(1300, 45));
        JButton btnNotify = createNavButton("📢 Broadcast Alert / Class Status");
        JButton btnMarkP = createNavButton("✅ Mark Present Token");
        JButton btnMarkA = createNavButton("❌ Mark Absent Leak");
        JButton btnDeleteLog = createNavButton("🗑️ Erase Syllabus / Notice Record");
        navSubGrid.add(btnNotify); navSubGrid.add(btnMarkP); navSubGrid.add(btnMarkA); navSubGrid.add(btnDeleteLog);

        JPanel topContainerBox = new JPanel(new BorderLayout()); topContainerBox.add(navBar, BorderLayout.NORTH); topContainerBox.add(navSubGrid, BorderLayout.SOUTH); add(topContainerBox, BorderLayout.NORTH);

        JPanel mainWorkspace = new JPanel(null); mainWorkspace.setBackground(new Color(10, 15, 30)); mainWorkspace.setPreferredSize(new Dimension(1160, 1000));
        JScrollPane scrollPane = new JScrollPane(mainWorkspace); scrollPane.setBorder(null); add(scrollPane, BorderLayout.CENTER);

        JPanel cardAtt = createFlatCard(); cardAtt.setBounds(30, 30, 360, 90); mainWorkspace.add(cardAtt);
        JLabel lblAT = new JLabel("Faculty Duty Logs Token"); lblAT.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblAT.setForeground(new Color(148, 163, 184)); lblAT.setBounds(20, 15, 200, 15); cardAtt.add(lblAT);
        lblFacultyAtt = new JLabel("0 Assigned Logs", JLabel.LEFT); lblFacultyAtt.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblFacultyAtt.setForeground(new Color(56, 189, 248)); lblFacultyAtt.setBounds(20, 38, 300, 30); cardAtt.add(lblFacultyAtt);

        JPanel cardMeta = createFlatCard(); cardMeta.setBounds(420, 30, 360, 90); mainWorkspace.add(cardMeta);
        JLabel lblMT = new JLabel("Institutional Infrastructure State"); lblMT.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblMT.setForeground(new Color(148, 163, 184)); lblMT.setBounds(20, 15, 200, 15); cardMeta.add(lblMT);
        lblGlobalMetrics = new JLabel("CONNECTED // NODE SECURE", JLabel.LEFT); lblGlobalMetrics.setFont(new Font("Segoe UI", Font.BOLD, 18)); lblGlobalMetrics.setForeground(new Color(34, 197, 94)); lblGlobalMetrics.setBounds(20, 38, 300, 30); cardMeta.add(lblGlobalMetrics);

        JPanel cardHelpdeskForm = createFlatCard(); cardHelpdeskForm.setBounds(30, 150, 360, 560); mainWorkspace.add(cardHelpdeskForm);
        JLabel lblFormTitle = new JLabel("📬 REPORT SYSTEM FAULT TO MAIN SHELL", JLabel.LEFT);
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblFormTitle.setForeground(Color.WHITE); lblFormTitle.setBounds(20, 15, 320, 20); cardHelpdeskForm.add(lblFormTitle);

        JTextArea txtProblemStatement = new JTextArea("Describe project lab failures, missing projector infrastructure, or classroom faults here...");
        txtProblemStatement.setFont(new Font("Segoe UI", Font.PLAIN, 12)); txtProblemStatement.setForeground(Color.GRAY); txtProblemStatement.setBackground(new Color(15, 23, 42)); txtProblemStatement.setLineWrap(true); txtProblemStatement.setWrapStyleWord(true);
        JScrollPane formScroll = new JScrollPane(txtProblemStatement); formScroll.setBounds(20, 50, 320, 320); cardHelpdeskForm.add(formScroll);

        JButton btnSubmitTicket = new JButton("Commit Fault Matrix Log");
        btnSubmitTicket.setFont(new Font("Segoe UI", Font.BOLD, 12)); btnSubmitTicket.setForeground(Color.WHITE); btnSubmitTicket.setBackground(new Color(234, 179, 8)); btnSubmitTicket.setBounds(20, 465, 320, 38); btnSubmitTicket.setFocusPainted(false); cardHelpdeskForm.add(btnSubmitTicket);

        JPanel cardNotice = createFlatCard(); cardNotice.setBounds(420, 150, 360, 560); mainWorkspace.add(cardNotice);
        JLabel lblNT = new JLabel("📢 ADMINISTRATIVE STRATEGIC BROADCAST MATRIX", JLabel.LEFT);
        lblNT.setFont(new Font("Segoe UI", Font.BOLD, 10)); lblNT.setForeground(Color.WHITE); lblNT.setBounds(20, 15, 330, 20); cardNotice.add(lblNT);
        txtFacultyNoticeRadar = new JTextArea(); txtFacultyNoticeRadar.setFont(new Font("Consolas", Font.PLAIN, 13)); txtFacultyNoticeRadar.setForeground(new Color(203, 213, 225));
        txtFacultyNoticeRadar.setBackground(new Color(15, 23, 42)); txtFacultyNoticeRadar.setEditable(false); txtFacultyNoticeRadar.setLineWrap(true); txtFacultyNoticeRadar.setWrapStyleWord(true);
        JScrollPane noticeScroll = new JScrollPane(txtFacultyNoticeRadar); noticeScroll.setBorder(BorderFactory.createLineBorder(new Color(71, 85, 105), 1)); noticeScroll.setBounds(20, 45, 320, 495); cardNotice.add(noticeScroll);

        JPanel cardLogs = createFlatCard(); cardLogs.setBounds(800, 30, 365, 680); mainWorkspace.add(cardLogs);
        JLabel lblLT = new JLabel("📂 LIVE CURRICULUM SYLLABUS DIRECTORY", JLabel.CENTER); lblLT.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblLT.setForeground(Color.WHITE); lblLT.setBounds(10, 15, 345, 20); cardLogs.add(lblLT);
        String[] cols = {"Syllabus ID", "Curriculum Payload Log"};
        modelPlatformLog = new DefaultTableModel(null, cols) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tablePlatformLog = new JTable(modelPlatformLog); tablePlatformLog.setFont(new Font("Segoe UI", Font.PLAIN, 12)); tablePlatformLog.setForeground(new Color(203, 213, 225)); tablePlatformLog.setBackground(new Color(15, 23, 42));
        tablePlatformLog.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11)); tablePlatformLog.getTableHeader().setBackground(new Color(24, 32, 56)); tablePlatformLog.getTableHeader().setForeground(Color.WHITE); tablePlatformLog.setRowHeight(26);
        JScrollPane logScroll = new JScrollPane(tablePlatformLog); logScroll.setBorder(null); logScroll.setBounds(15, 45, 335, 615); cardLogs.add(logScroll);

        btnNotify.addActionListener(e -> triggerFacultyNoticePopupForm());
        btnMarkP.addActionListener(e -> triggerAttendancePopupForm("PRESENT NODE ACTIVE"));
        btnMarkA.addActionListener(e -> triggerAttendancePopupForm("ABSENT SECURITY LEAK"));
        btnDeleteLog.addActionListener(e -> triggerDeletePopupForm());
        btnLogout.addActionListener(e -> { this.dispose(); new ProjectIndexPortal(); });

        fetchLiveFacultyWorkstationData(teacherUsername);
        setVisible(true);
    }
    // 🌟 PRESENT / ABSENT ATTENDANCE POPUP FORM MODULE
    private void triggerAttendancePopupForm(String statusType) {
        JDialog dialog = new JDialog(this, "Attendance Committer Form Panel", true);
        dialog.setSize(450, 280); dialog.getContentPane().setBackground(new Color(15, 23, 42)); dialog.setLayout(null); dialog.setLocationRelativeTo(this);

        JLabel lblT = new JLabel("🎯 ATTENDANCE REGISTRY LOGS - " + statusType, JLabel.CENTER);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblT.setForeground(new Color(56, 189, 248)); lblT.setBounds(10, 20, 410, 20); dialog.add(lblT);

        JLabel lblSel = new JLabel("Select Target Student Node:"); lblSel.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblSel.setForeground(Color.LIGHT_GRAY); lblSel.setBounds(40, 60, 250, 20); dialog.add(lblSel);

        JComboBox<String> cmbStudents = new JComboBox<>(); cmbStudents.setBounds(40, 85, 350, 32); dialog.add(cmbStudents);

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS schema_students (id INTEGER PRIMARY KEY AUTOINCREMENT, student_name TEXT)");
            try (ResultSet rs = stmt.executeQuery("SELECT student_name FROM schema_students ORDER BY student_name ASC")) {
                while (rs.next()) { cmbStudents.addItem(rs.getString("student_name").toUpperCase()); }
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        JButton btnSubmit = new JButton("Commit Status to Database Ledger"); btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 12)); btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(new Color(14, 165, 233)); btnSubmit.setBounds(40, 150, 350, 38); dialog.add(btnSubmit);

        btnSubmit.addActionListener(e -> {
            if (cmbStudents.getItemCount() == 0) { JOptionPane.showMessageDialog(dialog, "No student nodes mapped in DB!", "Error", JOptionPane.ERROR_MESSAGE); return; }
            String selectedStudent = (String) cmbStudents.getSelectedItem();
            try (Connection conn = DatabaseConnection.getConnection()) {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO attendance_ledger (node_name, status_log) VALUES (?, ?)")) {
                    ps.setString(1, selectedStudent); ps.setString(2, statusType); ps.executeUpdate();
                }
                dialog.dispose(); JOptionPane.showMessageDialog(this, "Attendance Log Secured for: " + selectedStudent, "Ledger Locked", JOptionPane.INFORMATION_MESSAGE);
                fetchLiveFacultyWorkstationData(activeFacultyUser);
            } catch (Exception ex) { ex.printStackTrace(); }
        });
        dialog.setVisible(true);
    }

    // 🌟 DELETE RECORDS POPUP FORM MODULE
    private void triggerDeletePopupForm() {
        JDialog dialog = new JDialog(this, "Erase Repository Records Portal", true);
        dialog.setSize(500, 300); dialog.getContentPane().setBackground(new Color(15, 23, 42)); dialog.setLayout(null); dialog.setLocationRelativeTo(this);

        JLabel lblT = new JLabel("🗑️ ERASE MANAGEMENT ENGINE HUB", JLabel.CENTER); lblT.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblT.setForeground(new Color(239, 68, 68)); lblT.setBounds(20, 15, 440, 20); dialog.add(lblT);
        JComboBox<String> cmbRecords = new JComboBox<>(); cmbStudentsPopulateOverride(cmbRecords, dialog);

        JButton btnDelete = new JButton("Purge Selection Permanently"); btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 12)); btnDelete.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(239, 68, 68)); btnDelete.setBounds(40, 160, 400, 38); dialog.add(btnDelete);

        btnDelete.addActionListener(e -> {
            if (cmbRecords.getItemCount() == 0) return;
            String item = (String) cmbRecords.getSelectedItem();
            String idStr = item.split(" - ")[0].replace("#NOT-", "").replace("#ATT-", "");
            int id = Integer.parseInt(idStr);
            try (Connection conn = DatabaseConnection.getConnection()) {
                if (item.contains("#ATT-")) { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM attendance_ledger WHERE id = ?")) { ps.setInt(1, id); ps.executeUpdate(); } }
                else { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM campus_syllabus WHERE id = ?")) { ps.setInt(1, id); ps.executeUpdate(); } }
                dialog.dispose(); JOptionPane.showMessageDialog(this, "Database Entry Overridden and Erased!", "Purge Complete", JOptionPane.WARNING_MESSAGE);
                fetchLiveFacultyWorkstationData(activeFacultyUser);
            } catch (Exception ex) { ex.printStackTrace(); }
        });
        dialog.setVisible(true);
    }

    private void cmbStudentsPopulateOverride(JComboBox<String> cmb, JDialog d) {
        cmb.setBounds(40, 70, 400, 32); d.add(cmb);
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, node_name, status_log FROM attendance_ledger ORDER BY id DESC LIMIT 10"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { cmb.addItem("#ATT-" + rs.getInt("id") + " - " + rs.getString("node_name").toUpperCase() + " [" + rs.getString("status_log") + "]"); }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, status_log FROM campus_syllabus ORDER BY id DESC LIMIT 10"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { cmb.addItem("#NOT-" + rs.getInt("id") + " - " + rs.getString("status_log").toUpperCase()); }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void triggerFacultyNoticePopupForm() {
        JDialog dialog = new JDialog(this, "Faculty Notification Core", true);
        dialog.setSize(500, 320); dialog.getContentPane().setBackground(new Color(15, 23, 42)); dialog.setLayout(null); dialog.setLocationRelativeTo(this);
        JLabel lblT = new JLabel("📢 BROADCAST MESSAGE / NOTIFICATION DESK", JLabel.CENTER); lblT.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblT.setForeground(new Color(56, 189, 248)); lblT.setBounds(20, 15, 460, 20); dialog.add(lblT);

        JComboBox<String> cmbType = new JComboBox<>(new String[]{"📢 CLASS STATUS: ON (AS SCHEDULED)", "🛑 CLASS STATUS: OFF / POSTPONED", "📝 SYLLABUS UNIT MODULE COMPLETED", "🎉 EXTRA MEETING / CAMPUS EVENT ALERT"});
        cmbType.setBounds(40, 50, 400, 32); dialog.add(cmbType);

        JTextField fieldInfo = new JTextField("Enter customized specific instructions here..."); fieldInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fieldInfo.setForeground(Color.GRAY); fieldInfo.setBackground(new Color(30, 41, 59)); fieldInfo.setBounds(40, 100, 400, 35); dialog.add(fieldInfo);

        JButton btnSubmit = new JButton("Dispatch Notification To Network"); btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 12)); btnSubmit.setForeground(Color.WHITE); btnSubmit.setBackground(new Color(14, 165, 233)); btnSubmit.setBounds(40, 160, 400, 38); dialog.add(btnSubmit);

        btnSubmit.addActionListener(e -> {
            String spec = fieldInfo.getText().trim(); String type = (String) cmbType.getSelectedItem();
            String fullPayload = type + " // DETAILS: " + spec;
            try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS campus_notices (id INTEGER PRIMARY KEY AUTOINCREMENT, alert_msg TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
                stmt.execute("CREATE TABLE IF NOT EXISTS campus_syllabus (id INTEGER PRIMARY KEY AUTOINCREMENT, status_log TEXT)");
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO campus_notices (alert_msg) VALUES (?)")) { ps.setString(1, "👨‍🏫 FROM FACULTY ("+activeFacultyUser.toUpperCase()+"): " + fullPayload); ps.executeUpdate(); }
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO campus_syllabus (status_log) VALUES (?)")) { ps.setString(1, "Broadcast Alert: " + type); ps.executeUpdate(); }
                dialog.dispose(); JOptionPane.showMessageDialog(this, "Notification Synced Live!", "Broadcast Success", JOptionPane.INFORMATION_MESSAGE);
                fetchLiveFacultyWorkstationData(activeFacultyUser);
            } catch (Exception ex) { ex.printStackTrace(); }
        });
        dialog.setVisible(true);
    }

    private void fetchLiveFacultyWorkstationData(String user) {
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS attendance_ledger (id INTEGER PRIMARY KEY AUTOINCREMENT, node_name TEXT, status_log TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS campus_notices (id INTEGER PRIMARY KEY AUTOINCREMENT, alert_msg TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            stmt.execute("CREATE TABLE IF NOT EXISTS campus_syllabus (id INTEGER PRIMARY KEY AUTOINCREMENT, status_log TEXT)");

            int attCount = 0;
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM attendance_ledger")) {
                try (ResultSet rs = ps.executeQuery()) { if (rs.next()) { attCount = rs.getInt(1); lblFacultyAtt.setText(attCount + " Total Logs Saved"); } }
            }
            StringBuilder sb = new StringBuilder();
            try (PreparedStatement ps = conn.prepareStatement("SELECT alert_msg, timestamp FROM campus_notices ORDER BY id DESC LIMIT 15"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { sb.append("[").append(rs.getString("timestamp")).append("]\n").append(rs.getString("alert_msg")).append("\n--------------------------------------------------------------------------------\n"); }
                if (sb.length() == 0) txtFacultyNoticeRadar.setText("• Master ledger connection stable.");
                else txtFacultyNoticeRadar.setText(sb.toString());
            }
            modelPlatformLog.setRowCount(0);
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, status_log FROM campus_syllabus ORDER BY id DESC LIMIT 10"); ResultSet rs = ps.executeQuery()) {
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

    private JButton createNavButton(String txt) {
        JButton btn = new JButton(txt); btn.setFont(new Font("Segoe UI", Font.BOLD, 11)); btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(24, 32, 56)); btn.setFocusPainted(false); btn.setBorder(BorderFactory.createLineBorder(new Color(71, 85, 105), 1)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(14, 165, 233)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(new Color(24, 32, 56)); }
        });
        return btn;
    }
}
