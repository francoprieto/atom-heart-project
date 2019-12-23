package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.Column;
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
	@Column(name="su")
	private int su;
	@Column(name="password")
	private String password;
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
	public int getSu() {
		return su;
	}
	public void setSu(int su) {
		this.su = su;
	}
	public SystemProfile getProfile() {
		return profile;
	}
	public void setProfile(SystemProfile profile) {
		this.profile = profile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}		
}
