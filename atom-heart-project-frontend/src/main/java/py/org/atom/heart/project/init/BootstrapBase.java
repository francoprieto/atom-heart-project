package py.org.atom.heart.project.init;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import py.org.atom.heart.project.FrontendBase;
import py.org.atom.heart.project.entity.security.SystemFeature;
import py.org.atom.heart.project.entity.security.SystemMenu;
import py.org.atom.heart.project.service.ServiceBase;
import py.org.atom.heart.project.service.ServiceException;
import py.org.atom.heart.project.web.annotation.Feature;
import py.org.atom.heart.project.web.annotation.MenuItem;
import py.org.atom.heart.project.web.controller.ControllerBase;
import py.org.atom.heart.project.web.controller.security.FeatureControllerBase;
import py.org.atom.heart.project.web.controller.security.ProfileControllerBase;
import py.org.atom.heart.project.web.controller.security.RoleControllerBase;
import py.org.atom.heart.project.web.controller.security.UserControllerBase;

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
	protected Class roleFeature; // The class that extends this shall initiate this property in a @PostConstrut method
	protected Reflections reflections;
	protected abstract Object newFeature(); // The class that extends this shall implement this method
	protected abstract Object newMenu(); // The class that extends this shall implement this method
	
	protected List<Object> loadFeatures(List<Object> newFeatures, List<Class> subTypes){
		if(newFeatures == null) newFeatures = new ArrayList<Object>();
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
	    				sf.setInactive(0);
	    				newFeatures.add(sf);
	    			}
	    		}
			}
		}
		return newFeatures;
	}
	protected List<Object> loadMenus(List<Object> newMenus, List<Class> subTypes){
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
    				sm.setIndex(o.idx());
    				newMenus.add(sm);
    			}
    		}
		}
		return newMenus;
	}		
	public void init() {
		if(this.featureService == null || this.featureClass == null || this.menuClass == null || this.menuService == null) return;

		@SuppressWarnings("rawtypes")
		Set<Class<? extends ControllerBase>> subTypes = reflections.getSubTypesOf(ControllerBase.class);
		Set<Class<? extends FeatureControllerBase>> featuresTypes = reflections.getSubTypesOf(FeatureControllerBase.class);
		Set<Class<? extends RoleControllerBase>> roleTypes = reflections.getSubTypesOf(RoleControllerBase.class);
		Set<Class<? extends ProfileControllerBase>> profileTypes = reflections.getSubTypesOf(ProfileControllerBase.class);
		Set<Class<? extends UserControllerBase>> userTypes = reflections.getSubTypesOf(UserControllerBase.class);
		List<Class> classes = new ArrayList<Class>();
		for(Class c : subTypes) classes.add(c);
		for(Class c : featuresTypes) classes.add(c);
		for(Class c : roleTypes) classes.add(c);
		for(Class c : profileTypes) classes.add(c);
		for(Class c : userTypes) classes.add(c);
		
		List<Object> newFeatures = null;
		newFeatures = this.loadFeatures(newFeatures, classes);
		
		List<Object> newMenus = null;
		newMenus = this.loadMenus(newMenus, classes);
		
		
		if(newFeatures != null && newFeatures.size() > 0) {
			try {
				this.featureService.bulk("Update " + this.featureClass.getCanonicalName() + " Set inactive = 1 ", null);
			}catch(Exception ex) {
				return;
			}
			try {
				this.featureService.bulk("Delete " + this.featureClass.getCanonicalName() + " f Where f.id Not in (Select o.id From " + this.roleFeature.getCanonicalName() + " rf Inner Join rf.feature o)",null);
			}catch(Exception ex) {
				return;
			}
			for(Object o : newFeatures) {
				try {
					Map<String,Object> ps = new HashMap<String,Object>();
					ps.put("id", ((SystemFeature) o).getId());
					Object obj = this.featureService.getById(this.featureClass, ((SystemFeature) o).getId());
					if(obj != null) {
						this.featureService.bulk("Update " + this.featureClass.getCanonicalName() + " Set inactive = 0 Where id=:id ", ps);
					}else {
						this.featureService.persist(o);
					}
				}catch(Exception ex) {
					//
				}
			}
		}
		if(newMenus != null && newMenus.size() > 0) {
			try {
				this.menuService.bulk("Delete " + this.menuClass.getCanonicalName(), null);
			}catch(Exception ex) {
				//
			}
			try {
				for(Object m : newMenus) {
					SystemMenu sm = (SystemMenu) m;
					if(this.getParent(sm) != null) this.menuService.persist(m);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	protected SystemMenu getParent(SystemMenu sm) throws Exception{
		if(sm == null) return null;
		if(sm.getParent() == null) return null;
		SystemMenu parent = (SystemMenu) this.menuService.getById(this.menuClass, sm.getParent());
		if(parent == null) {
			Constructor ctor = this.menuClass.getConstructor();
			Object o = ctor.newInstance();
			SystemMenu p = (SystemMenu) o;
			p.setId(sm.getParent());
			p.setParent("");
			p.setTitle(sm.getParent().toUpperCase());
			p.setUrl("#");
			p.setIndex(0);
			o = p;
			this.menuService.persist(o);
			return p;
		}else return parent;
	}

}
