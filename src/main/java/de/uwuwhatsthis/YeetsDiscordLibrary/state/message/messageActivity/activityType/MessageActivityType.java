package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.messageActivity.activityType;

public enum MessageActivityType {
    JOIN(1), SPECTATE(2), LISTEN(3), JOIN_REQUEST(5);

    private final int value;

    MessageActivityType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MessageActivityType getFromValue(int value){
        for (MessageActivityType messageActivityType : values()) {
            if (messageActivityType.value == value) return messageActivityType;
        }

        return null;
    }
}
