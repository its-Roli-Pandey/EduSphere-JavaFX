package com.edusphere.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class ResourceLibrary extends JFrame {
    // 🎯 कंस्ट्रक्टर में selectedTabIndex पैरामीटर जोड़ दिया है ताकि सही टैब सीधे ओपन हो सके
    public ResourceLibrary(int selectedTabIndex) {
        setTitle("EduSphere OS - Academic Resource Library Terminal");
        setSize(1000, 600); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); setResizable(false); setLayout(new BorderLayout());

        JPanel header = new JPanel(null); header.setBackground(new Color(15, 23, 42));
        header.setPreferredSize(new Dimension(1000, 65)); add(header, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("📚 CENTRAL ACADEMIC RESOURCE LIBRARY SUITE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18)); lblTitle.setForeground(new Color(56, 189, 248));
        lblTitle.setBounds(30, 18, 600, 30); header.add(lblTitle);

        JButton btnClose = new JButton("❌ Close Library"); btnClose.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnClose.setForeground(Color.WHITE); btnClose.setBackground(new Color(239, 68, 68));
        btnClose.setBounds(840, 18, 120, 30); btnClose.setFocusPainted(false); header.add(btnClose);
        btnClose.addActionListener(e -> this.dispose());

        JTabbedPane libraryTabs = new JTabbedPane();
        libraryTabs.setFont(new Font("Segoe UI", Font.BOLD, 12)); add(libraryTabs, BorderLayout.CENTER);

        // TAB 1: PREVIOUS YEAR PAPERS (INDEX 0)
        JPanel tabPYQ = new JPanel(null); tabPYQ.setBackground(new Color(10, 15, 30));
        libraryTabs.addTab("📝 Previous Year Questions (PYQs)", tabPYQ);

        String[] pyqCols = {"Paper Reference ID", "Subject Matrix Title", "Academic Year", "Action Terminal"};
        DefaultTableModel modelPYQ = new DefaultTableModel(null, pyqCols) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        JTable tablePYQ = createStyledTable(modelPYQ); JScrollPane sPYQ = new JScrollPane(tablePYQ);
        sPYQ.setBounds(30, 30, 925, 410); tabPYQ.add(sPYQ);

        modelPYQ.addRow(new Object[]{"#PYQ-JAVA24", "ADVANCED JAVA PROGRAMMING CORE", "2024", "📂 DOWNLOAD / VIEW PDF"});
        modelPYQ.addRow(new Object[]{"#PYQ-DSA23", "DATA STRUCTURES & ALGORITHMS", "2023", "📂 DOWNLOAD / VIEW PDF"});
        modelPYQ.addRow(new Object[]{"#PYQ-DBMS23", "DATABASE MANAGEMENT SYSTEMS (SQL)", "2023", "📂 DOWNLOAD / VIEW PDF"});
        modelPYQ.addRow(new Object[]{"#PYQ-OS22", "OPERATING SYSTEM CORE KERNEL", "2022", "📂 DOWNLOAD / VIEW PDF"});

        // TAB 2: CLASS LECTURE NOTES DESK (INDEX 1)
        JPanel tabNotes = new JPanel(null); tabNotes.setBackground(new Color(10, 15, 30));
        libraryTabs.addTab("📘 Class Lecture Notes Directory", tabNotes);

        String[] notesCols = {"Module Ref Code", "Lecture Note Title / Topic", "Uploaded By Faculty", "Resource Endpoint"};
        DefaultTableModel modelNotes = new DefaultTableModel(null, notesCols) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        JTable tableNotes = createStyledTable(modelNotes); JScrollPane sNotes = new JScrollPane(tableNotes);
        sNotes.setBounds(30, 30, 925, 410); tabNotes.add(sNotes);

        modelNotes.addRow(new Object[]{"#NOT-JAVA-U1", "JAVA SWING GRAPHICS & EVENT HANDLING", "PROF. ROLI", "📥 FETCH NOTES"});
        modelNotes.addRow(new Object[]{"#NOT-DSA-U3", "BINARY TREES & GRAPH TRAVERSAL NETWORKS", "DR. VERMA", "📥 FETCH NOTES"});
        modelNotes.addRow(new Object[]{"#NOT-DBMS-U2", "RELATIONAL ALGEBRA & QUERY OPTIMIZATION", "PROF. ANAND", "📥 FETCH NOTES"});
        modelNotes.addRow(new Object[]{"#NOT-OS-U4", "DEADLOCK DETECTION & MEMORY LOCK SCHEMAS", "DR. SHARMA", "📥 FETCH NOTES"});

        // 🎯 मैजिक लाइन: होम पेज से जो इंडेक्स पास होगा, सीधे वही टैब स्क्रीन पर एक्टिव दिखेगा!
        libraryTabs.setSelectedIndex(selectedTabIndex);

        setVisible(true);
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model); table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(new Color(203, 213, 225)); table.setBackground(new Color(15, 23, 42));
        table.setGridColor(new Color(30, 41, 59, 120)); table.setRowHeight(32);
        table.setSelectionBackground(new Color(14, 165, 233, 40));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(24, 32, 56));
        table.getTableHeader().setForeground(Color.WHITE);
        return table;
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new ResourceLibrary(0)); }
}
