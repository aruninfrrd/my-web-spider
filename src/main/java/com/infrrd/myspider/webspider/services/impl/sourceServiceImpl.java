package com.infrrd.myspider.webspider.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infrrd.myspider.webspider.entities.Source;
import com.infrrd.myspider.webspider.repository.SourceRepository;
import com.infrrd.myspider.webspider.services.SourceService;



@Service
public class sourceServiceImpl implements SourceService {
	@Autowired
	SourceRepository sourceRepo;

	@Override
	public Source addSource(Source source) {
		sourceRepo.save(source);
		return sourceRepo.save(source);
	}

	@Override
	public List<Source> getAllSources() {
    	return sourceRepo.findAll();
	}

	@Override
	public List<Source> getSourcesByWebsite(String website) {
		return sourceRepo.findByWebsite(website);
	}

	@Override
	public Boolean deleteSourceById(Long id) {
		sourceRepo.delete(id);
		if(sourceRepo.findOne(id) != null)
			return false;
		else
			return true;
		}

}
