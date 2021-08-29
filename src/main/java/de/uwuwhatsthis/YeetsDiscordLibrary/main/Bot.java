package de.uwuwhatsthis.YeetsDiscordLibrary.main;

import de.uwuwhatsthis.YeetsDiscordLibrary.errors.intents.IntentAlreadyAddedException;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.GatewayManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.intents.Intent;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.lambdas.OnWebsocketError;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;

public class Bot {
    private final String token;
    private final GatewayManager gatewayManager;
    private final Debugger debugger;

    public Bot(String token){
        this.token = token;
        this.gatewayManager = new GatewayManager(token);
        this.debugger = new Debugger("MainThread");
    }

    public void addIntent(Intent intent) {
        gatewayManager.addIntent(intent);
    }

    public void addIntents(Intent... intents){
        gatewayManager.addIntents(intents);
    }


    public void login(OnWebsocketError error){
        gatewayManager.connect(error);
    }



}
