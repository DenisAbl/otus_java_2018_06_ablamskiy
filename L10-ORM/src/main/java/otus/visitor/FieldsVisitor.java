package otus.visitor;

import com.google.common.primitives.Primitives;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class FieldsVisitor implements Visitor {

    private Map<Field, String> columns;

    public void visitByte(Field field){
        addColumn(field,"TINYINT");
    }

    public void visitBoolean(Field field){
        addColumn(field,"BOOLEAN");
    }

    public void visitCharacter(Field field){
        addColumn(field,"CHAR");
    }

    public void visitShort(Field field){
        addColumn(field,"SMALLINT");
    }

    public void visitInteger(Field field){
        addColumn(field,"INT");
    }

    public void visitLong(Field field){
        addColumn(field,"BIGINT");
    }

    public void visitFloat(Field field){
        addColumn(field,"FLOAT");
    }

    public void visitDouble(Field field){
        addColumn(field,"DOUBLE");
    }

    public void visitString(Field field){
        addColumn(field,"varchar(255)");
    }

    private void visitObject(Object object, String fieldName){
        System.out.println("it's an unsupported object");
    }

    private void addColumn(Field field, String mySqlDataType){
        String column = String.format("%s %s, ", field.getName(), mySqlDataType);
        columns.put(field,column);
    }

    @Override
    public Map<Field, String> dispatch(Class clazz) {
        columns = new HashMap<>();
        List<Field> fields = getFieldsList(clazz);
        fields.forEach(field ->  {
            try {
                field.setAccessible(true);
                Method method = getMethod(field);
                method.invoke(this, field);
            } catch (Exception e) {
            }
        });
        return columns;
    }

    private Method getMethod(Field field){

        //Если поле примитивного типа, то оно оборачивается при помощи Google Guava,
        //чтобы использовался один метод и для примитивов, и для их соответсвующих оберток.

        Class tempClass = field.getType();
        if (tempClass.isPrimitive()){
            tempClass = Primitives.wrap(tempClass);
        }
        String methodName;
        Method method = null;

        //Попытка вызова нужного метода visit и дальнейший перебор суперклассов для вызова нужного метода
        while (method == null && tempClass != Object.class) {
            //todo: Попробовать найти более гибкое решение для массива
            if (field.getType().isArray()){
                try {
                    method = getClass().getMethod("visitArray", tempClass);
                } catch (NoSuchMethodException e) {}
                break;
            }
            try {
                methodName = tempClass.getSimpleName();
                methodName = "visit"+ methodName;
                method = getClass().getMethod(methodName, Field.class);
            } catch (NoSuchMethodException e) {
                tempClass = tempClass.getSuperclass();
            }
        }
        //Перебор интерфейсов для вызова нужного метода, если перебор по super не дал результатов
        if (tempClass == Object.class) {
            tempClass = field.getType();
            while (method == null && tempClass != Object.class) {
                Class[] interfaces = tempClass.getInterfaces();
                if (interfaces.length == 0) {
                    tempClass = tempClass.getSuperclass();
                    continue;
                }
                for (Class anInterface : interfaces) {
                    methodName = anInterface.getSimpleName();
                    methodName ="visit"+ methodName;
                    try {
                        method =  getClass().getMethod(methodName, field.getClass());
                        break;
                    } catch (NoSuchMethodException e) {
                        tempClass = tempClass.getSuperclass();
                    }
                }
            }
        }
        if (method == null) {
            try {
                method = getClass().getMethod("visitObject",field.getClass());
            } catch (Exception e) {
                // Can't happen
            }
        }
        return method;
    }

    private List<Field> getFieldsList(Class clazz){
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = clazz;
        while (tempClass!=null) {
            fieldList.addAll(Arrays.stream(tempClass.getDeclaredFields())
                            .filter(field -> !Modifier.isStatic(field.getModifiers()))
                            .collect(toList()));
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }
}
