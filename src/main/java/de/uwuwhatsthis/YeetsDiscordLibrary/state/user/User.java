package de.uwuwhatsthis.YeetsDiscordLibrary.state.user;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import javax.swing.text.html.Option;
import java.util.Optional;

public class User {

    private boolean isBot;
    private String id;
    private Long idLong;
    private String avatarHash;
    private String userName;
    private String discriminator;

    public User(JSONObject data){
        isBot = Helper.getValueBool(data, "bot");

        id = Helper.getValueString(data, "id");
        idLong = Helper.parseLong(id);

        avatarHash = Helper.getValueString(data, "avatar");
        userName = Helper.getValueString(data, "username");
        discriminator = Helper.getValueString(data, "discriminator");
    }

    public Optional<Boolean> isBot() {
        return Optional.ofNullable(isBot);
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<Long> getIdLong() {
        return Optional.ofNullable(idLong);
    }

    public Optional<String> getAvatarHash() {
        return Optional.ofNullable(avatarHash);
    }

    public Optional<String> getUserName() {
        return Optional.ofNullable(userName);
    }

    public Optional<String> getDiscriminator() {
        return Optional.ofNullable(discriminator);
    }
}
