package py.org.atom.heart.project.entity.security;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import py.org.atom.heart.project.entity.InsertBase;

@MappedSuperclass
public class SystemRoleFeature<T,V> extends InsertBase{
	@Id
	@GeneratedValue
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
}
