package com.edusphere.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SupportPage extends JFrame {
    private JLabel lblScrollNotice, lblRightImage;
    private Timer scrollTimer;
    private int noticeY = 180;
    private final String localImagePath = "C:\\javaFX\\porject2\\images\\slider4.jpg";

    public SupportPage() {
        setTitle("EduSphere Cloud - Advanced Technical Support Tunnel");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(10, 15, 30));
        add(mainPanel);

        JPanel sliderPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(24, 43, 73), getWidth(), 0, new Color(234, 179, 8));
                g2d.setPaint(gp); g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sliderPanel.setBounds(30, 15, 925, 100);
        mainPanel.add(sliderPanel);

        JLabel lblSliderTitle = new JLabel("CENTRAL OPERATIONS HELPDESK RE-ROUTE MATRIX", JLabel.CENTER);
        lblSliderTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSliderTitle.setForeground(Color.WHITE);
        lblSliderTitle.setBounds(20, 35, 885, 30);
        sliderPanel.add(lblSliderTitle);

        JPanel panelLeftNotice = new JPanel(null);
        panelLeftNotice.setBackground(new Color(15, 23, 42));
        panelLeftNotice.setBorder(BorderFactory.createLineBorder(new Color(234, 179, 8, 40), 1));
        panelLeftNotice.setBounds(30, 135, 450, 200);
        mainPanel.add(panelLeftNotice);

        JLabel lblNoticeHeader = new JLabel("📢 PRIORITY ENQUIRY CHANNELS TICKETS:", JLabel.LEFT);
        lblNoticeHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNoticeHeader.setForeground(new Color(234, 179, 8));
        lblNoticeHeader.setBounds(20, 10, 400, 20);
        panelLeftNotice.add(lblNoticeHeader);

        lblScrollNotice = new JLabel("<html><p style='width:300px;'>[TICKET #4402]: Resolved credential lockout logs.<br><br>[TUNNEL]: Syncing priority mail pipeline: hello@edusphere.live<br><br>[HELP]: Standard operational hardware sync validated.<br><br>[STATUS]: Gateway diagnostics reporting 100% up-time logs.</p></html>");
        lblScrollNotice.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblScrollNotice.setForeground(new Color(203, 213, 225));
        lblScrollNotice.setBounds(20, noticeY, 410, 160);
        panelLeftNotice.add(lblScrollNotice);

        lblRightImage = new JLabel("", JLabel.CENTER);
        lblRightImage.setBounds(505, 135, 450, 200);
        lblRightImage.setBorder(BorderFactory.createLineBorder(new Color(234, 179, 8, 40), 1));
        mainPanel.add(lblRightImage);
        loadLocalImage(localImagePath);

        JPanel panelAbout = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 4)); g.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g.setColor(new Color(255, 255, 255, 12)); g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            }
        };
        panelAbout.setBounds(30, 355, 925, 180);
        panelAbout.setOpaque(false);
        mainPanel.add(panelAbout);

        JLabel lblAboutTitle = new JLabel("ABOUT EDUSPHERE SUPPORT RELAY", JLabel.LEFT);
        lblAboutTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAboutTitle.setForeground(new Color(234, 179, 8));
        lblAboutTitle.setBounds(25, 15, 300, 20);
        panelAbout.add(lblAboutTitle);

        JTextArea txtAbout = new JTextArea(
                "Our centralized helpdesk infrastructure is dedicated to maintaining flawless digital up-time.\n" +
                        "If users experience database index drops, permission mismatches, or file staging interruptions,\n" +
                        "this priority console routes diagnostic telemetry logs straight to our master system admins.\n" +
                        "We enforce immediate token restorations to keep institutional data flows highly fluid."
        );
        txtAbout.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtAbout.setForeground(new Color(148, 163, 184));
        txtAbout.setOpaque(false); txtAbout.setEditable(false);
        txtAbout.setBounds(25, 45, 875, 110);
        panelAbout.add(txtAbout);

        JPanel footerPanel = new JPanel(null);
        footerPanel.setBackground(new Color(15, 23, 42));
        footerPanel.setPreferredSize(new Dimension(1000, 55));
        add(footerPanel, BorderLayout.SOUTH);

        JLabel lblCopy = new JLabel("© 2026 EduSphere.live Core Matrix. All Rights Secured.");
        lblCopy.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblCopy.setForeground(Color.GRAY);
        lblCopy.setBounds(30, 18, 400, 20);
        footerPanel.add(lblCopy);

        JButton btnClose = new JButton("Close Module");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClose.setBounds(835, 14, 120, 28);
        footerPanel.add(btnClose);
        btnClose.addActionListener(e -> { scrollTimer.stop(); this.dispose(); });

        scrollTimer = new Timer(35, e -> {
            noticeY--;
            if (noticeY < -150) noticeY = 200;
            lblScrollNotice.setLocation(20, noticeY);
        });
        scrollTimer.start();

        setVisible(true);
    }

    private void loadLocalImage(String path) {
        try {
            if (new File(path).exists()) {
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(450, 200, Image.SCALE_SMOOTH);
                lblRightImage.setIcon(new ImageIcon(img));
            } else { lblRightImage.setText("Image Staged: slider4.jpg"); }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
