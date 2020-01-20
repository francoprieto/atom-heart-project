package py.org.atom.heart.project.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.PasswordHash;

import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

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
    
    public static String encode(String in) {
    	if(in == null) return null;
    	PasswordHash passwordHash = new Pbkdf2PasswordHashImpl();
        Map<String, String> parameters = new HashMap<String,String>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
        String generated = passwordHash.generate(in.toCharArray());   
        return generated;
    }
    
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
        if (credential instanceof UsernamePasswordCredential) {
            if(u != null) return new CredentialValidationResult(u.getId(),roles);
            else return CredentialValidationResult.INVALID_RESULT;
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
    
    protected Set<String> getFeatures(SystemUser u){
    	Set<String> sfs = null;
    	List objs = null;
    	if(u.getProfile() != null) {
    		SystemProfile p = (SystemProfile) u.getProfile();
    		String sql = null;
    		if(p.getProfileRoles() != null)
    			sql = "Select f From " + this.userClazz.getCanonicalName() 
    				+ " u Inner Join u.profile p Inner Join p.profileRoles pr Inner Join pr.role r Inner Join r.roleFeatures rf Inner Join rf.feature f "
    				+ " Where u.id = :id and p.disableDate is null and r.disableDate is null";
    		else
    			sql = "Select f From " + this.userClazz.getCanonicalName() 
					+ " u Inner Join u.userRoles ur Inner Join ur.role r Inner Join r.roleFeatures rf Inner Join rf.feature f "
					+ " Where u.id = :id and r.disableDate is null";
    		Map<String,Object> parms = new HashMap<String,Object>();
    		parms.put("id",u.getId());
    		objs = this.userService.getList(sql, parms, 0, 999999999);
    		for(Object o : objs) {
    			SystemFeature sf = (SystemFeature) o;
    			if(sfs == null) sfs = new HashSet<String>();
    			if(!sfs.contains(sf.getId())) sfs.add(sf.getId());
    		}
    	}
    	return sfs;
    }
}
