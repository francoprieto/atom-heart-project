package py.org.atom.heart.project.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.org.atom.heart.project.BackendBase;

// @Stateless // Annotate this as a Stateless enterprise java bean
public class ServiceBase<T> extends BackendBase {
	
	@PersistenceContext
	protected EntityManager em;
	
	public Long count(String query, Map<String,Object> parms){
		if(query == null || parms == null) return null;
		Query q = this.em.createQuery(query);
		for(String key : parms.keySet()){
			q.setParameter(key, parms.get(key));
		}
		return (Long) q.getSingleResult();
	} 
	
	public List<T> getList(String query, Map<String,Object> parms, int page, int rowsXpage) {
		Query q = this.em.createQuery(query);
		if(parms != null) {
			for(String k : parms.keySet()) q.setParameter(k, parms.get(k));
		}
		if(rowsXpage > 0){
			q.setMaxResults(rowsXpage);
			q.setFirstResult(page);
		}
		@SuppressWarnings("unchecked")
		List<T> out = q.getResultList();
		return out;
	}	
	
	public void bulk(String hql, Map<String,Object> params) throws ServiceException{
		if(hql == null || hql.trim().equals("") ) return;
		try {
			Query q = this.em.createQuery(hql);
			if(params != null && params.size() > 0) {
				for(String k : params.keySet()) q.setParameter(k, params.get(k));
			}
			q.executeUpdate();
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T getById(Class c, Object id) {
		Object o = this.em.find(c, id);
		if(o == null) return null;
		return (T) o;
	}
	@SuppressWarnings("rawtypes")
	public Object getIdValue(T in) {
		if(in == null) return null;
		try {
			Class c = in.getClass();
			Field[] fields = c.getDeclaredFields();
			if(fields == null || fields.length <= 0 ) return null;
			for(Field field : fields) {
				if(field.getAnnotationsByType(Id.class).length > 0) {
					String attr = field.getName();
					attr = "get" + attr.toLowerCase();
					Method[] methods = c.getMethods();
					if(methods == null || methods.length <= 0) return null;
					for(Method m : methods) {
						if(attr.trim().equals(m.getName().toLowerCase())) {
							return m.invoke(in, null);
						}
					}
				}
			}
		}catch(Exception ex) {
			return null;
		}
		return null;
	}
	
	public T update(T o) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			this.em.merge(o);
			this.em.flush();
			return o;
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	public T persist(T o) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			this.em.persist(o);
			this.em.flush();
			return o;
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}	
	
	public void remove(T o) throws ServiceException{
		if(o == null) throw new ServiceException("Empty data");
		try {
			o = this.em.merge(o);
			this.em.remove(o);
			this.em.flush();
		}catch(Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T fetchGraph(Object id, @SuppressWarnings("rawtypes") Class c, String graphName) {
		if(id == null || graphName == null || c == null) return null;
		@SuppressWarnings("rawtypes")
		EntityGraph entityGraph = this.em.getEntityGraph(graphName);
		Map<String, Object> properties = new HashMap<String,Object>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		Object out = this.em.find(c, id, properties);
		if(out == null) return null;
		return (T) out;
	}	
	public T fetchGraph(Object id, @SuppressWarnings("rawtypes") Class c, String graphName, String attributeNodes) {
		if(id == null || graphName == null || c == null) return null;
		@SuppressWarnings("rawtypes")
		EntityGraph entityGraph = this.em.getEntityGraph(graphName);
		if(attributeNodes != null && attributeNodes.trim().length() > 0) entityGraph.addAttributeNodes(attributeNodes);
		Map<String, Object> properties = new HashMap<String,Object>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		Object out = this.em.find(c, id, properties);
		if(out == null) return null;
		return (T) out;
	}
}
