package com.multipolar.bootcamp.logging.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.multipolar.bootcamp.logging.domain.AccessLog;

public interface AccessLogRepository extends MongoRepository<AccessLog, String> {

}
