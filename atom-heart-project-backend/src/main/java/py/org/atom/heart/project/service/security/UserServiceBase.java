package py.org.atom.heart.project.service.security;

import java.util.Set;

import py.org.atom.heart.project.entity.security.SystemUser;
import py.org.atom.heart.project.service.ServiceBase;
import py.org.atom.heart.project.service.ServiceException;

//@Stateless
public class UserServiceBase<T extends SystemUser> extends ServiceBase<T> {
	public T persist(T o, Set userRoles) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			boolean needsRoles = false;
			if(o.getProfile() != null) needsRoles = true;
			this.em.persist(o);
			if(userRoles != null && userRoles.size() > 0 && needsRoles) {
				for(Object f : userRoles) {
					this.em.persist(f);					
				}
			}
			this.em.flush();
			return o;
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	public T update(T o, String userRoleClass, Set userRoles) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			boolean needsRoles = false;
			if(o.getProfile() == null) needsRoles = true;
			this.em.merge(o);
			String id = ((SystemUser) o).getId();
			this.em.createQuery("Delete From " + userRoleClass + " o Where o.user.id = :id").setParameter("id", id).executeUpdate();
			if(userRoles != null && userRoles.size() > 0 && needsRoles) {
				for(Object f : userRoles) {
					this.em.persist(f);					
				}
			}
			this.em.flush();
			return o;
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}	
	public void remove(T o, String userClass, String userRoleFeatureClass) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			String id = ((SystemUser) o).getId();
			this.em.createQuery("Delete From " + userRoleFeatureClass + " o Where o.role.id = :id").setParameter("id", id).executeUpdate();
			this.em.createQuery("Delete From " + userClass + " o Where o.id = :id").setParameter("id", id).executeUpdate();
			this.em.flush();
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}	
}
