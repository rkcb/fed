package com.fed.repositories;

import com.fed.database.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tournaments", path = "tournaments")
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
}
