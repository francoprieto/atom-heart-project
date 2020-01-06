package py.org.atom.heart.project.security;

import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import py.org.atom.heart.project.FrontendBase;
/*
@AutoApplySession
@LoginToContinue(loginPage = "login.xhtml", errorPage = "", useForwardToLogin = false)
@RememberMe(isRememberMeExpression = "httpMessageContext.authParameters.rememberMe", cookieMaxAgeSeconds = 60 * 60 * 24 * 14) // 14 days
@ApplicationScoped
 */
public class AuthenticationMecanismBase extends FrontendBase implements HttpAuthenticationMechanism{

	@Inject
	private IdentityStore identityStore;

	public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
		
		Credential credential = context.getAuthParameters().getCredential();
		if(credential != null)
			return context.notifyContainerAboutLogin(identityStore.validate(credential));
		else
			return context.doNothing();
	}
	
	
}
