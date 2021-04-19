package py.org.atom.heart.project.entity.security;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import py.org.atom.heart.project.entity.InsertBase;
import py.org.atom.heart.project.util.PersistenceUtil;

@MappedSuperclass
public class SystemUserRole<T extends SystemUser,V extends SystemRole> extends InsertBase{
	@Id
	private long id;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	private T user;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="role_id")
	private V role;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public T getUser() {
		return user;
	}
	public void setUser(T user) {
		this.user = user;
	}
	public V getRole() {
		return role;
	}
	public void setRole(V role) {
		this.role = role;
	}
	@PrePersist
	public void prePersist() {
		this.id = PersistenceUtil.getLongId();
		this.setInsertDate(new Date());
	}	
}
