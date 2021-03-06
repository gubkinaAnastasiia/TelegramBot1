package com.example.telegrambot1.botAPI.createResponce;

import com.example.telegrambot1.botAPI.BotState;
import com.example.telegrambot1.cache.DataCache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CreateResponse {

    private final CreateWord createWord;
    private final CreateButton createButton;
    private final DataCache dataCache;

    public CreateResponse(CreateWord createWord, CreateButton createButton, DataCache dataCache) {
        this.createWord = createWord;
        this.createButton = createButton;
        this.dataCache = dataCache;
    }

    public SendMessage getAnswer(Update update) {
        long userId = 0;
        if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getFrom().getId();
        } else if (update.hasMessage()) {
            userId = update.getMessage().getFrom().getId();
        }
        String chatId = dataCache.getUserChatId(userId);
        BotState botState = dataCache.getUsersCurrentBotState(userId);

        System.err.println("До создания сообщения" + botState.name());

        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);

        switch (botState) {
            case SHOW_MAIN_MENU:
                reply.setText(createWord.getShowMainMenu());
                reply.setReplyMarkup(createButton.getShowMainMenu());
                dataCache.setUserCurrentBotState(userId, BotState.MAIN_SELECTION);
                break;
            case INFO:
                reply.setText(createWord.getInfo());
                reply.setReplyMarkup(createButton.getInfo());
                break;
            case SHOW_ALL_TEST:
                reply.setText(createWord.getShowAllTest());
                reply.setReplyMarkup(createButton.getShowAllTest());
                dataCache.setUserCurrentBotState(userId, BotState.TEST_SELECTION);
                break;
            case CONSENT_TEST:
                reply.setText(createWord.getConsentTest(userId));
                reply.setReplyMarkup(createButton.getConsentTest(userId));
                break;
            case TEST:
                reply.setText(createWord.getTest(userId));
                reply.setReplyMarkup(createButton.getTest(userId));
                break;
            case GETTING_RESULT:
                reply.setText(createWord.getTextResult(userId));
                reply.setReplyMarkup(createButton.getTextResult(userId));
                if (dataCache.getUserTest(userId).needPsychiatrist()) {
                    reply.setText(reply.getText() + createWord.needPsychiatrist());
                    reply.setReplyMarkup(createButton.needPsychiatrist());
                    dataCache.setUserCurrentBotState(userId, BotState.CHOOSING_PSYCHIATRIST);
                }
                break;

        }

        System.err.println("После создания ответа: " + dataCache.getUsersCurrentBotState(userId).name());
        System.err.println("-----------------------");
        return reply;
    }
}
