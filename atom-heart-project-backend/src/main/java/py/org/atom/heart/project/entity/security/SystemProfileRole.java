package py.org.atom.heart.project.entity.security;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import py.org.atom.heart.project.entity.InsertBase;
import py.org.atom.heart.project.util.PersistenceUtil;

@MappedSuperclass
public class SystemProfileRole<T extends SystemProfile,V extends SystemRole> extends InsertBase{
	
	@Id
	private long id;
	@ManyToOne
    @JoinColumn(name = "profile_id")
	private T profile;
	@ManyToOne
    @JoinColumn(name = "role_id")	
	private V role;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public T getProfile() {
		return profile;
	}
	public void setProfile(T profile) {
		this.profile = profile;
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
