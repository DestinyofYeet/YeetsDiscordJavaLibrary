package de.uwuwhatsthis.YeetsDiscordLibrary.state;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.application.Application;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateManager {

    private static StateManager instance;

    private Application application;

    private Map<String, Guild> cachedGuilds;

    public StateManager(){
        instance = this;
        application = null;
        cachedGuilds = new HashMap<>();
    }

    public static StateManager getInstance(){
        if (instance == null){
            instance = new StateManager();
        }

        return instance;
    }

    public void addGuild(Guild guild){
        cachedGuilds.put(guild.getGuildId(), guild);
    }

    public Guild getGuildById(String id){
        return cachedGuilds.get(id);
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
