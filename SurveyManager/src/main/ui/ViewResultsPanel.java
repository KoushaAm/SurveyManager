package ui;

import model.AllSurveys;
import model.Survey;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//represents a view results panel

public class ViewResultsPanel extends JPanel {


    private JScrollPane scrollPanelViewResults;
    private JLabel viewResultsIntro;
    private JTextField chooseSurveyTextFieldResults;
    private JButton confirmChoiceBtResults;
    private JButton viewResultBt;


    AllSurveys allSurveys;

    //MODIFIES: this
    //EFFECTS: constructs a view results panel
    public ViewResultsPanel(AllSurveys allSurveys) {
        this.allSurveys = allSurveys;
        chooseSurveyTextFieldResults = new JTextField();
        confirmChoiceBtResults = new JButton("Confirm choice");
        viewResultBt = new JButton("View results");


    }


    // MODIFIES: this
    // EFFECTS : constructs a results tab and returns the constructs JComponent
    public JComponent makeResultsTab() {
        scrollPanelViewResults = new JScrollPane(this);
        this.setLayout(new GridLayout(100, 2));


        this.setBackground(Color.DARK_GRAY);
        viewResultsIntro = new JLabel("View results");
        viewResultsIntro.setForeground(Color.orange);
        viewResultsIntro.setFont(new Font("Helvetica PLAIN", Font.BOLD, 20));


        this.add(viewResultsIntro);
        this.add(viewResultBt);

        viewResultBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSurveysForResults();
                viewResultBt.setEnabled(false);
            }
        });

        return scrollPanelViewResults;

    }

    //MODIFIES: this
    //EFFECTS: constructs labels that show the titles of all surveys
    public void showSurveysForResults() {

        if (allSurveys.getNumOfAllSurveys() > 0) {
            for (int k = 0; k < allSurveys.getNumOfAllSurveys(); k++) {
                JLabel surveyOptionLabel =
                        new JLabel(String.valueOf(k + 1) + ". " + allSurveys.getWithIndexOf(k).getTitle());
                surveyOptionLabel.setForeground(Color.yellow);
                surveyOptionLabel.setSize(20,20);
                this.add(surveyOptionLabel);
                this.revalidate();
            }

            this.add(chooseSurveyTextFieldResults);
            this.add(confirmChoiceBtResults);
            this.revalidate();


            //setUpButtonListenerConfirmSurveyChoiceForResult();
            confirmChoiceBtResults.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    findTargetSurvey();
                    confirmChoiceBtResults.setEnabled(false);
                }
            });

            this.revalidate();
        }

    }

    //MODIFIES: this
    //EFFECTS: finds the target index in all surveys and calls viewResults to display the results
    public void findTargetSurvey() {
        int index = Integer.valueOf(chooseSurveyTextFieldResults.getText());
        Survey surveyChoice = allSurveys.getWithIndexOf(index - 1);
        viewResults(surveyChoice);

    }

    //MODIFIES: this
    //EFFECTS; displays the result of each question so far for each survey, given the survey
    public void viewResults(Survey surveyChoice) {

        JLabel labelTitle = new JLabel("Survey name: " + surveyChoice.getTitle());
        labelTitle.setForeground(Color.ORANGE);
        this.add(labelTitle);
        for (int j = 0; j < surveyChoice.getNumOfQuestions(); j++) {
            JLabel questionIntro = new JLabel("Question " + String.valueOf(j + 1) + ": "
                    + surveyChoice.getQuestions().get(j));
            questionIntro.setForeground(Color.ORANGE);
            this.repaint();
            this.add(questionIntro);
            for (int x = 0; x < surveyChoice.getAnswers().get(j).size(); x++) {
                String eachQuestion = surveyChoice.getAnswers().get(j).get(x);
                String eachAnswer = String.valueOf(surveyChoice.getAnswerEntries().get(j).get(x));
                JLabel eachResultLabel = new JLabel(eachQuestion + ": " + eachAnswer);
                eachResultLabel.setForeground(Color.PINK);
                this.add(eachResultLabel);
                this.repaint();
            }
            this.revalidate();
        }

    }



}
