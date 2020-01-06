package py.org.atom.heart.project.init;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.reflections.Reflections;

import py.org.atom.heart.project.FrontendBase;
import py.org.atom.heart.project.entity.security.SystemFeature;
import py.org.atom.heart.project.entity.security.SystemMenu;
import py.org.atom.heart.project.service.ServiceBase;
import py.org.atom.heart.project.service.ServiceException;
import py.org.atom.heart.project.web.annotation.Feature;
import py.org.atom.heart.project.web.annotation.MenuItem;
import py.org.atom.heart.project.web.controller.ControllerBase;

/*
// Everytime the aplication is deployed, we scan every @Feature and @Menu annotations
// and then we need to persist this information in the database
//The class that extends this, should use these annotations:
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Startup
*/
public abstract class BootstrapBase<T extends ServiceBase, V extends ServiceBase> extends FrontendBase{
	
	protected T featureService; // The class that extends this shall inject the @EJB stateless 
	protected V menuService; // The class that extends this shall inject the @EJB stateless
	protected Class featureClass; // The class that extends this shall initiate this property in a @PostConstrut method
	protected Class menuClass; // The class that extends this shall initiate this property in a @PostConstrut method
	protected String featureEntityName; // The class that extends this shall initiate this property in a @PostConstrut method
	protected String menuEntityName; // The class that extends this shall initiate this property in a @PostConstrut method
	protected String controllerBasePackage; // The class that extends this shall initiate this property in a @PostConstrut method
	
	protected abstract Object newFeature(); // The class that extends this shall implement this method
	protected abstract Object newMenu(); // The class that extends this shall implement this method
	
	public void init() {
		if(this.featureService == null || this.featureEntityName == null || this.menuEntityName == null || this.menuService == null) return;
		
		Reflections reflections = new Reflections(this.controllerBasePackage);
		
		@SuppressWarnings("rawtypes")
		Set<Class<? extends ControllerBase>> subTypes = reflections.getSubTypesOf(ControllerBase.class);		
		List<Object> newFeatures = null;
		for(@SuppressWarnings("rawtypes") Class st : subTypes){
			for(Method m : st.getMethods()) {
				Annotation[] annotations = m.getAnnotationsByType(Feature.class);
	    		if(annotations.length > 0) {
	    			Feature o = (Feature) annotations[0];
	    			if(o != null) {
	    				if(newFeatures == null) newFeatures = new ArrayList<Object>();
	    				SystemFeature sf = (SystemFeature) this.newFeature();
	    				sf.setId(o.name());
	    				sf.setDescription(o.comments());
	    				newFeatures.add(sf);
	    			}
	    		}
			}
		}		
		
		List<Object> newMenus = null;
		for(@SuppressWarnings("rawtypes") Class st : subTypes){
    		@SuppressWarnings("unchecked")
			Annotation[] annotations = st.getAnnotationsByType(MenuItem.class);
    		if(annotations.length > 0) {
    			MenuItem o = (MenuItem) annotations[0];
    			if(o != null) {
    				if(newMenus == null) newMenus = new ArrayList<Object>();
    				SystemMenu sm = (SystemMenu) this.newMenu();
    				sm.setId(o.name());
    				sm.setParent(o.parent());
    				sm.setTitle(o.title());
    				sm.setUrl(o.url());
    				newMenus.add(sm);
    			}
    		}
		}		
		
		if(newFeatures != null && newFeatures.size() > 0) {
			List features = this.featureService.getList("Select o From " + this.featureEntityName + " o ", null, 0, 999999999);
			if(features != null) {
				try {
					for(Object o : features) this.featureService.remove(o);
					for(Object o : newFeatures) this.featureService.persist(o);
				}catch(Exception ex) {
					return;
				}
			}
		}
		if(newMenus != null && newMenus.size() > 0) {
			List menus = this.menuService.getList("Select o From " + this.menuEntityName + " o ", null, 0, 999999999);
			if(menus != null ) {
				try {
					for(Object o : menus) this.menuService.remove(o);
					for(Object o : newMenus) {
						if(o != null) {
							String parentId = ((SystemMenu) o).getParent();
							if(parentId == null) {
								String id = ((SystemMenu) o).getId();
								this.menuService.persist(o);
								this.persistChildren(id, menus);
							}
						}
					}
				}catch(Exception ex) {
					return;
				}
			}
		}
	}
	
	private void persistChildren(String parent, List menus) throws ServiceException {
		if(parent == null || menus == null) return;
		for(Object o : menus) {
			if(o != null) {
				SystemMenu sm = (SystemMenu) o;
				if(sm.getParent() != null && sm.getParent().trim().equals(parent.trim())) {
					String id = ((SystemMenu) o).getId();
					this.menuService.persist(o);
					this.persistChildren(id, menus);
				}
			}
		}
	}

}
