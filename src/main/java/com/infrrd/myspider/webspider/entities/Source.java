package com.infrrd.myspider.webspider.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SOURCE")
public class Source implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2690486855803725254L;
	@Id
	@GeneratedValue
	@Column(name="source_id")
	Long sourceId;
	
	@Column(name="website")
	String website;
	
	@Column(name="cs_keywords")
	String csKeywords;

	@Column(name="level")
	int level;

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCsKeywords() {
		return csKeywords;
	}

	public void setCsKeywords(String csKeywords) {
		this.csKeywords = csKeywords;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Source [sourceId=" + sourceId + ", website=" + website + ", csKeywords=" + csKeywords + ", level="
				+ level + "]";
	}
		
}

