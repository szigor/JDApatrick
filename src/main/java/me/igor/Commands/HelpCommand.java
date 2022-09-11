package me.igor.Commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HelpCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String messageSent = event.getMessage().getContentRaw();

        if (messageSent.equalsIgnoreCase("p!help")) {
            event.getChannel().sendMessage(
                    "__**Lista wszystkich moich komend:**__\n" +
                            "`!meme` - memik ogólny\n" +
                            "`!lolmeme` - memik o lidze legend\n" +
                            "`!ping` - pokazuje swój ping"
            ).queue();
        }
    }
}
