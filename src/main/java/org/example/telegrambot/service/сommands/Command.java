package org.example.telegrambot.service.сommands;

import org.example.telegrambot.service.communication.Response;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    public Response execute(long chatId, Update update);
    public String getDescription();
}
