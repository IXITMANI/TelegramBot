package org.example.telegrambot.service.communication;

public record Response(long chatId, String message) {
    public Response(long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }
}
