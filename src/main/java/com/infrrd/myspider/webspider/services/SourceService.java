package com.infrrd.myspider.webspider.services;

import java.util.List;

import com.infrrd.myspider.webspider.entities.Source;

public interface SourceService {
	Source addSource(Source source);
	List<Source> getAllSources();
	List<Source> getSourcesByWebsite(String website);
	Boolean deleteSourceById(Long id);
}
