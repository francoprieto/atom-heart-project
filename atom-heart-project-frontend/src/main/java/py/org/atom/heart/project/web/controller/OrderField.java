package py.org.atom.heart.project.web.controller;

import java.io.Serializable;

import py.org.atom.heart.project.FrontendBase;

public class OrderField extends FrontendBase implements Serializable{

	private static final long serialVersionUID = 900448613081501447L;
	private String key;
	private boolean asc;
	
	public OrderField(String key, boolean asc) {
		this.key = key;
		this.asc = asc;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isAsc() {
		return asc;
	}
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
}
