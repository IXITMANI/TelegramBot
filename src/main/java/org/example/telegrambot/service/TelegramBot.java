package org.example.telegrambot.service;

import org.example.telegrambot.service.communication.Response;
import org.example.telegrambot.service.сommands.Command;
import org.example.telegrambot.service.сommands.StartCommand;
import org.example.telegrambot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;


@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;

    Map<String, Command> commands = new HashMap<>();

    public TelegramBot(BotConfig Config) {
        this.config = Config;
        commands.put("/start", new StartCommand());
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
            String command = update.getMessage().getText().toLowerCase();
            long chatId = update.getMessage().getChatId();
            if (commands.containsKey(command)) {
                sendMessage(commands.get(command).execute(chatId, update));
            } else {
                sendMessage(new Response(chatId, "Люблю тебя солнышко <3"));
            }

        }
    }

    private void sendMessage(Response response) {
        System.out.println(response.message());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(response.chatId()));
        sendMessage.setText(response.message());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
