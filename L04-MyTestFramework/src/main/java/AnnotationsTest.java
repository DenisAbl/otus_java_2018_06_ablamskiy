import annotations.Before;
import annotations.Test;
import annotations.After;

public class AnnotationsTest {

    public AnnotationsTest() {
        System.out.println("Call of the constructor");
    }

    @Before
    public void before(){
        System.out.println("Before");
    }

    @Test
    public void testOne(){
        System.out.println("testOne");
    }

    @Test
    public void testTwo(){
        System.out.println("Test2");
    }

    @After
    public void after(){
        System.out.println("After");
    }

}
