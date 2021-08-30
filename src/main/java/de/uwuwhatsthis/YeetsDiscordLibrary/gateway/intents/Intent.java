package de.uwuwhatsthis.YeetsDiscordLibrary.gateway.intents;

public enum Intent {
    GUILDS(0),
    GUILD_MEMBERS(1),
    GUILD_BANS(2),
    GUILD_EMOJIS_AND_STICKERS(3),
    GUILD_INTEGRATIONS(4),
    GUILD_WEBHOOKS(5),
    GUILD_INVITES(6),
    GUILD_VOICE_STATES(7),
    GUILD_PRESENCES(8),
    GUILD_MESSAGES(9),
    GUILD_MESSAGE_REACTIONS(10),
    GUILD_MESSAGE_TYPING(11),
    DIRECT_MESSAGES(12),
    DIRECT_MESSAGE_REACTIONS(13),
    DIRECT_MESSAGE_TYPING(14);

    private final int offset;
    private final long raw;


    Intent(int offset){
        this.offset = offset;
        raw = 1L << offset;
    }

    public long getRaw() {
        return raw;
    }

    public int getOffset() {
        return offset;
    }

    public static long getAllIntents(){
        long tempRaw = -100;

        for (Intent value : Intent.values()) {
            if (tempRaw == -100){
                tempRaw = value.getRaw();
                continue;
            }

            if (value.getOffset() == 100) continue;

            tempRaw += value.getRaw();

        }

        return tempRaw;
    }
}
