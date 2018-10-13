import java.util.List;
import java.util.Objects;

public class TestClass2 {


    List<List<Integer>> doubleList;

    public TestClass2(List<List<Integer>> doubleList) {
        this.doubleList = doubleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClass2 that = (TestClass2) o;
        return Objects.equals(doubleList, that.doubleList);

    }

    @Override
    public int hashCode() {
        return Objects.hash(doubleList);
    }
}
