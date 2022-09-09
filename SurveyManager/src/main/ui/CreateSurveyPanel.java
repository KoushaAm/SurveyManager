package ui;

import model.AllSurveys;
import model.Survey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//represents a create survey panel

public class CreateSurveyPanel extends JPanel {

    Survey survey;
    private JPanel createSurveyPanel;
    protected JScrollPane scrollPane;
    protected JLabel introLabel;
    protected int numberOfQuestionsEach;
    JTextField titleField;
    JTextField numOfQuestionsField;


    JButton confirmToCreateBt;
    JButton confirmQuestionsAndNumOfOptionsBt;
    JLabel questionNLabel;
    JTextField questionNField;
    JLabel numOfOptionsLabel;
    JTextField numOfOptionsField;
    ArrayList<Integer> numberOfOptionsForEach;
    JTextField passwordField;
    JLabel passwordLabel;

    // needed to create Survey
    ArrayList<JTextField> numOfOptionsTextFields;
    JButton confirmOptionsBt;
    ArrayList<JTextField> optionsFields;
    ArrayList<JTextField> questionsFields;


    private AllSurveys allSurveys;
    private String password;
    private String title;
    private int numOfQuestions;
    private ArrayList<String> questions;
    private ArrayList<ArrayList<String>> answers;
    private ArrayList<ArrayList<Integer>> answerEntries;

    // MODIFIES: this
    //EFFECTS: constructs the survey panel
    public CreateSurveyPanel(AllSurveys allSurveys) {
        this.allSurveys = allSurveys;
        confirmToCreateBt = new JButton("Confirm");
        confirmQuestionsAndNumOfOptionsBt = new JButton("Confirm number of options");
        numberOfOptionsForEach = new ArrayList<>();
        numOfOptionsTextFields = new ArrayList<>();
        passwordLabel = new JLabel("Password: ");
        confirmOptionsBt = new JButton("Confirm your options");
        optionsFields = new ArrayList<>();
        questionsFields = new ArrayList<>();
        passwordField = new JTextField(8);
    }

    // MODIFIES: this
    //EFFECTS : constructs CreateSurveyTab and returns a panel as JComp
    public JComponent makeCreateSurveyTab() {
        scrollPane = new JScrollPane(this);
        this.setLayout(new GridLayout(100, 2));

        this.setBackground(Color.DARK_GRAY);
        introLabel = new JLabel("Create you own survey!");
        introLabel.setFont(new Font("Helvetica PLAIN", Font.BOLD, 20));
        introLabel.setForeground(Color.ORANGE);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(Color.ORANGE);
        titleField = new JTextField();

        JLabel numOfQuestionsLabel = new JLabel("Number of Questions:");
        numOfQuestionsField = new JTextField();
        numOfQuestionsLabel.setForeground(Color.ORANGE);


        this.add(introLabel);
        this.add(titleLabel);
        this.add(titleField);
        this.add(numOfQuestionsLabel);
        this.add(numOfQuestionsField);

        addConfirmButton();


        return scrollPane;
    }

