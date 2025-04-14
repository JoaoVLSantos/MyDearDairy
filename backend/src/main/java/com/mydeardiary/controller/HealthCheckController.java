package com.mydeardiary.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydeardiary.util.Constants;

@RestController()
public class HealthCheckController {
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> responseBody = new HashMap<String, String>();

        responseBody.put(Constants.MESSAGE, "Hello World!");

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}