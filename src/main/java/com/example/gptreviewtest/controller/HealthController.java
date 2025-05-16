package com.example.gptreviewtest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    private static final ZoneId SEOUL_ZONE = ZoneId.of("Asia/Seoul");

    /**
     * 서비스의 건강 상태를 체크합니다.
     * @return 현재 상태와 타임스탬프를 포함한 JSON 응답
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");

        ZonedDateTime koreaTime = ZonedDateTime.now(SEOUL_ZONE);
        String formattedTime = koreaTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        response.put("timestamp", formattedTime);
        return ResponseEntity.ok(response);
    }
}
