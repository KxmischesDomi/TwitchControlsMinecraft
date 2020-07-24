package net.codingarea.twitchbot.objects;

/**
 * @author anweisen & Dominik
 * TwitchBot developed on 07-24-2020
 * https://github.com/anweisen
 * https://github.com/KxmischesDomi
 */
public enum ControlType {

    KEYBOARD(true),
    HOTBAR(false),
    MOUSE(false),
    DROP(false),
    ;

    boolean activated;

    ControlType(boolean activated) {
        this.activated = activated;
    }

    public void setActivated(boolean activate) {
        this.activated = activate;
    }

    public boolean isActivated() {
        return activated;
    }

}
