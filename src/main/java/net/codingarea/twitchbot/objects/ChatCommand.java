package net.codingarea.twitchbot.objects;

import net.codingarea.twitchbot.TwitchBot;
import net.codingarea.twitchbot.manager.RobotManager;

import java.awt.event.KeyEvent;

/**
 * @author anweisen & Dominik
 * TwitchBot developed on 07-22-2020
 * https://github.com/anweisen
 * https://github.com/KxmischesDomi
 */
public enum ChatCommand {

    SPACE(KeyEvent.VK_SPACE, ControlType.KEYBOARD),
    W(KeyEvent.VK_W, ControlType.KEYBOARD),
    A(KeyEvent.VK_A, ControlType.KEYBOARD),
    S(KeyEvent.VK_S, ControlType.KEYBOARD),
    D(KeyEvent.VK_D, ControlType.KEYBOARD),
    SPRINT(null, ControlType.KEYBOARD),
    SNEAK(KeyEvent.VK_CONTROL, ControlType.KEYBOARD),
    Q(KeyEvent.VK_Q, ControlType.DROP),
    H1(KeyEvent.VK_1, ControlType.HOTBAR),
    H2(KeyEvent.VK_2, ControlType.HOTBAR),
    H3(KeyEvent.VK_3, ControlType.HOTBAR),
    H4(KeyEvent.VK_4, ControlType.HOTBAR),
    H5(KeyEvent.VK_5, ControlType.HOTBAR),
    H6(KeyEvent.VK_6, ControlType.HOTBAR),
    H7(KeyEvent.VK_7, ControlType.HOTBAR),
    H8(KeyEvent.VK_8, ControlType.HOTBAR),
    H9(KeyEvent.VK_9, ControlType.HOTBAR),
    ;

    private final RobotManager robotManager;

    final Integer key;
    final ControlType controlType;
    boolean pressed = false;

    ChatCommand(Integer key, ControlType controlType) {
        this.key = key;
        this.controlType = controlType;
        this.robotManager = TwitchBot.getInstance().getRobotManager();
    }

    public boolean isActivated() {
        return controlType.isActivated();
    }

    public void execute() {

        if (this.key == null) {
            robotManager.pressKey(KeyEvent.VK_SHIFT);
            robotManager.pressKey(KeyEvent.VK_W);
            return;
        }

        if (this.key == KeyEvent.VK_CONTROL) {
            if (pressed) robotManager.getRobot().keyPress(this.key);
            else robotManager.getRobot().keyRelease(this.key);
            pressed = !pressed;
            return;
        }

        robotManager.pressKey(this.key);

    }

}