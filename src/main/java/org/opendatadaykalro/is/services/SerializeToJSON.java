package org.opendatadaykalro.is.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class SerializeToJSON {
    private static SerializeToJSON instance = null;

    private SerializeToJSON() {
    }

    public static SerializeToJSON getInstance() {
        if (instance == null) {
            instance = new SerializeToJSON();
        }
        return instance;
    }

    public Object toJSON(Object object){
        if (object instanceof HashMap) {
            JSONObject json = new JSONObject();
            HashMap map = (HashMap) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable) object)) {
                json.add(toJSON(value));
            }
            return json;
        } else {
            return object;
        }
    }
}
