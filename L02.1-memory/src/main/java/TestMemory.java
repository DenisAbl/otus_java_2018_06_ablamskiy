import java.util.*;

public class TestMemory {


    public static void main(String[] args) throws InterruptedException {

        //Строка не из String Pool; Integer; Object; массив int

        MySizeOf.sizeOf(() -> new String(new byte[0]));
        MySizeOf.sizeOf(() -> new Integer(123));
        MySizeOf.sizeOf(() -> new Object());
        MySizeOf.sizeOf(() -> new int[0]);
        MySizeOf.sizeOf(() -> new int[10]);

        //Пустые контейнеры
        MySizeOf.sizeOf(ArrayList::new);
        MySizeOf.sizeOf(LinkedList::new);
        MySizeOf.sizeOf(HashMap::new);
        MySizeOf.sizeOf(Stack::new);

        //Коллекция из N элементов
        MySizeOf.sizeOf(()-> new  ArrayList<Integer>(10));


    }
}
