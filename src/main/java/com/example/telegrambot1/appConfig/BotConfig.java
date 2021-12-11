package com.example.telegrambot1.appConfig;

import com.example.telegrambot1.Bot;
import com.example.telegrambot1.botAPI.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public Bot myBot(TelegramFacade telegramFacade) {
        Bot myBot = new Bot(telegramFacade);
        myBot.setBotUserName(botUserName);
        myBot.setBotToken(botToken);
        myBot.setWebHookPath(webHookPath);
        return myBot;
    }

}
