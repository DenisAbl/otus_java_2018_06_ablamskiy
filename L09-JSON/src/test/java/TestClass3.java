import java.util.Arrays;

public class TestClass3 {

    public TestClass3(Integer[] array) {
        this.array = array;
    }

    Integer[] array;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClass3 that = (TestClass3) o;
        return Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}
