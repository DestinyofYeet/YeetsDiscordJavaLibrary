package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.stickers;

public enum StickerType {
    STANDARD(1), // official sticker pack
    GUILD(2); // user uploaded

    private final int value;

    StickerType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static StickerType getFromValue(int value){
        for (StickerType type : values()){
            if (type.getValue() == value) return type;
        }

        return null;
    }
}
