package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.reaction;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.emojis.Emoji;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class MessageReaction {
    private int count;
    private boolean botHasReacted;

    private Emoji emoji;
    private Guild guild;

    public MessageReaction(JSONObject data, Guild guild){
        this.guild = guild;

        count = Helper.getValueInt(data, "count");
        botHasReacted = Helper.getValueBool(data, "me");

        if (data.has("emoji")){
            emoji = new Emoji(data.getJSONObject("emoji"), guild);
        }
    }

    public Optional<Integer> getCount() {
        return Optional.ofNullable(count);
    }

    public Optional<Boolean> isBotHasReacted() {
        return Optional.ofNullable(botHasReacted);
    }

    public Optional<Emoji> getEmoji() {
        return Optional.ofNullable(emoji);
    }

    public Optional<Guild> getGuild() {
        return Optional.ofNullable(guild);
    }
}
