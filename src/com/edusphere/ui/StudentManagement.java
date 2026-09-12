package com.edusphere.ui;

import com.edusphere.config.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class StudentManagement extends JFrame {
    private JLabel lblWelcome, lblAttended, lblSyllabusCount;
    private JTextArea txtNoticeBoard;
    private JTable tableMyCourses;
    private DefaultTableModel modelMyCourses;
    private File selectedAttachment = null;

    public StudentManagement(String studentUsername) {
        setTitle("EduSphere Live - Student Digital Gateway Portal");
        setSize(1200, 850); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); setResizable(false); setLayout(new BorderLayout());

        JPanel navBar = new JPanel(null); navBar.setBackground(new Color(15, 23, 42)); navBar.setPreferredSize(new Dimension(1200, 60)); add(navBar, BorderLayout.NORTH);
        lblWelcome = new JLabel("🎓 WELCOME NODE: " + studentUsername.toUpperCase() + " // STUDENT ACCESS ACTIVE");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 14)); lblWelcome.setForeground(Color.WHITE); lblWelcome.setBounds(30, 15, 600, 30); navBar.add(lblWelcome);

        JButton btnLogout = new JButton("🚪 Log Out"); btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 11)); btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(239, 68, 68)); btnLogout.setBounds(1050, 15, 110, 30); btnLogout.setFocusPainted(false); navBar.add(btnLogout);

        JPanel mainWorkspace = new JPanel(null); mainWorkspace.setBackground(new Color(10, 15, 30)); mainWorkspace.setPreferredSize(new Dimension(1160, 1000));
        JScrollPane scrollPane = new JScrollPane(mainWorkspace); scrollPane.setBorder(null); add(scrollPane, BorderLayout.CENTER);

        JPanel cardAtt = createMetricCard(); cardAtt.setBounds(30, 30, 360, 90); mainWorkspace.add(cardAtt);
        JLabel lblAT = new JLabel("My Attendance Tokens"); lblAT.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblAT.setForeground(new Color(148, 163, 184)); lblAT.setBounds(20, 15, 200, 15); cardAtt.add(lblAT);
        lblAttended = new JLabel("0 Present Logs"); lblAttended.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblAttended.setForeground(new Color(34, 197, 94)); lblAttended.setBounds(20, 38, 300, 30); cardAtt.add(lblAttended);

        JPanel cardSyl = createMetricCard(); cardSyl.setBounds(420, 30, 360, 90); mainWorkspace.add(cardSyl);
        JLabel lblST = new JLabel("Total Curriculum Syllabi"); lblST.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblST.setForeground(new Color(148, 163, 184)); lblST.setBounds(20, 15, 200, 15); cardSyl.add(lblST);
        lblSyllabusCount = new JLabel("0 Modules Mapped"); lblSyllabusCount.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblSyllabusCount.setForeground(new Color(56, 189, 248)); lblSyllabusCount.setBounds(20, 38, 300, 30); cardSyl.add(lblSyllabusCount);

        // 🌟 NEW ADDTION: STUDENT LIVE HELPDESK GRIEVANCE FORM PANEL (शिकायत फॉर्म)
        JPanel cardHelpdeskForm = createMetricCard(); cardHelpdeskForm.setBounds(30, 150, 360, 560); mainWorkspace.add(cardHelpdeskForm);
        JLabel lblFormTitle = new JLabel("📬 SUBMIT TICKET TO MASTER ANCHOR", JLabel.LEFT);
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblFormTitle.setForeground(Color.WHITE); lblFormTitle.setBounds(20, 15, 320, 20); cardHelpdeskForm.add(lblFormTitle);

        JTextArea txtProblemStatement = new JTextArea("Describe your technical bug, fee issue, or hostel grievance here...");
        txtProblemStatement.setFont(new Font("Segoe UI", Font.PLAIN, 12)); txtProblemStatement.setForeground(Color.GRAY); txtProblemStatement.setBackground(new Color(15, 23, 42)); txtProblemStatement.setLineWrap(true); txtProblemStatement.setWrapStyleWord(true);
        JScrollPane formScroll = new JScrollPane(txtProblemStatement); formScroll.setBounds(20, 50, 320, 320); cardHelpdeskForm.add(formScroll);

        JLabel lblFileStatus = new JLabel("📁 Attachment: Optional (None Selected)", JLabel.LEFT);
        lblFileStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11)); lblFileStatus.setForeground(new Color(148, 163, 184)); lblFileStatus.setBounds(20, 385, 320, 20); cardHelpdeskForm.add(lblFileStatus);

        JButton btnUpload = new JButton("📎 Upload File / Photo / Video");
        btnUpload.setFont(new Font("Segoe UI", Font.BOLD, 11)); btnUpload.setForeground(Color.WHITE); btnUpload.setBackground(new Color(71, 85, 105)); btnUpload.setBounds(20, 415, 320, 32); btnUpload.setFocusPainted(false); cardHelpdeskForm.add(btnUpload);

        JButton btnSubmitTicket = new JButton("Commit Problem Statement");
        btnSubmitTicket.setFont(new Font("Segoe UI", Font.BOLD, 12)); btnSubmitTicket.setForeground(Color.WHITE); btnSubmitTicket.setBackground(new Color(14, 165, 233)); btnSubmitTicket.setBounds(20, 465, 320, 38); btnSubmitTicket.setFocusPainted(false); cardHelpdeskForm.add(btnSubmitTicket);

        JPanel cardNotice = createMetricCard(); cardNotice.setBounds(420, 150, 360, 560); mainWorkspace.add(cardNotice);
        JLabel lblNT = new JLabel("📢 CENTRAL CAMPUS RADAR — REAL-TIME ADMIN NOTICES", JLabel.LEFT);
        lblNT.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblNT.setForeground(Color.WHITE); lblNT.setBounds(20, 15, 330, 20); cardNotice.add(lblNT);
        txtNoticeBoard = new JTextArea(); txtNoticeBoard.setFont(new Font("Consolas", Font.PLAIN, 13)); txtNoticeBoard.setForeground(new Color(203, 213, 225));
        txtNoticeBoard.setBackground(new Color(15, 23, 42)); txtNoticeBoard.setEditable(false); txtNoticeBoard.setLineWrap(true); txtNoticeBoard.setWrapStyleWord(true);
        JScrollPane noticeScroll = new JScrollPane(txtNoticeBoard); noticeScroll.setBorder(BorderFactory.createLineBorder(new Color(71, 85, 105), 1)); noticeScroll.setBounds(20, 45, 320, 495); cardNotice.add(noticeScroll);

        JPanel cardCourses = createMetricCard(); cardCourses.setBounds(810, 30, 350, 680); mainWorkspace.add(cardCourses);
        JLabel lblCT = new JLabel("📚 AVAILABLE PLATFORM CURRICULUMS", JLabel.CENTER); lblCT.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblCT.setForeground(Color.WHITE); lblCT.setBounds(10, 15, 330, 20); cardCourses.add(lblCT);
        String[] cols = {"Course Key ID", "Course Title Matrix"};
        modelMyCourses = new DefaultTableModel(null, cols) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tableMyCourses = new JTable(modelMyCourses); tableMyCourses.setFont(new Font("Segoe UI", Font.PLAIN, 12)); tableMyCourses.setForeground(new Color(203, 213, 225)); tableMyCourses.setBackground(new Color(15, 23, 42));
        tableMyCourses.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11)); tableMyCourses.getTableHeader().setBackground(new Color(24, 32, 56)); tableMyCourses.getTableHeader().setForeground(Color.WHITE); tableMyCourses.setRowHeight(26);
        JScrollPane courseScroll = new JScrollPane(tableMyCourses); courseScroll.setBorder(null); courseScroll.setBounds(15, 45, 320, 615); cardCourses.add(courseScroll);

        txtProblemStatement.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) { if (txtProblemStatement.getText().contains("Describe your")) { txtProblemStatement.setText(""); txtProblemStatement.setForeground(Color.WHITE); } }
            @Override public void focusLost(java.awt.event.FocusEvent e) { if (txtProblemStatement.getText().isEmpty()) { txtProblemStatement.setText("Describe your technical bug, fee issue, or hostel grievance here..."); txtProblemStatement.setForeground(Color.GRAY); } }
        });

        btnUpload.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int choice = chooser.showOpenDialog(this);
            if (choice == JFileChooser.APPROVE_OPTION) {
                selectedAttachment = chooser.getSelectedFile();
                lblFileStatus.setText("📁 Attached: " + selectedAttachment.getName());
                lblFileStatus.setForeground(new Color(34, 197, 94));
            }
        });

        btnSubmitTicket.addActionListener(e -> {
            String logText = txtProblemStatement.getText().trim();
            if (logText.isEmpty() || logText.contains("Describe your")) return;
            String fileMeta = (selectedAttachment != null) ? " [FILE: " + selectedAttachment.getName() + "]" : " [NO ATTACHMENT]";
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement("INSERT INTO system_issues (scope, user_key, logs) VALUES ('Student', ?, ?)")) {
                ps.setString(1, studentUsername); ps.setString(2, logText + fileMeta); ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Problem Statement Ticket Sent Live to Admin Box!", "Ticket Dispatched", JOptionPane.INFORMATION_MESSAGE);
                txtProblemStatement.setText("Describe your technical bug, fee issue, or hostel grievance here..."); txtProblemStatement.setForeground(Color.GRAY);
                lblFileStatus.setText("📁 Attachment: Optional (None Selected)"); lblFileStatus.setForeground(new Color(148, 163, 184)); selectedAttachment = null;
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        fetchLiveStudentMatrixData(studentUsername);
        btnLogout.addActionListener(e -> { this.dispose(); new ProjectIndexPortal(); });
        setVisible(true);
    }
    private void fetchLiveStudentMatrixData(String user) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            int attCount = 0;
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM attendance_ledger WHERE node_name LIKE ? AND status_log LIKE '%PRESENT%'")) {
                ps.setString(1, "%" + user + "%");
                try (ResultSet rs = ps.executeQuery()) { if (rs.next()) { attCount = rs.getInt(1); lblAttended.setText(attCount + " Present Log Tokens"); } }
            }
            int sylCount = 0;
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM campus_syllabus"); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { sylCount = rs.getInt(1); lblSyllabusCount.setText(sylCount + " Syllabi Active"); }
            }
            StringBuilder sb = new StringBuilder();
            try (PreparedStatement ps = conn.prepareStatement("SELECT alert_msg, timestamp FROM campus_notices ORDER BY id DESC LIMIT 15"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sb.append("[").append(rs.getString("timestamp")).append("]\n").append(rs.getString("alert_msg")).append("\n");
                    sb.append("--------------------------------------------------------------------------------\n");
                }
                if (sb.length() == 0) txtNoticeBoard.setText("• No administrative notices, placement alerts or internship schedules running active.");
                else txtNoticeBoard.setText(sb.toString());
            }
            modelMyCourses.setRowCount(0);
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, course_name FROM campus_courses ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { Vector<String> row = new Vector<>(); row.add("#CRS-" + rs.getInt("id")); row.add(rs.getString("course_name").toUpperCase()); modelMyCourses.addRow(row); }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private JPanel createMetricCard() {
        JPanel card = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(new Color(30, 41, 59)); g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g.setColor(new Color(255, 255, 255, 12)); g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
            }
        };
        card.setOpaque(false); return card;
    }
}
