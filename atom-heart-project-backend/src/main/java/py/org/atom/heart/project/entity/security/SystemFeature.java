package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import py.org.atom.heart.project.entity.EntityBase;

@MappedSuperclass
public class SystemFeature<T extends SystemRoleFeature> extends EntityBase{
	@Id
	protected String id;
	@Column(name="description")
	private String description;
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name = "feature_id")
	private Set<T> roleFeatures;		
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
	public Set<T> getRoleFeatures() {
		return roleFeatures;
	}
	public void setRoleFeatures(Set<T> roleFeatures) {
		this.roleFeatures = roleFeatures;
	}		
}
