package net.codingarea.twitchbot.manager;

import net.codingarea.twitchbot.objects.ChatCommand;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author anweisen & Dominik
 * TwitchBot developed on 07-22-2020
 * https://github.com/anweisen
 * https://github.com/KxmischesDomi
 */
public class RobotManager {

    private static RobotManager instance;
    private Robot robot;

    public RobotManager() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void pressKey(int key) {
        robot.keyPress(key);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                robot.keyRelease(key);
            }
        }, 100);
    }

    public static RobotManager getInstance() {
        return instance;
    }

    public Robot getRobot() {
        return robot;
    }
}