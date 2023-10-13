package com.multipolar.bootcamp.gateaway.kafka;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccessLog {
    private String httpMethod;
    private String requestUri;
    private Integer responseStatusCode;
    private String content;
    private String clientIp;
    private String userAgent;
    private LocalDateTime timeStamp = LocalDateTime.now();

}
