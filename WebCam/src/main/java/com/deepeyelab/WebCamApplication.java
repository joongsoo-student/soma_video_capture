package com.deepeyelab;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.deepeyelab.capture.WebCamCapture;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class WebCamApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCamApplication.class, args);
	}
	
	@Component
    public static class MyWebSocketConfigurer implements WebSocketConfigurer {

        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(new MyBinaryHandler(), "/cameraFrame");
        }
    }


    @Component
    public static class MyBinaryHandler extends BinaryWebSocketHandler {
        public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
            try {
                session.sendMessage(new BinaryMessage(WebCamCapture.getInstance().getCurrentFrame()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
