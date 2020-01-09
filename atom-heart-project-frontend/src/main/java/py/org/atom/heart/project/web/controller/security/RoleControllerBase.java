package py.org.atom.heart.project.web.controller.security;

import javax.faces.model.SelectItem;

import py.org.atom.heart.project.entity.security.SystemRole;
import py.org.atom.heart.project.service.security.RoleServiceBase;
import py.org.atom.heart.project.web.controller.ControllerBase;
import py.org.atom.heart.project.web.controller.FilterField;
import py.org.atom.heart.project.web.controller.ListField;
import py.org.atom.heart.project.web.controller.ViewField;

/*
@Named("name")
@ConversationScoped
*/
@SuppressWarnings("rawtypes")
public abstract class RoleControllerBase<T extends SystemRole, V extends RoleServiceBase<T>> extends ControllerBase<T, V>{

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
		
		this.addViewField(this.labels.get("role-tab"), new ViewField("Id","id"));
	}

	@Override
	public void searchAction() {
		this.search();
	}

	@Override
	public void viewAction() {
	
	}

	@Override
	public void clearAction() {
		this.clear();
	}

	@Override
	public void removeAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAction() {
		// TODO Auto-generated method stub
		
	}

}
