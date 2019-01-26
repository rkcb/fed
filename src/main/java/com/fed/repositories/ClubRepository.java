package com.fed.repositories;

import com.fed.database.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "clubs", path = "clubs")
public interface ClubRepository extends JpaRepository<Club, Integer> {

    Club findByName(String name);

}
