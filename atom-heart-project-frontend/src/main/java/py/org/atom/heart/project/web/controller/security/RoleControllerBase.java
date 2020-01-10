package py.org.atom.heart.project.web.controller.security;

import java.util.List;

import javax.faces.model.SelectItem;

import py.org.atom.heart.project.entity.security.SystemFeature;
import py.org.atom.heart.project.entity.security.SystemRole;
import py.org.atom.heart.project.service.security.FeatureServiceBase;
import py.org.atom.heart.project.service.security.RoleServiceBase;
import py.org.atom.heart.project.web.controller.ControllerBase;
import py.org.atom.heart.project.web.controller.FilterField;
import py.org.atom.heart.project.web.controller.ListField;

/*
@Named("name")
@ConversationScoped
*/
@SuppressWarnings("rawtypes")
public abstract class RoleControllerBase<T extends SystemRole, V extends RoleServiceBase<T>> extends ControllerBase<T, V>{

	protected FeatureServiceBase<SystemFeature> fs; 
	
	protected List allFeatures;
	protected Class featureClazz;
	
	protected abstract T newInstance();
	public void init() {
		this.addFilter(new FilterField("Id " + this.labels.get("from"), "o.id", FilterField.GE, java.lang.Long.class, "id"));
		this.addFilter(new FilterField("Id " + this.labels.get("to"), "o.id", FilterField.LE, java.lang.Long.class, "id"));
		FilterField ff = new FilterField(this.labels.get("disabled"), "o.disableDate",FilterField.IS, java.lang.String.class,"disableDate");
		ff.addOption(new SelectItem("NOT NULL",this.labels.get("yes")));
		ff.addOption(new SelectItem("NULL",this.labels.get("no")));
		this.addFilter(ff);
		this.addListField(new ListField("Id","id","id",true));
		this.addListField(new ListField(this.labels.get("disabled"),"disableDate","disableDate",true));
		this.loadAllFeatures();
	}
	protected void loadAllFeatures() {
		this.allFeatures = this.fs.getList("Select o From " + this.featureClazz.getCanonicalName() + " o ", null, 0, 999999999);
	}
	public abstract void searchAction();
	public abstract void viewAction();
	public abstract void clearAction();
	public abstract void removeAction();
	public abstract void editAction();
	public abstract void newAction();
	public abstract void updateAction();
	public List getAllFeatures() {
		return allFeatures;
	}
	public void setAllFeatures(List allFeatures) {
		this.allFeatures = allFeatures;
	}
}
