package py.org.atom.heart.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@MappedSuperclass
public class InsertBase extends EntityBase{
	@Column(name=INSERT_USER)
	protected String insertUser;
	@Column(name=INSERT_DATE)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date insertDate;
	public String getInsertUser() {
		return insertUser;
	}
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	@PrePersist
	public void prePersist() {
		if(this.insertDate == null) this.insertDate = new Date();
	}
}
