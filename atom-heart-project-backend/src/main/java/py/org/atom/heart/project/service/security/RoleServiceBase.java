package py.org.atom.heart.project.service.security;

import java.util.Set;

import py.org.atom.heart.project.entity.security.SystemRole;
import py.org.atom.heart.project.service.ServiceBase;
import py.org.atom.heart.project.service.ServiceException;

//@Stateless
public class RoleServiceBase<T extends SystemRole> extends ServiceBase<T> {
	
	public T update(T o, String roleFeatureClass, Set roleFeatures) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			this.em.merge(o);
			long id = ((SystemRole) o).getId();
			this.em.createQuery("Delete From " + roleFeatureClass + " o Where o.role.id = :id").setParameter("id", id).executeUpdate();
			if(roleFeatures != null && roleFeatures.size() > 0) {
				for(Object f : roleFeatures) {
					this.em.persist(f);					
				}
			}
			this.em.flush();
			return o;
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	public T persist(T o, Set roleFeatures) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			this.em.persist(o);
			if(roleFeatures != null && roleFeatures.size() > 0) {
				for(Object f : roleFeatures) {
					this.em.persist(f);					
				}
			}
			this.em.flush();
			return o;
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	public void remove(T o, String roleClass, String roleFeatureClass, Set roleFeatures) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			long id = ((SystemRole) o).getId();
			this.em.createQuery("Delete From " + roleFeatureClass + " o Where o.role.id = :id").setParameter("id", id).executeUpdate();
			this.em.createQuery("Delete From " + roleClass + " o Where o.id = :id").setParameter("id", id).executeUpdate();
			this.em.flush();
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
}