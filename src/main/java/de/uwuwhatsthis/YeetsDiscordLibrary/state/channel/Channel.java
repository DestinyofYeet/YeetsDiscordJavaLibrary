package de.uwuwhatsthis.YeetsDiscordLibrary.state.channel;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;

import java.util.Optional;

public class Channel {
    protected String id, name;
    protected long idLong;

    public Channel(String id, String name){
        this.id = id;
        this.name = name;

        idLong = Helper.parseLong(id);
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Long> getIdLong() {
        return Optional.ofNullable(idLong);
    }
}
