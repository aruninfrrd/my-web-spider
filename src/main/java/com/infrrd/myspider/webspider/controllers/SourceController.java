package com.infrrd.myspider.webspider.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infrrd.myspider.webspider.entities.Source;
import com.infrrd.myspider.webspider.services.SourceService;

@CrossOrigin
@RestController
@RequestMapping("/mySpider")
public class SourceController {
	private static final Logger LOG = LoggerFactory.getLogger(SourceController.class);
	@Autowired
	SourceService sourceService;
	
	@GetMapping
	public ResponseEntity<?> homePage() {
		LOG.info("inside homepage");
		return new ResponseEntity<String>( "WELCOME TO WEB SPIDER", HttpStatus.OK );
	}
	
	@GetMapping("/resources")
	public ResponseEntity<?> getResources() {
		LOG.info("inside /resources");
		return new ResponseEntity<List<Source>>( sourceService.getAllSources(), HttpStatus.OK );
	}
	
	@PostMapping("/resource")
	public ResponseEntity<?> addSource( @RequestBody Source source){
		LOG.info("inside POST /resource" + source);
		return new ResponseEntity<Source>( sourceService.addSource(source), HttpStatus.OK );	
	}
	
	@DeleteMapping("/resource/{sourceId}")
	public ResponseEntity<?> deleteSource(@PathVariable ( "sourceId") Long sourceId){
		LOG.info("inside delete /resource" + sourceId);
		return new ResponseEntity<Boolean>( sourceService.deleteSourceById(sourceId), HttpStatus.OK );	
	}
}
