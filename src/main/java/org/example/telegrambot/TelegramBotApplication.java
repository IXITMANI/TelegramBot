package org.example.telegrambot;

import me.paulschwarz.springdotenv.DotenvConfig;
import me.paulschwarz.springdotenv.DotenvPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(TelegramBotApplication.class);

        app.addInitializers((ApplicationContextInitializer<ConfigurableApplicationContext>) ctx -> {
            DotenvConfig config = DotenvConfig.defaults();
            DotenvPropertySource dotenv = new DotenvPropertySource(config);
            ctx.getEnvironment().getPropertySources().addFirst(dotenv);
        });

        app.run(args);
    }
}