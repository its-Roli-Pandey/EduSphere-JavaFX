package com.edusphere.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ProjectIndexPortal extends JFrame {
    private JButton btnGoDashboard, btnMenuHome, btnMenuFeatures, btnMenuAbout, btnMenuSupport, btnMenuContact, btnGetStartedCenter, btnFloatingName;
    private JLabel lblSliderImage, lblAboutImage;
    private int currentImageIndex = 0;
    private Timer imageSliderTimer, textFloatTimer;
    private JPanel mainScrollContent;
    private JScrollPane mainScrollPane;

    private int textX = 100, textY = 100;
    private int dirX = 2, dirY = 2;

    private final String[] imagePaths = {
            "src/images/image_7f806bd2.png",
            "src/images/image_4da4b57a.png",
            "src/images/image_ab89f8e.png"
    };
    private final String aboutImagePath = "src/images/download.jpg";



    public ProjectIndexPortal() {
        setTitle("EduSphere Live - UIET Digital Academic Gateway");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel navBar = new JPanel(null);
        navBar.setBackground(new Color(15, 23, 42));
        navBar.setPreferredSize(new Dimension(1200, 70));
        add(navBar, BorderLayout.NORTH);

        JLabel lblBrand = new JLabel("EduSphere");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setBounds(45, 18, 250, 30);
        navBar.add(lblBrand);

        // 🚀 WELL-SPACED 5 BUTTONS MATRIX (सबको अलग-अलग परफेक्ट गैप पर सेट कर दिया है)
        btnMenuHome = createNavButton("Home"); btnMenuHome.setBounds(390, 20, 80, 30); navBar.add(btnMenuHome);
        btnMenuFeatures = createNavButton("Resources"); btnMenuFeatures.setBounds(485, 20, 105, 30); navBar.add(btnMenuFeatures);
        btnMenuAbout = createNavButton("Ecosystem"); btnMenuAbout.setBounds(605, 20, 105, 30); navBar.add(btnMenuAbout);
        btnMenuSupport = createNavButton("Support"); btnMenuSupport.setBounds(725, 20, 90, 30); navBar.add(btnMenuSupport);
        btnMenuContact = createNavButton("Contact"); btnMenuContact.setBounds(830, 20, 90, 30); navBar.add(btnMenuContact);

        btnGoDashboard = new JButton("SIGN IN");
        btnGoDashboard.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGoDashboard.setForeground(Color.WHITE);
        btnGoDashboard.setBackground(new Color(15, 23, 42));
        btnGoDashboard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnGoDashboard.setFocusPainted(false);
        btnGoDashboard.setBounds(980, 18, 150, 34);
        navBar.add(btnGoDashboard);

        mainScrollContent = new JPanel(null);
        mainScrollContent.setBackground(new Color(10, 15, 30));
        mainScrollContent.setPreferredSize(new Dimension(1160, 1250));

        mainScrollPane = new JScrollPane(mainScrollContent);
        mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(mainScrollPane, BorderLayout.CENTER);

        btnFloatingName = new JButton("🎓 ROLI // UIET CORE") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(30, 41, 59, 230)); g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(56, 189, 248, 150)); g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                super.paintComponent(g);
            }
        };
        btnFloatingName.setFont(new Font("Segoe UI", Font.BOLD, 12)); btnFloatingName.setForeground(new Color(56, 189, 248));
        btnFloatingName.setContentAreaFilled(false); btnFloatingName.setBorderPainted(false); btnFloatingName.setFocusPainted(false);
        btnFloatingName.setBounds(textX, textY, 180, 32); mainScrollContent.add(btnFloatingName);

        JPanel sliderContainer = new JPanel(null); sliderContainer.setOpaque(false); sliderContainer.setBounds(45, 15, 1105, 230); mainScrollContent.add(sliderContainer);
        lblSliderImage = new JLabel("", JLabel.CENTER); lblSliderImage.setBounds(0, 0, 1105, 230); lblSliderImage.setBorder(BorderFactory.createLineBorder(new Color(14, 165, 233, 60), 1));
        sliderContainer.add(lblSliderImage); loadLocalSliderImage(imagePaths[currentImageIndex]);

        JPanel panelTextSection = new JPanel(null); panelTextSection.setOpaque(false); panelTextSection.setBounds(45, 260, 1105, 130); mainScrollContent.add(panelTextSection);
        JLabel lblHeroTitle = new JLabel("<html><center>Making <font color='#1E90FF'>Your</font> Academic Life Easier EDUSPHERE</center></html>", JLabel.CENTER);
        lblHeroTitle.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblHeroTitle.setForeground(Color.WHITE); lblHeroTitle.setBounds(50, 10, 1005, 35); panelTextSection.add(lblHeroTitle);
        JLabel lblHeroSub = new JLabel("Everything you need to survive college—notes, tips, tools, and real support every day.", JLabel.CENTER);
        lblHeroSub.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblHeroSub.setForeground(new Color(148, 163, 184)); lblHeroSub.setBounds(50, 50, 1005, 20); panelTextSection.add(lblHeroSub);

        btnGetStartedCenter = new JButton("GET STARTED") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g; g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE); g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); super.paintComponent(g);
            }
        };
        btnGetStartedCenter.setFont(new Font("Segoe UI", Font.BOLD, 11)); btnGetStartedCenter.setForeground(new Color(14, 165, 233));
        btnGetStartedCenter.setContentAreaFilled(false); btnGetStartedCenter.setBorderPainted(false); btnGetStartedCenter.setFocusPainted(false);
        btnGetStartedCenter.setCursor(new Cursor(Cursor.HAND_CURSOR)); btnGetStartedCenter.setBounds(477, 85, 150, 32); panelTextSection.add(btnGetStartedCenter);

        int cardY = 410; int cardW = 345; int cardH = 175;
        JPanel cardFeatures = createPopOutCard("PYQ PAPERS", "We’ve compiled a complete set of previous year question papers for all subjects to help you prepare effectively. These papers will familiarize you with the exam pattern, highlight important topics, and support smart focused revision.");
        cardFeatures.setBounds(45, cardY, cardW, cardH); mainScrollContent.add(cardFeatures);
        JPanel cardFiles = createPopOutCard("CLASS NOTES", "Access a comprehensive collection of high-quality handwritten notes for every subject. Carefully curated for clarity and depth, these notes include detailed diagrams, key formulas, and clear explanations to simplify complex topics.");
        cardFiles.setBounds(425, cardY, cardW, cardH); mainScrollContent.add(cardFiles);
        JPanel cardContact = createPopOutCard("RELEVANT LECTURES", "All essential lectures across subjects have been thoughtfully compiled here to offer a clear, well-structured, and subject-focused learning experience. This collection is designed to help you grasp key concepts efficiently.");
        cardContact.setBounds(805, cardY, cardW, cardH); mainScrollContent.add(cardContact);

        JPanel panelAbout = createGlassCard(); panelAbout.setBounds(45, 610, 1105, 320); mainScrollContent.add(panelAbout);
        JLabel lblAboutTitle = new JLabel("ABOUT EDUSPHERE MANAGEMENT MATRIX", JLabel.LEFT);
        lblAboutTitle.setFont(new Font("Segoe UI", Font.BOLD, 14)); lblAboutTitle.setForeground(new Color(234, 179, 8)); lblAboutTitle.setBounds(25, 20, 400, 20); panelAbout.add(lblAboutTitle);
        JTextArea txtAboutDesc = createCardTextArea("EduSphere.live is a next-generation decentralized academic infrastructure architecture. Engineered to optimize institutional logistics, it features secure cryptographic authentication, automated authorization protocols, and real-time ledger records for faculty and student tracking grids.\n\nEnforced with standalone SQLite databank clusters and adaptive human-verification gates, EduSphere bridges the gap between educational transparency and robust enterprise specs.");
        txtAboutDesc.setBounds(25, 60, 520, 230); panelAbout.add(txtAboutDesc);
        lblAboutImage = new JLabel("", JLabel.CENTER); lblAboutImage.setBounds(565, 25, 515, 270); lblAboutImage.setBorder(BorderFactory.createLineBorder(new Color(14, 165, 233, 40), 1)); panelAbout.add(lblAboutImage);

        loadAboutLocalImage(aboutImagePath);


        JPanel panelNotice = createGlassCard(); panelNotice.setBounds(45, 950, 535, 180); mainScrollContent.add(panelNotice);
        JLabel lblNoticeTitle = new JLabel("CAMPUS NOTICE BOARD (LIVE SYSTEM)", JLabel.LEFT);
        lblNoticeTitle.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblNoticeTitle.setForeground(new Color(56, 189, 248)); lblNoticeTitle.setBounds(20, 15, 300, 20); panelNotice.add(lblNoticeTitle);
        JTextArea txtNotice = createCardTextArea("• Mid-Term Project Submissions scheduled for engineering tracks next week.\n• Faculty members requested to update Syllabus PPT logs in Teacher Portal.\n• Student registration matrix is now open for centralized new enrollments.");
        txtNotice.setBounds(20, 45, 500, 120); panelNotice.add(txtNotice);

        JPanel panelSecurity = createGlassCard(); panelSecurity.setBounds(615, 950, 535, 180); mainScrollContent.add(panelSecurity);
        JLabel lblSecTitle = new JLabel("CORE SECURITY ANALYTICS ENGINE", JLabel.LEFT);
        lblSecTitle.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblSecTitle.setForeground(new Color(34, 197, 94)); lblSecTitle.setBounds(20, 15, 300, 20); panelSecurity.add(lblSecTitle);

        JTextArea txtSec = createCardTextArea("• Session Connection Status: Secured via Local Isolation Tunnel arrays.\n• Security Gateway Verification Walls: Active Numeric Captcha verification logic.\n• Access Privilege Controller: Dual-Layer Role Authorization Enforced.\n• Cryptographic Ledger Integrity: Automated Hash Check sum validated.");
        txtSec.setBounds(20, 45, 500, 120); panelSecurity.add(txtSec);

        // FOOTER MARGIN (SHIFTED DOWN TO 1155)
        JPanel footerPanel = new JPanel(null); footerPanel.setBackground(new Color(10, 15, 30)); footerPanel.setBounds(45, 1155, 1105, 50); mainScrollContent.add(footerPanel);
        JSeparator sep = new JSeparator(); sep.setForeground(new Color(30, 41, 59, 100)); sep.setBounds(0, 0, 1105, 2); footerPanel.add(sep);
        JLabel lblCopyright = new JLabel("© 2026 EduSphere.live — Academic Resource Platform. Designed by ROLI");
        lblCopyright.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblCopyright.setForeground(new Color(56, 189, 248)); lblCopyright.setBounds(0, 15, 500, 20); footerPanel.add(lblCopyright);
        JLabel lblSocials = new JLabel("Facebook & Instagram: @edusphere.live");
        lblSocials.setFont(new Font("Segoe UI", Font.BOLD, 11)); lblSocials.setForeground(new Color(100, 116, 139)); lblSocials.setBounds(750, 15, 350, 20); footerPanel.add(lblSocials);

        btnMenuHome.addActionListener(e -> JOptionPane.showMessageDialog(this, "Central Portal Gateway Node is fully active.", "System Diagnostic", JOptionPane.INFORMATION_MESSAGE));
        btnMenuFeatures.addActionListener(e -> { if (SessionManager.isLoggedIn) new ResourcesPage(); else { SessionManager.pendingRedirectPage = "Resources"; new LoginScreen(this).setVisible(true); } });
        btnMenuAbout.addActionListener(e -> { if (SessionManager.isLoggedIn) new EcosystemPage(); else { SessionManager.pendingRedirectPage = "Ecosystem"; new LoginScreen(this).setVisible(true); } });
        btnMenuSupport.addActionListener(e -> { if (SessionManager.isLoggedIn) new SupportPage(); else { SessionManager.pendingRedirectPage = "Support"; new LoginScreen(this).setVisible(true); } });
        btnMenuContact.addActionListener(e -> new ContactUsPage());


        btnGoDashboard.addActionListener(e -> new LoginScreen(this).setVisible(true));
        btnGetStartedCenter.addActionListener(e -> new LoginScreen(this).setVisible(true));

        btnFloatingName.addActionListener(e -> JOptionPane.showMessageDialog(this, "EduSphere Core Node\nLead Systems Architect: PROF. ROLI\n[UIET DEPT]", "Identity Matrix", JOptionPane.INFORMATION_MESSAGE));
        imageSliderTimer = new Timer(4000, e -> { currentImageIndex = (currentImageIndex + 1) % imagePaths.length; loadLocalSliderImage(imagePaths[currentImageIndex]); });
        imageSliderTimer.start();

        // 🎯 एरर फ़िक्स: बटन की चौड़ाई को 45 से बढ़ाकर 100 कर दिया है ताकि '🌙 Themes' पूरा साफ़ दिखे और 'SIGN IN' को थोड़ा आगे खिसका दिया है
        JButton btnTheme = createNavButton("🌙 Themes");
        btnTheme.setBounds(880, 20, 90, 30);
        navBar.add(btnTheme);


        btnGoDashboard.setBounds(995, 18, 130, 34);

        btnTheme.addActionListener(e -> {
            togglePortalUIVisualTheme();
            btnTheme.setText(isDarkModeActive ? "🌙 Themes" : "☀️ Themes");
        });

        textFloatTimer = new Timer(30, e -> {
            textX += dirX; textY += dirY;
            if (textX < 10 || textX > 1000) dirX = -dirX;
            if (textY < 10 || textY > 1180) dirY = -dirY;
            btnFloatingName.setLocation(textX, textY);
        });

        textFloatTimer.start();

        setVisible(true);
    }


    private void loadLocalSliderImage(String path) {
        try {
            // C:\\ या src/ हटाकर सीधे क्लास पाथ से इमेज को स्ट्रीम किया
            String cleanPath = path.substring(path.lastIndexOf("/") + 1);
            java.net.URL imgUrl = getClass().getResource("/images/" + cleanPath);
            if (imgUrl != null) {
                ImageIcon raw = new ImageIcon(imgUrl);
                Image img = raw.getImage().getScaledInstance(1105, 230, Image.SCALE_SMOOTH);
                lblSliderImage.setIcon(new ImageIcon(img));
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadAboutLocalImage(String path) {
        try {
            String cleanPath = path.substring(path.lastIndexOf("/") + 1);
            java.net.URL imgUrl = getClass().getResource("/images/" + cleanPath);
            if (imgUrl != null) {
                ImageIcon raw = new ImageIcon(imgUrl);
                Image img = raw.getImage().getScaledInstance(380, 240, Image.SCALE_SMOOTH);
                lblAboutImage.setIcon(new ImageIcon(img));
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }


    private JButton createNavButton(String title) {
        JButton btn = new JButton(title);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(new Color(148, 163, 184));
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btn.setForeground(new Color(148, 163, 184)); }
        });
        return btn;
    }

    private JPanel createGlassCard() {
        JPanel card = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 4)); g.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g.setColor(new Color(255, 255, 255, 12)); g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            }
        };
        card.setOpaque(false); return card;
    }

    private JPanel createPopOutCard(String title, String desc) {
        JPanel card = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(40, 32, 108, 35)); g.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g.setColor(new Color(30, 144, 255, 80)); g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            }
        };
        card.setOpaque(false);

        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(30, 144, 255));
        lblTitle.setBounds(10, 12, 325, 25);
        card.add(lblTitle);

        JTextArea txtArea = createCardTextArea(desc);
        txtArea.setBounds(25, 45, 300, 120);
        card.add(txtArea);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBounds(card.getX() - 5, card.getY() - 5, card.getWidth() + 10, card.getHeight() + 10);
                lblTitle.setForeground(new Color(56, 189, 248));
                txtArea.setForeground(Color.WHITE);
                card.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBounds(card.getX() + 5, card.getY() + 5, card.getWidth() - 10, card.getHeight() - 10);
                lblTitle.setForeground(new Color(30, 144, 255));
                txtArea.setForeground(new Color(148, 163, 184));
                card.repaint();
            }
        });
        return card;
    }

    private JTextArea createCardTextArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        area.setForeground(new Color(148, 163, 184));
        area.setLineWrap(true); area.setWrapStyleWord(true);
        area.setOpaque(false); area.setEditable(false);
        return area;
    }
    // 🌙 FEATURE 3: DYNAMIC KERNEL THEME TOGGLE (DARK / LIGHT THEME CHANGER)
    private boolean isDarkModeActive = true;
    public void togglePortalUIVisualTheme() {
        isDarkModeActive = !isDarkModeActive;
        Color bg = isDarkModeActive ? new Color(10, 15, 30) : new Color(241, 245, 249);
        Color txtColor = isDarkModeActive ? Color.WHITE : new Color(15, 23, 42);
        Color cardBg = isDarkModeActive ? new Color(30, 41, 59) : Color.WHITE;

        mainScrollContent.setBackground(bg);
        for (Component c : mainScrollContent.getComponents()) {
            if (c instanceof JPanel) {
                c.setBackground(cardBg);
                for (Component sub : ((JPanel) c).getComponents()) {
                    if (sub instanceof JLabel) sub.setForeground(txtColor);
                    if (sub instanceof JTextArea) sub.setForeground(isDarkModeActive ? new Color(148, 163, 184) : new Color(71, 85, 105));
                }
            }
        }
        mainScrollContent.repaint();
    }

}
