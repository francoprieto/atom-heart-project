package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import py.org.atom.heart.project.entity.InsertUpdateDisableBase;

@MappedSuperclass
public class SystemUser<T extends SystemUserRole,V extends SystemProfile> extends InsertUpdateDisableBase{
	@Id
	private String id;
	@Column(name="su")
	private int su;
	@Column(name="password")
	private String password;
	@OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private Set<T> userRoles;
	@ManyToOne
    @JoinColumn(name="profile_id")	
	private V profile;
	@Transient
	private String genPassword;
	@Transient
	public boolean isActive() {
		if(this.getDisableDate() != null) return false; else return true;
	}
	@Transient
	private boolean superUser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<T> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<T> userRoles) {
		this.userRoles = userRoles;
	}
	public int getSu() {
		return su;
	}
	public void setSu(int su) {
		this.su = su;
	}
	public V getProfile() {
		return profile;
	}
	public void setProfile(V profile) {
		this.profile = profile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isSuperUser() {
		if(this.su == 1) this.superUser = true; else this.superUser = false;
		return superUser;
	}
	public void setSuperUser(boolean superUser) {
		if(superUser) this.su = 1; else this.su = 0;
		this.superUser = superUser;
	}
	public String getGenPassword() {
		return genPassword;
	}
	public void setGenPassword(String genPassword) {
		this.genPassword = genPassword;
	}	
}
