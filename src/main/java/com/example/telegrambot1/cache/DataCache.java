package com.example.telegrambot1.cache;

import com.example.telegrambot1.botAPI.BotState;
import com.example.telegrambot1.test.Test;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface DataCache {

    void setUserCurrentBotState(long userId, BotState botState);

    void setUserTest(long userId, Test test);

    void setUserChatId(long userId, String chatId);

    void setUserException(long userId, String exception);

    void setUserMessageId(long userId, Integer messageId);

    BotState getUsersCurrentBotState(long userId);

    Test getUserTest(long userId);

    String getUserChatId(long userId);

    String getUserException(long userId);

    Integer getUserMessageId(long userId);
}
