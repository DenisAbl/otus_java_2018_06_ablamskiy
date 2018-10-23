package otus.visitor;

import java.lang.reflect.Field;
import java.util.Map;

public interface Visitor {

    Map<Field, String> dispatch(Class clazz);
}
