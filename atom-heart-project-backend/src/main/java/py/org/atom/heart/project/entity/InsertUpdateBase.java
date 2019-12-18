package py.org.atom.heart.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class InsertUpdateBase extends InsertBase{
	

	@Column(name=UPDATE_USER)
	private String updateUser;
	@Column(name=UPDATE_DATE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
