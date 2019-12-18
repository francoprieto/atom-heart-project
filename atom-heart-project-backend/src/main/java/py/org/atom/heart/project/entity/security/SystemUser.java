package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import py.org.atom.heart.project.entity.InsertUpdateDisableBase;

@MappedSuperclass
public class SystemUser extends InsertUpdateDisableBase{
	@Id
	private String id;
	@OneToMany(mappedBy = "role")
	private Set<SystemUserRole> userRoles;	
	@ManyToOne
    @JoinColumn(name = "profile_id")	
	private SystemProfile profile;
	@Transient
	public boolean isActive() {
		if(this.getDisableDate() != null) return false; else return true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<SystemUserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<SystemUserRole> userRoles) {
		this.userRoles = userRoles;
	}
}
