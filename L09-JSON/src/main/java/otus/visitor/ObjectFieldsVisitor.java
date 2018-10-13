package otus.visitor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ObjectFieldsVisitor implements Visitor {

    public void visit(Byte byteField, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,byteField);
    }

    public void visit(Boolean booleanField, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,booleanField);
    }

    public void visit(Character character, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,character);
    }

    public void visit(Short shortField, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,shortField);
    }

    public void visit(Integer integer, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,integer);
    }

    public void visit(Long longField, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,longField);
    }

    public void visit(Float floatField, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,floatField);
    }

    public void visit(Double doubleField, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,doubleField);
    }

    public void visit(String string, String fieldName, JSONObject jsonObject){
        jsonObject.put(fieldName,string);
    }

    public void visit(List list, String fieldName, JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        jsonObject.put(fieldName,jsonArray);
    }
    public void visit(Set set, String fieldName, JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(set);
        jsonObject.put(fieldName,jsonArray);
    }
    public void visit(Queue queue, String fieldName, JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(queue);
        jsonObject.put(fieldName,jsonArray);
    }

    public void visitArray(Object array, String fieldName, JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {

                jsonArray.add(Array.get(array, i));
            }
        jsonObject.put(fieldName,jsonArray);
    }
    public void visitObject(Object object, String fieldName, JSONObject jsonObject){
       jsonObject.put(fieldName,dispatch(object));

    }

    @Override
    public JSONObject dispatch(Object object) {
        JSONObject jsonObject = new JSONObject();
        List<Field> fields = getFieldsList(object);
        fields.forEach(field ->  {
            try {
                field.setAccessible(true);
                Method method = getMethod(field,object);
                method.invoke(this, field.get(object),field.getName(),jsonObject);
            } catch (Exception e) {
            }
        });
        return jsonObject;
    }

    protected Method getMethod(Field field, Object object) throws IllegalAccessException {

        Class tempClass = field.get(object).getClass();
        Method method = null;

        //Попытка вызова нужного метода visit и дальнейший перебор суперклассов для вызова нужного метода
        while (method == null && tempClass != Object.class) {
            String methodName = "visit";

            //todo: Попробовать найти более гибкое решение для массива
            if (field.get(object).getClass().isArray()){
                try {
                    method = getClass().getMethod("visitArray", Object.class, String.class, JSONObject.class);
                } catch (NoSuchMethodException e) {}
                break;
            }
            try {
                method = getClass().getMethod(methodName, tempClass, String.class,JSONObject.class);
            } catch (NoSuchMethodException e) {
                tempClass = tempClass.getSuperclass();
            }
        }
        //Перебор интерфейсов для вызова нужного метода, если перебор по super не дал результатов
        if (tempClass == Object.class) {
            tempClass = field.get(object).getClass();
            while (method == null && tempClass != Object.class) {
                Class[] interfaces = tempClass.getInterfaces();
                if (interfaces.length == 0) {
                    tempClass = tempClass.getSuperclass();
                    continue;
                }
                for (Class anInterface : interfaces) {
                    String methodName = "visit";
                    try {
                        method = getClass().getMethod(methodName, anInterface, String.class, JSONObject.class);
                        break;
                    } catch (NoSuchMethodException e) {
                        tempClass = tempClass.getSuperclass();
                    }
                }
            }
        }
        if (method == null) {
            try {
                method = getClass().getMethod("visitObject", Object.class, String.class, JSONObject.class);
            } catch (Exception e) {
                // Can't happen
            }
        }
        return method;
    }

    private List<Field> getFieldsList(Object object){
        List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field->!Modifier.isStatic(field.getModifiers()))
                .filter(field->!Modifier.isTransient(field.getModifiers()))
                .collect(toList());
        return  fields;

    }
}
