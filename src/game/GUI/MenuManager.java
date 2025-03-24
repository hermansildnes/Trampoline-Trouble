package game.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import game.Game;
import game.GameView;

public class MenuManager {
    private Game game;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Color COSMIC_LATTE = new Color(255,248,231);
    private Color TEXT_COLOR = new Color(0, 0, 0);
    private String assetPath = "data/assets/GUI/";
    private static final String GAME_PANEL = "Game";

    public MenuManager() {
        frame = new JFrame("Trampoline Trouble");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createMainMenu(), "Main Menu");
        cardPanel.add(createLevelSelectMenu(), "Select Level");
        cardPanel.add(createSettingsMenu(), "Settings");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    private void startGame(int levelNumber) {
        game = new Game(levelNumber, this);
        GameView gameView = game.getGameView();
        try {
            cardPanel.remove(cardPanel.getComponent(cardPanel.getComponentCount()-1));
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        cardPanel.add(gameView, GAME_PANEL);
        cardLayout.show(cardPanel, GAME_PANEL);
        gameView.setFocusable(true);
        gameView.requestFocus();

    }

    public void returnToMenu() {
        cardLayout.show(cardPanel, "Main Menu");
        game = null;
    }

    private JPanel createMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);
        
        // Game title
        JLabel gameName = new JLabel("Trampoline Trouble");
        gameName.setFont(new Font("Arial", Font.BOLD, 36));
        gameName.setForeground(TEXT_COLOR);
        gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Play button
        JButton playButton = new JButton(new ImageIcon(assetPath + "button_large.png"));
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setPressedIcon(new ImageIcon(assetPath + "button_large_pressed.png"));
        playButton.addActionListener(e -> cardLayout.show(cardPanel, "Select Level"));

        playButton.setFocusPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusable(false);
        playButton.setBorderPainted(false);
        
        // Settings button
        JButton settingsButton = new JButton("Settings");
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(e -> cardLayout.show(cardPanel, "Settings"));
        
        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        
        // Add components with padding
        panel.add(Box.createVerticalGlue());
        panel.add(gameName);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(playButton);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(settingsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

private JPanel createLevelSelectMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(COSMIC_LATTE);
        
        // Level selection title
        JLabel titleLabel = new JLabel("Select Level");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Level buttons
        JButton level1Button = new JButton("Level 1");
        level1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        level1Button.addActionListener(e -> startGame(1));
        
        JButton level2Button = new JButton("Level 2");
        level2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        level2Button.addActionListener(e -> startGame(2));
        
        JButton level3Button = new JButton("Level 3");
        level3Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        level3Button.addActionListener(e -> startGame(3));
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main Menu"));
        
        // Add components
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(titleLabel);
        panel.add(bottomPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        bottomPanel.add(level1Button);
        bottomPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        bottomPanel.add(level2Button);
        bottomPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        bottomPanel.add(level3Button);
        bottomPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel createSettingsMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);
        
        // Settings title
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Volume slider
        JPanel volumePanel = new JPanel();
        volumePanel.setOpaque(false);
        JLabel volumeLabel = new JLabel("Volume: ");
        volumeLabel.setForeground(TEXT_COLOR);
        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setOpaque(false);
        volumePanel.add(volumeLabel);
        volumePanel.add(volumeSlider);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main Menu"));
        
        // Add components
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(volumePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }


}