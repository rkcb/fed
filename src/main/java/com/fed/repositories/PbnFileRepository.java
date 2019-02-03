package com.fed.repositories;

import com.fed.database.PbnFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PbnFileRepository extends JpaRepository<PbnFile, Integer> {
}
