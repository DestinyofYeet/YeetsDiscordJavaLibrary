package de.uwuwhatsthis.YeetsDiscordLibrary.state.channel;

public enum ChannelType {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4),
    GUILD_NEWS(5),
    GUILD_STORE(6),
    GUILD_NEWS_THREAD(10),
    GUILD_PUBLIC_THREAD(11),
    GUILD_PRIVATE_THREAD(12),
    GUILD_STAGE_VOICE(13),

    UNKNOWN(-1);


    private final int value;

    ChannelType(int value){
        this.value = value;
    }

    public static ChannelType getFromValue(int value){
        for (ChannelType channelType : values()) {
            if (channelType.getValue() == value) return channelType;
        }

        return null;
    }

    public int getValue() {
        return value;
    }
}
