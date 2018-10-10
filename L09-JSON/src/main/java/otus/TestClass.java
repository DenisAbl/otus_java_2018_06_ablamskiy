package otus;

import java.util.*;

public class TestClass {

    byte firstByte;
    Byte secondByte;
    boolean firstBoolean;
    Boolean secondBoolean;
    char firstChar;
    char secondChar;
    short firstShort;
    Short secondShort;
    int firstInt;
    Integer secondInteger;
    long firstLong;
    Long secondLong;
    float firstFloat;
    Float secondFloat;
    double firstDouble;
    Double secondDouble;
    String firstString;
    Object object;

    int[] array;

    List<String> list;
    Set<String> set;
    Stack<Long> stack;

    public TestClass(byte firstByte, Byte secondByte, boolean firstBoolean, Boolean secondBoolean, char firstChar,
                     char secondChar, short firstShort, Short secondShort, int firstInt, Integer secondInteger,
                     long firstLong, Long secondLong, float firstFloat, Float secondFloat, double firstDouble,
                     Double secondDouble, String firstString, Object object, int[] array, List<String> list,
                     Set<String> set, Stack<Long> stack) {
        this.firstByte = firstByte;
        this.secondByte = secondByte;
        this.firstBoolean = firstBoolean;
        this.secondBoolean = secondBoolean;
        this.firstChar = firstChar;
        this.secondChar = secondChar;
        this.firstShort = firstShort;
        this.secondShort = secondShort;
        this.firstInt = firstInt;
        this.secondInteger = secondInteger;
        this.firstLong = firstLong;
        this.secondLong = secondLong;
        this.firstFloat = firstFloat;
        this.secondFloat = secondFloat;
        this.firstDouble = firstDouble;
        this.secondDouble = secondDouble;
        this.firstString = firstString;
        this.object = object;
        this.array = array;
        this.list = list;
        this.set = set;
        this.stack = stack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClass testClass = (TestClass) o;
        return firstByte == testClass.firstByte &&
                firstBoolean == testClass.firstBoolean &&
                firstChar == testClass.firstChar &&
                secondChar == testClass.secondChar &&
                firstShort == testClass.firstShort &&
                firstInt == testClass.firstInt &&
                firstLong == testClass.firstLong &&
                Float.compare(testClass.firstFloat, firstFloat) == 0 &&
                Double.compare(testClass.firstDouble, firstDouble) == 0 &&
                Objects.equals(secondByte, testClass.secondByte) &&
                Objects.equals(secondBoolean, testClass.secondBoolean) &&
                Objects.equals(secondShort, testClass.secondShort) &&
                Objects.equals(secondInteger, testClass.secondInteger) &&
                Objects.equals(secondLong, testClass.secondLong) &&
                Objects.equals(secondFloat, testClass.secondFloat) &&
                Objects.equals(secondDouble, testClass.secondDouble) &&
                Objects.equals(firstString, testClass.firstString) &&
                Objects.equals(object, testClass.object) &&
                Arrays.equals(array, testClass.array) &&
                Objects.equals(list, testClass.list) &&
                Objects.equals(set, testClass.set) &&
                Objects.equals(stack, testClass.stack);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(firstByte, secondByte, firstBoolean, secondBoolean, firstChar, secondChar, firstShort,
                                secondShort, firstInt, secondInteger, firstLong, secondLong, firstFloat, secondFloat,
                                firstDouble, secondDouble, firstString, object, list, set, stack);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
}
