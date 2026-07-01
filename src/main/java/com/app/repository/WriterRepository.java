package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.app.entity.ScrapYard;


@Repository
public interface WriterRepository extends JpaRepository<ScrapYard, Long> {

}
