package ui;


import model.Event;
import model.EventLog;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// constructs a login page with JFrame

public class LoginOrSignUp extends RegisterPage {


    private boolean goingToMainPage = false;

    // MODIFIES: this
    //EFFECTS: constructs a Survey app frame
    public LoginOrSignUp() {
        super("Survey App");
    }


    // MODIFIES: this
    //EFFECTS: creates buttons and fields
    public void createButtonsAndFields() {
        super.createButtonsAndFields();
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj == loginBt) {
                    canFindUser();
                } else if (obj == signUpBt) {
                    saveUser();
                }
            }
        };
        loginBt.addActionListener(listener);
        signUpBt.addActionListener(listener);
    }






    // MODIFIES: this
    //EFFECT: checks if user already exist by called loadUser()
    public void canFindUser() {

        if (!fieldUsername.getText().equals("") && !fieldPassword.getText().equals("")) {
            user = new User(fieldUsername.getText(), fieldPassword.getText());
            loadUser(user);
        }
    }

    // MODIFIES: this
    //EFFECT: loads given users password and username
    public void loadUser(User user) {
        try {
            JsonReader jsonReader = new JsonReader(JSON_LOCATION + user.getUserName()
                    + "&" + user.getPassword() + ".json");
            this.user = jsonReader.readUser();
            goingToMainPage = true;
            frame.dispose();
            new VisualSurveyApp();
        } catch (IOException e) {
            registerMessage.setText("not found!");
        }
    }

    // MODIFIES: this
    //EFFECTS: saves user's info for signing up
    public void saveUser() {
        user = new User(fieldUsername.getText(), fieldPassword.getText());

        try {
            jsonWriterUser = new JsonWriter(JSON_LOCATION + user.getUserName()
                    + "&" + user.getPassword() + ".json");
            jsonWriterUser.open();
            jsonWriterUser.writeUser(user);
            jsonWriterUser.close();

//            System.out.println("Saved " + user.getUserName() + "'s" + " info to :" + JSON_LOCATION);
        } catch (FileNotFoundException e) {
//            System.out.println("Cannot write to: " + JSON_LOCATION);
        }
        goingToMainPage = true;
        new VisualSurveyApp();
        frame.dispose();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (!goingToMainPage) {
            for (Event ev : EventLog.getInstance()) {
                System.out.println(ev.toString());
            }
        }
    }




}
