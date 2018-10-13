package otus.visitor;

import org.json.simple.JSONObject;

public interface Visitor {

    public JSONObject dispatch(Object o);
}
