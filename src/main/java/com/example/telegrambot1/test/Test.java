package com.example.telegrambot1.test;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Test {
    boolean hasNextQuest();
    boolean checkAnswer(String answer);
    void nextQuest();

    void setResult(String answer);

    String getStartMessage();
    String getQuest();
    String getResult();
    InlineKeyboardMarkup getKeybord();
}
