package com.fed.repositories;

import com.fed.database.CompetitionRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "competitionregistrations", path = "competitionregistrations")
public interface CompetitionRegistrationRepository extends JpaRepository<CompetitionRegistration,
 Integer> {



}
