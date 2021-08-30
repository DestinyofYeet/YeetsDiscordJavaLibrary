package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.voiceState;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.member.Member;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class VoiceState {
    private static Debugger debugger = new Debugger("VoiceState");

    private Guild guild;
    private String guildId, channelId, userId, sessionId, requestToSpeakTimestamp;
    private Long guildIdLong, channelIdLong, sessionIdLong;
    private boolean isServerDeaf, isServerMuted, isSelfDeaf, isSelfMuted, isStreaming, isMutedByBot, isCameraEnabled;

    private Member member;

    public VoiceState(JSONObject data, Guild guild){
        this.guild = guild;
        guildId = Helper.getValueString(data, "guild_id");
        channelId = Helper.getValueString(data, "channel_id");
        userId = Helper.getValueString(data, "user_id");
        sessionId = Helper.getValueString(data, "session_id");
        requestToSpeakTimestamp = Helper.getValueString(data, "request_to_speak_timestamp");

        isServerDeaf = Helper.getValueBool(data, "deaf");
        isServerMuted = Helper.getValueBool(data, "mute");
        isSelfDeaf = Helper.getValueBool(data, "self_deaf");
        isSelfMuted = Helper.getValueBool(data, "self_mute");
        isStreaming = Helper.getValueBool(data, "self_stream");
        isCameraEnabled = Helper.getValueBool(data, "self_video");
        isMutedByBot = Helper.getValueBool(data, "suppress");

        guildIdLong = Helper.parseLong(guildId);
        channelIdLong = Helper.parseLong(channelId);
        sessionIdLong = Helper.parseLong(sessionId);

        if (guildId != null && !guild.getGuildId().equals(guildId)){
            debugger.error("Provided guild and guildId mismatch!");
            this.guild = StateManager.getInstance().getGuildById(guildId);
        }
    }

    public Optional<Guild> getGuild() {
        return Optional.of(guild);
    }

    public Optional<String> getGuildId() {
        return Optional.of(guildId);
    }

    public Optional<String>  getChannelId() {
        return Optional.of(channelId);
    }

    public Optional<String>  getUserId() {
        return Optional.of(userId);
    }

    public Optional<String>  getSessionId() {
        return Optional.of(sessionId);
    }

    public Optional<String>  getRequestToSpeakTimestamp() {
        return Optional.of(requestToSpeakTimestamp);
    }

    public Optional<Long> getGuildIdLong() {
        return Optional.of(guildIdLong);
    }

    public Optional<Long> getChannelIdLong() {
        return Optional.of(channelIdLong);
    }

    public Optional<Long> getSessionIdLong() {
        return Optional.of(sessionIdLong);
    }

    public Optional<Boolean> isServerDeaf() {
        return Optional.of(isServerDeaf);
    }

    public Optional<Boolean> isServerMuted() {
        return Optional.of(isServerMuted);
    }

    public Optional<Boolean> isSelfDeaf() {
        return Optional.of(isSelfDeaf);
    }

    public Optional<Boolean> isSelfMuted() {
        return Optional.of(isSelfMuted);
    }

    public Optional<Boolean> isStreaming() {
        return Optional.of(isStreaming);
    }

    public Optional<Boolean> isMutedByBot() {
        return Optional.of(isMutedByBot);
    }

    public Optional<Boolean> isCameraEnabled() {
        return Optional.of(isCameraEnabled);
    }

    public Optional<Member> getMember() {
        return Optional.of(member);
    }
}
