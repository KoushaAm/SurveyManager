package persistence;

// This class has been created with help of Writable interface in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
// (Paul Carter, 2020)

import org.json.JSONObject;

public interface Writable {

    //(Paul Carter, 2020)
    //EFFECTS: returns this as JSON object
    JSONObject toJson();

}
