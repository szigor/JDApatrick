package me.igor.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LoLMemeCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String messageSent = event.getMessage().getContentRaw();

        String urlApi = "https://meme-api.herokuapp.com/gimme/leagueofmemes";

        if (messageSent.equalsIgnoreCase("!lolmeme")) {
            String title = "";
            String imageurl = "";
            String postlink = "";
            boolean isNsfw = false;
            boolean isSpoiler = false;
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
                    //If you are ok with both spoilers and nsfw memes then replace lines 31-45 with "break;"
                }
                //event.getMessage().delete().queue();

                String userAsMention = event.getMessage().getAuthor().getAsMention();
                event.getChannel().sendMessage(userAsMention + " memik dla ciebie").queue();

                event.getChannel().sendMessageEmbeds(new EmbedBuilder()
                        .setColor(0xF08080)
                        .setTitle(title)
                        .setImage(imageurl)
                        .setFooter("Memy od `Patryka Rozgwiazdy`")
                        .build()).queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("**Something went wrong!** Please try again later!").queue();
            }
        }
    }
}
