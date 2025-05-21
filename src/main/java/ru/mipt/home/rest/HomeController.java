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

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@RestController
@Slf4j
public class HomeController {

    private final FileService fileService;
    private final Counter totalLogRequests;
    private final Counter successLogRequests;
    private final Counter failedLogRequests;
    private final Timer logProcessingTimer;

    public HomeController(FileService fileService, MeterRegistry registry) {
        this.fileService = fileService;

        this.totalLogRequests = Counter.builder("app.log.requests.total")
                .description("Total number of /log requests")
                .register(registry);

        this.successLogRequests = Counter.builder("app.log.requests.success")
                .description("Successful log requests")
                .register(registry);

        this.failedLogRequests = Counter.builder("app.log.requests.failed")
                .description("Failed log requests")
                .register(registry);

        this.logProcessingTimer = Timer.builder("app.log.processing.time")
                .description("Time taken to process log requests")
                .register(registry);
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
        totalLogRequests.increment();

        Timer.Sample logTimer = Timer.start();
        try {
            fileService.writeToFile(path, message.getMessage());
            successLogRequests.increment();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            failedLogRequests.increment();
        }
        logTimer.stop(logProcessingTimer);
    }

    @GetMapping("/logs")
    public String fromLog(@Value("${app.log-file}") String path) {
        return fileService.readFile(path).orElse("Can't read from log file");
    }
}
