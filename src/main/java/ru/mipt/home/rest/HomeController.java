package ru.mipt.home.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.home.models.SimpleMessage;
import ru.mipt.home.models.StatusResponse;
import ru.mipt.home.services.FileService;

@RestController
@Slf4j
public class HomeController {

    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String home(@Value("${HOSTNAME:Can't get the hostname}") String hostname) {
        return "Welcome to the app. The hostname is " + hostname + ".";
    }

    @GetMapping("/status")
    public StatusResponse status() {
        return StatusResponse.builder()
                .status("ok")
                .build();
    }

    @PostMapping("/log")
    public void appendLog(@RequestBody SimpleMessage message, @Value("${app.log-file}") String path) {
        fileService.writeToFile(path, message.getMessage());
    }

    @GetMapping("/logs")
    public String fromLog(@Value("${app.log-file}") String path) {
        return fileService.readFile(path).orElse("Can't read from log file");
    }
}
