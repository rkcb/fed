package com.fed.repositories;

import com.fed.database.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByCode(String code);

}
