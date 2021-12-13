package com.example.telegrambot1.botAPI.createResponce;

import com.example.telegrambot1.botAPI.ButtonState;
import com.example.telegrambot1.cache.DataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateButton implements Create<InlineKeyboardMarkup> {

    private final DataCache dataCache;

    public CreateButton(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    @Override
    public InlineKeyboardMarkup getShowMainMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonGetTest = new InlineKeyboardButton();
        buttonGetTest.setCallbackData(ButtonState.GET_TEST.name());
        buttonGetTest.setText("Получить тест");

        InlineKeyboardButton buttonGetInfo = new InlineKeyboardButton();
        buttonGetInfo.setCallbackData(ButtonState.GET_INFO.name());
        buttonGetInfo.setText("Получить информацию");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonGetTest);
        List<InlineKeyboardButton> keyboardButtonList2 = new ArrayList<>();
        keyboardButtonList2.add(buttonGetInfo);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        keyboardButtonList.add(keyboardButtonList2);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInfo() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonBack = new InlineKeyboardButton();
        buttonBack.setCallbackData(ButtonState.BACK.name());
        buttonBack.setText("Назад");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonBack);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getShowAllTest() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonTestBeck = new InlineKeyboardButton();
        buttonTestBeck.setText("Тест Бека");
        buttonTestBeck.setCallbackData(ButtonState.TEST_BECK.name());

        InlineKeyboardButton buttonBack = new InlineKeyboardButton();
        buttonBack.setText("Назад");
        buttonBack.setCallbackData(ButtonState.BACK.name());

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonTestBeck);

        List<InlineKeyboardButton> keyboardButtonList2 = new ArrayList<>();
        keyboardButtonList2.add(buttonBack);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        keyboardButtonList.add(keyboardButtonList2);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getTest(long userId) {
        return dataCache.getUserTest(userId).getKeybord();
    }

    @Override
    public InlineKeyboardMarkup getTextResult(long userId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonOk = new InlineKeyboardButton();
        buttonOk.setCallbackData(ButtonState.OK.name());
        buttonOk.setText("Ок");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonOk);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }


    @Override
    public InlineKeyboardMarkup getConsentTest(long userId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        buttonYes.setCallbackData(ButtonState.YES.name());
        buttonYes.setText("Да");

        InlineKeyboardButton buttonNo = new InlineKeyboardButton();
        buttonNo.setCallbackData(ButtonState.NO.name());
        buttonNo.setText("Нет");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonNo);
        keyboardButtonList1.add(buttonYes);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup needPsychiatrist() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        buttonYes.setCallbackData(ButtonState.YES.name());
        buttonYes.setText("Да");

        InlineKeyboardButton buttonNo = new InlineKeyboardButton();
        buttonNo.setCallbackData(ButtonState.NO.name());
        buttonNo.setText("Нет");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(buttonNo);
        keyboardButtonList1.add(buttonYes);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }
}
