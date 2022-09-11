package me.igor.Commands;

import me.igor.Counter.MemeCounter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class MemeCommand extends ListenerAdapter {

    private String title = "";
    private String imageurl = "";
    private String postlink = "";
    private boolean isNsfw = false;
    private boolean isSpoiler = false;
    private String urlApi = "https://meme-api.herokuapp.com/gimme";

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {

        TextChannel textChannel = event.getGuild().getDefaultChannel().asTextChannel();


        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (MemeCounter.get() == 0) {
            fillVariables();
            embedSender(textChannel);
            MemeCounter.increment();
        } else if (MemeCounter.get() == 1) {
            try {
                TimeUnit.MINUTES.sleep(170);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            fillVariables();
            embedSender(textChannel);
            MemeCounter.increment();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String messageSent = event.getMessage().getContentRaw();

        if (messageSent.equalsIgnoreCase("!meme")) {
            fillVariables();
            //event.getMessage().delete().queue();

            String userAsMention = event.getMessage().getAuthor().getAsMention();
            event.getChannel().sendMessage(userAsMention + " memik dla ciebie").queue();

            embedSender(event.getChannel().asTextChannel());
        }
    }

    private void embedSender(TextChannel textChannel) {
        textChannel
                .sendMessageEmbeds(new EmbedBuilder()
                        .setColor(0xF08080)
                        .setTitle(title)
                        .setImage(imageurl)
                        .setFooter("Memy od `Patryka Rozgwiazdy`")
                        .build()).queue();
    }

    private void fillVariables() {
        try {
            while (true) {
                URL memeurl = new URL(urlApi);
                BufferedReader bf = new BufferedReader(new InputStreamReader(memeurl.openConnection().getInputStream()));
                String input = bf.readLine();
                title = input.substring(input.indexOf("\"title\":") + "\"title\":\"".length(), input.indexOf("\",\"url\":"));
                imageurl = input.substring(input.indexOf("\"url\":\"") + "\"url\":\"".length(), input.indexOf("\",\"nsfw\":"));
                postlink = input.substring(input.indexOf("\"postLink\":\"") + "\"postLink\":\"".length(), input.indexOf("\",\"subreddit\":"));
                if (input.substring(input.indexOf("\"nsfw\":") + "\"nsfw\":".length(), input.indexOf(",\"spoiler\":")).equalsIgnoreCase("true")) {
                    isNsfw = true;
                } else {
                    isNsfw = false;
                }
                if (input.substring(input.indexOf(",\"spoiler\":") + ",\"spoiler\":".length(), input.indexOf("}")).equalsIgnoreCase("true")) {
                    isSpoiler = true;
                } else {
                    isSpoiler = false;
                }
                if (isNsfw == false && isSpoiler == false) {
                    break;
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            System.out.println("Something gone wrong");
        }
    }

}
