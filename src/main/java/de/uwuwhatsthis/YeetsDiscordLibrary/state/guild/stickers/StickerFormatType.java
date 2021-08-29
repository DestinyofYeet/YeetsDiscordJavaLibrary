package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.stickers;

public enum StickerFormatType {
    PNG(1),
    APNG(2),
    LOTTIE(3);

    private final int value;

    StickerFormatType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StickerFormatType getFromValue(int value){
        for (StickerFormatType type: values()){
            if (type.getValue() == value) return type;
        }

        return null;
    }
}
