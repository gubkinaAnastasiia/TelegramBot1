package com.example.telegrambot1.botAPI.processingResponce;

import com.example.telegrambot1.botAPI.BotState;
import com.example.telegrambot1.botAPI.exception.ExceptionProcessing;
import com.example.telegrambot1.cache.DataCache;
import com.example.telegrambot1.test.Test;
import com.example.telegrambot1.test.TestBeck;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("button")
public class ProcessingButton implements Processing{

    private final DataCache dataCache;

    public ProcessingButton(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    @Override
    public void dataAcceptance(Update update) {

        String message = update.getCallbackQuery().getData();
        long userId = update.getCallbackQuery().getFrom().getId();
        BotState botState = dataCache.getUsersCurrentBotState(userId);

        System.err.println("До обработки ответа: " + botState.name());

        switch (botState) {
            case MAIN_SELECTION:
                if (message.equals("GET_TEST")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_ALL_TEST);
                } else if (message.equals("GET_INFO")) {
                    dataCache.setUserCurrentBotState(userId, BotState.INFO);
                } else {
                    throw new ExceptionProcessing("Вы ввели некоректные данные в главном меню. Повторите ввод.");
                }
                break;
            case INFO:
                dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
            case TEST_SELECTION:
                if (message.equals("BACK")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                    break;
                } else {
                    Test newTest = null;
                    switch (message) {
                        case "TEST_BECK":
                            newTest = new TestBeck();
                            break;
                    }
                    if (newTest != null) {
                        dataCache.setUserTest(userId, newTest);
                        dataCache.setUserCurrentBotState(userId, BotState.CONSENT_TEST);
                    } else {
                        throw new ExceptionProcessing("Вы ввели некоректные данные в выборе теста. Повторите ввод.");
                    }
                }
                break;
            case CONSENT_TEST:
                if (message.equals("YES")) {
                    dataCache.setUserCurrentBotState(userId, BotState.TEST);
                } else if (message.equals("NO")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_ALL_TEST);
                } else {
                    throw new ExceptionProcessing("Вы ввели некоректные данные в подтверждении прохождения теста. Повторите ввод.");
                }
                break;
            case TEST:
                Test test = dataCache.getUserTest(userId);
                if (test.checkAnswer(message)) {
                    test.setResult(message);
                    if (test.hasNextQuest()) {
                        test.nextQuest();
                    } else {
                        dataCache.setUserCurrentBotState(userId, BotState.GETTING_RESULT);
                    }
                } else {
                    throw new ExceptionProcessing("Вы ввели некоректные данные при введении ответа на тест. Повторите ввод.");
                }
                break;
            case GETTING_RESULT:
                dataCache.setUserCurrentBotState(userId, BotState.SHOW_ALL_TEST);
                break;
            case CHOOSING_PSYCHIATRIST:
                if (message.equals("YES")) {
                    dataCache.setUserCurrentBotState(userId, BotState.INFO);
                } else if (message.equals("NO")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                } else {
                    throw new ExceptionProcessing("Вы ввели некоректные данные для согласия или отказа получения информации. Повторите ввод");
                }
        }

        System.err.println("После обработки ответа:" + dataCache.getUsersCurrentBotState(userId).name());
    }
}
