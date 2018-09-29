package courses.otus;

public enum Operation {

    WITHDRAW(1), DEPOSIT(2), BALANCE(3), EXIT(4);

    private int id;

    Operation(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    //скорее всего велосипед, поискать решение

    public static Operation getOperationById(int id){
        Operation operation = null;
        for (Operation op : Operation.values()){
            if (op.getId() == id){
                operation = op;
                break;
            }
        }
        return operation;
    }

}
