package net.codingarea.twitchbot.twitch;

import net.codingarea.twitchbot.TwitchBot;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author anweisen & Dominik
 * TwitchBot developed on 07-23-2020
 * https://github.com/anweisen
 * https://github.com/KxmischesDomi
 */
public class ChatBot extends PircBot {

    private static ChatBot activeBot;
    private final TwitchBot main = TwitchBot.getInstance();

    public ChatBot() {

        if (activeBot != null) {
            System.out.println("Stoppe zuerst den Bot!");
            return;
        }

        activeBot = this;
        this.setName("TwitchBot");
        try {
            this.connect("irc.chat.twitch.tv", 6667, "oauth:fov5ory4rauatzxvc12jtgzezpt5vm");
            this.joinChannel("#" + main.getNameField().getText());
            setVerbose(TwitchBot.getInstance().chatLog);
            main.updateIcon();

        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }

    }

    public static void stop() {
        try {
            activeBot.disconnect();
        } catch (Exception ignored) { }
        activeBot = null;
        TwitchBot.getInstance().updateIcon();
    }

    boolean bol = false;

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        TwitchBot.getInstance().getChatManager().handleInput(message);
    }

    public static ChatBot getActiveBot() {
        return activeBot;
    }

}