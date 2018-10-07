package courses.otus.models;

import courses.otus.models.ATMImpl.Memento;
import courses.otus.models.Interfaces.ATM;
import courses.otus.models.Interfaces.ATMDepartmentObserver;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartmentImpl implements ATMDepartmentObserver {

    private List<ATM> atmList;
    private List<Memento> snapshotList = new ArrayList<>();

    public ATMDepartmentImpl(List<ATM> atmList){
        this.atmList = atmList;
        makeInitialSnapshots();
    }

    @Override
    public long getTotalSum() {
        return atmList.stream().mapToLong(ATM::getTotalSum).sum();
    }

    @Override
    public void restoreInitialState() {
        snapshotList.forEach(Memento::reload);
    }

    @Override
    public List<ATM> getAtmList() {
        return atmList;
    }

    private void makeInitialSnapshots(){
        atmList.forEach(atm -> snapshotList.add(atm.saveStateSnapshot()));
    }

    @Override
    public void addATM(ATM atm) {
        atmList.add(atm);
    }

    @Override
    public void removeATM(int i) {
        atmList.remove(i);
    }
}
