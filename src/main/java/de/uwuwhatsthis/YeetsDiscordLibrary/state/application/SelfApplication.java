package de.uwuwhatsthis.YeetsDiscordLibrary.state.application;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.SelfUser;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class SelfApplication {
    private SelfUser selfUser;
    private int flags;
    private String id;
    private Long idLong;

    public SelfApplication(JSONObject applicationData, JSONObject selfUserData){
        selfUser = new SelfUser(selfUserData);
        flags = Helper.getValueInt(applicationData, "flags");
        id = Helper.getValueString(applicationData, "id");
        idLong = Helper.parseLong(id);
    }

    public String getId() {
        return id;
    }

    public Long getIdLong() {
        return idLong;
    }

    public Optional<SelfUser> getSelfUser() {
        return Optional.ofNullable(selfUser);
    }

    public Optional<Integer> getFlags() {
        return Optional.ofNullable(flags);
    }
}
