package co.com.ias.example.springwebsocket.config;

import co.com.ias.example.springwebsocket.controller.MainHandler;
import co.com.ias.example.springwebsocket.domain.Pong;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;

@Configuration
public class GsonConfiguration {

    @Bean
    public Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Pong.class, new Pong.Converter())
                .create();
    }

    @Bean
    public WebSocketHandler mainHandler(Gson gson) {
        return new MainHandler(gson);
    }
}
