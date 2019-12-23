package py.org.atom.heart.project.web.controller;

import py.org.atom.heart.project.entity.security.SystemUser;
import py.org.atom.heart.project.service.ServiceBase;

public class SessionControllerBase<T,V> extends ControllerBase<T, V>{

	protected SystemUser login(String user, String password) {
		if(user == null || password == null || user.trim().length() <= 0 || password.trim().length()<=0)
			return null;
		ServiceBase<T> srv = (ServiceBase<T>) this.service;
		//SystemUser out = (SystemUser) srv.getById(T.class, user);
		return null;
	}
	
	@Override
	protected T newInstance() {
		return null;
	}

	@Override
	public void init() {
	}

	@Override
	public void searchAction() {
	}

	@Override
	public void clearAction() {
	}

	@Override
	public void removeAction() {
	}

	@Override
	public void editAction() {
	}

	@Override
	public void newAction() {
	}

	@Override
	public void updateAction() {
	}

}
