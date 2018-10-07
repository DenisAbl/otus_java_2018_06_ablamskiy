package courses.otus;

import courses.otus.exceptions.NoSuchOperationException;

public enum Operation {

    WITHDRAW(1), DEPOSIT(2), BALANCE(3), EXIT(4);

    private int id;

    Operation(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static Operation getOperationById(int id) throws NoSuchOperationException {
        Operation operation = null;
        for (Operation op : Operation.values()){
            if (op.getId() == id){
                operation = op;
                break;
            }
        }
        if (operation == null) { throw new NoSuchOperationException("Incorrect operation number");}
        return operation;
    }

}
