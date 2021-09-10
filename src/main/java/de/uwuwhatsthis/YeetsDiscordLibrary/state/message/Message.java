package de.uwuwhatsthis.YeetsDiscordLibrary.state.message;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.ChannelMention;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.member.Member;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role.Role;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.voiceState.VoiceState;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.attachment.Attachment;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.Embed;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.messageActivity.MessageActivity;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.reaction.MessageReaction;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.type.MessageType;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private String id, channelId, guildId, content, timestampRaw, editedTimestampRaw, nonce, webhook_id;
    private int typeInt;
    private boolean isTTS, isMentionEveryone, isPinned;
    private long idLong, channelIdLong, guildIdLong;

    private LocalDateTime timeStamp, editedTimestamp;

    private User author;
    private Member member;
    private List<User> mentions;
    private List<Role> roleMentions;
    private List<ChannelMention> channelMentions;
    private List<Attachment> attachments;
    private List<Embed> embeds;
    private List<MessageReaction> reactions;
    private MessageType messageType;
    private MessageActivity activity;
    private Guild guild;

    public Message(JSONObject data){
        id = Helper.getValueString(data, "id");
        channelId = Helper.getValueString(data, "channel_id");
        guildId = Helper.getValueString(data, "guild_id");
        content = Helper.getValueString(data, "content");
        timestampRaw = Helper.getValueString(data, "timestamp");
        editedTimestampRaw = Helper.getValueString(data, "edited_timestamp");

        isTTS = Helper.getValueBool(data, "tts");
        isMentionEveryone = Helper.getValueBool(data, "mention_everyone");

//        timeStamp = Helper.parseISO8601(timestampRaw); TODO: parse date
//        editedTimestamp = Helper.parseISO8601(editedTimestampRaw);

        if (guildId != null){
            guild = StateManager.getInstance().getGuildById(guildId).orElse(null);
        }

        if (data.has("author")){
            author = new User(data.getJSONObject("author"));
        }

        if (data.has("member")){
            VoiceState voiceState = null;

            if (guild != null)
                voiceState = guild.getVoiceStateById(author.getId().orElse(null)).orElse(null);

            member = new Member(data.getJSONObject("member"), voiceState, guild);
        }

        if (data.has("mentions")){
            data.getJSONArray("mentions").forEach(rawData -> {
                JSONObject mentionsData = (JSONObject) rawData;

                JSONObject userData = (JSONObject) mentionsData.remove("member");
                System.out.println(mentionsData.toString(2));
                System.out.println(userData.toString(2));
            });
        }
    }
}
