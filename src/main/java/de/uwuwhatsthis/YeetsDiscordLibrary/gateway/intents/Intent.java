package de.uwuwhatsthis.YeetsDiscordLibrary.gateway.intents;

public enum Intent {
    GUILDS(1),
    GUILD_MEMBERS(2),
    GUILD_BANS(4),
    GUILD_EMOJIS_AND_STICKERS(8),
    GUILD_INTEGRATIONS(16),
    GUILD_WEBHOOKS(32),
    GUILD_INVITES(64),
    GUILD_VOICE_STATES(128),
    GUILD_PRESENCES(256),
    GUILD_MESSAGES(512),
    GUILD_MESSAGE_REACTIONS(1024),
    GUILD_MESSAGE_TYPING(2048),
    DIRECT_MESSAGES(4096),
    DIRECT_MESSAGE_REACTIONS(8192),
    DIRECT_MESSAGE_TYPING(16_384),

    EVERYTHING(32_767), NOTHING(0);

    private int value;
    private static int everything = 0;


    Intent(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
