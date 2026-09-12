package com.edusphere.ui;

import javax.swing.*;
import java.awt.*;

public class CourseManagement extends JFrame {
    private JTextField txtCourseName, txtDuration, txtFees;
    private JButton btnSave, btnBack;

    public CourseManagement() {
        setTitle("EduSphere - Course Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("Add New Course", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(50, 20, 400, 30);
        add(lblTitle);

        JLabel lblName = new JLabel("Course Name:");
        lblName.setBounds(50, 80, 100, 30);
        add(lblName);

        txtCourseName = new JTextField();
        txtCourseName.setBounds(150, 80, 250, 30);
        add(txtCourseName);

        JLabel lblDuration = new JLabel("Duration:");
        lblDuration.setBounds(50, 130, 100, 30);
        add(lblDuration);

        txtDuration = new JTextField();
        txtDuration.setBounds(150, 130, 250, 30);
        add(txtDuration);

        JLabel lblFees = new JLabel("Course Fees:");
        lblFees.setBounds(50, 180, 100, 30);
        add(lblFees);

        txtFees = new JTextField();
        txtFees.setBounds(150, 180, 250, 30);
        add(txtFees);

        btnSave = new JButton("Save Course");
        btnSave.setBounds(150, 240, 130, 35);
        add(btnSave);

        btnBack = new JButton("Back");
        btnBack.setBounds(290, 240, 110, 35);
        add(btnBack);

        btnSave.addActionListener(e -> {
            if(txtCourseName.getText().isEmpty() || txtDuration.getText().isEmpty() || txtFees.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
            } else {
                JOptionPane.showMessageDialog(null, "Course data saved successfully!");
            }
        });

        btnBack.addActionListener(e -> {
            this.dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
