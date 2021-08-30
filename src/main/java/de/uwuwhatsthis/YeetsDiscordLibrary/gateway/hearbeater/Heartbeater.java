package de.uwuwhatsthis.YeetsDiscordLibrary.gateway.hearbeater;

import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.GatewayManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Heartbeater {

    private Future<?> heartBeatFuture;
    private final Runnable heartBeatRunnable;
    private boolean running;
    private final GatewayManager gatewayManager;
    private int interval;

    private final static Debugger debugger = new Debugger("Heartbeater");

    public Heartbeater(GatewayManager gatewayManager){
        this.gatewayManager = gatewayManager;
        running = true;
        interval = 0;

        heartBeatFuture = null;
        heartBeatRunnable = () -> {
            Random random = new Random();

            debugger.debug("Started heartbeater");
            while (running){
                sendHeartbeat();

                long calculatedSleep = interval;
                if (gatewayManager.getSequence() == 0){
                    // Used for the first connection
                    calculatedSleep = (long) (interval * random.nextFloat());
                }

                try{
                    TimeUnit.MILLISECONDS.sleep(calculatedSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

            debugger.debug("Heartbeat thread is exiting!");
        };
    }

    public void sendHeartbeat(){
        if (!gatewayManager.receivedHeartBeatAck()){
            // we didn't receive a confirmation ack of our heartbeat -> attempt to resume
            gatewayManager.resume();
            return;
        }

        gatewayManager.setReceivedHeartBeatAck(false);
        JSONObject json = new JSONObject();
        json.put("op", 1);
        json.put("d", gatewayManager.getSequence() != 0 ? gatewayManager.getSequence(): "null");


        gatewayManager.getWebsocketConnection().sendText(json.toString(2));
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void start(){
        heartBeatFuture = StateManager.getInstance().getExecutorService().submit(heartBeatRunnable);
    }

    public void stop(){
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public Future<?> getHeartBeatFuture() {
        return heartBeatFuture;
    }
}
