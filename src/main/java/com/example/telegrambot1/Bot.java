package com.example.telegrambot1;

import com.example.telegrambot1.botAPI.TelegramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;
    private TelegramFacade telegramFacade;

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
//            execute(sendMessage);
            List<BotApiMethod<?>> listMessage = telegramFacade.handleUpdate(update);
            if (listMessage.size()!=0) {
                for (BotApiMethod<?> elem : listMessage) {
                    execute(elem);
                }
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
