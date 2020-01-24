package py.org.atom.heart.project.web.controller;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import py.org.atom.heart.project.entity.security.SystemMenu;
import py.org.atom.heart.project.service.ServiceBase;

public class HomeControllerBase extends ControllerBase {
	
	protected MenuModel mainMenu;
	protected List menus;
	protected Class menuClazz;
	protected ExternalContext ctx;
	protected String accessPrefix;
	@Override
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.ctx = fc.getExternalContext();
		this.loadMenus();
	}
	protected void loadMenus() {
		ServiceBase sb = (ServiceBase) this.service;
		this.menus = sb.getList("Select o From " + this.menuClazz.getCanonicalName() + " o Order by o.index", null, 0, 999999999);
		if(this.mainMenu == null) this.mainMenu = new DefaultMenuModel();
		for(Object o : this.menus) {
			SystemMenu sm = (SystemMenu) o;
			if(sm.getParent().trim().equals("")) {
				DefaultSubMenu parentMenu = new DefaultSubMenu(sm.getTitle());
				parentMenu = this.loadChildren(parentMenu);
				if(parentMenu.getElementsCount() > 0) this.mainMenu.addElement(parentMenu);
			}
		}
	}
	protected DefaultSubMenu loadChildren(DefaultSubMenu parent) {
		if(parent == null) return null;
		for(Object o : this.menus) {
			SystemMenu sm = (SystemMenu) o;
			if(sm.getParent().trim().toLowerCase().equals(parent.getLabel().toLowerCase().trim())) {
				boolean isParent = false;
				for(Object op : this.menus) {
					SystemMenu psm = (SystemMenu) o;
					if(psm.getParent().equals(sm.getId())) {
						isParent = true;
						break;
					}
				}
				if(!isParent) {
					DefaultMenuItem item = new DefaultMenuItem(sm.getTitle());
					item.setAjax(false);
					item.setUrl(this.ctx.getApplicationContextPath() + "/" + sm.getUrl());
					if(this.ctx.isUserInRole("SU") || this.ctx.isUserInRole(sm.getId() + "." + this.accessPrefix))
						parent.addElement(item);
				}else {
					DefaultSubMenu subMenu = new DefaultSubMenu(sm.getTitle());
					parent = this.loadChildren(subMenu);
				}
			}
		}
		return parent;
	}
	public MenuModel getMainMenu() {
		return mainMenu;
	}
	public void setMainMenu(MenuModel mainMenu) {
		this.mainMenu = mainMenu;
	}

	@Override
	protected Object newInstance() { return null; }
	@Override
	public void searchAction() {}
	@Override
	public void viewAction() { }
	@Override
	public void clearAction() { }
	@Override
	public void removeAction() { }
	@Override
	public void editAction() { }
	@Override
	public void newAction() { }
	@Override
	public void updateAction() { }
	@Override
	public void saveAction() { }
	@Override
	protected boolean validateSave() { return false; }
	@Override
	protected boolean validateDelete() { return false; }
	@Override
	protected boolean validateUpdate() { return false; }
}
