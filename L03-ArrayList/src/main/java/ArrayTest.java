import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class ArrayTest {
    public static void main(String[] args) {
//        List<Integer> integerList = new MyArrayList<>();
//        for (int i = 0; i < 40; i++) {
//            integerList.add(i);
//        }
        List<Integer> integerList = new MyArrayList<>();

        //добавляем в коллекцию элементы
        Collections.addAll(integerList,6,4,5,3,2000,-3234);

        //упорядочиваем по порядку
        Collections.sort(integerList, Comparator.naturalOrder());

        //создаем новый объект ArrayList в который копируем ранее созданный список.
        List<Integer> additionalArrayList = new ArrayList<>();
        Collections.addAll(additionalArrayList,-89,-9,-1000000,0,300,8);
        Collections.copy(additionalArrayList,integerList);
        additionalArrayList.forEach(System.out::println);

    }
}
