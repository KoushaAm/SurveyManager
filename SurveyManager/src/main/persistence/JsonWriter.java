package persistence;

// This class has been created with help of JsonWriter Class in JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
// (Paul Carter, 2020)

import model.AllSurveys;
import model.User;
import org.json.JSONObject;
import java.io.*;

// this class writes the User and AllSurvey object into different Json files

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;


    //(Paul Carter, 2020)
    //EFFECT : construct a writer to write file destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //(Paul Carter, 2020)
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if
    //          destination not found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // (Paul Carter, 2020)
    // MODIFIES: this
    // EFFECTS: writes JSON representation of User object to file
    public void writeUser(User user) {
        JSONObject json = user.toJson();
        saveFile(json.toString(TAB));
    }

    // (Paul Carter, 2020)
    // MODIFIES: this
    // EFFECTS: writes JSON representation of AllSurveys object to file
    public void writeAllSurveys(AllSurveys allSurveys) {
        JSONObject json = allSurveys.toJson();
        saveFile(json.toString(TAB));
    }


    //(Paul Carter, 2020)
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //(Paul Carter, 2020)
    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveFile(String json) {
        writer.print(json);
    }

}

