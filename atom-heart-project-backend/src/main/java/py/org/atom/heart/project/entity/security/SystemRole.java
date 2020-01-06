package py.org.atom.heart.project.entity.security;

import java.util.Date;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import py.org.atom.heart.project.entity.InsertUpdateDisableBase;

@MappedSuperclass
public class SystemRole<T extends SystemUserRole,V extends SystemRoleFeature> extends InsertUpdateDisableBase{
	@Id
	private long id;
	@OneToMany(mappedBy = "user")
	private Set<T> userRoles;
	@OneToMany(mappedBy = "feature")
	private Set<V> roleFeatures;	
	@Transient
	public boolean isActive() {
		if(this.getDisableDate() != null) return false; else return true;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Set<T> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<T> userRoles) {
		this.userRoles = userRoles;
	}
	public Set<V> getRoleFeatures() {
		return roleFeatures;
	}
	public void setRoleFeatures(Set<V> roleFeatures) {
		this.roleFeatures = roleFeatures;
	}	
	@PrePersist
	public void prePersist() {
		this.id = (new Date()).getTime();
		this.setInsertDate(new Date());
	}	
}
