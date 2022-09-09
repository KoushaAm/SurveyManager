package ui;

import model.Survey;
import model.AllSurveys;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// This class has been created with help of WorkroomApp Class in JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
// (Paul Carter, 2020)

// The class provides functionality for creating Survey, doing Survey,
// viewing Survey, viewing results and quiting from application.


public class SurveyAppConsole {

    private static final String JSON_LOCATION = "./data/";
    private static final String ALL_SURVEYS_LOCATION = "./data/allSurveys.json";
    private Scanner input;
    private AllSurveys allSurveys = new AllSurveys();
    private User user;
    private JsonWriter jsonWriterUser;
    private JsonWriter jsonWriteAllSurveys;

    private JsonReader jsonReader;

    // EFFECT: runs the Survey app
    public SurveyAppConsole() {
        jsonWriteAllSurveys = new JsonWriter(ALL_SURVEYS_LOCATION);
        run();


    }

    // MODIFIES: this
    // EFFECT: processes user input
    public void run() {
        boolean running = true;
        String command;

        init();

        while (running) {
            showMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                running = false;
                System.out.println("Exit...");
            } else {
                getCommand(command);
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner
    //          gets users Name and password
    //          creates a new User.
    public void init() {
        // ask user to login


        input = new Scanner(System.in);
        input.useDelimiter("\n");

        System.out.println("Enter your Name:\n");
        String username = input.next();

        while (username.equals("")) {
            System.out.println("You must enter a name: ");
            username = input.next();
        }

        System.out.println("Enter a password:");
        String password = input.next();

        while (password.equals("")) {
            System.out.println("You must enter a password: ");
            password = input.next();
        }
        user = new User(username, password);

        System.out.println("login (l) / signUp (s)");
        String command = input.next().toLowerCase();
        if (command.equals("l")) {
            loadUser(user);
        } else {
            saveUser();
        }


    }

    //EFFECT: print menu of options
    public void showMenu() {
        System.out.println("\nSelect an option: ");
        System.out.println("\nc --> Create a Survey.");
        System.out.println("\nd --> Do a Survey.");
        System.out.println("\nv --> View surveys");
        System.out.println("\nr --> See results.");
        System.out.println("\nt --> Terminate a Survey.");
        System.out.println("\nl --> load the previous activities.");
        System.out.println("\ns --> save survey activity.");
        System.out.println("\nq --> Quit.\n");
    }

    // EFFECTS: process users commands
    public void getCommand(String command) {
        if (command.equals("c")) {
            createSurvey();

        } else if (command.equals("d")) {
            doSurvey();

        } else if (command.equals("v")) {
            viewAllSurveys();

        } else if (command.equals("r")) {
            seeResults();

        } else if (command.equals("s")) {
            saveAllSurveys();
        } else if (command.equals("l")) {
            loadAllSurveys();
        } else if (command.equals("t")) {
            removeSurvey();

        } else {
            System.out.println("<INVALID INPUT>");
        }
    }


    // MODIFIES: this
    // EFFECTS: constructs a Survey by asking the user for title
    //          number of questions, questions, and answers to
    //          each question
    //          adds the constructed Survey to AllSurveys
    public void createSurvey() {
        ArrayList<String> questions = new ArrayList<String>();
        ArrayList<ArrayList<String>> allAnswers = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<Integer>> answerEntries = new ArrayList<ArrayList<Integer>>();


        System.out.println("Enter the title of your Survey: \n");
        String title = input.next();

        System.out.println("Enter the number of Questions which you wish to add: \n");
        int numOfQuestions = input.nextInt();

        for (int i = 0; i < numOfQuestions; i++) {

            ArrayList<String> answersToEachQuestion = new ArrayList<String>();
            System.out.println("Enter question #" + (i + 1));
            String question = input.next();
            questions.add(question);
            answersToEachQuestion = createOptions(answersToEachQuestion);
            allAnswers.add(answersToEachQuestion);
        }

        answerEntries = createInitialAnswerEntries(allAnswers);
        // for checking, add this for the verification of the constructed survey later

        String password = secureSurvey();
        Survey survey = new Survey(title, numOfQuestions, password, questions, allAnswers, answerEntries);
        allSurveys.addSurveyToAllSurveys(survey);

        System.out.println("\n New survey is created! \n" + "Title: " + survey.getTitle() + "\n");
        System.out.println("\n Number of Questions: " + survey.getNumOfQuestions());
        System.out.println("\n List of Questions: " + survey.getQuestions());
        System.out.println("\n List of options: " + survey.getAnswers());
    }

    public String secureSurvey() {
        String password = "";
        while (password.equals("")) {
            System.out.println("Enter a password for your survey: ");
            password = input.next();
        }
        return password;
    }

    // EFFECTS: creates a list with same number of elements as the passed on list
    //          but with every single value in the list of list replaced by 0.
    public ArrayList<ArrayList<Integer>> createInitialAnswerEntries(ArrayList<ArrayList<String>> entries) {
        ArrayList<ArrayList<Integer>> answerEntries = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> initialEntriesOfASet;

        for (int i = 0; i < entries.size(); i++) {
            initialEntriesOfASet = new ArrayList<Integer>();

            for (int k = 0; k < entries.get(i).size(); k++) {
                initialEntriesOfASet.add(0);
            }
            answerEntries.add(initialEntriesOfASet);
        }

        return answerEntries;
    }

    // EFFECTS: allows user to enter options to each given question and
    //          adds each option to the given list of answers.
    public ArrayList<String> createOptions(ArrayList<String> answers) {
        int numOfAnswers;

        System.out.println("How many options would you like for answers?(Enter an integer)");
        numOfAnswers = input.nextInt();
        while (numOfAnswers < 2) {
            System.out.println("Your # of options must be greater than 2, enter another value: ");
            numOfAnswers = input.nextInt();
        }
        String answer;

        for (int k = 0; k < numOfAnswers; k++) {
            System.out.println("Enter option #" + (k + 1));
            answer = input.next();
            answers.add(answer);
        }

        return answers;
    }

    // EFFECTS: allows user to do a survey if surveys are
    //          available in allSurveys.
    public void doSurvey() {
        int command = 0;
        int response = 0;

        if (allSurveys.isItEmpty()) {
            System.out.println("There are no available surveys at this time");

        } else {
            executeSurvey();
        }
    }

    // EFFECTS: takes the max number of input allowed for a question
    //          if input is greater than zero or equal/less than
    //          max number, it asks for input again until a valid input
    //          is given by the user.
    public int userInputForSurveyChoice(int maxNumAllowed) {
        System.out.println("Enter the # of your preferred Survey: ");
        int command = input.nextInt();

        while (command > allSurveys.getNumOfAllSurveys() || command <= 0) {
            System.out.println("Enter a valid number:  ");
            command = input.nextInt();
        }

        return command;
    }


    // EFFECTS: allows the user to select a user from all Surveys and runs
    //          and runs Survey of user's choice after taking its input as an
    //          integer.
    public void executeSurvey() {
        viewAllSurveys();

        int command = userInputForSurveyChoice(allSurveys.getNumOfAllSurveys());

        Survey surveyChoice = allSurveys.getWithIndexOf(command - 1);
        System.out.println("\n**********************\n || " + surveyChoice.getTitle() + " ||" + "\n");

        for (int q = 0; q < surveyChoice.getNumOfQuestions(); q++) {
            System.out.println("Question " + String.valueOf(q + 1) + ": " + surveyChoice.getQuestions().get(q));

            for (int a = 0; a < surveyChoice.getAnswers().get(q).size(); a++) {
                System.out.println(String.valueOf(a + 1) + ": " + surveyChoice.getAnswers().get(q).get(a));
            }
            System.out.println("\n" + "Enter the # of your option (Integer): ");
            int response = input.nextInt();
            while (0 < response && response > surveyChoice.getAnswers().get(q).size()) {
                System.out.println("\n" + "Enter the # of your option (Integer): ");
                response = input.nextInt();
            }

            surveyChoice.changeResultOfEntries(surveyChoice, q, response);
        }
    }

    // EFFECTS: print all Surveys in AllSurvey
    public void viewAllSurveys() {
        if (isValidFreeToProceed(allSurveys)) {
            System.out.println("ALL SURVEYS:");
            for (int i = 0; i < allSurveys.getNumOfAllSurveys(); i++) {
                System.out.println(String.valueOf(i + 1) + ": " + allSurveys.getWithIndexOf(i).getTitle());
            }
        } else {
            System.out.println("No survey found!");
        }
    }

    // EFFECTS: produces true if the given allSurveys is empty otherwise false.
    public Boolean isValidFreeToProceed(AllSurveys allSurveys) {
        if (allSurveys.isItEmpty()) {
            return false;
        }
        return true;
    }


    // EFFECTS: prints all available Surveys and asks user to select one Survey
    //          it then returns the selected Survey answerEntries for each question
    public void seeResults() {
        if (isValidFreeToProceed(allSurveys)) {
            System.out.println("Enter the # of survey that you wish to see its result: ");
            viewAllSurveys();

            int command = userInputForSurveyChoice(allSurveys.getNumOfAllSurveys());
            Survey surveyChoice = allSurveys.getWithIndexOf(command - 1);

            System.out.println("The result of " + "'" + surveyChoice.getTitle() + "'");

            for (int k = 0; k < surveyChoice.getNumOfQuestions(); k++) {
                System.out.println("__________________________________________");
                System.out.println("Question " + String.valueOf(k + 1) + ": " + surveyChoice.getQuestions().get(k));

                System.out.println("Total number of entries for each answer:");
                for (int x = 0; x < surveyChoice.getAnswers().get(k).size(); x++) {
                    String eachQuestion = surveyChoice.getAnswers().get(k).get(x);
                    String eachAnswer = String.valueOf(surveyChoice.getAnswerEntries().get(k).get(x));
                    System.out.println(eachQuestion + ": " + eachAnswer);
                }

            }
        } else {
            System.out.println("no survey available!");
        }
    }

    // MODIFIES: this
    // EFFECTS: takes the index of the Survey in allSurvey then,
    //        removes the Survey from allSurvey given its index in the allSurvey
    public void removeSurvey() {
        if (isValidFreeToProceed(allSurveys)) {
            System.out.println("Enter the # of the Survey you would like to delete: ");
            viewAllSurveys();
            int command = input.nextInt();
            Survey surveyChoice = allSurveys.getWithIndexOf(command - 1);

            System.out.println("To confirm that you are the creator of this survey please enter your password: ");

            String password = input.next();
            if (password.equals(user.getPassword())) {
                deleteSurveyFromAllSurveyAction(command - 1);

            } else {
                System.out.println("Password is incorrect, your attempt was unsuccessful!");
            }

        } else {
            System.out.println("No survey available!");
        }


    }

    //(Paul Carter, 2020)
    // MODIFIES: this
    // EFFECTS: deletes the Survey from allSurvey given the index of Survey in all Survey
    public void deleteSurveyFromAllSurveyAction(int index) {
        allSurveys.removeSurveyFromAllSurveys(index);
        deleteSurveyFromJson(index);
        System.out.println("Survey deleted successfully!");
    }

    // EFFECTS: delete the survey with the given index from Json file located in ALL_SURVEYS_LOCATION.
    public void deleteSurveyFromJson(int index) {
        try {
            JsonReader reader = new JsonReader(ALL_SURVEYS_LOCATION);
            AllSurveys savedAllSurveys = reader.readAllSurveys();
            savedAllSurveys.removeSurveyFromAllSurveys(index);

            JsonWriter writer = new JsonWriter(ALL_SURVEYS_LOCATION);
            writer.open();
            writer.writeAllSurveys(savedAllSurveys);
            writer.close();

        } catch (IOException e) {
            System.out.println("All Surveys not found");
        }

    }

    //(Paul Carter, 2020)
    //EFFECTS: save the user to the given file destination
    public void saveUser() {
        try {
            jsonWriterUser = new JsonWriter(JSON_LOCATION + user.getUserName()
                    + "&" + user.getPassword() + ".json");
            jsonWriterUser.open();
            jsonWriterUser.writeUser(user);
            jsonWriterUser.close();

            System.out.println("Saved " + user.getUserName() + "'s" + " info to :" + JSON_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write to: " + JSON_LOCATION);
        }
    }

    //(Paul Carter, 2020)
    //MODIFIES: this
    //EFFECTS: loads the user from file
    public void loadUser(User user) {
        try {
            JsonReader jsonReader = new JsonReader(JSON_LOCATION + user.getUserName()
                    + "&" + user.getPassword() + ".json");
            user = jsonReader.readUser();
            System.out.println("loaded the user from " + JSON_LOCATION);
        } catch (IOException e) {
            System.out.println("User not found!");
            System.out.println("Try another option: \n");
            init();
        }
    }


    //(Paul Carter, 2020)
    //EFFECTS: save the AllSurveys object to a file destination
    public void saveAllSurveys() {
        try {
            JsonReader jsonReadAllSurveys = new JsonReader(ALL_SURVEYS_LOCATION);

            AllSurveys oldAllSurveys = new AllSurveys();
            oldAllSurveys = jsonReadAllSurveys.readAllSurveys();
            allSurveys.mergeTwoAllSurveys(oldAllSurveys);

            jsonWriteAllSurveys.open();
            jsonWriteAllSurveys.writeAllSurveys(allSurveys);
            jsonWriteAllSurveys.close();

            System.out.println("Saved all surveys to " + ALL_SURVEYS_LOCATION);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file to " + ALL_SURVEYS_LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // (Paul Carter, 2020)
    // MODIFIES: this
    // EFFECTS: loads all surveys from fil
    public void loadAllSurveys() {
        try {
            AllSurveys oldAllSurveys = new AllSurveys();
            JsonReader jsonReader = new JsonReader(ALL_SURVEYS_LOCATION);

            oldAllSurveys = jsonReader.readAllSurveys();
            allSurveys.mergeTwoAllSurveys(oldAllSurveys);
            //allSurveys = jsonReader.readAllSurveys();

            System.out.println("Survey's loaded from " + ALL_SURVEYS_LOCATION);

        } catch (IOException e) {
            System.out.println("can't read");
        }
    }
}

