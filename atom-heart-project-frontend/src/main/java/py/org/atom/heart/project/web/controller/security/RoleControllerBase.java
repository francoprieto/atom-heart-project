package py.org.atom.heart.project.web.controller.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import py.org.atom.heart.project.entity.security.SystemRole;
import py.org.atom.heart.project.service.ServiceBase;
import py.org.atom.heart.project.service.ServiceException;
import py.org.atom.heart.project.service.security.RoleServiceBase;
import py.org.atom.heart.project.web.controller.Constants;
import py.org.atom.heart.project.web.controller.ControllerBase;
import py.org.atom.heart.project.web.controller.FilterField;
import py.org.atom.heart.project.web.controller.ListField;

/*
@Named("name")
@ConversationScoped
*/
@SuppressWarnings("rawtypes")
public abstract class RoleControllerBase<T extends SystemRole, V extends RoleServiceBase<T>> extends ControllerBase<T, V>{ 
	
	protected Class featureClazz;
	protected Class roleFeatureClazz;
	protected List allFeatures;
	protected List instanceFeatures;
	
	protected abstract T newInstance();
	protected abstract Set<Object> getRoleFeatures();
	public void init() {
		this.addFilter(new FilterField("Id " + this.labels.get("from"), "o.id", FilterField.GE, java.lang.Long.class, "id"));
		this.addFilter(new FilterField("Id " + this.labels.get("to"), "o.id", FilterField.LE, java.lang.Long.class, "id"));
		FilterField ff = new FilterField(this.labels.get("enabled"), "o.disableDate",FilterField.IS, java.lang.String.class,"enabled");
		ff.addOption(new SelectItem("NULL",this.labels.get("yes")));
		ff.addOption(new SelectItem("NOT NULL",this.labels.get("no")));
		this.addFilter(ff);
		this.addListField(new ListField("Id","id","id",true));
		this.addListField(new ListField(this.labels.get("enabled"),"disableDate","enabled",true));
		this.loadAllFeatures();
	}
	protected void loadAllFeatures() {
		List allFeatures = this.service.getList("Select o From " + this.featureClazz.getCanonicalName() + " o Order by o.id", null, 0, 999999999);
		if(allFeatures == null) return;
		this.allFeatures = new ArrayList();
		for(Object o : allFeatures) {
			this.allFeatures.add(o);
		}
	}
	public List getAllFeatures() {
		return allFeatures;
	}
	public void setAllFeatures(List allFeatures) {
		this.allFeatures = allFeatures;
	}
	public List getInstanceFeatures() {
		return instanceFeatures;
	}
	public void setInstanceFeatures(List instanceFeatures) {
		this.instanceFeatures = instanceFeatures;
	}
	
	protected void edit() {
		if(this.instance == null) return;
		this.screen = Constants.FORM;
	}
	
	protected void view() {
		if(this.instance == null) return;
		this.screen = Constants.VIEW;	
	}
	
	@Override
	protected void save() {
		if(this.instance == null) return;
		if(!this.validateSave()) return;
		RoleServiceBase sb = (RoleServiceBase) this.service;
		try {
			sb.persist(this.instance, this.getRoleFeatures());
		} catch (ServiceException e) {
			this.error(this.labels.get("saveError") + " " + e);
			return;
		}
		this.info(this.labels.get("saveInfo"));
		if(!this.saveAndStay) this.screen = Constants.LIST;
	}	
	
	@Override
	protected void update() {
		if(this.instance == null) return;
		if(!this.validateUpdate()) return;
		RoleServiceBase sb = (RoleServiceBase) this.service;
		try {
			sb.update(this.instance, roleFeatureClazz.getCanonicalName(), this.getRoleFeatures());
		} catch (ServiceException e) {
			this.error(this.labels.get("updateError") + " " + e);
			return;
		}
		this.info(this.labels.get("updateInfo"));
		if(!this.saveAndStay) this.screen = Constants.LIST;
	}	
	
	@Override
	protected void remove() {
		if(this.instance == null) return;
		if(!this.validateDelete()) return;
		RoleServiceBase sb = (RoleServiceBase) this.service;
		try {
			sb.remove(this.instance, clazz.getCanonicalName(), roleFeatureClazz.getCanonicalName(), this.getRoleFeatures());
		} catch (ServiceException e) {
			this.error(this.labels.get("deleteError") + " " + e);
			return;
		}
		this.info(this.labels.get("deleteInfo"));
		if(!this.saveAndStay) this.screen = Constants.LIST;
	}		
}
