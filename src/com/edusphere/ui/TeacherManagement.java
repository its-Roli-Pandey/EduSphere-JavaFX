package com.edusphere.ui;

import javax.swing.*;
import java.awt.*;

public class TeacherManagement extends JFrame {
    private JTextField txtTeacherName, txtEmail, txtSubject;
    private JButton btnSave, btnBack;

    public TeacherManagement() {
        setTitle("EduSphere - Teacher Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("Add New Teacher", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(50, 20, 400, 30);
        add(lblTitle);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(50, 80, 100, 30);
        add(lblName);

        txtTeacherName = new JTextField();
        txtTeacherName.setBounds(150, 80, 250, 30);
        add(txtTeacherName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 130, 100, 30);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 130, 250, 30);
        add(txtEmail);

        JLabel lblSubject = new JLabel("Subject:");
        lblSubject.setBounds(50, 180, 100, 30);
        add(lblSubject);

        txtSubject = new JTextField();
        txtSubject.setBounds(150, 180, 250, 30);
        add(txtSubject);

        btnSave = new JButton("Save Teacher");
        btnSave.setBounds(150, 240, 130, 35);
        add(btnSave);

        btnBack = new JButton("Back");
        btnBack.setBounds(290, 240, 110, 35);
        add(btnBack);

        btnSave.addActionListener(e -> {
            if(txtTeacherName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtSubject.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
            } else {
                JOptionPane.showMessageDialog(null, "Teacher data saved successfully!");
            }
        });

        btnBack.addActionListener(e -> {
            this.dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
