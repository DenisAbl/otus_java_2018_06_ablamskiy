package otus;

import org.json.simple.JSONObject;
import otus.visitor.ObjectFieldsVisitor;
import otus.visitor.Visitor;

public class Ason {


    private Visitor visitor;

    public Ason(){

        visitor = new ObjectFieldsVisitor();
    }

    public String toJson(Object object){
        JSONObject jsonObject = visitor.dispatch(object);
        return jsonObject.toJSONString();
    }

}
