package de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents;

import de.uwuwhatsthis.YeetsDiscordLibrary.main.Bot;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;

import java.util.Optional;

public class GenericGuildEvent {
    protected Guild guild;
    protected Bot bot;

    public GenericGuildEvent(Guild guild){
        this.guild = guild;
        this.bot = Bot.getInstance();
    }

    public Optional<Guild> getGuild() {
        return Optional.of(guild);
    }

    public Optional<Bot> getBot() {
        return Optional.of(bot);
    }
}
