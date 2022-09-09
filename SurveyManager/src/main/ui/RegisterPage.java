package ui;

import model.Event;
import model.EventLog;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

//represent a register a page or first page


public class RegisterPage extends JFrame implements WindowListener {


    public static final int HEIGHT = 500;
    public static final int WIDTH = 450;

    protected JFrame frame = new JFrame();
    protected JLabel topLabel;
    protected JTextField fieldUsername = new JTextField();
    protected JPasswordField fieldPassword = new JPasswordField();
    protected JButton loginBt = new JButton("Login");
    protected JButton signUpBt = new JButton("Sign up");
    protected JLabel usernameLabel = new JLabel("Username");
    protected JLabel passwordLabel = new JLabel("Password");
    protected JLabel registerMessage = new JLabel("");
    protected JLabel surveyAppIntroLabel = new JLabel("Survey Manager");

    protected JPanel mainPanel;


    //user login
    protected User user;
    protected static final String JSON_LOCATION = "./data/";
    protected JsonWriter jsonWriterUser;


    // source: https://www.freeiconspng.com/images/survey-icon
    private ImageIcon icon = new ImageIcon("./data/survey-icon-12.png");


    private BufferedImage image;

    // MODIFIES: this
    //EFFECTS: constructs a Survey app frame
    public RegisterPage(String name) {
        super(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        createButtonsAndFields();
        frame.addWindowListener(this);
        frame.setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: create Buttons and fields for login/signup page
    public void createButtonsAndFields() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(icon);

        iconLabel.setBackground(Color.BLACK);
        iconLabel.setBounds(160, 300, 150,150);
        frame.add(iconLabel);

        addButtonsAndTextFields();


        frame.revalidate();
    }

    // MODIFIES: this
    //EFFECTS: adds buttons and fields to JFrame
    protected void addButtonsAndTextFields() {
        surveyAppIntroLabel.setFont(new Font("Helvetica PLAIN", Font.BOLD, 20));
        surveyAppIntroLabel.setBounds(140, 50, 200, 25);
        frame.add(surveyAppIntroLabel);

        usernameLabel.setBounds(50, 100, 75, 25);
        passwordLabel.setBounds(50, 150, 75, 25);

        frame.add(usernameLabel);
        frame.add(passwordLabel);

        registerMessage.setBounds(50, 250, 75, 75);
        registerMessage.setFont(new Font(null, Font.BOLD, 12));
        frame.add(registerMessage);

        fieldUsername.setBounds(125, 100, 200, 25);
        fieldPassword.setBounds(125, 150, 200, 25);
        frame.add(fieldUsername);
        frame.add(fieldPassword);

        loginBt.setBounds(125, 220, 200, 25);
        frame.add(loginBt);
//
        signUpBt.setBounds(125, 250, 200, 25);
        frame.add(signUpBt);
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
