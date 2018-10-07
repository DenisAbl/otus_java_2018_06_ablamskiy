package courses.otus.service;

import courses.otus.models.ClientAccount;


import java.util.HashMap;
import java.util.Map;

public class DBServiceImpl implements DBService {

    //dummy DB
    private Map<Integer, ClientAccount> clientAccountMap = new HashMap<>();

    public DBServiceImpl(){
    clientAccountMap.put(1, new ClientAccount(1,"0000","2562.32"));
    clientAccountMap.put(2, new ClientAccount(2,"0000","0"));
    clientAccountMap.put(3, new ClientAccount(3,"0000","325"));
    clientAccountMap.put(4, new ClientAccount(4,"0000","50187.1"));
    clientAccountMap.put(5, new ClientAccount(5,"0000","8578.3"));
}

    public String getPinByAccountId(int accountId) {
        return clientAccountMap.get(accountId).getAccountPin();
    }

    public ClientAccount getClientAccountById(int accountId){
        return clientAccountMap.get(accountId);
    }

}
