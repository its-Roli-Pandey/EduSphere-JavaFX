package com.edusphere.ui;

import javax.swing.*;
import java.awt.*;

public class AttendanceScreen extends JFrame {
    private JTextField txtStudentID;
    private JRadioButton rbPresent, rbAbsent;
    private ButtonGroup bgStatus;
    private JButton btnSave, btnBack;

    public AttendanceScreen() {
        setTitle("EduSphere - Attendance Terminal");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("Daily Attendance Registry", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(50, 20, 400, 30);
        add(lblTitle);

        JLabel lblID = new JLabel("Student ID:");
        lblID.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblID.setBounds(50, 80, 100, 30);
        add(lblID);

        txtStudentID = new JTextField();
        txtStudentID.setBounds(160, 80, 240, 30);
        add(txtStudentID);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblStatus.setBounds(50, 140, 100, 30);
        add(lblStatus);

        rbPresent = new JRadioButton("Present");
        rbPresent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rbPresent.setForeground(new Color(46, 204, 113));
        rbPresent.setBounds(160, 140, 100, 30);
        rbPresent.setSelected(true);

        rbAbsent = new JRadioButton("Absent");
        rbAbsent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rbAbsent.setForeground(new Color(231, 76, 60));
        rbAbsent.setBounds(280, 140, 100, 30);

        bgStatus = new ButtonGroup();
        bgStatus.add(rbPresent);
        bgStatus.add(rbAbsent);

        add(rbPresent);
        add(rbAbsent);

        btnSave = new JButton("Save Record");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBounds(160, 210, 120, 35);
        add(btnSave);

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setBounds(290, 210, 110, 35);
        add(btnBack);

        btnSave.addActionListener(e -> {
            if (txtStudentID.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Student ID!");
            } else {
                String status = rbPresent.isSelected() ? "Present" : "Absent";
                JOptionPane.showMessageDialog(this, "Attendance marked as " + status + " successfully!");
            }
        });

        btnBack.addActionListener(e -> this.dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
