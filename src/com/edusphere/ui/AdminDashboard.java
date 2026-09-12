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

public class AdminDashboard extends JFrame {
    private JPanel mainScrollContent;
    private JLabel lblSliderImage, lblStudVal, lblTeachVal, lblClassVal, lblSupportTitle;
    // 🎯 एरर फ़िक्स: फैकल्टी लेजर टेबल के लिए tableSup वेरिएबल को टॉप पर डिक्लेअर कर दिया है
    private JTable tableStud, tableTeach, tableSup, tableGrievances, tableNoticesArchive;

    // 🎯 ARCHITECTURE FIX: Declared modelSup right here at the supreme class field level
    private DefaultTableModel modelStud, modelTeach, modelSup, modelGrievances, modelNoticesArchive;

    private int currentImageIndex = 0, syllabusAngle = 0, unitsAngle = 0, classesAngle = 0, offsetY = 295;
    private Timer imageSliderTimer;
    private final String[] imagePaths = {
            "src/images/uiet_night.png", "src/images/slider2.png",
            "src/images/slider3.jpg", "src/images/slider4.jpg"
    };

    public AdminDashboard() {
        // Your constructor code starts right here...
        setTitle("EduSphere OS - Supreme Command Panel");
        setSize(1300, 900); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); setResizable(false); setLayout(new BorderLayout());

