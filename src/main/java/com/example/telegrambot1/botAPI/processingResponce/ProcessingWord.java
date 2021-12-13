package com.example.telegrambot1.botAPI.processingResponce;

import com.example.telegrambot1.botAPI.BotState;
import com.example.telegrambot1.botAPI.exception.ExceptionProcessing;
import com.example.telegrambot1.cache.DataCache;
import com.example.telegrambot1.test.Test;
import com.example.telegrambot1.test.TestBeck;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("word")
public class ProcessingWord implements Processing{

    private final DataCache dataCache;

    public ProcessingWord(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    @Override
    public void dataAcceptance(Update update) {

        String message = update.getMessage().getText();
        long userId = update.getMessage().getFrom().getId();
        BotState botState = dataCache.getUsersCurrentBotState(userId);

        System.err.println("До обработки ответа: " + botState.name());


        switch (botState) {
            case MAIN_SELECTION:
                if (message.equals("1")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_ALL_TEST);
                } else if (message.equals("2")) {
                    dataCache.setUserCurrentBotState(userId, BotState.INFO);
                } else {
                    String exception = "Вы ввели некоректные данные в главном меню. Повторите ввод.";
                    dataCache.setUserException(userId, exception);
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                    throw new ExceptionProcessing(exception);
                }
                break;
            case INFO:
                dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
            case TEST_SELECTION:
                if (message.equals("2")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                    break;
                } else {
                    Test newTest = null;
                    switch (message) {
                        case "1":
                            newTest = new TestBeck();
                            break;
                    }
                    if (newTest != null) {
                        dataCache.setUserTest(userId, newTest);
                        dataCache.setUserCurrentBotState(userId, BotState.CONSENT_TEST);
                    } else {
                        String exception = "Вы ввели некоректные данные в выборе теста. Повторите ввод.";
                        dataCache.setUserException(userId, exception);
                        dataCache.setUserCurrentBotState(userId, BotState.SHOW_ALL_TEST);
                        throw new ExceptionProcessing(exception);
                    }
                }
                break;
            case CONSENT_TEST:
                if (message.equals("да")) {
                    dataCache.setUserCurrentBotState(userId, BotState.TEST);
                } else if (message.equals("нет")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_ALL_TEST);
                } else {
                    String exception = "Вы ввели некоректные данные в подтверждении прохождения теста. Повторите ввод.";
                    dataCache.setUserException(userId, exception);
                    throw new ExceptionProcessing(exception);
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
                    String exception = "Вы ввели некоректные данные при введении ответа на тест. Повторите ввод.";
                    dataCache.setUserException(userId, exception);
                    throw new ExceptionProcessing(exception);
                }
                break;
            case GETTING_RESULT:
                dataCache.setUserCurrentBotState(userId, BotState.SHOW_ALL_TEST);
                break;
            case CHOOSING_PSYCHIATRIST:
                if (message.equals("да")) {
                    dataCache.setUserCurrentBotState(userId, BotState.INFO);
                } else if (message.equals("нет")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                } else {
                    String exception = "Вы ввели некоректные данные для согласия или отказа получения информации. Повторите ввод";
                    dataCache.setUserException(userId, exception);
                    throw new ExceptionProcessing(exception);
                }
                break;
        }

        System.err.println("После обработки ответа:" + dataCache.getUsersCurrentBotState(userId).name());
    }
}
