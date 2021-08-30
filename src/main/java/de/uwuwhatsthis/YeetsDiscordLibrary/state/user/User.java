package de.uwuwhatsthis.YeetsDiscordLibrary.state.user;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

public class User {

    private boolean isBot;
    private String id;
    private Long idLong;
    private String avatarHash;
    private String userName;
    private String discriminator;

    public User(JSONObject data){
        isBot = data.getBoolean("bot");

        id = data.getString("id");
        idLong = Long.parseLong(id);

        avatarHash = Helper.getValueString(data, "avatar");
        userName = data.getString("username");
        discriminator = data.getString("discriminator");
    }

    public boolean isBot() {
        return isBot;
    }

    public String getId() {
        return id;
    }

    public Long getIdLong() {
        return idLong;
    }

    public String getAvatarHash() {
        return avatarHash;
    }

    public String getUserName() {
        return userName;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getAsMention(){
        return "<@!" + id + ">";
    }
}
