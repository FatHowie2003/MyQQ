package com.example.my_qq_spring.properties;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.util.WebAppRootListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class WebSocketConf implements ServletContextInitializer {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        servletContext.addListener(WebAppRootListener.class);

        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","52428800");

        servletContext.setInitParameter("org.apache.tomcat.websocket.binaryBufferSize","52428800");

    }

}

