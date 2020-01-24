package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import py.org.atom.heart.project.entity.EntityBase;

@MappedSuperclass
public class SystemFeature<T extends SystemRoleFeature> extends EntityBase{
	@Id
	protected String id;
	@Column(name="description")
	private String description;
	@Column(name="inactive")
	private int inactive;
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name = "feature_id")
	private Set<T> roleFeatures;	
	@Transient
	private boolean active;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public int getInactive() {
		return inactive;
	}
	public void setInactive(int inactive) {
		this.inactive = inactive;
	}
	public Set<T> getRoleFeatures() {
		return roleFeatures;
	}
	public void setRoleFeatures(Set<T> roleFeatures) {
		this.roleFeatures = roleFeatures;
	}
	public boolean isActive() {
		if(this.inactive == 0) this.active = true; else this.active = false;
		return active;
	}
	public void setActive(boolean active) {
		if(active) this.inactive = 0; else this.inactive = 1;
		this.active = active;
	}	
}
