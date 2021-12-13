package com.example.telegrambot1.botAPI.createResponce;

import com.example.telegrambot1.cache.DataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateWord implements Create<String>{

    private final DataCache dataCache;

    public CreateWord(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    @Override
    public String getShowMainMenu() {
        return "Добрый день. Выберете, что хотите сделать:\n" +
                "1. Пройти тест.\n" +
                "2. Получить информацию о психиатре.\n";
    }

    @Override
    public String getInfo() {
        return "Информация о психиатрах + ссылки";
    }

    @Override
    public String getShowAllTest() {
        return "Выберите какой тест хотите пройти или введите номер теста.\n" +
                "1. Тест Бека.\n"+
                "2. Назад";
    }

    @Override
    public String getTest(long userId) {
        return dataCache.getUserTest(userId).getQuest();
    }

    @Override
    public String getTextResult(long userId) {
        return dataCache.getUserTest(userId).getTextResult();
    }

    @Override
    public String getConsentTest(long userId) {
        return dataCache.getUserTest(userId).getStartMessage();
    }

    @Override
    public String needPsychiatrist() {
        return "\nВам нужен психиатр, хотите получить более подробную информацию?";
    }
}
