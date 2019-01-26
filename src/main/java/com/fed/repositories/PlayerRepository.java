package com.fed.repositories;

import com.fed.database.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByCode(String code);

}
