package py.org.atom.heart.project.entity;

import javax.persistence.Transient;

import py.org.atom.heart.project.BackendBase;

public abstract class EntityBase extends BackendBase{

	public static final String INSERT_USER="insert_user";
	public static final String INSERT_DATE="insert_date";
	public static final String UPDATE_USER="update_user";
	public static final String UPDATE_DATE="update_date";
	public static final String DISABLE_USER="disable_user";
	public static final String DISABLE_DATE="disable_date";
	@Transient
	private boolean selected;
	@Transient
	protected String currentUser;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
}
