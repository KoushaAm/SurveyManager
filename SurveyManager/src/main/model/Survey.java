package model;

import org.json.JSONObject;
import org.json.JSONArray;

import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// this class represents a survey that has title,
// number of   questions, answers and answerEntries .
public class Survey implements Writable {

    private String password;
    private String title;
    private int numOfQuestions;
    private ArrayList<String> questions;
    private ArrayList<ArrayList<String>> answers;
    private ArrayList<ArrayList<Integer>> answerEntries;

    // EFFECT: construct a survey with given title, number of questions,
    //         a list of questions , a list of answers and list of answers
    //         entered for each option of each question
    public Survey(String title, int numOfQuestions, String password,  ArrayList<String> questions,
                  ArrayList<ArrayList<String>> answers, ArrayList<ArrayList<Integer>> answerEntries) {
        this.title = title;
        this.password = password;
        this.numOfQuestions = numOfQuestions;
        this.questions = questions;
        this.answers = answers;
        this.answerEntries = answerEntries;

        EventLog.getInstance().logEvent(new Event("Constructed a survey titled '" + title + "'"));
    }

    // MODIFIES: this
    // EFFECT: takes the index of choice in answerEntries and a survey of choice with a response
    //         then adds the 1 to the index of option responded
    public void changeResultOfEntries(Survey surveyChoice, int index, int response) {
        int targetIndex = surveyChoice.getAnswerEntries().get(index).get(response - 1);
        surveyChoice.getAnswerEntries().get(index).set(response - 1, (targetIndex + 1));
        EventLog.getInstance().logEvent(new Event("Updated the results of '"
                + surveyChoice.getTitle() + "'."));
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public ArrayList<ArrayList<String>> getAnswers() {
        return answers;
    }

    public ArrayList<ArrayList<Integer>> getAnswerEntries() {
        return answerEntries;
    }

    public String getTitle() {
        if (title.equals("")) {
            return "Untitled Survey";
        } else {
            return title;
        }
    }

    public Integer getNumOfQuestions() {
        return numOfQuestions;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("numOfQuestions", numOfQuestions);
        json.put("password", password);
        json.put("questions", questionsToJson());
        json.put("answers", answersToJson());
        json.put("answer Entries", answerEntriesToJson());
        return json;
    }

    public JSONArray questionsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String q : questions) {
            jsonArray.put(q);
        }
        return jsonArray;
    }

    public JSONArray answersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < answers.size(); i++) {
            jsonArray.put(eachAnswerToJson(answers.get(i)));
        }
        return jsonArray;
    }

    public JSONArray eachAnswerToJson(ArrayList<String> eachQuestionOptions) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < eachQuestionOptions.size(); i++) {
            jsonArray.put(eachQuestionOptions.get(i));
        }
        return jsonArray;
    }

    public JSONArray answerEntriesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < answerEntries.size(); i++) {
            jsonArray.put(eachAnswerEntryToJson(answerEntries.get(i)));
        }
        return jsonArray;
    }

    public JSONArray eachAnswerEntryToJson(ArrayList<Integer> eachAnswerEntry) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < eachAnswerEntry.size(); i++) {
            jsonArray.put(eachAnswerEntry.get(i));
        }
        return jsonArray;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o)
//            return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Survey survey = (Survey) o;
//        return this.password == survey.password && Objects.equals(password, survey.password)
//        && Objects.equals(title, survey.title) && Objects.equals(questions, survey.questions)
//        && Objects.equals(answers, survey.answers) && Objects.equals(answerEntries, survey.answerEntries);
//    }

}











