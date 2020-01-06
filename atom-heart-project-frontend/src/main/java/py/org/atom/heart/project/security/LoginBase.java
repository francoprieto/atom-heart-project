package py.org.atom.heart.project.security;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import py.org.atom.heart.project.FrontendBase;

public class LoginBase extends FrontendBase{
	
	@Inject
	protected SecurityContext securityContext;
	
	@NotNull
	protected String username;
	@NotNull
	protected String password;
	
	protected boolean continued = true;
	
	protected String failMessage = "";
	
	protected String loggedPage;
	protected String loginPage;
	
	public void loginAction() {
		FacesContext context = FacesContext.getCurrentInstance();
		AuthenticationStatus status = this.continueAuthentication(context.getExternalContext());
        switch (status) {
        case SEND_CONTINUE:
        	context.responseComplete();
            break;
        case SEND_FAILURE:
        	warn(this.failMessage);
            break;
        case SUCCESS:
        	try {
        		context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + this.loggedPage);
        	}catch(Exception ex) {
        		return;
        	}
        	break;
        case NOT_DONE:
        }
        return;
	}

    private AuthenticationStatus continueAuthentication(ExternalContext externalContext) {
        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(username, password))
        );
    }
    
    public String logoutAction() {
    	FacesContext context = FacesContext.getCurrentInstance();
    	context.getExternalContext().invalidateSession();
    	return this.loginPage;
    }    
    
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isContinued() {
		return continued;
	}



	public void setContinued(boolean continued) {
		this.continued = continued;
	}	
	
}
