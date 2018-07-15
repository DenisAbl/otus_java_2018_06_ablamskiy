import java.lang.reflect.InvocationTargetException;

public class ObjectFactory<T> {

    private Object objectType;
    private int numberOfObjects;
    private T[] objectsArray;


    public ObjectFactory(Object o, int numberOfObjects) {
        this.objectType = o;
        this.numberOfObjects = numberOfObjects;

    }

    public void createObjects(){

         for (int i = 0; i < numberOfObjects - 1; i++){


        }

    }
//
//    public void createReferences() {
//        this.objectsArray = new Object[numberOfObjects];
//    }
//
//    int getNumberOfObjects() {
//        return numberOfObjects;
//    }

}
