package py.org.atom.heart.project.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) 
public @interface MenuItem{
	 int idx();
	 String name();
	 String parent();
	 String url();
	 String title();
}
