package py.org.atom.heart.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@MappedSuperclass
public class InsertUpdateDisableBase extends InsertUpdateBase{
	
	@Column(name=DISABLE_USER)
	protected String disableUser;
	@Column(name=DISABLE_DATE)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date disableDate;
	@Transient
	private boolean enabled;
	public String getDisableUser() {
		return disableUser;
	}
	public void setDisableUser(String disableUser) {
		this.disableUser = disableUser;
	}
	public Date getDisableDate() {
		return disableDate;
	}
	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}
	public boolean isEnabled() {
		if(this.disableDate != null) this.enabled = false; else this.enabled = true;
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if(!this.enabled) {
			this.disableDate = new Date();
			this.disableUser = this.currentUser;
		}else {
			this.disableDate = null;
			this.disableUser = null;
		}
	}		
}
