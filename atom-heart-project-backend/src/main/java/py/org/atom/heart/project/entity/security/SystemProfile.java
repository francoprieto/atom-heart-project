package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import py.org.atom.heart.project.entity.InsertUpdateDisableBase;

@MappedSuperclass
public class SystemProfile<T> extends InsertUpdateDisableBase{
	@Id
	private String id;
	@OneToMany(mappedBy = "profile")
	private Set<T> users;
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
}
