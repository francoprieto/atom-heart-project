package py.org.atom.heart.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class InsertUpdateDisableBase extends InsertUpdateBase{
	
	@Column(name=DISABLE_USER)
	private String disableUser;
	@Column(name=DISABLE_DATE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date disableDate;
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
	
}
