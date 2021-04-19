package py.org.atom.heart.project.web.controller.security;

import py.org.atom.heart.project.entity.security.SystemFeature;
import py.org.atom.heart.project.service.security.FeatureServiceBase;
import py.org.atom.heart.project.web.controller.ControllerBase;
import py.org.atom.heart.project.web.controller.FilterField;
import py.org.atom.heart.project.web.controller.ListField;


/*
@Named("name")
@ConversationScoped
*/
@SuppressWarnings("rawtypes")
public abstract class FeatureControllerBase<T extends SystemFeature, V extends FeatureServiceBase<T>> extends ControllerBase<T,V>{

	protected abstract T newInstance();

	public void init() {
		// Filter fields for the search option
		this.addFilter(new FilterField("Id", "o.id", FilterField.LIKE, java.lang.String.class, "id"));
		this.addFilter(new FilterField(this.labels.get("description"), "o.description", FilterField.LIKE, java.lang.String.class, "description"));	
		//List table fields
		this.addListField(new ListField("Id","id","id",true));
		this.addListField(new ListField(this.labels.get("description"),"description","description",true));
	}
	public void searchAction() {
		this.search();
	}
	public void clearAction() {
		this.clear();
	}
}
