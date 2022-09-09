package ui;

import model.AllSurveys;
import model.Survey;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

//represents a delete survey panel

public class DeleteSurveyPanel extends JPanel {


    private int indexOfTarget = -1;
    private static final String ALL_SURVEYS_LOCATION = "./data/allSurveys.json";
    private AllSurveys allSurveys;
    int index;
    private JScrollPane scrollPanelTerminateSurvey;
    private JLabel terminateSurveyIntro;
    private JButton terminateSurveyBt;
    private JTextField chooseSurveyTextFieldTerminate;
    private JButton confirmSurveyChoiceBtTerminator;
    private JTextField passwordField;
    private JButton confirmPasswordBt;
    private JLabel askForPasswordLabel;
    private JLabel messageLabel;

    // MODIFIES: this
    //EFFECTS: constructs delete survey panel
    public DeleteSurveyPanel(AllSurveys allSurveys) {
        this.allSurveys = allSurveys;
        terminateSurveyBt = new JButton("Delete a survey");
        chooseSurveyTextFieldTerminate = new JTextField();
        confirmSurveyChoiceBtTerminator = new JButton("Confirm you choice");

        askForPasswordLabel = new JLabel("Enter the password:");
        askForPasswordLabel.setForeground(Color.PINK);
        passwordField = new JTextField();
        confirmPasswordBt = new JButton("Confirm to delete");
        messageLabel = new JLabel();
    }

    // MODIFIES: this
    // EFFECTS: constructs terminateSurvey Tab
    public JComponent makeTerminateSurvey() {
        scrollPanelTerminateSurvey = new JScrollPane(this);
        this.setLayout(new GridLayout(100, 2));
        this.setBackground(Color.DARK_GRAY);
        terminateSurveyIntro = new JLabel("Delete survey");
        terminateSurveyIntro.setForeground(Color.ORANGE);
        terminateSurveyIntro.setFont(new Font("Helvetica PLAIN", Font.BOLD, 20));
        this.add(terminateSurveyIntro);
        this.add(terminateSurveyBt);

        terminateSurveyBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSurveysForTermination();
                terminateSurveyBt.setEnabled(false);
            }
        });
        return scrollPanelTerminateSurvey;
    }

    // MODIFIES: this
    // EFFECTS: shows all surveys tiles in form of label
    public void showSurveysForTermination() {
        if (allSurveys.getNumOfAllSurveys() > 0) {
            for (int k = 0; k < allSurveys.getNumOfAllSurveys(); k++) {
                JLabel surveyOptionLabel =
                        new JLabel(String.valueOf(k + 1) + ". " + allSurveys.getWithIndexOf(k).getTitle());
                surveyOptionLabel.setForeground(Color.yellow);
                this.add(surveyOptionLabel);
                this.revalidate();
            }

            this.add(chooseSurveyTextFieldTerminate);
            this.add(confirmSurveyChoiceBtTerminator);
            this.repaint();

            confirmSurveyChoiceBtTerminator.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    findTargetTSurveyForTermination();
                }
            });

            this.revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: finds the targeted survey and calls the remove survey function to remove it
    public void findTargetTSurveyForTermination() {

        index = Integer.valueOf(chooseSurveyTextFieldTerminate.getText()) - 1;


        this.add(askForPasswordLabel);
        this.add(passwordField);
        this.add(confirmPasswordBt);

        this.revalidate();

        confirmPasswordBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(allSurveys.getWithIndexOf(index).getPassword());
                if (passwordField.getText().equals(allSurveys.getWithIndexOf(index).getPassword())) {
                    messageLabel.setText("Survey" + " '" + allSurveys.getWithIndexOf(index).getTitle() + "' "
                            + " deleted successfully");
                    messageLabel.setForeground(Color.green);
                    Survey surveyChoice = allSurveys.getWithIndexOf(index);
                    deleteSurveyFromJson(surveyChoice, index);
                    allSurveys.removeSurveyFromAllSurveys(index);

                } else {
                    messageLabel.setText("Incorrect password. Cannot delete this survey.");
                    messageLabel.setForeground(Color.RED);
                }
            }
        });

        this.add(messageLabel);

    }

    // MODIFIES: this
    // EFFECTS: delete the survey with the given index from Json file located in ALL_SURVEYS_LOCATION.
    public void deleteSurveyFromJson(Survey surveyChoice, int index) {
        try {
            JsonReader reader = new JsonReader(ALL_SURVEYS_LOCATION);
            AllSurveys savedAllSurveys = reader.readAllSurveys();
            ArrayList<Survey> allSurveysArray = savedAllSurveys.getAllSurveys();

            for (Survey s : allSurveysArray) {
                if (s.getTitle().equals(surveyChoice.getTitle())
                        && s.getPassword().equals(surveyChoice.getPassword())) {
                    indexOfTarget = allSurveysArray.indexOf(s);
                }
            }
            if (indexOfTarget != -1) {
                savedAllSurveys.removeSurveyFromAllSurveys(indexOfTarget);
            }


            JsonWriter writer = new JsonWriter(ALL_SURVEYS_LOCATION);
            writer.open();
            writer.writeAllSurveys(savedAllSurveys);
            writer.close();

        } catch (IOException e) {
            //System.out.println("All Surveys not found");
        } catch (IndexOutOfBoundsException e) {
            // survey hasn't been saved yet
        }

    }

}
