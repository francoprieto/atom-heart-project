package py.org.atom.heart.project.entity.security;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import py.org.atom.heart.project.entity.InsertBase;

@MappedSuperclass
public class SystemRoleFeature extends InsertBase{
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
    @JoinColumn(name = "role_id")
	private SystemRole role;
	@ManyToOne
	@JoinColumn(name = "feature_id")
	private SystemFeature feature;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SystemRole getRole() {
		return role;
	}
	public void setRole(SystemRole role) {
		this.role = role;
	}
	public SystemFeature getFeature() {
		return feature;
	}
	public void setFeature(SystemFeature feature) {
		this.feature = feature;
	}
}
