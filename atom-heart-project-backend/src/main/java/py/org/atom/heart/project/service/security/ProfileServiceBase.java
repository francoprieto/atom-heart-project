package py.org.atom.heart.project.service.security;

import java.util.Set;

import py.org.atom.heart.project.entity.security.SystemProfile;
import py.org.atom.heart.project.service.ServiceBase;
import py.org.atom.heart.project.service.ServiceException;

//@Stateless
public class ProfileServiceBase<T extends SystemProfile> extends ServiceBase<T>{

	public T update(T o, String profileRoleClass, Set profileRoles) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			this.em.merge(o);
			String id = ((SystemProfile) o).getId();
			this.em.createQuery("Delete From " + profileRoleClass + " o Where o.profile.id = :id").setParameter("id", id).executeUpdate();
			if(profileRoles != null && profileRoles.size() > 0) {
				for(Object r : profileRoles) {
					this.em.persist(r);					
				}
			}
			this.em.flush();
			return o;
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	public T persist(T o, Set profileRoles) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			this.em.persist(o);
			if(profileRoles != null && profileRoles.size() > 0) {
				for(Object r : profileRoles) {
					this.em.persist(r);					
				}
			}
			this.em.flush();
			return o;
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	public void remove(T o, String featureClass, String profileRoleClass, Set profileRoles) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			String id = ((SystemProfile) o).getId();
			this.em.createQuery("Delete From " + profileRoleClass + " o Where o.profile.id = :id").setParameter("id", id).executeUpdate();
			this.em.createQuery("Delete From " + featureClass + " o Where o.id = :id").setParameter("id", id).executeUpdate();
			this.em.flush();
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}	
	
}
