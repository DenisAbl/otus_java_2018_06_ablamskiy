package courses.otus.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Dispenser {

    private Map<Integer,Integer> moneyMap = new TreeMap<>();

    public Dispenser(){
        moneyMap.put(Cassette.FIFTYS,0);
        moneyMap.put(Cassette.ONE_HUNDREDS,0);
        moneyMap.put(Cassette.TWO_HUNDREDS,0);
        moneyMap.put(Cassette.FIVE_HUNDREDS,0);
        moneyMap.put(Cassette.ONE_THOUSANDS,0);
        moneyMap.put(Cassette.TWO_THOUSANDS,0);
        moneyMap.put(Cassette.FIVE_THOUSANDS,0);
    }

    public void getDepositMoney(BigDecimal amount){

    }

    public Map<Integer,Integer> WithdrawMoney(){
        Map<Integer,Integer> moneyMap = new TreeMap<>();
        moneyMap.putAll(this.moneyMap);
        moneyMap.forEach((k,v)-> System.out.println("Номинал: " + k + " Количество купюр: "+ v));
        this.moneyMap.clear();
        return moneyMap;
    }

    public void collectBanknotes(Map<Integer,Integer> map){
        moneyMap.putAll(map);
    }

}
