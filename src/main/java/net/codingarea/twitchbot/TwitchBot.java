package net.codingarea.twitchbot;

import net.codingarea.twitchbot.manager.ChatManager;
import net.codingarea.twitchbot.manager.RobotManager;
import net.codingarea.twitchbot.objects.ChatCommand;
import net.codingarea.twitchbot.objects.ControlType;
import net.codingarea.twitchbot.objects.TextAreaOutputStream;
import net.codingarea.twitchbot.twitch.ChatBot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;

/**
 * @author anweisen & Dominik
 * TwitchBot developed on 07-22-2020
 * https://github.com/anweisen
 * https://github.com/KxmischesDomi
 */
public class TwitchBot extends JFrame {

    private static TwitchBot instance;

    private final RobotManager robotManager;
    private final ChatManager chatManager;

    private JFrame console;
    private JTextField nameField;

    public int seconds = 5;
    public boolean chatLog = true;

    private Image startedIcon;
    private Image stoppedIcon;

    public TwitchBot() {
        instance = this;
        robotManager = new RobotManager();
        chatManager = new ChatManager();

        setupWindow();
        loadImages();
        updateIcon();
    }

    JTextArea textArea = new JTextArea(15, 30);

    private void setupWindow() {
        this.setSize(200, 300);
        this.setTitle(" ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createConsole();

        this.add(createMenu());
        this.setVisible(true);
    }

    private JPanel createMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel("Twitch name");
        menu.add(name);
        nameField = new JTextField("", 15);
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new ChatBot();
                }
            }

        });
        menu.add(nameField);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(event -> new ChatBot());
        menu.add(startButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(event -> ChatBot.stop());
        menu.add(stopButton);

        JButton openConsoleButton = new JButton("Open console");
        openConsoleButton.addActionListener(event -> console.setVisible(true));
        menu.add(openConsoleButton);

        JCheckBox keyboardCheck = new JCheckBox("Keyboard Control", true);
        keyboardCheck.addActionListener(event -> ControlType.KEYBOARD.setActivated(keyboardCheck.isSelected()));
        menu.add(keyboardCheck);

        JCheckBox hotbarCheck = new JCheckBox("Hotbar Control");
        hotbarCheck.addActionListener(event -> ControlType.HOTBAR.setActivated(hotbarCheck.isSelected()));
        menu.add(hotbarCheck);

        JCheckBox dropItemCheck = new JCheckBox("Drop-Item Control");
        dropItemCheck.addActionListener(event -> ControlType.DROP.setActivated(dropItemCheck.isSelected()));
        menu.add(dropItemCheck);

/*        JCheckBox mouseCheck = new JCheckBox("Mouse Control");
        mouseCheck.addActionListener(event -> mouse = mouseCheck.isSelected());
        menu.add(mouseCheck);*/

        JCheckBox chatLogCheck = new JCheckBox("Chatlog", true);
        chatLogCheck.addActionListener(event -> {
            chatLog = chatLogCheck.isSelected();
            if (ChatBot.getActiveBot() == null) return;
            ChatBot.getActiveBot().setVerbose(chatLogCheck.isSelected());
        });
        menu.add(chatLogCheck);

        JSlider secondsSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        secondsSlider.setPreferredSize(new Dimension(150, 50));
        secondsSlider.addChangeListener(event -> {
            JSlider source = (JSlider)event.getSource();
            if (!source.getValueIsAdjusting()) {
                seconds = (int) source.getValue();
            }
        });
        secondsSlider.setMajorTickSpacing(10);
        secondsSlider.setMinorTickSpacing(1);
        secondsSlider.setPaintTicks(true);
        secondsSlider.setPaintLabels(true);
        menu.add(secondsSlider);

        return menu;
    }

    private void createConsole() {
        console = new JFrame("TwitchBot Console | You may close this window | By KxmischesDomi");
        console.setSize(550, 300);
        console.add(getTextPanel());
        console.setVisible(true);
    }

    private JPanel getTextPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        PrintStream stream = new PrintStream(new TextAreaOutputStream(
                textArea, "TwitchBot "));
        System.setOut(stream);
        System.setErr(stream);
        return panel;
    }

    private void loadImages() {
        try {
            startedIcon = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("started.png"));
            stoppedIcon = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("stopped.png"));
        } catch (Exception e) {
        }
    }

    public void updateIcon() {

        ChatBot bot = ChatBot.getActiveBot();

        if (bot == null) {
            if (stoppedIcon != null) this.setIconImage(stoppedIcon);
        } else {
            if (startedIcon != null) this.setIconImage(startedIcon);
        }

    }

    public static void main(String[] args) {
        new TwitchBot();
    }

    public static TwitchBot getInstance() {
        return instance;
    }

    public RobotManager getRobotManager() {
        return robotManager;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

}