    // MODIFIES: this
    //EFFECTS: constructs confirm button
    public void addConfirmButton() {
        confirmToCreateBt = new JButton("Confirm");
        this.add(confirmToCreateBt);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfQuestionsEach = Integer.valueOf(numOfQuestionsField.getText());
                numOfQuestions = Integer.valueOf(numOfQuestionsField.getText());
                title = titleField.getText();
                setUpLoopForQuestions();
                confirmToCreateBt.setEnabled(false);
            }
        };

        confirmToCreateBt.addActionListener(actionListener);

    }


    // MODIFIES: this
    //EFFECTS: constructs labels and textFields for constructing the question of each survey in a loop
    public void setUpLoopForQuestions() {

        for (int i = 0; i < numberOfQuestionsEach; i++) {
            questionNLabel = new JLabel("Question " + String.valueOf(i + 1));
            questionNLabel.setForeground(Color.PINK);
            questionNField = new JTextField();

            // add this question field to array for taking records of
            // questions in string form
            questionsFields.add(questionNField);

            numOfOptionsLabel = new JLabel("Number of options: ");
            numOfOptionsLabel.setForeground(Color.PINK);
            numOfOptionsField = new JTextField();

            numOfOptionsTextFields.add(numOfOptionsField);

            this.add(questionNLabel);
            this.add(questionNField);
            this.add(numOfOptionsLabel);
            this.add(numOfOptionsField);
        }

        addConfirmQuestionsAndNumOfOptionsBt();

//        confirmQuestionsAndNumOfOptionsBt = new JButton("Confirm the question and number of Options");
//        this.add(confirmQuestionsAndNumOfOptionsBt);
//        this.revalidate();
//
//        confirmQuestionsAndNumOfOptionsBt.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                getNumOfOptionsForEachQuestion();
//                askForOptions();
//            }
//        });


    }

    // MODIFIES: this
    //EFFECTS: adds confirm questions and options button
    public void addConfirmQuestionsAndNumOfOptionsBt() {
        confirmQuestionsAndNumOfOptionsBt = new JButton("Confirm the question and number of Options");
        this.add(confirmQuestionsAndNumOfOptionsBt);
        this.revalidate();

        confirmQuestionsAndNumOfOptionsBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getNumOfOptionsForEachQuestion();
                askForOptions();
                confirmQuestionsAndNumOfOptionsBt.setEnabled(false);
            }
        });
    }


    // MODIFIES: numberOfOptionsForEach
    //EFFECTS: constructs the listener for updateSurveys button
    public void getNumOfOptionsForEachQuestion() {
        for (JTextField field : numOfOptionsTextFields) {
            numberOfOptionsForEach.add(Integer.valueOf(field.getText()));
        }
    }

    // MODIFIES: this
    //EFFECTS: adds text fields to options
    public void addTextFieldForOptions() {
        for (int j = 0; j < numberOfOptionsForEach.size(); j++) {
            JLabel optionEntryLabel = new JLabel("Options for Question " + (j + 1) + ":");
            optionEntryLabel.setForeground(Color.ORANGE);
            this.add(optionEntryLabel);

            for (int k = 0; k < numberOfOptionsForEach.get(j); k++) {
                JLabel labelForOption = new JLabel("Enter option " + String.valueOf(k + 1) + ".");
                labelForOption.setForeground(Color.yellow);
                JTextField eachOption = new JTextField();
                this.add(labelForOption);
                this.add(eachOption);
                optionsFields.add(eachOption);
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: asks for Options by constructing Labels and textFields.
    public void askForOptions() {
        addTextFieldForOptions();
        confirmOptionsBt =  new JButton("Confirm your options");

        this.add(confirmOptionsBt);
        this.revalidate();
        this.revalidate();


        confirmOptionsBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("getting command from confirm options button");
                readQuestions();
                readOptions();
                generateAnswerEntries();
                finalizeSubmission();
                confirmOptionsBt.setEnabled(false);
            }
        });

    }


    // MODIFIES: this
    // EFFECTS: reads the question fields and stores the string
    // value of each text field to question arrayList
    public void readQuestions() {
        questions = new ArrayList<>();
        for (int i = 0; i < questionsFields.size(); i++) {
            questions.add(questionsFields.get(i).getText());
        }
    }


    // MODIFIES: numberOfOptionsForEach
    // EFFECTS: reads the options fields and stores the
    // string value of each text field to answer arrayList
    public void readOptions() {
        answers = new ArrayList<>();
        String anOption;
        int len = numberOfOptionsForEach.size();
        int acc = 0;
        ArrayList<String> opsForOneQ;

        for (int j = 0; j < len; j++) {
            opsForOneQ = new ArrayList<>();
            int maxOpNum = numberOfOptionsForEach.get(j);

            for (int i = acc; i < (maxOpNum + acc); i++) {
                anOption = optionsFields.get(i).getText();
                opsForOneQ.add(anOption);
            }
            acc += maxOpNum;
            answers.add(opsForOneQ);
        }
    }


    //MODIFIES: answerEntries
    //EFFECTS : constructs an initial Answer entry for a survey
    public void generateAnswerEntries() {
        answerEntries = new ArrayList<>();
        int len = numberOfOptionsForEach.size();
        ArrayList<Integer> entry;
        int acc = 0;
        for (int j = 0; j < len; j++) {
            entry = new ArrayList<>();
            int maxOpNum = numberOfOptionsForEach.get(j);

            for (int i = acc; i < (maxOpNum + acc); i++) {
                entry.add(0);
            }
            acc += maxOpNum;
            answerEntries.add(entry);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the creation of Survey
    public void finalizeSubmission() {
        //ask for password
        //make sure all textFields are field

        this.add(passwordLabel);
        this.add(passwordField);
        //createSurveyPanel.add(createSurveyBt);
        this.revalidate();



    }


    // MODIFIES : this
    // EFFECTS: instantiates a survey and adds it to allSurveys
    public void createSurvey() {
        password = passwordField.getText();
        survey = new Survey(title, numOfQuestions, password, questions, answers, answerEntries);
        AllSurveys someAllSurveys = new AllSurveys();
        someAllSurveys.addSurveyToAllSurveys(survey);
        allSurveys.mergeTwoAllSurveys(someAllSurveys);

        numberOfOptionsForEach = new ArrayList<>();
        numOfOptionsTextFields = new ArrayList<>();
        optionsFields = new ArrayList<>();
        questionsFields = new ArrayList<>();
        numberOfQuestionsEach = 0;

        password = "";
        title = "";
        numOfQuestions = 0;
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        answerEntries = new ArrayList<>();


    }



}
