package com.example.telegrambot1.botAPI;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ResultAnswer {
    private SendMessage sendMessage;
    private AnswerCallbackQuery answerCallbackQuery;

    public boolean hasSendMessage() {
        return sendMessage!=null;
    }

    public boolean hasAnswerCallbackQuery() {
        return answerCallbackQuery!=null;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public AnswerCallbackQuery getAnswerCallbackQuery() {
        return answerCallbackQuery;
    }

    public void setAnswerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) {
        this.answerCallbackQuery = answerCallbackQuery;
    }
}
