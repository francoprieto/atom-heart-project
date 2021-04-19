package py.org.atom.heart.project.web.controller;

import py.org.atom.heart.project.FrontendBase;

public class Constants extends FrontendBase{

	public static final String FORM = "F";
	public static final String LIST = "L";
	public static final String VIEW = "V";
	public static final int DATA_TABLE_DEFAULT_PAGE_SIZE=15;
	public static final String BASE_QUERY="Select o From [entity] o";
	public static final String BASE_COUNT="Select count(o) From [entity] o";
	
}
