package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.voiceState;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.member.Member;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

public class VoiceState {
    private static Debugger debugger = new Debugger("VoiceState");
    private Guild guild;
    private String guildId, channelId, userId, sessionId, requestToSpeakTimestamp;
    private Long guildIdLong, channelIdLong, sessionIdLong;
    private boolean isServerDeaf, isServerMuted, isSelfDeaf, isSelfMuted, isStreaming, isMutedByBot, isCameraEnabled;

    private Member member;

    public VoiceState(JSONObject data, Guild guild){
        this.guild = guild;
        guildId = Helper.applyValueString(data, "guild_id");
        channelId = Helper.applyValueString(data, "channel_id");
        userId = Helper.applyValueString(data, "user_id");
        sessionId = Helper.applyValueString(data, "session_id");
        requestToSpeakTimestamp = Helper.applyValueString(data, "request_to_speak_timestamp");

        isServerDeaf = Helper.applyValueBool(data, "deaf");
        isServerMuted = Helper.applyValueBool(data, "mute");
        isSelfDeaf = Helper.applyValueBool(data, "self_deaf");
        isSelfMuted = Helper.applyValueBool(data, "self_mute");
        isStreaming = Helper.applyValueBool(data, "self_stream");
        isCameraEnabled = Helper.applyValueBool(data, "self_video");
        isMutedByBot = Helper.applyValueBool(data, "suppress");

        guildIdLong = Helper.parseLong(guildId);
        channelIdLong = Helper.parseLong(channelId);
        sessionIdLong = Helper.parseLong(sessionId);

        if (guildId != null && !guild.getGuildId().equals(guildId)){
            debugger.error("Provided guild and guildId mismatch!");
            this.guild = StateManager.getInstance().getGuildById(guildId);
        }
    }

    public static Debugger getDebugger() {
        return debugger;
    }

    public Guild getGuild() {
        return guild;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getRequestToSpeakTimestamp() {
        return requestToSpeakTimestamp;
    }

    public Long getGuildIdLong() {
        return guildIdLong;
    }

    public Long getChannelIdLong() {
        return channelIdLong;
    }

    public Long getSessionIdLong() {
        return sessionIdLong;
    }

    public boolean isServerDeaf() {
        return isServerDeaf;
    }

    public boolean isServerMuted() {
        return isServerMuted;
    }

    public boolean isSelfDeaf() {
        return isSelfDeaf;
    }

    public boolean isSelfMuted() {
        return isSelfMuted;
    }

    public boolean isStreaming() {
        return isStreaming;
    }

    public boolean isMutedByBot() {
        return isMutedByBot;
    }

    public boolean isCameraEnabled() {
        return isCameraEnabled;
    }

    public Member getMember() {
        return member;
    }
}
