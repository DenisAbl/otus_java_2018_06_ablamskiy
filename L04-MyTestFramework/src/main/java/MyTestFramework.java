import annotations.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MyTestFramework {

    private HashMap<Method,List<Annotation>> methodsAndAnnotations;
    private Object instance;
    private boolean hasTests = false;
    private boolean testDone = false;

    public MyTestFramework(){
    }

    public void runTests(Class type,String...args) {

        //Get a Map of Methods and corresponding Annotations
        methodsAndAnnotations = ReflectionHelper.getAnnotatedMethods(ReflectionHelper.instantiate(type, args));

        //Create an instance of test class to invoke a methods
        instance = ReflectionHelper.instantiate(type, args);

        while(hasTests()) {
            invokeMethodWithAnnotation("@annotations.Before()");
            invokeMethodWithAnnotation("@annotations.Test()");
            invokeMethodWithAnnotation("@annotations.After()");
        }
    }

    private void invokeMethodWithAnnotation(String annotationName){
        testDone = false;
        Iterator<Map.Entry<Method,List<Annotation>>> iterator = methodsAndAnnotations.entrySet().iterator();
        while (iterator.hasNext() && !testDone) {
            Map.Entry<Method,List<Annotation>> entry = iterator.next();
            entry.getValue().forEach(annotation -> {
            if (annotation.toString().equals(annotationName)) {
                    try {
                        entry.getKey().invoke(this.instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if(annotationName.equals("@annotations.Test()")){
                        iterator.remove();
                        testDone = true;
                    }
                }
            });
        }
    }

    private boolean hasTests(){
        this.hasTests = false;
        this.methodsAndAnnotations.forEach((method,annotationList)-> {
            for (Annotation annotation : annotationList) {
                if(annotation.toString().equals("@annotations.Test()")){
                    this.hasTests = true;
                }
            }
        });
    return this.hasTests;}

}
