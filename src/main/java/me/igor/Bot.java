package me.igor;

import me.igor.Commands.HelpCommand;
import me.igor.Commands.LoLMemeCommand;
import me.igor.Commands.MemeCommand;
import me.igor.Commands.PingCommand;
import me.igor.Counter.MemeCounter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

public class Bot {
    private Bot() throws LoginException {

        JDABuilder.createDefault(
                        System.getenv("TOKEN"),
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_VOICE_STATES
                )
                .addEventListeners(
                        new MemeCommand(),
                        new LoLMemeCommand(),
                        new PingCommand(),
                        new HelpCommand())
                .setActivity(Activity.watching("p!help - komendy"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
        while (true) {
            try {
                TimeUnit.MINUTES.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            MemeCounter.checkHourIfFineThenSetToZero();
        }
    }
}
