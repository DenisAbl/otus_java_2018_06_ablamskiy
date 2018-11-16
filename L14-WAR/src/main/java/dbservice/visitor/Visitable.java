package dbservice.visitor;


public interface Visitable {

    void accept(Visitor visitor);
}
