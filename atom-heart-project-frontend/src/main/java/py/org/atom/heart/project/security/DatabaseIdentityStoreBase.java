package py.org.atom.heart.project.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.PasswordHash;

import py.org.atom.heart.project.FrontendBase;
import py.org.atom.heart.project.entity.security.SystemFeature;
import py.org.atom.heart.project.entity.security.SystemProfile;
import py.org.atom.heart.project.entity.security.SystemProfileRole;
import py.org.atom.heart.project.entity.security.SystemRole;
import py.org.atom.heart.project.entity.security.SystemRoleFeature;
import py.org.atom.heart.project.entity.security.SystemUser;
import py.org.atom.heart.project.entity.security.SystemUserRole;
import py.org.atom.heart.project.service.ServiceBase;

//@ApplicationScoped // Anotate this as a ApplicationScoped bean
public class DatabaseIdentityStoreBase<T extends ServiceBase> extends FrontendBase implements IdentityStore{
	public static final String SALT="B3a7l35";
	protected T userService;
	protected Class userClazz;
	
    @Inject
    protected PasswordHash passwordHash;
    
    public CredentialValidationResult validate(Credential credential) {
    	String username = null;
    	SystemUser u = null;
    	Set<String> roles = null;
    	if(credential instanceof UsernamePasswordCredential || credential instanceof CallerOnlyCredential) {
    		username = ((UsernamePasswordCredential) credential).getCaller();
    		Object o = userService.getById(userClazz, username);
    		if(o == null) return CredentialValidationResult.INVALID_RESULT;
            u = (SystemUser) o;
            if(!u.isActive()) return CredentialValidationResult.INVALID_RESULT;
    	}
        if (credential instanceof UsernamePasswordCredential) {
            String password = ((UsernamePasswordCredential) credential).getPasswordAsString();
            Map<String, String> parameters = new HashMap<String,String>();
            parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
            parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
            parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
            passwordHash.initialize(parameters);
            String adminPwd = passwordHash.generate("admin".toCharArray());
            String generated = passwordHash.generate(password.toCharArray());
            if(!passwordHash.verify(password.toCharArray(), u.getPassword())) 
            	return CredentialValidationResult.INVALID_RESULT;
        }
        if(u.getSu() == 1) {
        	Set<String> su = new HashSet<String>();
        	su.add("SU");
        	su.add("USER");
        	return new CredentialValidationResult(u.getId(),su);
        }    		
        roles = this.getFeatures(u); 
        roles.add("USER");
        if (credential instanceof CallerOnlyCredential) {
            if(u != null) return new CredentialValidationResult(u.getId(),roles);
            else return CredentialValidationResult.INVALID_RESULT;
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
    protected Set<String> getFeatures(SystemUser u){
    	Set<String> sfs = null;
    	if(u.getProfile() != null) {
    		SystemProfile p = (SystemProfile) u.getProfile();
    		if(p.getProfileRoles() != null) {
    			Set<SystemProfileRole> prs = (Set<SystemProfileRole>) p.getProfileRoles();
    			Set<SystemRole> rs = null;
    			for(SystemProfileRole pr : prs) {
    				if(pr.getRole() != null && ((SystemRole) pr.getRole()).getDisableDate() == null) {
	    				if(rs == null) rs = new HashSet<SystemRole>();
	    				rs.add(pr.getRole());
    				}
    			}
    			sfs = this.getFeatures(rs);
    		}
    	}else {
	    	if(u.getUserRoles() == null) return null;
	    	Set<SystemUserRole> urs = u.getUserRoles();
	    	if(urs == null) return null;
	    	Set<SystemRole> rs = null;
	    	for(SystemUserRole ur : urs) {
	    		if(ur.getRole() != null && ((SystemRole) ur.getRole()).getDisableDate() == null) {
    				if(rs == null) rs = new HashSet<SystemRole>();
    				rs.add(ur.getRole());
	    		}
	    	}
	    	sfs = this.getFeatures(rs);
    	}
    	return sfs;
    }
    protected Set<String> getFeatures(Set<SystemRole> rs){
    	if(rs == null || rs.size() <= 0) return null;
    	Set<String> sfs = null;
    	for(SystemRole r : rs) {
			Set<SystemRoleFeature> rfs = r.getRoleFeatures();
			if(rfs != null) {
				for(SystemRoleFeature rf : rfs) {
					if(rf.getFeature() != null) {
						if(sfs == null) sfs = new HashSet<String>();
						sfs.add(((SystemFeature)rf.getFeature()).getId());
					}
				}
			}
    	}
    	return sfs;
    }
}
