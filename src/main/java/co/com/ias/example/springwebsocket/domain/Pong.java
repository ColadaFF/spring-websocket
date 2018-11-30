package co.com.ias.example.springwebsocket.domain;

import com.google.gson.*;

import java.lang.reflect.Type;

public class Pong implements WebSocketMessage {

    public static class Converter implements JsonDeserializer<Pong> {
        @Override
        public Pong deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject asJsonObject = json.getAsJsonObject();
            long replyTo = asJsonObject.get("replyTo").getAsLong();
            return Pong.valueOf(replyTo);
        }
    }

    public static Pong valueOf(Long replyTo) {
        return new Pong(replyTo);
    }

    private final Long replyTo;
    private final MessageType type = MessageType.PONG;

    private Pong(Long replyTo) {
        this.replyTo = replyTo;
    }

    public Long getReplyTo() {
        return replyTo;
    }

    @Override
    public String toString() {
        return "Ping{" +
                "replyTo=" + replyTo +
                ", type=" + type +
                '}';
    }

    @Override
    public MessageType type() {
        return type;
    }
}
