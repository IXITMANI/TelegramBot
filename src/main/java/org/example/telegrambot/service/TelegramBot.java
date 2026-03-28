package org.example.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.example.telegrambot.service.communication.Response;
import org.example.telegrambot.service.сommands.*;
import org.example.telegrambot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;

    Map<String, Command> commands = new HashMap<>();

    public TelegramBot(BotConfig Config) {
        this.config = Config;
        commands.put("/start", new StartCommand());
        // New commands
        commands.put("/help", new HelpCommand(commands));
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (commands.containsKey(command)) {
                sendMessage(commands.get(command).execute(chatId, update));
            } else {
                sendMessage(new Response(chatId, "Люблю тебя солнышко <3"));
            }
        }
    }

    private void sendMessage(Response response) {
        System.out.println(response.chatId()+ " " + response.message());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(response.chatId()));
        sendMessage.setText(response.message());

        try {
            execute(sendMessage);
            log.info("Replied to user {} {}", response.chatId(), response.message());
        } catch (TelegramApiException e) {
            log.error("Send message error {}", e.getMessage());
        }
    }
}
