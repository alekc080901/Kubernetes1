package ru.mipt.home;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
}
