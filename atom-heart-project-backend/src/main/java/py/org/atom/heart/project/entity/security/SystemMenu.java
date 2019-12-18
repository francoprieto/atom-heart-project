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
	@Column(name="uri",nullable=false)
	private String uri;
	@Column(name="title",nullable=false)
	private String title;
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
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
