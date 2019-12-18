package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import py.org.atom.heart.project.entity.EntityBase;

@MappedSuperclass
public class SystemFeature extends EntityBase{
	@Id
	private String id;
	@Column(name="description")
	private String description;
	@OneToMany(mappedBy = "feature")
	private Set<SystemRoleFeature> roleFeatures;		
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
	public Set<SystemRoleFeature> getRoleFeatures() {
		return roleFeatures;
	}
	public void setRoleFeatures(Set<SystemRoleFeature> roleFeatures) {
		this.roleFeatures = roleFeatures;
	}		
}
