package py.org.atom.heart.project.entity.security;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import py.org.atom.heart.project.entity.InsertBase;

@MappedSuperclass
public class SystemUserRole extends InsertBase{
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
    @JoinColumn(name = "user_id")
	private SystemUser user;
	@ManyToOne
    @JoinColumn(name = "role_id")
	private SystemRole role;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SystemUser getUser() {
		return user;
	}
	public void setUser(SystemUser user) {
		this.user = user;
	}
	public SystemRole getRole() {
		return role;
	}
	public void setRole(SystemRole role) {
		this.role = role;
	}
}
