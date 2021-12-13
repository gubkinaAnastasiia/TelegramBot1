package com.example.telegrambot1.cache;

import com.example.telegrambot1.botAPI.BotState;
import com.example.telegrambot1.test.Test;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class UserDataCache implements DataCache{

    private Map<Long, BotState> userBotState = new HashMap<>();
    private Map<Long, Test> userTest = new HashMap<>();
    private Map<Long, String> userChatId = new HashMap<>();
    private Map<Long, String> userException = new HashMap<>();
    private Map<Long, Integer> userMessageId = new HashMap<>();


    @Override
    public void setUserCurrentBotState(long userId, BotState botState) {
        userBotState.put(userId, botState);
    }

    @Override
    public void setUserTest(long userId, Test test) {
        userTest.put(userId, test);
    }

    @Override
    public void setUserChatId(long userId, String chatId) {
        userChatId.put(userId, chatId);
    }

    @Override
    public void setUserException(long userId, String exception) {
        userException.put(userId, exception);
    }

    @Override
    public void setUserMessageId(long userId, Integer messageId) {
        userMessageId.put(userId, messageId);
    }

    @Override
    public BotState getUsersCurrentBotState(long userId) {
        return userBotState.get(userId);
    }

    @Override
    public Test getUserTest(long userId) {
        return userTest.get(userId);
    }

    @Override
    public String getUserChatId(long userId) {
        return userChatId.get(userId);
    }

    @Override
    public String getUserException(long userId) {
        return userException.get(userId);
    }

    @Override
    public Integer getUserMessageId(long userId) {
        return userMessageId.get(userId);
    }
}
