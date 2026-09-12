package com.edusphere.ui;

import javax.swing.*;
import java.awt.*;

public class MarksEntryScreen extends JFrame {
    private JTextField txtStudentID, txtSubject, txtMarks;
    private JButton btnSave, btnBack;

    public MarksEntryScreen() {
        setTitle("EduSphere - Performance Ledger");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("Enter Student Academic Marks", JLabel.CENTER);
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

        JLabel lblSubject = new JLabel("Subject Name:");
        lblSubject.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubject.setBounds(50, 140, 100, 30);
        add(lblSubject);

        txtSubject = new JTextField();
        txtSubject.setBounds(160, 140, 240, 30);
        add(txtSubject);

        JLabel lblMarks = new JLabel("Marks Obtained:");
        lblMarks.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMarks.setBounds(50, 200, 120, 30);
        add(lblMarks);

        txtMarks = new JTextField();
        txtMarks.setBounds(160, 200, 240, 30);
        add(txtMarks);

        btnSave = new JButton("Save Ledger");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBounds(160, 270, 120, 35);
        add(btnSave);

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setBounds(290, 270, 110, 35);
        add(btnBack);

        btnSave.addActionListener(e -> {
            if (txtStudentID.getText().trim().isEmpty() || txtSubject.getText().trim().isEmpty() || txtMarks.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields properly!");
            } else {
                JOptionPane.showMessageDialog(this, "Academic marks saved successfully!");
            }
        });

        btnBack.addActionListener(e -> this.dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
