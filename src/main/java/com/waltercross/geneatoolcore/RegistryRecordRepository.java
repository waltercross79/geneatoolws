package com.waltercross.geneatoolcore;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistryRecordRepository extends MongoRepository<RegistryRecord, String> {

}
