package by.crashcar.telegram.notifier.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Configuration
public class TelegramNotifierConfiguration {

    @Bean
    public TelegramLongPollingBot telegramLongPollingBot(BotConfig botConfig) {
        return new TelegramLongPollingBot(botConfig.getToken()) {
            @Override
            public String getBotUsername() {
                return botConfig.getName();
            }

            @Override
            public void onUpdateReceived(Update update) {

            }
        };
    }
}
