package org.example.telegrambot.service.сommands;

import org.example.telegrambot.service.communication.Response;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class HelpCommand implements Command {
    Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Response execute(long chatId, Update update) {
        String message = "";
        for (Command command : commands.values()) {
            message = message + command.getDescription() + "\n";
        }
        return new Response(chatId, message);
    }

    @Override
    public String getDescription() {
        return "/help - Список всех команд";
    }
}
