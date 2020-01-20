package py.org.atom.heart.project.entity.security;

import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import py.org.atom.heart.project.entity.InsertUpdateDisableBase;
import py.org.atom.heart.project.util.PersistenceUtil;

@MappedSuperclass
public class SystemRole<T extends SystemUserRole,V extends SystemRoleFeature> extends InsertUpdateDisableBase{
	@Id
	protected long id;
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Set<T> userRoles;
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Set<V> roleFeatures;	
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
		this.id = PersistenceUtil.getLongId();
		super.prePersist();
	}	
}
