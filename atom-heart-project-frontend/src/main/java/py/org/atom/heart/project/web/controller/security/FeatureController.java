package py.org.atom.heart.project.web.controller.security;

import py.org.atom.heart.project.entity.security.SystemFeature;
import py.org.atom.heart.project.service.security.FeatureServiceBase;
import py.org.atom.heart.project.web.controller.ControllerBase;

/*
@Named("name")
@ConversationScoped
*/
public abstract class FeatureController<T extends SystemFeature, V extends FeatureServiceBase<T>> extends ControllerBase<T,V>{

	@Override
	protected abstract T newInstance();

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void searchAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearAction() {
		// TODO Auto-generated method stub
		
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
