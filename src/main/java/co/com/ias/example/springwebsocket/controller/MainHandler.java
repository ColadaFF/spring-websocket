package co.com.ias.example.springwebsocket.controller;

import co.com.ias.example.springwebsocket.domain.Ping;
import co.com.ias.example.springwebsocket.domain.Pong;
import com.google.gson.Gson;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainHandler extends TextWebSocketHandler {

    private final Gson gson;


    private final Flowable<Ping> pingFlowable = Flowable.interval(1, TimeUnit.SECONDS)
            .map(Ping::valueOf);

    private final Map<Long, Ping> tracer = new HashMap<>();

    public MainHandler(Gson gson) {
        this.gson = gson;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        URI connectionUrl = session.getUri();
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(connectionUrl)
                .build()
                .getQueryParams();
        System.out.printf("Connection established: %s %n", queryParams);

        Disposable disposable = pingFlowable
                .doOnNext(ping -> tracer.put(ping.getId(), ping))
                .map(gson::toJson)
                .map(ping -> new TextMessage(ping.toString()))
                .subscribe(session::sendMessage);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("Connection close");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.printf("Got message: %s, %s %n", message, tracer);

        Pong pong = gson.fromJson(message.getPayload(), Pong.class);

        boolean b = tracer.containsKey(pong.getReplyTo());
        if (b) {
            tracer.remove(pong.getReplyTo());
        }

    }
}
