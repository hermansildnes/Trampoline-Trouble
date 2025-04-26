package game.GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

import game.AudioManager;
import game.Game;
import game.GameProgress;
import game.GameView;

public class MenuManager {
    // Constants
    private static final String GAME_PANEL = "Game";
    private Color COSMIC_LATTE = new Color(255, 248, 231);
    private Color TEXT_COLOR = new Color(0, 0, 0);
    private Font TEXT_FONT;
    private String FONT_NAME;
    
    // UI components
    private Game game;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    // Key binding related fields
    private JButton leftKeyButton;
    private JButton rightKeyButton;
    private JButton downKeyButton;
    private boolean waitingForKeyInput = false;
    private JButton activeButton = null;
    
    // Default key bindings
    private int leftKey = KeyEvent.VK_A;
    private int rightKey = KeyEvent.VK_D;
    private int downKey = KeyEvent.VK_S;

    // Audio related fields
    private AudioManager audioManager;

    // Menu navigation related fields
    private String currentPanel = "Main Menu";
    private String previousPanel = "Main Menu";

    // Progress related fields
    private GameProgress gameProgress;
    private JProgressBar[] levelProgressBars;
    private JProgressBar gameOverProgressBar;
    private JProgressBar victoryProgressBar;

    public MenuManager() {
        frame = new JFrame("Trampoline Trouble");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);

        audioManager = new AudioManager();
        loadAudio();

        loadCustomFont("data/assets/fonts/PressStart2P.ttf");

        gameProgress = new GameProgress();

