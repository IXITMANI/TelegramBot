package org.example.telegrambot.service.сommands;

import org.example.telegrambot.service.communication.Response;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {


    @Override
    public Response execute(long chatId, Update update) {

        String answer = "Привет, " + update.getMessage().getChat().getFirstName() + "!";

        return new Response(chatId, answer);
    }

    @Override
    public String getDescription() {
        return "Запуск ботика <3";
    }
}
