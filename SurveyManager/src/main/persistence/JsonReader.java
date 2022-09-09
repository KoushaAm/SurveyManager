package persistence;

// This class has been created with help of JsonReader Class in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
// (Paul Carter, 2020)

// the class reads the user info and all Surveys from two different file destinations


import model.AllSurveys;
import model.Survey;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    //(Paul Carter, 2020)
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //(Paul Carter, 2020)
    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User readUser() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    //(Paul Carter, 2020)
    // EFFECTS: reads AllSurveys object from file and returns it;
    // if error occurred in data file it throws IOException
    public AllSurveys readAllSurveys() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAllSurveys(jsonObject);
    }

    //(Paul Carter, 2020)
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // (Paul Carter, 2020)
    // EFFECTS: parses a user from JSONObject and returns it
    private User parseUser(JSONObject jsonObject) {
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        User user = new User(userName, password);
        return user;
    }

    // EFFECTS: parses AllSurveys object from JSONObject and returns it
    private AllSurveys parseAllSurveys(JSONObject jsonObject) {
        AllSurveys allSurveys = new AllSurveys();

        JSONArray allSurveysArray = jsonObject.getJSONArray("All Surveys");

        for (int i = 0; i < allSurveysArray.length(); i++) {
            Survey oneSurvey = parseSurvey(allSurveysArray.getJSONObject(i));
            allSurveys.addSurveyToAllSurveys(oneSurvey);
        }

        return allSurveys;
    }

    // EFFECTS: parses each Survey object in AllSurvey object and returns it
    private Survey parseSurvey(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int numOfQuestions = jsonObject.getInt("numOfQuestions");
        String password = jsonObject.getString("password");

        //parse questions
        JSONArray questionsJson = jsonObject.getJSONArray("questions");
        ArrayList<String> questions = new ArrayList<>();
        questions = parseQuestions(questionsJson);

        //parse answers
        JSONArray answersJson = jsonObject.getJSONArray("answers");
        ArrayList<ArrayList<String>> answers = new ArrayList<>();
        answers = parseAnswers(answersJson);

        //parse answer entries
        JSONArray answerEntriesJson = jsonObject.getJSONArray("answer Entries");
        ArrayList<ArrayList<Integer>> answerEntries = new ArrayList<>();
        answerEntries = parseAnswerEntries(answerEntriesJson);


        Survey survey = new Survey(title, numOfQuestions, password, questions, answers, answerEntries);

        return survey;
    }

    // EFFECTS: parse each string in json array and returns a an array of strings
    private ArrayList<String> parseQuestions(JSONArray questionsJson) {
        ArrayList<String> questions = new ArrayList<>();

        for (int i = 0; i < questionsJson.length(); i++) {
            String question = (String)questionsJson.get(i);
            questions.add(question);
        }

        return questions;
    }

    // EFFECTS: parses each JsonArray of answers (JsonArray of Strings) and returns it
    private ArrayList<ArrayList<String>> parseAnswers(JSONArray answersJson) {

        ArrayList<ArrayList<String>> answers = new ArrayList<>();
        ArrayList<String> answersToEachQ;

        for (int k = 0; k < answersJson.length(); k++) {
            answersToEachQ = new ArrayList<>();
            JSONArray answersToEachQJson = (JSONArray) answersJson.get(k);

            for (int x = 0; x < answersToEachQJson.length(); x++) {
                String option = (String) answersToEachQJson.get(x);
                answersToEachQ.add(option);
            }
            answers.add(answersToEachQ);
        }

        return answers;
    }

    // EFFECTS: parses each JsonArray of answer entries and returns a 2d array list of integers
    private ArrayList<ArrayList<Integer>> parseAnswerEntries(JSONArray answerEntriesJson) {
        ArrayList<ArrayList<Integer>> answerEntries = new ArrayList<>();
        ArrayList<Integer> answerEntriesForEachQ;

        for (int k = 0; k < answerEntriesJson.length(); k++) {
            answerEntriesForEachQ = new ArrayList<>();
            JSONArray answerEntriesToEachQJson = (JSONArray) answerEntriesJson.get(k);

            for (int x = 0; x < answerEntriesToEachQJson.length(); x++) {
                int answerEntry = (Integer) answerEntriesToEachQJson.get(x);
                answerEntriesForEachQ.add(answerEntry);
            }
            answerEntries.add(answerEntriesForEachQ);
        }

        return answerEntries;
    }






}
