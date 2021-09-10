package de.uwuwhatsthis.YeetsDiscordLibrary.main;

import de.uwuwhatsthis.YeetsDiscordLibrary.events.EventListener;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.GatewayManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.intents.Intent;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.lambdas.OnWebsocketError;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.application.SelfApplication;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.SlashCommand;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;

public class Bot {
    private static Bot instance;
    private final String token;
    private final GatewayManager gatewayManager;
    private final Debugger debugger;
    private final StateManager stateManager;

    public Bot(String token){
        this.token = token;
        this.stateManager = StateManager.getInstance();
        this.gatewayManager = new GatewayManager(token);
        this.debugger = new Debugger("MainThread");

        stateManager.setToken(token);

        instance = this;
    }

    public void addIntent(Intent intent) {
        gatewayManager.addIntent(intent);
    }

    public void addIntents(Intent... intents){
        gatewayManager.addIntents(intents);
    }

    public void addIntent(long intent){
        gatewayManager.addIntent(intent);
    }

    public void addSlashCommand(SlashCommand command){
        StateManager.getInstance().addSlashCommand(command);
    }

    public void addSlashCommands(SlashCommand... commands){
        for (SlashCommand command : commands) {
            addSlashCommand(command);
        }
    }


    public void login(OnWebsocketError error){
        gatewayManager.connect(error);
    }

    public void addEventListener(EventListener listener){
        stateManager.addEventListener(listener);
    }

    public void addEventListeners(EventListener... eventListeners){
        stateManager.addEventListeners(eventListeners);
    }

    public SelfApplication getApplication(){
        return stateManager.getApplication();
    }

    public static Bot getInstance() {
        return instance;
    }
}
