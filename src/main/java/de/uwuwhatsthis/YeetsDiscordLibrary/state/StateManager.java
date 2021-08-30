package de.uwuwhatsthis.YeetsDiscordLibrary.state;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.application.Application;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StateManager {

    private static StateManager instance;

    private Application application;

    private Map<String, Guild> cachedGuilds;

    private ExecutorService executorService;

    public StateManager(){
        instance = this;
        application = null;
        cachedGuilds = new HashMap<>();
        executorService = Executors.newFixedThreadPool(10);
    }

    public static StateManager getInstance(){
        if (instance == null){
            instance = new StateManager();
        }

        return instance;
    }

    public void shutdown(){
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public void addGuild(Guild guild){
        guild.getGuildId().ifPresent(id -> cachedGuilds.put(id, guild));
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

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
