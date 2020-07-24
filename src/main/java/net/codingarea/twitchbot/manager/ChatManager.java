package net.codingarea.twitchbot.manager;

import net.codingarea.twitchbot.TwitchBot;
import net.codingarea.twitchbot.objects.ChatCommand;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen & Dominik
 * TwitchBot developed on 07-23-2020
 * https://github.com/anweisen
 * https://github.com/KxmischesDomi
 */
public class ChatManager {

    private ConcurrentHashMap<ChatCommand, Integer> map;

    public ChatManager() {
        map = new ConcurrentHashMap<>();

        startTimer();
    }

    private void onTime() {
        Entry<ChatCommand, Integer> best = null;

        for (Entry<ChatCommand, Integer> entry : map.entrySet()) {
            if (best == null) best = entry;
            if (entry.getValue() > best.getValue()) best = entry;
        }
        if (best != null) {
            best.getKey().execute();
            System.out.println("Winner: " + best.getKey().name());
        }
        map = new ConcurrentHashMap<>();
    }

    public void handleInput(String message) {

        for (ChatCommand chatCommand : ChatCommand.values()) {
            if (chatCommand.name().replace("H", "").equalsIgnoreCase(message)) {
                if (!chatCommand.isActivated()) continue;
                if (!map.containsKey(chatCommand)) {
                    map.put(chatCommand, 1);
                    continue;
                }
                map.put(chatCommand, map.get(chatCommand)+1);
                return;
            }
        }

    }

    private int seconds = 0;
    private final Random random = new Random();

    private void startTimer() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if (seconds >= TwitchBot.getInstance().seconds) {
                    seconds = 0;
                    onTime();
                }
            }
        }, 1000, 1000);

    }

}