import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MyTestFramework {

    private HashMap<Method,List<Annotation>> methodsAndAnnotations;
    private boolean hasTests = false;
    private boolean testDone = false;

    public MyTestFramework(){
    }

    public void runTests(Class type,String...args) {

        //Get a Map of Methods and corresponding Annotations
        methodsAndAnnotations = ReflectionHelper.getAnnotatedMethods(ReflectionHelper.instantiate(type, args));

        while(hasTests()) {
            //Create an instance of test class to invoke a methods
            Object instance = ReflectionHelper.instantiate(type, args);
            invokeMethodWithAnnotation(Before.class, instance);
            invokeMethodWithAnnotation(Test.class, instance);
            invokeMethodWithAnnotation(After.class, instance);
        }
    }

    private void invokeMethodWithAnnotation(Class annotationName, Object instance){
        testDone = false;
        Iterator<Map.Entry<Method,List<Annotation>>> iterator = methodsAndAnnotations.entrySet().iterator();
        while (iterator.hasNext() && !testDone) {
            Map.Entry<Method,List<Annotation>> entry = iterator.next();
            entry.getValue().forEach(annotation -> {
            if (annotationName.isInstance(annotation)) {
                    try {
                        entry.getKey().invoke(instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if(annotation instanceof Test){
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
                if(annotation instanceof Test){
                    this.hasTests = true;
                }
            }
        });
    return this.hasTests;}

}
