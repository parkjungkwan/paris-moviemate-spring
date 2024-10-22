package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.queryDsl.MovieQueryDSL;
import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long>, MovieQueryDSL {

    @Query("SELECT m.title FROM MovieEntity m")
    List<String> getNowPlayingList();

    List<MovieEntity> findChart();

    @Query("SELECT m FROM MovieEntity m WHERE m.title = :title")
    Optional<MovieEntity> findByTitle(String title);
}
