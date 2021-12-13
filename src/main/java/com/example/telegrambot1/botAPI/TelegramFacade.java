package com.example.telegrambot1.botAPI;

import com.example.telegrambot1.botAPI.createResponce.CreateResponse;
import com.example.telegrambot1.botAPI.exception.ExceptionProcessing;
import com.example.telegrambot1.botAPI.processingResponce.ProcessingButton;
import com.example.telegrambot1.botAPI.processingResponce.ProcessingWord;
import com.example.telegrambot1.cache.DataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramFacade {

    private final DataCache dataCache;
    private final CreateResponse createResponse;
    private final ProcessingButton processingButton;
    private final ProcessingWord processingWord;


    public TelegramFacade(DataCache dataCache, CreateResponse createResponse, ProcessingButton processingButton, ProcessingWord processingWord) {
        this.dataCache = dataCache;
        this.createResponse = createResponse;
        this.processingButton = processingButton;
        this.processingWord = processingWord;
    }


    public List<BotApiMethod<?>> handleUpdate(Update update) {

        List<BotApiMethod<?>> resultMessage = new ArrayList<>();

        //обработка входящего сообщения
        try {
            if (update.hasCallbackQuery()) {
                handleInputButton(update);
            } else if (update.hasMessage() && update.getMessage().hasText()) {
                hanleInputMessage(update);
            }
        } catch (ExceptionProcessing e) {
            if (update.hasCallbackQuery()) {
                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
                answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
                answerCallbackQuery.setShowAlert(false);
                answerCallbackQuery.setText("Нажмите на кнопку последнего сообщения");
                resultMessage.add(answerCallbackQuery);
                return resultMessage;
            } /*else if (update.hasMessage()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText(e.getMessage());
                resultMessage.add(sendMessage);
            }*/
        }

        if (update.hasCallbackQuery() || update.getMessage().getText().startsWith("/"))
            resultMessage.add(createResponse.getAnswer(update));
        return resultMessage;
    }

    private void hanleInputMessage(Update update) {
        String inputMessage = update.getMessage().getText();
        long userId = update.getMessage().getFrom().getId();

        //создание нового пользователя
        if (dataCache.getUserChatId(userId)==null) {
            dataCache.setUserChatId(userId, String.valueOf(update.getMessage().getChatId()));
            dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }

        //обработка команд
        switch (inputMessage) {
            case "/start":
                dataCache.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
        }

        //отправка сообщения на обработку
        //processingWord.dataAcceptance(update);

    }

    private void handleInputButton(Update update) {
        //отправка кнопки на обработку
        processingButton.dataAcceptance(update);
    }
}
