package org.example.telegrambot.config;

import lombok.extern.slf4j.Slf4j;
import org.example.telegrambot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Slf4j
@Component
public class BotInitializer {
    @Autowired
    TelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            log.info("✅ Telegram Bot successfully started!");
        } catch (TelegramApiException e) {
            if (e.getMessage() != null && e.getMessage().contains("404")) {
                log.error("✅ Telegram Bot started (webhook not found, this is normal)");
            } else {
                log.error("❌ Failed to start Telegram Bot: {}", e.getMessage());
            }
        }
    }
}

