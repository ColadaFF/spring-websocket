package co.com.ias.example.springwebsocket.domain;

import java.util.Objects;

public class Ping implements WebSocketMessage {

    public static Ping valueOf(Long id) {
        return new Ping(id);
    }

    private final Long id;
    private final MessageType type = MessageType.PING;

    private Ping(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ping)) return false;
        Ping ping = (Ping) o;
        return Objects.equals(getId(), ping.getId()) &&
                type == ping.type;
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(getId(), type);
    }

    @Override
    public String toString() {
        return "Ping{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }

    @Override
    public MessageType type() {
        return type;
    }
}
