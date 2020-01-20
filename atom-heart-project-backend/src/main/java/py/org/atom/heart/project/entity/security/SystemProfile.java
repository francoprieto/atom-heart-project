package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import py.org.atom.heart.project.entity.InsertUpdateDisableBase;

@MappedSuperclass
public class SystemProfile<T extends SystemUser,V extends SystemProfileRole> extends InsertUpdateDisableBase{
	@Id
	protected String id;
	@OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "profile_id")
	protected Set<T> users;
	@OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "profile_id")
	protected Set<V> profileRoles;
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
	public Set<T> getUsers() {
		return users;
	}
	public void setUsers(Set<T> users) {
		this.users = users;
	}
	public Set<V> getProfileRoles() {
		return profileRoles;
	}
	public void setProfileRoles(Set<V> profileRoles) {
		this.profileRoles = profileRoles;
	}
}
