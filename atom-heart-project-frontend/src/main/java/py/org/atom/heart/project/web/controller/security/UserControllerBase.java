package py.org.atom.heart.project.web.controller.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import py.org.atom.heart.project.entity.security.SystemRole;
import py.org.atom.heart.project.entity.security.SystemUser;
import py.org.atom.heart.project.service.ServiceException;
import py.org.atom.heart.project.service.security.RoleServiceBase;
import py.org.atom.heart.project.service.security.UserServiceBase;
import py.org.atom.heart.project.web.controller.Constants;
import py.org.atom.heart.project.web.controller.ControllerBase;
import py.org.atom.heart.project.web.controller.FilterField;
import py.org.atom.heart.project.web.controller.ListField;

public abstract class UserControllerBase<T extends SystemUser, V extends UserServiceBase> extends ControllerBase<T, V>{
	protected Class roleClazz;
	protected Class profileClazz;
	protected Class userRoleClazz;
	protected List allRoles;
	protected Set instanceRoles;
	protected List allProfiles;
	protected abstract T newInstance();
	protected abstract Set<Object> getUserRoles();
	public void init() {
		this.addFilter(new FilterField("Id", "o.id", FilterField.LIKE, java.lang.String.class, "id"));
		FilterField ff = new FilterField(this.labels.get("enabled"), "o.disableDate",FilterField.IS, java.lang.String.class,"enabled");
		ff.addOption(new SelectItem("NULL",this.labels.get("yes")));
		ff.addOption(new SelectItem("NOT NULL",this.labels.get("no")));
		this.addFilter(ff);	
		this.addListField(new ListField("Id","id","id",true));
		this.addListField(new ListField(this.labels.get("enabled"),"disableDate","enabled",true));
		this.loadAllRoles();
		this.loadAllProfile();
	}
	protected void loadAllRoles() {
		List allRoles = this.service.getList("Select o From " + this.roleClazz.getCanonicalName() + " o Order by o.id", null, 0, 999999999);
		if(allRoles == null) return;
		this.allRoles = new ArrayList();
		for(Object o : allRoles) {
			this.allRoles.add(o);
		}		
	}
	protected void loadAllProfile() {
		List allProfiles = this.service.getList("Select o From " + this.profileClazz.getCanonicalName() + " o Order by o.id", null, 0, 999999999);
		if(allProfiles == null) return;
		this.allProfiles = new ArrayList();
		for(Object o : allProfiles) {
			this.allProfiles.add(o);
		}		
	}	
	public List getAllRoles() {
		return allRoles;
	}
	public void setAllRoles(List allRoles) {
		this.allRoles = allRoles;
	}
	public List getAllProfiles() {
		return allProfiles;
	}
	public void setAllProfiles(List allProfiles) {
		this.allProfiles = allProfiles;
	}
	public Set getInstanceRoles() {
		return instanceRoles;
	}
	public void setInstanceRoles(Set instanceRoles) {
		this.instanceRoles = instanceRoles;
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
	protected void update() {
		if(this.instance == null) return;
		if(!this.validateUpdate()) return;
		UserServiceBase sb = (UserServiceBase) this.service;
		try {
			sb.update(this.instance, userRoleClazz.getCanonicalName(), this.getUserRoles());
		} catch (ServiceException e) {
			this.error(this.labels.get("updateError") + " " + e);
			return;
		}
		this.info(this.labels.get("updateInfo"));
		if(!this.saveAndStay) this.screen = Constants.LIST;
	}	
	
	@Override
	protected void save() {
		if(this.instance == null) return;
		if(!this.validateSave()) return;
		UserServiceBase sb = (UserServiceBase) this.service;
		try {
			sb.persist(this.instance, this.getInstanceRoles());
		} catch (ServiceException e) {
			this.error(this.labels.get("saveError") + " " + e);
			return;
		}
		this.info(this.labels.get("saveInfo"));
		if(!this.saveAndStay) this.screen = Constants.LIST;
	}	

	@Override
	protected void remove() {
		if(this.instance == null) return;
		if(!this.validateDelete()) return;
		UserServiceBase sb = (UserServiceBase) this.service;
		try {
			sb.remove(this.instance, clazz.getCanonicalName(), userRoleClazz.getCanonicalName());
		} catch (ServiceException e) {
			this.error(this.labels.get("deleteError") + " " + e);
			return;
		}
		this.info(this.labels.get("deleteInfo"));
		if(!this.saveAndStay) this.screen = Constants.LIST;
	}	
	
}