        levelProgressBars = new JProgressBar[gameProgress.getNumberOfLevels()];

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createMainMenu(), "Main Menu");
        cardPanel.add(createLevelSelectMenu(), "Select Level");
        cardPanel.add(createSettingsMenu(), "Settings");
        cardPanel.add(createPauseMenu(), "Pause Menu");
        cardPanel.add(createGameOverMenu(), "Game Over");
        cardPanel.add(createVictoryMenu(), "Victory");


        frame.add(cardPanel);
        frame.setVisible(true);
        
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE && currentPanel.equals(GAME_PANEL)) {
                    cardLayout.show(cardPanel, GAME_PANEL);
                    if (game != null) {
                        game.resumeGame();
                    }
                }
                if (waitingForKeyInput && activeButton != null) {
                    int keyCode = e.getKeyCode();
                    
                    if (keyCode != KeyEvent.VK_ESCAPE && 
                        keyCode != KeyEvent.VK_ENTER &&
                        keyCode != KeyEvent.VK_SPACE) {
                        
                        if (activeButton == leftKeyButton) {
                            leftKey = keyCode;
                        } else if (activeButton == rightKeyButton) {
                            rightKey = keyCode;
                        } else if (activeButton == downKeyButton) {
                            downKey = keyCode;
                        }
                        
                        activeButton.setText(KeyEvent.getKeyText(keyCode));
                        
                        waitingForKeyInput = false;
                        activeButton = null;
                    }
                }
            }
        });



        audioManager.playMusic("mainmenu");
    }

    void startGame(int levelNumber) {

        if (!audioManager.isMusicPlaying("level" + levelNumber)) {
            audioManager.playMusic("level" + levelNumber);
        }

        previousPanel = currentPanel;
        currentPanel = GAME_PANEL;
        
        game = new Game(levelNumber, this, audioManager, leftKey, rightKey, downKey);
        GameView gameView = game.getGameView();
        cardPanel.add(gameView, GAME_PANEL);
        cardLayout.show(cardPanel, GAME_PANEL);
        gameView.setFocusable(true);
        gameView.requestFocus();
    }

    public void returnToMenu() {
        audioManager.playMusic("mainmenu");

        cardLayout.show(cardPanel, "Main Menu");
        game = null;
    }

    private JPanel createMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);
        
        // Game title
        JLabel gameName = new JLabel("Trampoline Trouble");
        gameName.setFont(new Font(FONT_NAME, Font.BOLD, 36));
        gameName.setForeground(TEXT_COLOR);
        gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Play button
        JButton playButton = createLargeButton("Play");
        playButton.addActionListener(e -> showPanel("Select Level"));
        
        // Settings button
        JButton settingsButton = createLargeButton("Settings");
        settingsButton.addActionListener(e -> showPanel("Settings"));

        JButton loadButton = createLargeButton("Load Game");
        loadButton.addActionListener(e -> {
            if (gameProgress.loadFromFile()) {
                JOptionPane.showMessageDialog(frame, "Game loaded successfully!", "Load Complete", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        
        // Exit button
        JButton exitButton = createLargeButton("Exit & Save");
        exitButton.addActionListener(e -> {
            gameProgress.saveToFile();
            System.exit(0);
        });
        
        // Add components        
        panel.add(Box.createVerticalGlue());
        panel.add(gameName);
        panel.add(Box.createRigidArea(new Dimension(0, 70)));
        panel.add(playButton);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(settingsButton);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(loadButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

        private JPanel createLevelSelectMenu() {
        // Use BorderLayout for overall structure
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Gaps between components
        mainPanel.setBackground(COSMIC_LATTE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // --- Title Panel (North) ---
        JLabel titleLabel = new JLabel("Select Level");
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // Center title
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // --- Levels Panel (Center) ---
        JPanel levelsContentPanel = new JPanel(); // Panel to hold the level rows
        levelsContentPanel.setLayout(new BoxLayout(levelsContentPanel, BoxLayout.Y_AXIS));
        levelsContentPanel.setBackground(COSMIC_LATTE);
        // levelsContentPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alignment within BoxLayout

        // Add level panels with spacing
        levelsContentPanel.add(Box.createVerticalGlue()); // Push content to center vertically
        levelsContentPanel.add(createLevelPanel(1));
        levelsContentPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Reduced spacing
        levelsContentPanel.add(createLevelPanel(2));
        levelsContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        levelsContentPanel.add(createLevelPanel(3));
        levelsContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        levelsContentPanel.add(createLevelPanel(4));
        levelsContentPanel.add(Box.createVerticalGlue()); // Push content to center vertically

        // Add the levels panel inside a ScrollPane in case of future expansion
        JScrollPane scrollPane = new JScrollPane(levelsContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); // Remove scroll pane border
        scrollPane.getViewport().setBackground(COSMIC_LATTE); // Match background
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Buttons Panel (South) ---
        JPanel bottomPanel = new JPanel(new BorderLayout()); // Use BorderLayout for left/right alignment
        bottomPanel.setBackground(COSMIC_LATTE);

        // Back button (aligned left)
        JButton backButton = createLargeButton("Back");
        // Make back button slightly smaller if needed
        // backButton.setFont(new Font(FONT_NAME", Font.BOLD, 20));
        // backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> showPanel(previousPanel)); // Use previousPanel state
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Unlock All button (aligned right)
        JButton unlockAllButton = new JButton("Unlock All");
        unlockAllButton.setFont(new Font(FONT_NAME, Font.PLAIN, 12)); // Smaller font
        unlockAllButton.setToolTipText("Unlock all levels (for testing)");
        unlockAllButton.setMargin(new java.awt.Insets(2, 5, 2, 5)); // Smaller padding
        unlockAllButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to unlock all levels?",
                    "Unlock All Levels",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                gameProgress.unlockAllLevels();
                // Refresh the level select screen to show unlocked levels
                showPanel("Select Level"); // This will recreate the panel via the logic in showPanel
            }
        });
        // Panel to push the unlock button to the right
        JPanel unlockButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        unlockButtonPanel.setBackground(COSMIC_LATTE);
        unlockButtonPanel.add(unlockAllButton);
        bottomPanel.add(unlockButtonPanel, BorderLayout.EAST);


        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        return mainPanel;
    }
    
    private JPanel createPauseMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);
        
        // Pause title
        JLabel titleLabel = new JLabel("Game Paused");
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Resume button
        JButton resumeButton = createLargeButton("Resume");
        resumeButton.addActionListener(e -> resumeGame());
        
        // Settings button
        JButton settingsButton = createLargeButton("Settings");
        settingsButton.addActionListener(e -> showPanel("Settings"));
        
        // Exit button
        JButton exitButton = createLargeButton("Exit & Save");
        exitButton.addActionListener(e -> {
            gameProgress.saveToFile();
            System.exit(0);
    });
        
        // Add components
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 70)));
        panel.add(resumeButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(settingsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    private JPanel createGameOverMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);
        
        // Game Over title
        JLabel titleLabel = new JLabel("Game Over");
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Progress display panel
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.setBackground(COSMIC_LATTE);
        progressPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressPanel.setMaximumSize(new Dimension(400, 100));
        
        // Level completion label
        JLabel progressLabel = new JLabel("Level Progress");
        progressLabel.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        progressLabel.setForeground(TEXT_COLOR);
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setPreferredSize(new Dimension(300, 20));
        progressBar.setMaximumSize(new Dimension(300, 20));
        
        gameOverProgressBar = progressBar;


        // Main menu button
        JButton mainMenuButton = createLargeButton("Main Menu");
        mainMenuButton.addActionListener(e -> {
            showPanel("Main Menu");
            System.out.println("Game Over progress: " + gameProgress.getLevelProgress(game.getCurrentLevel()));
        });
        
        // Exit button
        JButton exitButton = createLargeButton("Exit & Save");
        exitButton.addActionListener(e -> {
            gameProgress.saveToFile();
            System.exit(0);
        });
        
        // Add components to progress panel
        progressPanel.add(progressLabel);
        progressPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        progressPanel.add(progressBar); // Add it to the panel
        
        // Add all components
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(progressPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));
        panel.add(mainMenuButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
            
    }

    private JPanel createVictoryMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);
        
        // Victory title
        JLabel titleLabel = new JLabel("Level Complete!");
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 128, 0)); // Green color for victory
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Progress display panel (similar to Game Over)
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.setBackground(COSMIC_LATTE);
        progressPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressPanel.setMaximumSize(new Dimension(400, 100));
        
        // Level completion label
        JLabel progressLabel = new JLabel("Level Progress");
        progressLabel.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        progressLabel.setForeground(TEXT_COLOR);
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Progress bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setPreferredSize(new Dimension(300, 20));
        progressBar.setMaximumSize(new Dimension(300, 20));
        progressBar.setValue(100);

        victoryProgressBar = progressBar;
        
        // Next Level button
        JButton nextLevelButton = createLargeButton("Next Level");
        nextLevelButton.addActionListener(e -> {
            if (game != null) {
                if (game.getCurrentLevel() + 1  <= gameProgress.getNumberOfLevels()) {
                    startGame(game.getCurrentLevel() + 1);
                } else {
                    // If this was the final level
                    JOptionPane.showMessageDialog(frame, 
                        "Congratulations! You've completed all levels!", 
                        "Game Complete", 
                        JOptionPane.INFORMATION_MESSAGE);
                    showPanel("Main Menu");
                }
            }
        });
        
        // Main Menu button
        JButton mainMenuButton = createLargeButton("Main Menu");
        mainMenuButton.addActionListener(e -> showPanel("Main Menu"));
        
        // Add components to progress panel
        progressPanel.add(progressLabel);
        progressPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        progressPanel.add(progressBar);
        
        // Add all components
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(progressPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));
        panel.add(nextLevelButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(mainMenuButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    private JPanel createSettingsMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);
        
        // Settings title
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Middle panel
        JPanel settingsContainer = new JPanel();
        settingsContainer.setLayout(new BoxLayout(settingsContainer, BoxLayout.Y_AXIS));
        settingsContainer.setBackground(COSMIC_LATTE);
        settingsContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // VVolume panel
        JPanel volumePanel = new JPanel();
        volumePanel.setLayout(new BoxLayout(volumePanel, BoxLayout.X_AXIS));
        volumePanel.setBackground(COSMIC_LATTE);
        volumePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumePanel.setMaximumSize(new Dimension(600, 50));
        
        // Volume label
        JLabel volumeLabel = new JLabel("Volume");
        volumeLabel.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        volumeLabel.setForeground(TEXT_COLOR);
        
        // Volume slider
        JSlider volumeSlider = new JSlider(0, 100, (int)(audioManager.getVolume() * 100));
        volumeSlider.setValue((int)(audioManager.getVolume() * 100));
        volumeSlider.setOpaque(false);
        volumeSlider.setPreferredSize(new Dimension(250, 50));
        volumeSlider.setMaximumSize(new Dimension(250, 50));

        volumeSlider.addChangeListener(e -> {
            float volumeLevel = volumeSlider.getValue() / 100.0f;
            audioManager.setMasterVolume(volumeLevel);
        });
        
        // Add all components
        volumePanel.add(Box.createRigidArea(new Dimension(20, 0)));
        volumePanel.add(volumeLabel);
        volumePanel.add(Box.createHorizontalGlue());
        volumePanel.add(volumeSlider);
        volumePanel.add(Box.createRigidArea(new Dimension(20, 0)));
        
        // Key bindings title
        JLabel keybindingsLabel = new JLabel("Controls");
        keybindingsLabel.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        keybindingsLabel.setForeground(TEXT_COLOR);
        keybindingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Keybinding panels
        JPanel moveLeftPanel = createKeybindingRow("Move Left", KeyEvent.getKeyText(leftKey));
        JPanel moveRightPanel = createKeybindingRow("Move Right", KeyEvent.getKeyText(rightKey));
        JPanel moveDownPanel = createKeybindingRow("Fast Fall", KeyEvent.getKeyText(downKey));
        
        // Back button
        JButton backButton = createLargeButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            saveKeyBindings();
            showPanel(previousPanel);
        });
        
        // Add all sections
        settingsContainer.add(volumePanel);
        settingsContainer.add(Box.createRigidArea(new Dimension(0, 30)));
        settingsContainer.add(keybindingsLabel);
        settingsContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsContainer.add(moveLeftPanel);
        settingsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsContainer.add(moveRightPanel);
        settingsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsContainer.add(moveDownPanel);
        
        // Add all components
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(settingsContainer);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    // Helper method to create a level panel with button and progress bar
    private JPanel createLevelPanel(int levelNumber) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COSMIC_LATTE);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(400, 100));

        boolean isUnlocked = gameProgress.isLevelUnlocked(levelNumber);
        float progress = gameProgress.getLevelProgress(levelNumber);


        // Level button
        JButton levelButton = createLargeButton("Level " + levelNumber);
        levelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (isUnlocked) {
            levelButton.addActionListener(e -> startGame(levelNumber));
            levelButton.setEnabled(true);
        } else {
            levelButton.setEnabled(false);
            levelButton.setForeground(Color.GRAY);
            levelButton.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, 
                "Complete the previous level to unlock this one!", 
                "Level Locked", 
                JOptionPane.INFORMATION_MESSAGE)
            );
        }
                
        // Progress bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int)(progress * 100));
        progressBar.setStringPainted(true);
        progressBar.setString(Math.round(progress * 100) + "% Complete");
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setPreferredSize(new Dimension(300, 20));
        progressBar.setMaximumSize(new Dimension(300, 20));

        levelProgressBars[levelNumber - 1] = progressBar;
        
        // Add components
        panel.add(levelButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(progressBar);
        
        return panel;
    }

    private JPanel createKeybindingRow(String actionName, String defaultKey) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(COSMIC_LATTE);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(600, 40));
        
        // Action label
        JLabel label = new JLabel(actionName);
        label.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
        label.setForeground(TEXT_COLOR);
        
        // Key button
        JButton keyButton = new JButton(defaultKey);
        keyButton.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        keyButton.setFocusable(false);
        
        Dimension buttonSize = new Dimension(100, 30);
        keyButton.setPreferredSize(buttonSize);
        keyButton.setMinimumSize(buttonSize);
        keyButton.setMaximumSize(buttonSize);
        

        if (actionName.equals("Move Left")) {
            leftKeyButton = keyButton;
        } else if (actionName.equals("Move Right")) {
            rightKeyButton = keyButton;
        } else if (actionName.equals("Fast Fall")) {
            downKeyButton = keyButton;
        }
        
        keyButton.addActionListener(e -> startKeyBindingCapture(keyButton, actionName));
        
        // Add all components
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(label);
        panel.add(Box.createHorizontalGlue());
        panel.add(keyButton);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        
        return panel;
    }
    

    private void startKeyBindingCapture(JButton button, String actionName) {
        if (waitingForKeyInput) {
            // Cancel the previous capture if there was one
            if (activeButton != null) {
                activeButton.setText(getKeyTextForAction(actionName));
            }
        }
        
        waitingForKeyInput = true;
        activeButton = button;
        button.setText("Press Key...");
        
        frame.requestFocus();
    }
    

    private String getKeyTextForAction(String actionName) {
        if (actionName.equals("Move Left")) {
            return KeyEvent.getKeyText(leftKey);
        } else if (actionName.equals("Move Right")) {
            return KeyEvent.getKeyText(rightKey);
        } else if (actionName.equals("Fast Fall")) {
            return KeyEvent.getKeyText(downKey);
        }
        return "";
    }
    
    private void saveKeyBindings() {
        if (game != null) {
            // Notify the game of the new key bindings
            game.updateKeyBindings(leftKey, rightKey, downKey);
        }
    }
    
    public JButton createLargeButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension size = new Dimension(200, 50);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);

       int maxFontSize = 24;
       int minFontSize = 12;
       Font finalFont = null; 

       for (int currentSize = maxFontSize; currentSize >= minFontSize; currentSize--) {
           Font testFont = new Font(FONT_NAME, Font.BOLD, currentSize);

           FontMetrics metrics = button.getFontMetrics(testFont);
           int textWidth = metrics.stringWidth(text);


           if (textWidth <= size.width-35) {
               finalFont = testFont;
               break;
           }
       }
        button.setFont(finalFont);

        return button;
    }

    private void loadCustomFont(String fontPath) {
        try {
            TEXT_FONT = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            FONT_NAME = TEXT_FONT.getFontName();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(TEXT_FONT);
            
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public Font getFont() {
        return TEXT_FONT;
    }

    private void loadAudio() {

        audioManager.loadMusic("mainmenu", "data/assets/music/mainmenu/soundtrack.wav");

        audioManager.loadMusic("level1", "data/assets/music/level1/soundtrack.wav");
        audioManager.loadMusic("level2", "data/assets/music/level2/soundtrack.wav");
        audioManager.loadMusic("level3", "data/assets/music/level3/soundtrack.wav");
        audioManager.loadMusic("level4", "data/assets/music/level4/soundtrack.wav");

        audioManager.loadSoundEffect("bounce", "data/assets/soundeffects/bounce.wav");
        audioManager.loadSoundEffect("damage", "data/assets/soundeffects/shoot.wav");
        audioManager.loadSoundEffect("collect", "data/assets/soundeffects/collected.wav");
        audioManager.loadSoundEffect("laser", "data/assets/soundeffects/laser.wav");
        audioManager.loadSoundEffect("victory", "data/assets/soundeffects/victory.wav");
    }
    
    public void resumeGame() {
        cardLayout.show(cardPanel, GAME_PANEL);
        if (game != null) {
            game.resumeGame();
        }
    }

    public void showPanel(String panelName) {
        if (!currentPanel.equals(panelName)) {
            previousPanel = currentPanel;
        }
        
        
        currentPanel = panelName;
        
        if (panelName.equals("Main Menu")) {
            if (previousPanel.equals(GAME_PANEL) || previousPanel.equals("Victory") || previousPanel.equals("Game Over")) {
                audioManager.playMusic("mainmenu");    
            }
        }

        if (panelName.equals("Select Level")) {
            cardPanel.remove(cardPanel.getComponent(1));
            cardPanel.add(createLevelSelectMenu(), "Select Level", 1);
            cardPanel.revalidate();
        }

        cardLayout.show(cardPanel, panelName);
    }

    public void showGameOverPanel(float progress) {
        if (game != null) {
            gameProgress.updateLevelProgress(game.getCurrentLevel(), progress);

            if (gameOverProgressBar != null) {
                gameOverProgressBar.setValue((int)(progress * 100));
                gameOverProgressBar.setString(Math.round(progress * 100) + "% Complete");
            }
        }
        showPanel("Game Over");

    }

    public void showVictoryPanel() {
        if (game != null) {
            gameProgress.updateLevelProgress(game.getCurrentLevel(), 1.0f);

            if (victoryProgressBar != null) {
                victoryProgressBar.setValue(100);
                victoryProgressBar.setString("100% Complete");
            }
        }
        showPanel("Victory");
    }
}