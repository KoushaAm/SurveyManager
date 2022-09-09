package ui;

import model.AllSurveys;
import model.Survey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// represents a do survey panel

public class DoSurveyPanel extends JPanel {

    private JScrollPane scrollPanelDoSurvey;
    private JLabel doSurveyIntroLabel;
    private JButton chooseASurveyBt;
    private JTextField chooseSurveyTextField;
    private JButton confirmSurveyChoiceBt;


    private JLabel nthQuestionLabel;
    private JLabel nthOptionLabel;
    private JTextField nthAnswerField;
    private JButton collectResponseBt;
    private ArrayList<JTextField> answerToEachQuestionField;
    private JButton viewSurveysBt;

    private AllSurveys allSurveys;


    // MODIFIES: this
    // EFFECTS: constructs a do survey app
    public DoSurveyPanel(AllSurveys allSurveys) {
        this.allSurveys = allSurveys;
        chooseASurveyBt = new JButton("Choose a Survey");
        chooseSurveyTextField = new JTextField();
        confirmSurveyChoiceBt = new JButton("Confirm choice");
        nthQuestionLabel = new JLabel();
        nthOptionLabel = new JLabel();
        nthAnswerField = new JTextField();
        collectResponseBt = new JButton("Submit your answers");
        answerToEachQuestionField = new ArrayList<>();
        viewSurveysBt = new JButton("View surveys");

    }

    // MODIFIES: this
    // EFFECTS: constructs a DoSurveyTab
    public JComponent makeDoSurveyTab() {

        scrollPanelDoSurvey = new JScrollPane(this);
        this.setLayout(new GridLayout(100, 2));

        this.setBackground(Color.DARK_GRAY);
        doSurveyIntroLabel = new JLabel("Do a Survey");
        doSurveyIntroLabel.setForeground(Color.ORANGE);
        doSurveyIntroLabel.setFont(new Font("Helvetica PLAIN", Font.BOLD, 20));


        this.add(doSurveyIntroLabel);

        this.add(viewSurveysBt);
        viewSurveysBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showSurveys();
                viewSurveysBt.setEnabled(false);

            }
        });

        return scrollPanelDoSurvey;
    }

    // MODIFIES: this
    // EFFECTS: constructs the labels to represent the name of all surveys.
    public void showSurveys() {
        if (allSurveys.getNumOfAllSurveys() > 0) {
            for (int k = 0; k < allSurveys.getNumOfAllSurveys(); k++) {
                JLabel surveyOptionLabel =
                        new JLabel(String.valueOf(k + 1) + ". " + allSurveys.getWithIndexOf(k).getTitle());
                surveyOptionLabel.setFont(new Font("Helvetica PLAIN", Font.BOLD, 15));
                surveyOptionLabel.setForeground(Color.yellow);
                this.add(surveyOptionLabel);
                this.revalidate();
            }

            this.add(chooseSurveyTextField);
            this.add(confirmSurveyChoiceBt);
            this.repaint();


            confirmSurveyChoiceBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    executeSurvey();
                }
            });
            this.revalidate();
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes the execution of survey
    public void executeSurvey() {
        int index = Integer.valueOf(chooseSurveyTextField.getText());
        Survey surveyChoice = allSurveys.getWithIndexOf(index - 1);
        executeQuestionsAndOptions(surveyChoice);


        this.add(collectResponseBt);
        this.revalidate();
        collectResponseBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectResponse(surveyChoice);
            }
        });


    }

    // MODIFIES: this
    // EFFECTS: given a survey executes the survey and produce labels and text fields to obtain users input
    private void executeQuestionsAndOptions(Survey surveyChoice) {
        for (int q = 0; q < surveyChoice.getNumOfQuestions(); q++) {
            nthQuestionLabel = new JLabel("Question "
                    + String.valueOf(q + 1) + ": " + surveyChoice.getQuestions().get(q));
            this.add(nthQuestionLabel);
            nthQuestionLabel.setForeground(Color.ORANGE);
            this.revalidate();

            for (int a = 0; a < surveyChoice.getAnswers().get(q).size(); a++) {
                nthOptionLabel = new JLabel(String.valueOf(a + 1) + ":" + surveyChoice.getAnswers().get(q).get(a));
                nthOptionLabel.setForeground(Color.ORANGE);
                this.add(nthOptionLabel);
            }
            //answer field
            nthAnswerField = new JTextField();
            this.add(nthAnswerField);
            answerToEachQuestionField.add(nthAnswerField);
            this.revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: obtains the values of users response to each question and updates the
    // answer entries of the given survey
    public void collectResponse(Survey survey) {
        for (int k = 0; k < survey.getNumOfQuestions(); k++) {
            int answer = Integer.valueOf(answerToEachQuestionField.get(k).getText());
            survey.changeResultOfEntries(survey, k, answer);
        }
        //out.println(allSurveys.getWithIndexOf(0).getAnswerEntries());
        JLabel message = new JLabel("Submission Successful!");
        message.setForeground(Color.PINK);
        this.add(message);
        this.revalidate();


    }
}
