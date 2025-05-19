package ru.mipt.home;

import jakarta.annotation.PostConstruct;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.mipt.home.services.FileService;

@Configuration
public class BeanConfiguration {

    @Autowired
    private FileService fileService;
    @Value("${app.log-file}")
    private String logPath;

    @PostConstruct
    public void init() {
        fileService.createIfNotExists(logPath);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnProperty("server.ssl.enabled")
    public ServletWebServerFactory servletContainer(@Value("${server.http.port}") int httpPort,
                                                    @Value("${server.port}") int httpsPort) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHttpConnector(httpPort, httpsPort));
        return tomcat;
    }

    private Connector createHttpConnector(int httpPort, int httpsPort) {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setScheme("http");
        connector.setRedirectPort(httpsPort);
        return connector;
    }
}
