package by.crashcar.telegram.notifier.service.impl;

import by.crashcar.core.dto.CreateOrderEvent;
import by.crashcar.core.exception.ServiceException;
import by.crashcar.telegram.notifier.configuration.BotConfig;
import by.crashcar.telegram.notifier.service.TelegramSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class DefaultTelegramSender implements TelegramSender {
    private final TelegramLongPollingBot bot;
    private final BotConfig botConfig;

    @Override
    public void send(CreateOrderEvent createOrderEvent) {
        try {
            bot.execute(getSendMessage(createOrderEvent));
        } catch (TelegramApiException e) {
            throw new ServiceException("Can't send createOrderEvent " + createOrderEvent, e);
        }
    }

    private SendMessage getSendMessage(CreateOrderEvent orderEvent) {
        SendMessage sendMessage = new SendMessage(botConfig.getChatId(), getText(orderEvent));
        sendMessage.enableHtml(true);
        return sendMessage;
    }

    private String getText(CreateOrderEvent orderEvent) {
        StringBuilder builder = new StringBuilder();
        builder.append("У вас новая заявка!\n")
                .append("id: ").append(orderEvent.getId()).append("\n")
                .append("Телефон: ").append(orderEvent.getPhone()).append("\n")
                .append("Сообщение: ").append(orderEvent.getMessage()).append("\n")
                .append("Файлы:").append("\n");

        orderEvent.getLinks()
                .forEach(link ->
                        builder.append("<a href=\"").append(link).append("\">").append("Ссылка").append("</a>\n"));

        return builder.toString();

    }
}
