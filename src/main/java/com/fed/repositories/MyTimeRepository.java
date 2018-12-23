package com.fed.repositories;

import com.fed.database.MyTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "mytimes", path = "mytimes")
public interface MyTimeRepository extends JpaRepository<MyTime, Integer> {

    @Query(value = "select * from my_time where start >= ?1", nativeQuery = true)
    List<MyTime> myfind(String a);

    @Query(value = "select * from my_time where start >= :a and start <= :b", nativeQuery = true)
    List<MyTime> myfindTwo(@Param("a") String a, @Param("b") String b);



}

