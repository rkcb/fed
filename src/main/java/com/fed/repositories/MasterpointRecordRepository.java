package com.fed.repositories;

import com.fed.database.MasterpointRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "masterpointrecords", path = "masterpointrecords")
public interface MasterpointRecordRepository extends JpaRepository<MasterpointRecord, Integer> {
}
