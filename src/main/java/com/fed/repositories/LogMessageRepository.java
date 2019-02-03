package com.fed.repositories;

import com.fed.database.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "logmessages", path = "logmessages")
public interface LogMessageRepository extends JpaRepository<LogMessage, Integer> {
}
