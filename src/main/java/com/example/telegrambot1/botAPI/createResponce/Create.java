package com.example.telegrambot1.botAPI.createResponce;

public interface Create<T> {
    T getShowMainMenu();
    T getInfo();
    T getShowAllTest();
    T getTest(long userId);
    T getTextResult(long userId);
    T getConsentTest(long userId);
    T needPsychiatrist();
}
