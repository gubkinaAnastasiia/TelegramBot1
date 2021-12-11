package com.example.telegrambot1.botAPI.processingResponce;

import com.example.telegrambot1.botAPI.BotState;
import com.example.telegrambot1.botAPI.exception.ExceptionProcessing;
import com.example.telegrambot1.cache.DataCache;
import com.example.telegrambot1.cache.UserDataCache;
import com.example.telegrambot1.test.Test;
import com.example.telegrambot1.test.TestBeck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
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
            case TEST_SELECTION:
                Test newTest = null;
                switch (message) {
                    case "TEST_BECK": newTest = new TestBeck(); break;
                }
                if (newTest != null) {
                    dataCache.setUserTest(userId, newTest);
                    dataCache.setUserCurrentBotState(userId, BotState.CONSENT_TEST);
                } else {
                    throw new ExceptionProcessing("Вы ввели некоректные данные в выборе теста. Повторите ввод.");
                }
                break;
            case CONSENT_TEST:
                if (message.equals("YES")) {
                    dataCache.setUserCurrentBotState(userId, BotState.TEST);
                } else if (message.equals("NO")) {
                    dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
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
                dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
        }

        System.err.println("После обработки ответа:" + dataCache.getUsersCurrentBotState(userId).name());
    }
}
