package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

// the class represents  a list of all Survey created

public class AllSurveys implements Writable {

    private ArrayList<Survey> allSurveys;

    // EFFECT: construct a list of ALLSurvey with that stores Survey
    public AllSurveys() {
        this.allSurveys = new ArrayList<Survey>();
    }

    // MODIFIES: this
    // EFFECT: adds the given Survey to allSurvey
    public void addSurveyToAllSurveys(Survey survey) {
        allSurveys.add(survey);
        EventLog.getInstance().logEvent(new Event("Added '" + survey.getTitle() + "' to all surveys"));

    }

    // MODIFIES: this
    //EFFECTS: merges an allSurvey to the current allSurveys
    public void mergeTwoAllSurveys(AllSurveys anotherAllSurveys) {
        if (!allSurveys.isEmpty()) {
            for (int i = 0; i < anotherAllSurveys.getNumOfAllSurveys(); i++) {
                boolean alreadyExist = false;

                for (int k = 0; k < allSurveys.size(); k++) {

                    if (anotherAllSurveys.getWithIndexOf(i).getTitle().equals(allSurveys.get(k).getTitle())
                            && anotherAllSurveys.getWithIndexOf(i).getPassword().equals(
                                    allSurveys.get(k).getPassword())) {
                        alreadyExist = true;
                    }
                }
                if (!alreadyExist) {
                    allSurveys.add(anotherAllSurveys.getWithIndexOf(i));
                }
            }
        } else {
            for (int x = 0; x < anotherAllSurveys.getNumOfAllSurveys(); x++) {
                allSurveys.add(anotherAllSurveys.getWithIndexOf(x));

            }
        }
    }


    // EFFECT: returns true if allSurveys is empty, else false
    public Boolean isItEmpty() {
        return allSurveys.isEmpty();
    }

    // REQUIRED: allSurveys is not empty
    // MODIFIES: this
    // EFFECT: removes the Survey with the given index in
    public void removeSurveyFromAllSurveys(int index) {
        EventLog.getInstance().logEvent(
                new Event("Removed '" + this.getWithIndexOf(index).getTitle() + "' from all surveys"));
        allSurveys.remove(index);

    }

    public int getNumOfAllSurveys() {
        return allSurveys.size();
    }


    public Survey getWithIndexOf(int i) {
        return allSurveys.get(i);
    }


    public ArrayList<Survey> getAllSurveys() {
        return allSurveys;
    }


    //EFFECTS: converts all Surveys to Json
    @Override
    public JSONObject toJson() {
        EventLog.getInstance().logEvent(
                new Event("saved all surveys"));
        JSONObject json = new JSONObject();
        json.put("numOfSurveys", getNumOfAllSurveys());
        json.put("All Surveys", allSurveysToJson());
        return json;
    }

    //EFFECTS: converts each survey in allSurveys to Json
    public JSONArray allSurveysToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Survey s : allSurveys) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

}
