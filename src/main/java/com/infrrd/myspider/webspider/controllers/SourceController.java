package com.infrrd.myspider.webspider.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import org.springframework.web.bind.annotation.RequestParam;
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
		return new ResponseEntity<String>("WELCOME TO WEB SPIDER", HttpStatus.OK);
	}

	@GetMapping("/resources")
	public ResponseEntity<?> getResources() {
		LOG.info("inside /resources");
		return new ResponseEntity<List<Source>>(sourceService.getAllSources(), HttpStatus.OK);
	}

	@PostMapping("/resource")
	public ResponseEntity<?> addSource(@RequestBody Source source) {
		LOG.info("inside POST /resource" + source);
		return new ResponseEntity<Source>(sourceService.addSource(source), HttpStatus.OK);
	}

	@DeleteMapping("/resource/{sourceId}")
	public ResponseEntity<?> deleteSource(@PathVariable("sourceId") Long sourceId) {
		LOG.info("inside delete /resource" + sourceId);
		return new ResponseEntity<Boolean>(sourceService.deleteSourceById(sourceId), HttpStatus.OK);
	}

	@PostMapping("/occurence")
	public ResponseEntity<?> getOccurence(@RequestBody Source source) throws IOException {
		final Logger LOG = LoggerFactory.getLogger(SourceController.class);
		Map<String, Integer> linkCount = new HashMap<String, Integer>();

		Document doc = Jsoup.connect(source.getWebsite()).get();
		String str = doc.text();
		Elements links = doc.select("a");
		for (Element link : links) {
			LOG.info("URL list ==========\n\n" + link.attr("abs:href") + "\n" + link.text() + "\n\n");
		}
		LOG.info("Total number of links" + links.size());
		List<String> myList = Arrays.asList(source.getCsKeywords().split(","));

		for (String keyword : myList) {
			int count = 0;
			Pattern pattern = Pattern.compile("(?i)" + keyword);
			Matcher matcher = pattern.matcher(str);
			while (matcher.find())
				count++;

			linkCount.put(keyword, count);
		}
		return new ResponseEntity<Map<String, Integer>>(linkCount, HttpStatus.OK);
	}

	@GetMapping("/suburls")
	public ResponseEntity<?> getSubUrls(@RequestParam(value = "parentLink") String parentLink,
			@RequestParam(value = "level") Integer level) {
		Set<String> urlSet = new HashSet<>();
		Set<String> parentURLs = new HashSet<>();
		String website="";
		parentURLs.add(parentLink);
		Pattern pattern = Pattern.compile("(https?://)?(www|WWW).*?/");
        Matcher matcher=pattern.matcher(parentLink);		
    	if(matcher.find()) {
    	website=matcher.group();	
    	}
        
        int levelCount=1;
	    while (--level >= 0) {
			LOG.info("Finding URLs for level: " +(levelCount));
			Set<String> children = new HashSet<>();
			for (String parent : parentURLs) {
				Document doc = null;
				
				try {
					//LOG.info("Getting doc for "+parent );
					if(urlSet.contains(parent) || !Pattern.matches(".*"+website+".*", parent) ) {
						LOG.info("duplicate or other domain found "+parent);
						continue;
					}
					doc = Jsoup.connect(parent).ignoreContentType(true).timeout(0).get();
					//LOG.info("Got doc ");
					Elements links = doc.select("a");
					for (Element link : links) {
						children.add(link.attr("abs:href"));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOG.info("IO exeption+  \n"+e.getMessage());
				}

			}
			urlSet.addAll(parentURLs);
			parentURLs.clear();
			children.removeAll(new HashSet<String>(urlSet));
			parentURLs.addAll(children);
			LOG.info("Found "+children.size()+" URLs for level: " +(levelCount++));
			children.clear();
		}
		urlSet.addAll(parentURLs);
		
		LOG.info("\n Total URLs found--"+urlSet.size());
		return new ResponseEntity<Set<String>>(urlSet, HttpStatus.OK);
	}

}
