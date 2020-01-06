package py.org.atom.heart.project.web.controller;

public class ListField extends DataBase{
	private String title;
	private String property1;
	private String property2;
	private String property3;
	private String id;
	private boolean sort;
	private int size;
	public ListField(String title, String id, String property, boolean sort) {
		this.title = title;
		this.id = id;
		this.property1 = property;
		this.sort = sort;
	}
	public ListField(String title, String id, String property1, String property2, boolean sort) {
		this.title = title;
		this.id = id;
		this.property1 = property1;
		this.property2 = property2;
		this.sort = sort;
	}
	public ListField(String title, String id, String property1, String property2, String property3, boolean sort) {
		this.title = title;
		this.id = id;
		this.property1 = property1;
		this.property2 = property2;
		this.property3 = property3;
		this.sort = sort;
	}	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProperty1() {
		return property1;
	}
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	public String getProperty2() {
		return property2;
	}
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	public String getProperty3() {
		return property3;
	}
	public void setProperty3(String property3) {
		this.property3 = property3;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isSort() {
		return sort;
	}
	public void setSort(boolean sort) {
		this.sort = sort;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}	
}
