package com.example.telegrambot1.cache;

import com.example.telegrambot1.botAPI.BotState;
import com.example.telegrambot1.test.Test;
import org.springframework.stereotype.Component;

public interface DataCache {

    void setUserCurrentBotState(long userId, BotState botState);

    void setUserTest(long userId, Test test);

    void setUserChatId(long userId, String chatId);

    void setUserException(long userId, String exception);

    BotState getUsersCurrentBotState(long userId);

    Test getUserTest(long userId);

    String getUserChatId(long userId);

    String getUserException(long userId);

}
