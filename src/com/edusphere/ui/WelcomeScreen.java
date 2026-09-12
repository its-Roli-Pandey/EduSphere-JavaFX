package com.edusphere.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class WelcomeScreen extends JWindow {

    private int loadingProgress = 0;
    private JLabel lblPercentage;

    public WelcomeScreen() {
        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 23, 42), w, h, new Color(30, 41, 59));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                g2d.setColor(new Color(56, 189, 248, 15));
                g2d.drawOval(-50, -50, 300, 300);
                g2d.drawOval(w - 200, h - 200, 400, 400);

                g2d.setColor(new Color(14, 165, 233));
                g2d.fillRect(0, h - 4, (w * loadingProgress) / 100, 4);
            }
        };

        setSize(600, 380);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 600, 380, 20, 20));

        JLabel lblLogo = new JLabel("eΣ", JLabel.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(14, 165, 233, 30));
                g2d.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblLogo.setForeground(new Color(56, 189, 248));
        lblLogo.setBounds(260, 45, 80, 80);
        content.add(lblLogo);

        JLabel lblTitle = new JLabel("EDUSPHERE", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(50, 140, 500, 45);
        content.add(lblTitle);

        JLabel lblSub = new JLabel("INTELLIGENT ACADEMIC ECOSYSTEM CORE", JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSub.setForeground(new Color(148, 163, 184));
        lblSub.setBounds(50, 190, 500, 20);
        content.add(lblSub);

        lblPercentage = new JLabel("00%", JLabel.CENTER);
        lblPercentage.setFont(new Font("Consolas", Font.BOLD, 24));
        lblPercentage.setForeground(new Color(56, 189, 248));
        lblPercentage.setBounds(250, 240, 100, 30);
        content.add(lblPercentage);

        JLabel lblStatus = new JLabel("INITIALIZING SECURE DATABASE TERMINAL CONFIG...", JLabel.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblStatus.setForeground(new Color(100, 116, 139));
        lblStatus.setBounds(50, 320, 500, 20);
        content.add(lblStatus);

        add(content);
        setVisible(true);

        startLoadingAnimation(content);
    }

    private void startLoadingAnimation(JPanel content) {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(25);
                    loadingProgress = i;

                    final String textPercent = String.format("%02d%%", i);
                    SwingUtilities.invokeLater(() -> {
                        lblPercentage.setText(textPercent);
                        content.repaint();
                    });
                }

                SwingUtilities.invokeLater(() -> {
                    this.dispose();
                    new ProjectIndexPortal(); // 🎯 सुधार: अब यह सीधे वेब-स्टाइल इंडेक्स पोर्टल खोलेगा
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeScreen());
    }
}
