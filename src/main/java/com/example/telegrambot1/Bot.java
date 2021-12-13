package com.example.telegrambot1;

import com.example.telegrambot1.botAPI.ResultAnswer;
import com.example.telegrambot1.botAPI.TelegramFacade;
import com.example.telegrambot1.cache.DataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;
    private TelegramFacade telegramFacade;
    @Autowired
    private DataCache dataCache;

    public Bot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setText("Hi");
//            sendMessage.setChatId("1625441396");
//            System.err.println(sendMessage.getReplyToMessageId());
//            EditMessageText messageText = new EditMessageText();

            ResultAnswer sendMessage = telegramFacade.handleUpdate(update);

            long userId = update.hasMessage() ? update.getMessage().getFrom().getId() :
                    update.getCallbackQuery().getFrom().getId();
            if (sendMessage.hasSendMessage()) {
                if (dataCache.getUserMessageId(userId) == null) {
                    Message message = execute(sendMessage.getSendMessage());
                    dataCache.setUserMessageId(userId, message.getMessageId());
                } else {
                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setMessageId(dataCache.getUserMessageId(userId));
                    editMessageText.setChatId(dataCache.getUserChatId(userId));
                    editMessageText.setText(sendMessage.getSendMessage().getText());
                    editMessageText.setReplyMarkup((InlineKeyboardMarkup) sendMessage.getSendMessage().getReplyMarkup());
                    execute(editMessageText);
                }
            } else if (sendMessage.hasAnswerCallbackQuery()) {
                execute(sendMessage.getAnswerCallbackQuery());
            }


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

}
