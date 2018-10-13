import com.google.gson.Gson;
import org.junit.Assert;
import otus.*;
import org.junit.Test;
import java.util.*;

public class AsonTest {

    @Test
    public void commonObjectTest() throws IllegalAccessException {
        Ason ason = new Ason();
        Gson gson = new Gson();
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Stack<Long> stack = new Stack<>();
        list.add("First list String");
        list.add("Second list String");
        set.add("First set string");
        set.add("Second set string");
        stack.push(11111111111111L);
        stack.push(22222222222222L);
        TestClass testObject = new TestClass((byte)120,(byte)65,true,true,
                                            'h','H',(short)928,(short)25,5000,10000,
                                            50000000L,1000000000L,10.567f,1000.987f,
                                            76.56784958d,6794.3345967843d,"Something",
                                            new int[]{1,2,3,4,5} ,list,set,stack);

        String asonString = ason.toJson(testObject);
        TestClass testObject2 = gson.fromJson(asonString,TestClass.class);

        Assert.assertEquals("Common object test fails",testObject,testObject2);
    }

    @Test
    public void nestedListsTest(){
        List<List<Integer>> doubleList = new ArrayList<>();
        List<Integer> innerList1 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        List<Integer> innerList2 = new ArrayList<>(Arrays.asList(20,30,40,50));
        doubleList.add(innerList1);
        doubleList.add(innerList2);
        Object testObject1 = new TestClass2(doubleList);

        Ason ason = new Ason();
        Gson gson = new Gson();
        String asonString = ason.toJson(testObject1);
        TestClass2 testObject2 = gson.fromJson(asonString,TestClass2.class);

        Assert.assertEquals("Nested lists test failed",testObject1,testObject2);


    }

    @Test
    public void arrayTest(){
        int number = 0;
        Integer[] array = new Integer[10];
        for (int i = 0; i < array.length; i++){
            array[i] = number++;
            }
        TestClass3 object = new TestClass3(array);
        Ason ason = new Ason();
        Gson gson = new Gson();
        String asonString = ason.toJson(object);
        TestClass3 object2 = gson.fromJson(asonString,TestClass3.class);

        Assert.assertEquals("Array test failed",object,object2);

    }


}
