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
        return "Добрый день. \n" +
                "Выберите какой тест хотите пройти или введите номер теста.\n" +
                "1. Тест Бека.";
    }

    @Override
    public String getTest(long userId) {
        return dataCache.getUserTest(userId).getQuest();
    }

    @Override
    public String getResult(long userId) {
        return dataCache.getUserTest(userId).getResult();
    }

    @Override
    public String getConsentTest(long userId) {
        return dataCache.getUserTest(userId).getStartMessage();
    }
}
