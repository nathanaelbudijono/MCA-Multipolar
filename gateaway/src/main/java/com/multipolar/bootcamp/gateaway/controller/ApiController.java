package com.multipolar.bootcamp.gateaway.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gateaway.dto.AccountDto;
import com.multipolar.bootcamp.gateaway.dto.ErrorMessageDto;
import com.multipolar.bootcamp.gateaway.kafka.AccessLog;
import com.multipolar.bootcamp.gateaway.service.AccessLogService;
import com.multipolar.bootcamp.gateaway.util.RestTemplateUtil;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final String CUSTOMER_URL = "http://localhost:8081/api/bankAccount";

    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private final AccessLogService logService;

    @Autowired
    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService logService) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.logService = logService;
    }

    @GetMapping("/getaccount")
    public ResponseEntity<?> getBankAccount(HttpServletRequest request) throws JsonProcessingException {
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        try {
            ResponseEntity<?> response = restTemplateUtil.getList(CUSTOMER_URL,
                    new ParameterizedTypeReference<Object>() {
                    });
            ResponseEntity.status(response.getStatusCode()).body(response.getBody());

            AccessLog accessLog = new AccessLog("GET", CUSTOMER_URL + "getaccount",
                    response.getStatusCodeValue(), "Success", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDto> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET", CUSTOMER_URL + "/getaccount",
                    ex.getRawStatusCode(), "Failed", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping("/getaccount/{id}")
    public ResponseEntity<?> getBankAccountById(@PathVariable String id, HttpServletRequest request)
            throws JsonProcessingException {
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.get(CUSTOMER_URL + "/" + id, AccountDto.class);
            AccessLog accessLog = new AccessLog("GET", CUSTOMER_URL + "getaccount",
                    response.getStatusCodeValue(), "Success", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDto> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET", CUSTOMER_URL + "/getaccount",
                    ex.getRawStatusCode(), "Failed", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PostMapping("/createaccount")
    public ResponseEntity<?> createBankAccount(@RequestBody AccountDto accountDto, HttpServletRequest request)
            throws JsonProcessingException {
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.post(CUSTOMER_URL, accountDto, AccountDto.class);
            AccessLog accessLog = new AccessLog("POST", CUSTOMER_URL + "createaccount",
                    response.getStatusCodeValue(), "Success", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDto> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("POST", CUSTOMER_URL + "/createaccount",
                    ex.getRawStatusCode(), "Failed", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PutMapping("/updateaccount/{id}")
    public ResponseEntity<?> updateBankAccount(@PathVariable String id, @RequestBody AccountDto accountDto,
            HttpServletRequest request)
            throws JsonProcessingException {
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.put(CUSTOMER_URL + "/" + id, accountDto,
                    AccountDto.class);
            AccessLog accessLog = new AccessLog("PUT", CUSTOMER_URL + "updateaccount",
                    response.getStatusCodeValue(), "Success", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDto> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("PUT", CUSTOMER_URL + "/updateaccount",
                    ex.getRawStatusCode(), "Failed", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteaccount/{id}")
    public ResponseEntity<?> deleteBankAccount(@PathVariable String id, HttpServletRequest request)
            throws JsonProcessingException {
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.delete(CUSTOMER_URL + "/" + id);
            AccessLog accessLog = new AccessLog("DELETE", CUSTOMER_URL + "deleteaccount",
                    response.getStatusCodeValue(), "Success", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDto> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("DELETE", CUSTOMER_URL + "/deleteaccount",
                    ex.getRawStatusCode(), "Failed", clientIp, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }
}
