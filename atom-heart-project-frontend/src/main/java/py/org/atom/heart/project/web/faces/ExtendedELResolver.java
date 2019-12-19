package py.org.atom.hear.project.web.faces;

import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javax.el.BeanELResolver;
import javax.el.ELContext;

public class ExtendedELResolver extends BeanELResolver {

    private static final String PRIMEFACES_RESOURCE_PREFIX = "primefaces:";
    private static final String RESOURCES_HANDLER = "class org.omnifaces.resourcehandler.GraphicResourceHandler";
    private static final String PF_RESOURCES_HANDLER = "class org.primefaces.application.resource.PrimeResourceHandler";
    private static final String PRIMEFACES_EXT_RESOURCES_HANDLER = "class org.primefaces.extensions.application.PrimeFacesExtensionsResourceHandler";

    @Override
    public Object getValue(ELContext context, Object base, Object property) {

            if (property == null || base == null || base instanceof ResourceBundle || base instanceof Map || base instanceof Collection
                    || property.toString().startsWith(PRIMEFACES_RESOURCE_PREFIX) || base.getClass().toString().equals(RESOURCES_HANDLER)
                    || base.getClass().toString().equals(PRIMEFACES_EXT_RESOURCES_HANDLER) || base.getClass().toString().equals(PF_RESOURCES_HANDLER)) {
                return null;
            }
            String propertyString = property.toString();
            if (propertyString.contains(".")) {
                Object value = base;

                for (String propertyPart : propertyString.split("\\.")) {
                    value = super.getValue(context, value, propertyPart);
                }

                return value;
            } else {
                Object v = super.getValue(context, base, property);
               return v;
            }
    }
    
    @Override
    public void setValue(ELContext context, Object base, Object property, Object val) {
        if (base != null && !(base instanceof ResourceBundle) && !(base instanceof Map) && !(base instanceof Collection))
            super.setValue(context, base, property, val);
    }

}
