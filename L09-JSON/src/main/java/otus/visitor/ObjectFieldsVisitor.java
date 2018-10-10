package otus.visitor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

public class ObjectFieldsVisitor implements Visitor {

    public String visit(Byte byteField){
        return byteField.toString();
    }

    public String visit(Boolean booleanField){
        return booleanField.toString();}

    public String visit(Character character){return character.toString();}

    public String visit(Short shortField){return shortField.toString();}

    public String visit(Integer integer){
        return integer.toString();
    }

    public String visit(Long longField){
        return longField.toString();
    }

    public String visit(Float floatField){return floatField.toString();}

    public String visit(Double doubleField){return doubleField.toString();}

    public String visit(String string){
        return string;
    }

    public String visit(Collection collection){
        return collection.toString();
    }

    public void visitObject(Object object){
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields){
        dispatch(field);}

    }

    @Override
    public String dispatch(Object object) {
        String result = "";
        try {
            Method method = getMethod(object.getClass());
            result = (String) method.invoke(this, object);
            return result;


        } catch (Exception e) { }
        return result;
    }

    protected Method getMethod(Class clazz) {
        Class tempClass = clazz;
        Method method = null;

        //Superclasses
        while (method == null && tempClass != Object.class) {
            String methodName = "visit";
            try {
                method = getClass().getMethod(methodName, tempClass);
            } catch (NoSuchMethodException e) {
                tempClass = tempClass.getSuperclass();
            }
        }
        //Interfaces
        if (tempClass == Object.class) {
            Class[] interfaces = clazz.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                String methodName = "visit";
                try {
                    method = getClass().getMethod(methodName, interfaces[i]);
                } catch (NoSuchMethodException e) {}
            }
        }
        if (method == null) {
            try {
                method = getClass().getMethod("visitObject", clazz);
            } catch (Exception e) {
                // Can't happen
            }
        }
        return method;
    }
}
