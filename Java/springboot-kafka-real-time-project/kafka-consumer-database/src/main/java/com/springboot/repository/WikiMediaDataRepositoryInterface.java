package com.springboot.repository;

import com.springboot.entity.WikiMediaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiMediaDataRepositoryInterface extends JpaRepository<WikiMediaData, Long> {

}
