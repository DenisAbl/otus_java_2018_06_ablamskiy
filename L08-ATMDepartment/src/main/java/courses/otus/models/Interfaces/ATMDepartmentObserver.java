package courses.otus.models.Interfaces;

import java.util.List;

public interface ATMDepartmentObserver {

    long getTotalSum();

    //Observer's publisher method
    void restoreInitialState();

    List<ATM> getAtmList();

    void addATM(ATM atm);

    void removeATM(int i);
}
