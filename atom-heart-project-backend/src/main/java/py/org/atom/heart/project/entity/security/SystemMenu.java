package py.org.atom.heart.project.entity.security;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import py.org.atom.heart.project.entity.EntityBase;

@MappedSuperclass
public class SystemMenu extends EntityBase{
	@Id
	private String id;
	@Column(name="parent",nullable=false)
	private String parent;
	@Column(name="url",nullable=false)
	private String url;
	@Column(name="title",nullable=false)
	private String title;
	@Column(name="_index",nullable=false)
	private int index;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}	
}
