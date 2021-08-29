package de.uwuwhatsthis.YeetsDiscordLibrary.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Debugger {
    private final String identifier;
    private boolean showMillis;

    public Debugger(String identifier){

        this.identifier = identifier;
        this.showMillis = false;
    }

    private String getFormat(){
        if (!showMillis)
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("d.M.u H:m:s")) + " - [" + identifier + "] ";
        else
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("d.M.u H:m:s:S")) + " - [" + identifier + "] ";
    }

    public void debug(String message){
        if (Constants.DEBUG){
            System.out.println("DEBUG - " + getFormat() + ": " + message);
        }
    }

    public void info(String message){
        System.out.println("INFO - " + getFormat() + ": " + message);
    }

    public void error(String message){
        System.err.println("ERROR - " + getFormat() + ": " + message);
    }

    public void setShowMillis(boolean showMillis) {
        this.showMillis = showMillis;
    }
}
