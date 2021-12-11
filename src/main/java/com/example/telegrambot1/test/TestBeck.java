package com.example.telegrambot1.test;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestBeck implements Test{

    final int MAX_QUEST = 2;
    int current_quest = 0;
    int result = 0;

    List<String> quest = Arrays.asList(
            "Вопрос 1.\n" +
                    "1. Я не чувствую себя несчастным\n" +
                    "2. Я чувствую себя несчастным\n" +
                    "3. Я все время несчастен и не могу освободиться от этого чувства\n" +
                    "4. Я настолько несчастен и опечален, что не могу этого вынести",

            "Вопрос 2.\n" +
                    "1. Думая о будущем, я не чувствую себя особенно разочарованным\n" +
                    "2. Думая о будущем, я чувствую себя разочарованным\n" +
                    "3. Я чувствую, что мне нечего ждать в будущем\n" +
                    "4. Я чувствую, что будущее безнадежно и ничего не изменится к лучшему"
    );

    @Override
    public boolean hasNextQuest() {
        return current_quest+1 < MAX_QUEST;
    }

    @Override
    public boolean checkAnswer(String answer) {
        switch (answer) {
            case "1":
            case "2":
            case "3":
            case "4":
                return true;
        }
        return false;
    }

    @Override
    public void nextQuest() {
        current_quest++;
    }

    @Override
    public void setResult(String answer) {
        switch (answer) {
            case "1": break;
            case "2": result+=1; break;
            case "3": result+=2; break;
            case "4": result+=3; break;
        }
    }

    @Override
    public String getStartMessage() {
        return "Вы выбирали тест Бека." +
                "В этом тесте вам понадобится выбирать варианты ответа, наиболее близкие к вам.\n" +
                "Всего будет 21 вопрос.\n" +
                "Начнем тест?";
    }

    @Override
    public String getQuest() {
        return quest.get(current_quest);
    }

    @Override
    public String getResult() {
        String yourResult = "Ваш результат: ";
        if (result < 21) {
            return  yourResult + "Все отлично";
        } else if (result >=21 && result < 42) {
            return  yourResult + "Сомнительно";
        } else if (result >=42) {
            return  yourResult + "Все плохо";
        }
        return "Произошел сбой";
    }

    @Override
    public InlineKeyboardMarkup getKeybord() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("1");
        button1.setCallbackData("1");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("2");
        button2.setCallbackData("2");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("3");
        button3.setCallbackData("3");

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("4");
        button4.setCallbackData("4");

        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(button1);
        keyboardButtonList1.add(button2);
        List<InlineKeyboardButton> keyboardButtonList2 = new ArrayList<>();
        keyboardButtonList2.add(button3);
        keyboardButtonList2.add(button4);

        List<List<InlineKeyboardButton>> keyboardButtonList = new ArrayList<>();
        keyboardButtonList.add(keyboardButtonList1);
        keyboardButtonList.add(keyboardButtonList2);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonList);

        return inlineKeyboardMarkup;
    }
}
