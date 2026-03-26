package org.example.telegrambot.config;

import org.example.telegrambot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {
    @Autowired
    TelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            System.out.println("✅ Telegram Bot successfully started!");
        } catch (TelegramApiException e) {
            if (e.getMessage() != null && e.getMessage().contains("404")) {
                System.out.println("✅ Telegram Bot started (webhook not found, this is normal)");
            } else {
                System.err.println("❌ Failed to start Telegram Bot: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

