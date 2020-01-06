package py.org.atom.heart.project.entity.security;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import py.org.atom.heart.project.entity.InsertBase;

@MappedSuperclass
public class SystemRoleFeature<T extends SystemRole,V extends SystemFeature> extends InsertBase{
	@Id
	private long id;
	@ManyToOne
    @JoinColumn(name = "role_id")
	private T role;
	@ManyToOne
	@JoinColumn(name = "feature_id")
	private V feature;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public T getRole() {
		return role;
	}
	public void setRole(T role) {
		this.role = role;
	}
	public V getFeature() {
		return feature;
	}
	public void setFeature(V feature) {
		this.feature = feature;
	}
	@PrePersist
	public void prePersist() {
		this.id = (new Date()).getTime();
		this.setInsertDate(new Date());
	}
}
