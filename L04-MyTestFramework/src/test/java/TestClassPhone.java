import annotations.After;
import annotations.Before;
import annotations.Test;

public class TestClassPhone {


    TestClassPhone(){
    }

    @Test
    public void instatiate(){
        Phone testPhone = new Phone();
        assert (testPhone.getNumber()==0 && testPhone.getPlatform()==null && testPhone.getTrademark()==null) : "Initialisation test failed";
        System.out.println("instantiation test completed");
    }

    @Test
    public void settersTest(){
        Phone testPhone = new Phone();
        testPhone.setNumber(123);
        testPhone.setPlatform("Simbian");
        testPhone.setTrademark("Moto");
        assert (testPhone.getNumber()==123 &&
                testPhone.getPlatform().equals("Simbian") &&
                testPhone.getTrademark().equals("Moto")) : "setters/getters test failed";
        System.out.println("Setters/getters test completed");

    }

    @Before
    public void before(){
        System.out.println("\"Before\" annotation execution");
    }

    @After
    public void after(){
        System.out.println("\"After\" annotation execution");
    }

}
