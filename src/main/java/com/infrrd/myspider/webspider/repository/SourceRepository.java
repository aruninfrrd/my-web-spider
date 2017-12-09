package com.infrrd.myspider.webspider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.infrrd.myspider.webspider.entities.Source;


public interface SourceRepository extends JpaRepository<Source, Long> {
	List<Source> findByWebsite(String website);	
}
