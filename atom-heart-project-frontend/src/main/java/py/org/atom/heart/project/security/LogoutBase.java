package py.org.atom.heart.project.security;

import javax.faces.context.FacesContext;

import py.org.atom.heart.project.FrontendBase;

public class LogoutBase extends FrontendBase{
	
    public void logoutAction() {
    	FacesContext context = FacesContext.getCurrentInstance();
    	context.getExternalContext().invalidateSession();
    	return;
    }
}
