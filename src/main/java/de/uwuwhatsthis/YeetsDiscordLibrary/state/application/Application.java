package de.uwuwhatsthis.YeetsDiscordLibrary.state.application;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.SelfUser;
import org.json.JSONObject;

public class Application {
    private SelfUser selfUser;
    private int flags;
    private String id;
    private Long idLong;

    public Application(JSONObject applicationData, JSONObject selfUserData){
        selfUser = new SelfUser(selfUserData);
        flags = applicationData.getInt("flags");
        id = applicationData.getString("id");
        idLong = Long.parseLong(id);
    }

    public SelfUser getSelfUser() {
        return selfUser;
    }

    public int getFlags() {
        return flags;
    }

    public String getId() {
        return id;
    }

    public Long getIdLong() {
        return idLong;
    }
}
