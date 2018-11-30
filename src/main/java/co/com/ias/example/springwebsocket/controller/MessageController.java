package co.com.ias.example.springwebsocket.controller;

import co.com.ias.example.springwebsocket.domain.Ping;
import co.com.ias.example.springwebsocket.domain.Pong;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {


    @MessageMapping("/test")
    @SendTo
    public Pong replyPing(Ping ping) throws Exception {
        return Pong.valueOf(ping.getId());
    }
}
