package com.fairshare.fairshare.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/healthz")
    public Map<String, Object> health() {
        return Map.of("status", "OK");
    }
}
