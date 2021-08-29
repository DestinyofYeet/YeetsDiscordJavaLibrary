package de.uwuwhatsthis.YeetsDiscordLibrary.main;

import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.GatewayManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.events.RawEventManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;

public class Bot {
    private final String token;
    private final RawEventManager rawEventManager;
    private final GatewayManager gatewayManager;
    private final Debugger debugger;

    public Bot(String token){
        this.token = token;
        this.rawEventManager = new RawEventManager();
        this.gatewayManager = new GatewayManager(token, rawEventManager);
        this.debugger = new Debugger("MainThread");
    }


    public void login(){
        gatewayManager.connect(cause -> {
            debugger.error("There was an error trying to start the bot!");
            cause.printStackTrace();
            gatewayManager.stop();
            System.exit(0);
        });
    }



}
