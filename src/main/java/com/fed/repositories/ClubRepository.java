package com.fed.repositories;

import com.fed.database.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Integer> {

    Club findByName(String name);

}