        JPanel topHeaderMenu = new JPanel(null); topHeaderMenu.setBackground(new Color(15, 23, 42));
        topHeaderMenu.setPreferredSize(new Dimension(1300, 50)); add(topHeaderMenu, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("EduSphere Executive Grid Control Matrix Panel");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15)); lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(30, 12, 450, 25); topHeaderMenu.add(lblTitle);

        JButton btnBackToHome = new JButton("🏡 Terminate Console");
        btnBackToHome.setFont(new Font("Segoe UI", Font.BOLD, 11)); btnBackToHome.setForeground(Color.WHITE);
        btnBackToHome.setBackground(new Color(239, 68, 68)); btnBackToHome.setBounds(1120, 10, 150, 30);
        btnBackToHome.setFocusPainted(false); topHeaderMenu.add(btnBackToHome);

        JPanel operationsSubNavBar = new JPanel(new GridLayout(1, 9, 8, 0));
        operationsSubNavBar.setBackground(new Color(30, 41, 59));
        operationsSubNavBar.setPreferredSize(new Dimension(1300, 45));

        JButton btnAddCourse = createNavMenuButton("📚 New Course");
        JButton btnAddStudent = createNavMenuButton("🎓 New Student");
        JButton btnAddTeacher = createNavMenuButton("👨‍🏫 Add Teacher");
        JButton btnUpdateSyllabus = createNavMenuButton("📝 Update Syllabus");
        JButton btnAddBranch = createNavMenuButton("🏢 New Branch");
        JButton btnAddInternship = createNavMenuButton("💼 Internship Info");
        JButton btnAddPlacement = createNavMenuButton("🚀 Placement Registry");
        JButton btnAddEvent = createNavMenuButton("🎉 Campus Event");
        JButton btnDevTest = createNavMenuButton("💻 Open Workstations");

        operationsSubNavBar.add(btnAddCourse); operationsSubNavBar.add(btnAddStudent);
        operationsSubNavBar.add(btnAddTeacher); operationsSubNavBar.add(btnUpdateSyllabus);
        operationsSubNavBar.add(btnAddBranch); operationsSubNavBar.add(btnAddInternship);
        operationsSubNavBar.add(btnAddPlacement); operationsSubNavBar.add(btnAddEvent);
        operationsSubNavBar.add(btnDevTest);

        JPanel topContainerBox = new JPanel(new BorderLayout());
        topContainerBox.add(topHeaderMenu, BorderLayout.NORTH);
        topContainerBox.add(operationsSubNavBar, BorderLayout.SOUTH);
        add(topContainerBox, BorderLayout.NORTH);

        mainScrollContent = new JPanel(null); mainScrollContent.setBackground(new Color(10, 15, 30));
        mainScrollContent.setPreferredSize(new Dimension(1260, 1450));
        JScrollPane mainScrollPane = new JScrollPane(mainScrollContent); mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(20); add(mainScrollPane, BorderLayout.CENTER);

        JPanel sliderContainer = new JPanel(null); sliderContainer.setOpaque(false); sliderContainer.setBounds(30, 15, 1220, 200); mainScrollContent.add(sliderContainer);
        lblSliderImage = new JLabel("", JLabel.CENTER); lblSliderImage.setBounds(0, 0, 1220, 200);
        lblSliderImage.setBorder(BorderFactory.createLineBorder(new Color(30, 41, 59), 1)); sliderContainer.add(lblSliderImage);
        loadDashboardSliderImage(imagePaths[currentImageIndex]);

        JPanel cardStudents = createFlatCard(); cardStudents.setBounds(30, offsetY, 240, 75); mainScrollContent.add(cardStudents);
        JLabel lblST = new JLabel("Total Courses Active"); lblST.setFont(new Font("Segoe UI", Font.PLAIN, 11)); lblST.setForeground(new Color(148, 163, 184)); lblST.setBounds(15, 12, 200, 15); cardStudents.add(lblST);
        lblStudVal = new JLabel("0 Nodes"); lblStudVal.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblStudVal.setForeground(new Color(34, 197, 94)); lblStudVal.setBounds(15, 32, 200, 30); cardStudents.add(lblStudVal);

        JPanel cardTeachers = createFlatCard(); cardTeachers.setBounds(290, offsetY, 240, 75); mainScrollContent.add(cardTeachers);
        JLabel lblTT = new JLabel("Total Enrolled Students Log"); lblTT.setFont(new Font("Segoe UI", Font.PLAIN, 11)); lblTT.setForeground(new Color(148, 163, 184)); lblTT.setBounds(15, 12, 200, 15); cardTeachers.add(lblTT);
        lblTeachVal = new JLabel("0 Nodes"); lblTeachVal.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblTeachVal.setForeground(new Color(56, 189, 248)); lblTeachVal.setBounds(15, 32, 200, 30); cardTeachers.add(lblTeachVal);

        JPanel cardClasses = createFlatCard(); cardClasses.setBounds(550, offsetY, 240, 75); mainScrollContent.add(cardClasses);
        JLabel lblCT = new JLabel("Authorized System Faculty Staff"); lblCT.setFont(new Font("Segoe UI", Font.PLAIN, 11)); lblCT.setForeground(new Color(148, 163, 184)); lblCT.setBounds(15, 12, 200, 15); cardClasses.add(lblCT);
        lblClassVal = new JLabel("0 Records"); lblClassVal.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblClassVal.setForeground(new Color(234, 179, 8)); lblClassVal.setBounds(15, 32, 200, 30); cardClasses.add(lblClassVal);

        JPanel cardHealth = createFlatCard(); cardHealth.setBounds(810, offsetY, 440, 380); mainScrollContent.add(cardHealth);
        JLabel lblHT = new JLabel("Institutional Data Telemetry Rings Matrix", JLabel.CENTER); lblHT.setFont(new Font("Segoe UI", Font.BOLD, 13)); lblHT.setForeground(Color.WHITE); lblHT.setBounds(20, 15, 400, 20); cardHealth.add(lblHT);

        JLabel lblRadialCircle = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g; g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setStroke(new BasicStroke(12)); g2d.setColor(new Color(30, 41, 59)); g2d.drawOval(20, 20, 180, 180);
                g2d.setColor(new Color(14, 165, 233)); g2d.drawArc(20, 20, 180, 180, 90, -syllabusAngle);
                g2d.setStroke(new BasicStroke(10)); g2d.setColor(new Color(30, 41, 59)); g2d.drawOval(45, 45, 130, 130);
                g2d.setColor(new Color(34, 197, 94)); g2d.drawArc(45, 45, 130, 130, 90, -unitsAngle);
                g2d.setStroke(new BasicStroke(8)); g2d.setColor(new Color(30, 41, 59)); g2d.drawOval(70, 70, 80, 80);
                g2d.setColor(new Color(234, 179, 8)); g2d.drawArc(70, 70, 80, 80, 90, -classesAngle);
            }
        };
        lblRadialCircle.setBounds(110, 50, 220, 220); cardHealth.add(lblRadialCircle);
        JLabel lblB = new JLabel("■ Course Registry Vol"); lblB.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblB.setForeground(new Color(14, 165, 233)); lblB.setBounds(40, 285, 180, 20); cardHealth.add(lblB);
        JLabel lblG = new JLabel("■ Student Network Core"); lblG.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblG.setForeground(new Color(34, 197, 94)); lblG.setBounds(240, 285, 180, 20); cardHealth.add(lblG);
        JLabel lblY = new JLabel("■ System Integrity Core Relay Channel: ACTIVE", JLabel.CENTER); lblY.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblY.setForeground(new Color(234, 179, 8)); lblY.setBounds(20, 320, 400, 20); cardHealth.add(lblY);

        int tableGridY = offsetY + 90;
        JPanel cardMasterGrids = createFlatCard(); cardMasterGrids.setBounds(30, tableGridY, 760, 290); mainScrollContent.add(cardMasterGrids);

        JLabel lblTableCoursesTitle = new JLabel("📚 MASTER REGISTERED COURSES DIRECTORY", JLabel.LEFT); lblTableCoursesTitle.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblTableCoursesTitle.setForeground(new Color(14, 165, 233)); lblTableCoursesTitle.setBounds(15, 10, 300, 20); cardMasterGrids.add(lblTableCoursesTitle);
        String[] columnsStudent = {"Course Ref ID", "Framework Title Block Name"};
        modelStud = new DefaultTableModel(null, columnsStudent) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tableStud = createEmbeddedTable(modelStud); JScrollPane scrollStud = new JScrollPane(tableStud); scrollStud.setBorder(null); scrollStud.setBounds(15, 35, 350, 240); cardMasterGrids.add(scrollStud);

        JLabel lblTableStudTitle = new JLabel("🎓 CENTRAL MASTER ENROLLED STUDENTS LEDGER", JLabel.LEFT); lblTableStudTitle.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblTableStudTitle.setForeground(new Color(34, 197, 94)); lblTableStudTitle.setBounds(395, 10, 350, 20); cardMasterGrids.add(lblTableStudTitle);
        String[] columnsTeacher = {"Student ID", "Profile Identity Name"};
        modelTeach = new DefaultTableModel(null, columnsTeacher) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tableTeach = createEmbeddedTable(modelTeach); JScrollPane scrollTeach = new JScrollPane(tableTeach); scrollTeach.setBorder(null); scrollTeach.setBounds(395, 35, 350, 240); cardMasterGrids.add(scrollTeach);

        JPanel cardFacultyGrid = createFlatCard(); cardFacultyGrid.setBounds(30, tableGridY + 305, 1220, 200); mainScrollContent.add(cardFacultyGrid);
        lblSupportTitle = new JLabel("👨‍🏫 SYSTEM AUTHORIZED FACULTY TEACHERS LEDGER ARCHIVE STATUS", JLabel.LEFT); lblSupportTitle.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblSupportTitle.setForeground(new Color(234, 179, 8)); lblSupportTitle.setBounds(15, 12, 850, 20); cardFacultyGrid.add(lblSupportTitle);
        String[] columnsSupport = {"Faculty Index Key", "Authorized Teacher Name Endpoint"};
        modelSup = new DefaultTableModel(null, columnsSupport) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tableSup = createEmbeddedTable(modelSup); JScrollPane scrollSup = new JScrollPane(tableSup); scrollSup.setBorder(null); scrollSup.setBounds(15, 40, 1190, 145); cardFacultyGrid.add(scrollSup);

        JPanel cardGrievanceGrid = createFlatCard(); cardGrievanceGrid.setBounds(30, tableGridY + 520, 1220, 200); mainScrollContent.add(cardGrievanceGrid);
        JLabel lblGrievBox = new JLabel("📬 MASTER GRIEVANCE TICKET BOX LEDGER — USER COMPLAINTS REGISTER", JLabel.LEFT); lblGrievBox.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblGrievBox.setForeground(new Color(239, 68, 68)); lblGrievBox.setBounds(15, 12, 800, 20); cardGrievanceGrid.add(lblGrievBox);
        String[] columnsGriev = {"Ticket ID", "User Identity Group", "Filer Name Node", "Reported Bug / System Fault Statement Logs"};
        modelGrievances = new DefaultTableModel(null, columnsGriev) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tableGrievances = createEmbeddedTable(modelGrievances); JScrollPane scrollGriev = new JScrollPane(tableGrievances); scrollGriev.setBorder(null); scrollGriev.setBounds(15, 40, 1190, 145); cardGrievanceGrid.add(scrollGriev);

        JPanel cardNoticesGrid = createFlatCard(); cardNoticesGrid.setBounds(30, tableGridY + 735, 1220, 200); mainScrollContent.add(cardNoticesGrid);
        JLabel lblNoticeBox = new JLabel("📢 GLOBAL HISTORIC NOTIFICATION BROADCAST ARCHIVE VAULT LOGS", JLabel.LEFT); lblNoticeBox.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblNoticeBox.setForeground(Color.WHITE); lblNoticeBox.setBounds(15, 12, 800, 20); cardNoticesGrid.add(lblNoticeBox);
        String[] columnsNoticeArch = {"Broadcast Ref Key", "Fired Timestamp Vector", "Synchronized Core Alert Payload Content Message"};
        modelNoticesArchive = new DefaultTableModel(null, columnsNoticeArch) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tableNoticesArchive = createEmbeddedTable(modelNoticesArchive); JScrollPane scrollNotices = new JScrollPane(tableNoticesArchive); scrollNotices.setBorder(null); scrollNotices.setBounds(15, 40, 1190, 145); cardNoticesGrid.add(scrollNotices);

        btnAddCourse.addActionListener(e -> triggerCoursePopupForm());
        btnAddStudent.addActionListener(e -> triggerStudentPopupForm());
        btnAddTeacher.addActionListener(e -> triggerTeacherPopupForm());
        btnUpdateSyllabus.addActionListener(e -> triggerSyllabusPopupForm());
        btnAddBranch.addActionListener(e -> triggerBranchPopupForm());
        btnAddInternship.addActionListener(e -> triggerInternshipPopupForm());
        btnAddPlacement.addActionListener(e -> triggerPlacementPopupForm());
        btnAddEvent.addActionListener(e -> triggerEventPopupForm());

        btnDevTest.addActionListener(e -> {
            new StudentDashboard("ROLI_STUDENT");
            new TeacherDashboard("PROF. ROLI_FACULTY");
            JOptionPane.showMessageDialog(this, "Simultaneous isolated Teacher & Student Workspace Shell Nodes Activated safely!", "Grid Networks Fired", JOptionPane.WARNING_MESSAGE);
        });

        fetchLiveDatabaseMetrics();
        btnBackToHome.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Exit Admin Command Shell Console?", "Security Notice", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) { this.dispose(); new ProjectIndexPortal(); }
        });

        JPanel footerContainer = new JPanel(null); footerContainer.setBackground(new Color(15, 23, 42)); footerContainer.setBounds(30, tableGridY + 950, 1220, 90); mainScrollContent.add(footerContainer);
        JSeparator sep = new JSeparator(); sep.setForeground(new Color(30, 41, 59)); sep.setBounds(0, 0, 1220, 2); footerContainer.add(sep);
        JLabel lblFB = new JLabel("EduSphere Control Matrix Shell System"); lblFB.setFont(new Font("Segoe UI", Font.BOLD, 13)); lblFB.setForeground(Color.WHITE); lblFB.setBounds(30, 15, 400, 25); footerContainer.add(lblFB);
        JLabel lblCR = new JLabel("© 2026 EduSphere Operational Infrastructure Enterprise. CONTROL MATRIX REGISTERED TO: ROLI // UIET SYSTEM ARCHITECT");
        lblCR.setFont(new Font("Segoe UI", Font.PLAIN, 11)); lblCR.setForeground(Color.GRAY); lblCR.setBounds(30, 50, 1000, 20); footerContainer.add(lblCR);

        imageSliderTimer = new Timer(4000, i -> { currentImageIndex = (currentImageIndex + 1) % imagePaths.length; loadDashboardSliderImage(imagePaths[currentImageIndex]); });
        imageSliderTimer.start(); setVisible(true);
    }

    private void triggerCoursePopupForm() {
        JDialog dialog = createFormDialog("Add New Course Vector", 400, 250); JTextField txtCourse = createFormTextField(); addFormRow(dialog, "Course Name:", txtCourse, 40);
        JButton btnSubmit = createFormSubmitButton("Deploy Course Layer"); btnSubmit.setBounds(130, 130, 220, 35); dialog.add(btnSubmit);
        btnSubmit.addActionListener(e -> {
            String val = txtCourse.getText().trim(); if(val.isEmpty()) return;
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS campus_courses (id INTEGER PRIMARY KEY AUTOINCREMENT, course_name TEXT)");
            executePreparedInsert("INSERT INTO campus_courses (course_name) VALUES (?)", val); dialog.dispose();
            JOptionPane.showMessageDialog(this, "New Course Pipeline Deployed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        dialog.setVisible(true);
    }

    private void triggerStudentPopupForm() {
        JDialog dialog = createFormDialog("Student Registration Gateway", 450, 320); JTextField txtName = createFormTextField(); JTextField txtEmail = createFormTextField(); addFormRow(dialog, "Student Name:", txtName, 40); addFormRow(dialog, "Email Route:", txtEmail, 95);
        JButton btnSubmit = createFormSubmitButton("Enroll Student Token"); btnSubmit.setBounds(130, 180, 240, 35); dialog.add(btnSubmit);
        btnSubmit.addActionListener(e -> {
            String name = txtName.getText().trim(); if(name.isEmpty()) return;
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS schema_students (id INTEGER PRIMARY KEY AUTOINCREMENT, student_name TEXT)");
            executePreparedInsert("INSERT INTO schema_students (student_name) VALUES (?)", name); dialog.dispose();
            JOptionPane.showMessageDialog(this, "Student Framework Account Activated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        dialog.setVisible(true);
    }

    private void triggerTeacherPopupForm() {
        JDialog dialog = createFormDialog("Faculty Staff Authorization Desk", 450, 320); JTextField txtUser = createFormTextField(); JTextField txtDept = createFormTextField(); addFormRow(dialog, "Teacher Name:", txtUser, 40); addFormRow(dialog, "Department Node:", txtDept, 95);
        JButton btnSubmit = createFormSubmitButton("Authorize Core Faculty"); btnSubmit.setBounds(130, 180, 240, 35); dialog.add(btnSubmit);
        btnSubmit.addActionListener(e -> {
            String name = txtUser.getText().trim(); if(name.isEmpty()) return;
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS schema_teachers (id INTEGER PRIMARY KEY AUTOINCREMENT, teacher_name TEXT)");
            executePreparedInsert("INSERT INTO schema_teachers (teacher_name) VALUES (?)", name); dialog.dispose();
            JOptionPane.showMessageDialog(this, "Faculty Cryptographic Credentials Active!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        dialog.setVisible(true);
    }

    private void triggerSyllabusPopupForm() {
        JDialog dialog = createFormDialog("Syllabus Management Registry", 450, 250); JTextField txtSyllabus = createFormTextField(); addFormRow(dialog, "Curriculum Log:", txtSyllabus, 40);
        JButton btnSubmit = createFormSubmitButton("Publish Matrix Update"); btnSubmit.setBounds(130, 120, 240, 35); dialog.add(btnSubmit);
        btnSubmit.addActionListener(e -> {
            String val = txtSyllabus.getText().trim(); if(val.isEmpty()) return;
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS campus_syllabus (id INTEGER PRIMARY KEY AUTOINCREMENT, status_log TEXT)");
            executePreparedInsert("INSERT INTO campus_syllabus (status_log) VALUES (?)", val); dialog.dispose();
            JOptionPane.showMessageDialog(this, "Syllabus Matrix Saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        dialog.setVisible(true);
    }

    private void triggerBranchPopupForm() {
        JDialog dialog = createFormDialog("Branch Parameter Deployment", 450, 250); JTextField txtBranch = createFormTextField(); addFormRow(dialog, "Branch Variant:", txtBranch, 40);
        JButton btnSubmit = createFormSubmitButton("Map Branch Node"); btnSubmit.setBounds(130, 120, 240, 35); dialog.add(btnSubmit);
        btnSubmit.addActionListener(e -> {
            String val = txtBranch.getText().trim(); if(val.isEmpty()) return;
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS campus_branches (id INTEGER PRIMARY KEY AUTOINCREMENT, branch_name TEXT)");
            executePreparedInsert("INSERT INTO campus_branches (branch_name) VALUES (?)", val); dialog.dispose();
            JOptionPane.showMessageDialog(this, "Branch Configuration Deployed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        dialog.setVisible(true);
    }

    private void triggerInternshipPopupForm() {
        JDialog dialog = createFormDialog("Internship Drive Broadcaster", 500, 280); JTextField txtIntern = createFormTextField(); addFormRow(dialog, "Drive Payload:", txtIntern, 40);
        JButton btnSubmit = createFormSubmitButton("Dispatch To Network Grid"); btnSubmit.setBounds(130, 130, 260, 35); dialog.add(btnSubmit);
        btnSubmit.addActionListener(e -> {
            String val = txtIntern.getText().trim(); if(val.isEmpty()) return;
            executePreparedInsert("INSERT INTO campus_notices (alert_msg) VALUES (?)", "🎉 CAMPUS EVENT: " + val); dialog.dispose();
            JOptionPane.showMessageDialog(this, "Campus Event synchronized live!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        dialog.setVisible(true);
    }

    private JDialog createFormDialog(String title, int w, int h) {
        JDialog d = new JDialog(this, title, true); d.setSize(w, h); d.getContentPane().setBackground(new Color(15, 23, 42)); d.setLayout(null); d.setLocationRelativeTo(this); return d;
    }

    private JTextField createFormTextField() {
        JTextField f = new JTextField(); f.setFont(new Font("Segoe UI", Font.PLAIN, 12)); f.setForeground(Color.WHITE); f.setBackground(new Color(30, 41, 59)); f.setCaretColor(new Color(56, 189, 248));
        f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(71, 85, 105), 1), BorderFactory.createEmptyBorder(2, 6, 2, 8))); return f;
    }

    private JButton createFormSubmitButton(String txt) {
        JButton b = new JButton(txt); b.setFont(new Font("Segoe UI", Font.BOLD, 12)); b.setForeground(Color.WHITE); b.setBackground(new Color(14, 165, 233)); b.setFocusPainted(false); b.setCursor(new Cursor(Cursor.HAND_CURSOR)); return b;
    }

    private void addFormRow(JDialog d, String lblText, JTextField field, int y) {
        JLabel l = new JLabel(lblText, JLabel.RIGHT); l.setFont(new Font("Segoe UI", Font.BOLD, 12)); l.setForeground(new Color(148, 163, 184)); l.setBounds(10, y, 110, 30); d.add(l); field.setBounds(130, y, 220, 32); d.add(field);
    }

    private void executeDirectUpdateQuery(String sql) {
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) { stmt.execute(sql); } catch (Exception e) { e.printStackTrace(); }
    }

    private void executePreparedInsert(String sql, String bindValue) {
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bindValue); pstmt.executeUpdate(); fetchLiveDatabaseMetrics();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void fetchLiveDatabaseMetrics() {
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS campus_courses (id INTEGER PRIMARY KEY AUTOINCREMENT, course_name TEXT)");
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS schema_students (id PRIMARY KEY AUTOINCREMENT, student_name TEXT)");
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS schema_teachers (id INTEGER PRIMARY KEY AUTOINCREMENT, teacher_name TEXT)");
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS system_issues (id INTEGER PRIMARY KEY AUTOINCREMENT, scope TEXT, user_key TEXT, logs TEXT)");
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS campus_notices (id INTEGER PRIMARY KEY AUTOINCREMENT, alert_msg TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");

            int courseCount = 0, studentCount = 0, teacherCount = 0;
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM campus_courses"); ResultSet rs = ps.executeQuery()) { if (rs.next()) { courseCount = rs.getInt(1); lblStudVal.setText(courseCount + " Courses"); } }
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM schema_students"); ResultSet rs = ps.executeQuery()) { if (rs.next()) { studentCount = rs.getInt(1); lblTeachVal.setText(studentCount + " Students"); } }
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM schema_teachers"); ResultSet rs = ps.executeQuery()) { if (rs.next()) { teacherCount = rs.getInt(1); lblClassVal.setText(teacherCount + " Teachers"); } }

            syllabusAngle = Math.min(360, courseCount * 36); unitsAngle = Math.min(360, studentCount * 18); classesAngle = Math.min(360, teacherCount * 45);

            modelStud.setRowCount(0);
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, course_name FROM campus_courses ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { Vector<String> row = new Vector<>(); row.add("#CRS-0" + rs.getInt("id")); row.add(rs.getString("course_name").toUpperCase()); modelStud.addRow(row); }
            }
            modelTeach.setRowCount(0);
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, student_name FROM schema_students ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { Vector<String> row = new Vector<>(); row.add("#STU-0" + rs.getInt("id")); row.add(rs.getString("student_name").toUpperCase()); modelTeach.addRow(row); }
            }
            modelSup.setRowCount(0);
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, teacher_name FROM schema_teachers ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { Vector<String> row = new Vector<>(); row.add("#TCH-90" + rs.getInt("id")); row.add(rs.getString("teacher_name").toUpperCase()); modelSup.addRow(row); }
            }

            modelGrievances.setRowCount(0);
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, scope, user_key, logs FROM system_issues ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vector<String> row = new Vector<>();
                    row.add(rs.getString("scope").equals("Student") ? "#BUG-" + rs.getInt("id") : "#FLT-" + rs.getInt("id"));
                    row.add(rs.getString("scope").toUpperCase()); row.add(rs.getString("user_key").toUpperCase());
                    row.add(rs.getString("logs")); modelGrievances.addRow(row);
                }
            }

            modelNoticesArchive.setRowCount(0);
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, timestamp, alert_msg FROM campus_notices ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vector<String> row = new Vector<>();
                    row.add("#BCAST-00" + rs.getInt("id")); row.add(rs.getString("timestamp"));
                    row.add(rs.getString("alert_msg")); modelNoticesArchive.addRow(row);
                }
            }
            mainScrollContent.repaint();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadDashboardSliderImage(String pathStr) {
        try {
            String cleanPath = pathStr.substring(pathStr.lastIndexOf("/") + 1);
            java.net.URL imgUrl = getClass().getResource("/images/" + cleanPath);
            if (imgUrl != null) {
                ImageIcon rawIcon = new ImageIcon(imgUrl);
                Image img = rawIcon.getImage().getScaledInstance(1220, 200, Image.SCALE_SMOOTH);
                lblSliderImage.setIcon(new ImageIcon(img));
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private JButton createNavMenuButton(String label) {
        JButton btn = new JButton(label); btn.setFont(new Font("Segoe UI", Font.BOLD, 11)); btn.setForeground(Color.WHITE); btn.setBackground(new Color(24, 32, 56)); btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(71, 85, 105), 1)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(14, 165, 233)); } public void mouseExited(MouseEvent e) { btn.setBackground(new Color(24, 32, 56)); }
        });
        return btn;
    }

    private JPanel createFlatCard() {
        JPanel card = new JPanel(null) { @Override protected void paintComponent(Graphics g) { g.setColor(new Color(30, 41, 59)); g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); g.setColor(new Color(255, 255, 255, 12)); g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10); } };
        card.setOpaque(false); return card;
    }

    private JTable createEmbeddedTable(DefaultTableModel model) {
        JTable table = new JTable(model); table.setFont(new Font("Segoe UI", Font.PLAIN, 12)); table.setForeground(new Color(203, 213, 225)); table.setBackground(new Color(15, 23, 42));
        table.setGridColor(new Color(30, 41, 59, 120)); table.setRowHeight(28); table.setSelectionBackground(new Color(14, 165, 233, 40));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12)); table.getTableHeader().setBackground(new Color(24, 32, 56)); table.getTableHeader().setForeground(Color.WHITE); return table;
    }
    // 🎯 एरर फ़िक्स: प्लेसमेंट फॉर्म को ओपन करने वाला triggerPlacementPopupForm मेथड जोड़ दिया है
    private void triggerPlacementPopupForm() {
        JDialog dialog = createFormDialog("Placement Package Registry Desk", 500, 280);
        JTextField txtPlace = createFormTextField();
        addFormRow(dialog, "Drive Metrics:", txtPlace, 40);
        JButton btnSubmit = createFormSubmitButton("Push Live Alert Logs");
        btnSubmit.setBounds(130, 130, 260, 35);
        dialog.add(btnSubmit);

        btnSubmit.addActionListener(e -> {
            String val = txtPlace.getText().trim();
            if(val.isEmpty()) return;
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS campus_notices (id INTEGER PRIMARY KEY AUTOINCREMENT, alert_msg TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            executePreparedInsert("INSERT INTO campus_notices (alert_msg) VALUES (?)", "🚀 PLACEMENT DRIVE: " + val);
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Placement Records Ledger updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        dialog.setVisible(true);
    }
    // 🎯 एरर फ़िक्स: कैम्पस इवेंट्स को ब्रॉडकास्ट करने वाला triggerEventPopupForm मेथड जोड़ दिया है
    private void triggerEventPopupForm() {
        JDialog dialog = createFormDialog("Central Campus Broadcast Matrix", 500, 280);
        JTextField txtEvent = createFormTextField();
        addFormRow(dialog, "Event Payload:", txtEvent, 40);
        JButton btnSubmit = createFormSubmitButton("Fire Broadcast Signal");
        btnSubmit.setBounds(130, 130, 260, 35);
        dialog.add(btnSubmit);

        btnSubmit.addActionListener(e -> {
            String val = txtEvent.getText().trim();
            if(val.isEmpty()) return;
            executeDirectUpdateQuery("CREATE TABLE IF NOT EXISTS campus_notices (id INTEGER PRIMARY KEY AUTOINCREMENT, alert_msg TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            executePreparedInsert("INSERT INTO campus_notices (alert_msg) VALUES (?)", "🎉 CAMPUS EVENT: " + val);
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Campus Event synchronized live!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        dialog.setVisible(true);
    }

}
