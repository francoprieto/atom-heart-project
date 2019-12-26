package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import py.org.atom.heart.project.entity.InsertUpdateDisableBase;

@MappedSuperclass
public class SystemRole<T,V> extends InsertUpdateDisableBase{
	@Id
	@GeneratedValue
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
}
