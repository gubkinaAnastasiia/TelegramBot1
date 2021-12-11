package com.example.telegrambot1.botAPI.createResponce;

public interface Create<T> {
    T getShowMainMenu();
    T getTest(long userId);
    T getResult(long userId);
    T getConsentTest(long userId);
}
