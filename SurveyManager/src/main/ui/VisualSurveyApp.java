package ui;

import model.AllSurveys;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//Constructs VisualSurveyApp based on JFrame

// citation : https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
//            https://beginnersbook.com/2015/07/java-swing-tutorial/


public class VisualSurveyApp extends JFrame implements WindowListener {

    private static final String ALL_SURVEYS_LOCATION = "./data/allSurveys.json";
    JsonWriter jsonWriteAllSurveys = new JsonWriter(ALL_SURVEYS_LOCATION);
    JsonReader jsonReader;

    public static final int HEIGHT = 1000;
    public static final int WEIGHT = 1000;

    JComponent panel1 = new JPanel();
    JComponent panel2 = new JPanel();
    JComponent panel4 = new JPanel();
    JComponent panel5 = new JPanel();

    JFrame frame = new JFrame();
    JButton loadSurveysBt = new JButton("Load surveys");
    JButton saveSurveysBt = new JButton("Save activity");
    JButton logOutBt = new JButton("Log out");


    JPanel actionPanel = new JPanel();
    JTabbedPane tabbedPane = new JTabbedPane();

    Container mainContainer = this.getContentPane();


    //create survey
    CardLayout cardLayoutOfCreateSurvey = new CardLayout();
    CreateSurveyPanel createSurveyPanel;

    // for create survey
    int numOfCardLayouts = 1;

    // all surveys
    AllSurveys allSurveys = new AllSurveys();


    //do survey
    int numOfCardLayoutsDoSurvey = 0;
    DoSurveyPanel doSurveyPanel;
    CardLayout doSurveyCardLayout = new CardLayout();

    ArrayList<JLabel> wasteLabels = new ArrayList<>();

    //view results tab
    int numOfCardLayoutsForViewResults = 0;
    CardLayout viewResultsLayout = new CardLayout();
    ViewResultsPanel viewResultsPanel;


    //terminate Survey
    int numOfCardLayoutsForDeleteSurveys = 1;
    DeleteSurveyPanel deleteSurveyPanel;
    CardLayout cardLayoutDeleteSurvey = new CardLayout();

    // view surveysTab
    JPanel viewSurveys;
    JScrollPane scrollPaneViewSurvey;
    JButton updateSurveys = new JButton("Update");
    JLabel introViewSurveys = new JLabel("View Surveys");



    //MODIFIES: this
    //EFFECTS: construct VisualSurveyApp
    public VisualSurveyApp() {
        super("Survey App");
        frame.setSize(WEIGHT, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(this);
        // main container
        createMainContainer();

        // middle menu panel
        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(new LineBorder(Color.BLACK, 3));
        middlePanel.setLayout(new FlowLayout(4, 4, 4));
        middlePanel.setBackground(new Color(168, 91, 40));
        JPanel gridPanel = new JPanel();

        // action panel
        createMainActionPanel();

        //grid layout for menu buttons
        gridPanel.setLayout(new GridLayout(4, 1, 3, 3));
        gridPanel.setBorder(new LineBorder(Color.BLACK, 0));
        gridPanel.setBackground(Color.darkGray);

        gridPanel.add(saveSurveysBt);
        gridPanel.add(loadSurveysBt);
        gridPanel.add(logOutBt);

        middlePanel.add(gridPanel);

        mainContainer.add(middlePanel, BorderLayout.WEST);

        setUpLoadSurveysListener();
        setUpSaveSurveysListener();
        setUpButtonListenerLogoutBt();
        frame.setVisible(true);
    }

    // EFFECTS: constructs the main container
    private void createMainContainer() {
        mainContainer.setLayout(new BorderLayout(8, 1));
        mainContainer.setBackground(Color.darkGray);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.lightGray));
        frame.add(mainContainer);
    }

    // EFFECTS: constructs tabs for the main container
    public void createMainActionPanel() {
        actionPanel.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        createSurveyTab();
        doSurveyTab();
        createTab3();
        viewResultsTab();
        deleteSurveyTab();

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    //MODIFIES: this
    //EFFECTS : constructs tab 5 (delete survey)
    private void deleteSurveyTab() {
        panel5.setLayout(cardLayoutDeleteSurvey);
        deleteSurveyPanel = new DeleteSurveyPanel(allSurveys);
        JComponent onePanel = deleteSurveyPanel.makeTerminateSurvey();
        panel5.add(onePanel, "1");
        cardLayoutDeleteSurvey.show(panel5, "1");
        addRefreshButtonForDeleteSurvey();

        tabbedPane.addTab("Delete Survey", panel5);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_4);
        tabbedPane.setForegroundAt(4, Color.orange);
        tabbedPane.setBackgroundAt(4, Color.black);
    }

    //EFFECTS : constructs tab 4 (view results)
    private void viewResultsTab() {
        panel4.setLayout(viewResultsLayout);
        viewResultsPanel = new ViewResultsPanel(allSurveys);
        JComponent onePanel = viewResultsPanel.makeResultsTab();
        panel4.add(onePanel, "1");
        viewResultsLayout.show(panel4, "1");
        addFinishViewingResults();

        tabbedPane.addTab("View results", panel4);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        tabbedPane.setForegroundAt(3, Color.orange);
        tabbedPane.setBackgroundAt(3, Color.black);
    }

    //MODIFIES: this
    //EFFECTS : constructs tab 3 (view surveys)
    private void createTab3() {
        viewResultsPanel = new ViewResultsPanel(allSurveys);
        JComponent panel3 = makeViewAllSurveys();
        tabbedPane.addTab("View all surveys", panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        tabbedPane.setForegroundAt(2, Color.orange);
        tabbedPane.setBackgroundAt(2, Color.black);
    }

    //MODIFIES: this
    //EFFECTS : constructs tab 2 (do survey)
    private void doSurveyTab() {
        panel2.setLayout(doSurveyCardLayout);
        doSurveyPanel = new DoSurveyPanel(allSurveys);
        JComponent onePanel = doSurveyPanel.makeDoSurveyTab();
        panel2.add(onePanel, "1");
        addFinishDoingSurveyBt();
        doSurveyCardLayout.show(panel2, "1");

        tabbedPane.addTab("Do Survey", panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setForegroundAt(1, Color.orange);
        tabbedPane.setBackgroundAt(1, Color.black);
    }

    //MODIFIES: this
    //EFFECTS: constructs create survey tab
    private void createSurveyTab() {
        panel1.setLayout(cardLayoutOfCreateSurvey);
        createSurveyPanel = new CreateSurveyPanel(allSurveys);
        JComponent onePanel = createSurveyPanel.makeCreateSurveyTab();
        addCreateSurveyButton();
        panel1.add(onePanel, "1");
        cardLayoutOfCreateSurvey.show(panel1,  "1");


        tabbedPane.addTab("Create survey", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.setForegroundAt(0, Color.orange);
        tabbedPane.setBackgroundAt(0, Color.black);
    }


    //MODIFIES: this
    //EFFECTS: re-constructs create Survey tab and adds createSurveyBt
    public void addCreateSurveyButton() {
        JButton createSurveyBt = new JButton("Create survey");
        createSurveyPanel.add(createSurveyBt);
        createSurveyBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSurveyPanel.createSurvey();
                createSurveyPanel = new CreateSurveyPanel(allSurveys);
                JComponent newPanel = createSurveyPanel.makeCreateSurveyTab();
                addCreateSurveyButton();
                numOfCardLayouts++;
                panel1.add(newPanel, String.valueOf(numOfCardLayouts));
                cardLayoutOfCreateSurvey.show(panel1,  String.valueOf(numOfCardLayouts));
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: re-constructs do survey tab and adds finish survey button
    public void addFinishDoingSurveyBt() {
        JButton finishSurveyBt = new JButton("Done");
        doSurveyPanel.add(finishSurveyBt);
        doSurveyPanel.revalidate();

        finishSurveyBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSurveyPanel = new DoSurveyPanel(allSurveys);
                JComponent newPanel = doSurveyPanel.makeDoSurveyTab();
                addFinishDoingSurveyBt();
                numOfCardLayoutsDoSurvey++;
                panel2.add(newPanel, String.valueOf(numOfCardLayoutsDoSurvey));
                doSurveyCardLayout.show(panel2, String.valueOf(numOfCardLayoutsDoSurvey));
            }
        });
    }

    //MODIFIES: this
    // EFFECTS: re-constructs the view results tab and adds finish viewing results Bt
    public void addFinishViewingResults() {
        JButton finishViewingResultsBt = new JButton("Refresh");
        viewResultsPanel.add(finishViewingResultsBt);
        viewResultsPanel.revalidate();

        finishViewingResultsBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewResultsPanel = new ViewResultsPanel(allSurveys);
                JComponent newPanel = viewResultsPanel.makeResultsTab();
                addFinishViewingResults();
                numOfCardLayoutsForViewResults++;
                panel4.add(newPanel, String.valueOf(numOfCardLayoutsForViewResults));
                viewResultsLayout.show(panel4, String.valueOf(numOfCardLayoutsForViewResults));
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: add refresh button with action listener
    public void addRefreshButtonForDeleteSurvey() {
        JButton refreshDeleteSurveyBt = new JButton("Refresh");
        deleteSurveyPanel.add(refreshDeleteSurveyBt);
        deleteSurveyPanel.revalidate();

        refreshDeleteSurveyBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSurveyPanel = new DeleteSurveyPanel(allSurveys);
                JComponent newPanel = deleteSurveyPanel.makeTerminateSurvey();
                addRefreshButtonForDeleteSurvey();
                numOfCardLayoutsForDeleteSurveys++;
                panel5.add(newPanel, String.valueOf(numOfCardLayoutsForDeleteSurveys));
                cardLayoutDeleteSurvey.show(panel5, String.valueOf(numOfCardLayoutsForDeleteSurveys));
            }
        });
    }


    //MODIFIES: this
    //EFFECTS: constructs the listener for logOutBt button
    public void setUpButtonListenerLogoutBt() {
        logOutBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginOrSignUp();
                frame.dispose();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: constructs the listener for collectResponseBt button
    public void setUpLoadSurveysListener() {
        loadSurveysBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllSurveys();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: constructs the listener for saveSurveysBt button
    public void setUpSaveSurveysListener() {
        saveSurveysBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAllSurveys();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: constructs the listener for updateSurveys button
    public void setUpButtonListenerViewUpdatedSurveys() {
        updateSurveys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdatedSurveys();
            }
        });
    }


    //MODIFIES: this
    // EFFECTS: loads all surveys to the existing all surveys from JSON File
    public void loadAllSurveys() {

        try {

            AllSurveys oldAllSurveys = new AllSurveys();
            jsonReader = new JsonReader(ALL_SURVEYS_LOCATION);

            oldAllSurveys = jsonReader.readAllSurveys();
            allSurveys.mergeTwoAllSurveys(oldAllSurveys);

        } catch (IOException e) {
            //System.out.println("can't read");
        }

    }

    //MODIFIES: this
    // EFFECTS: saves all surveys to JSON file
    public void saveAllSurveys() {
        try {
            JsonReader jsonReadAllSurveys = new JsonReader(ALL_SURVEYS_LOCATION);

            AllSurveys oldAllSurveys = new AllSurveys();
            oldAllSurveys = jsonReadAllSurveys.readAllSurveys();
            allSurveys.mergeTwoAllSurveys(oldAllSurveys);

            jsonWriteAllSurveys.open();
            jsonWriteAllSurveys.writeAllSurveys(allSurveys);
            jsonWriteAllSurveys.close();

            //System.out.println("Saved all surveys to " + ALL_SURVEYS_LOCATION);

        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write file to " + ALL_SURVEYS_LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //MODIFIES: this
    // EFFECTS: displays all surveys in form of labels and updates it
    public JComponent makeViewAllSurveys() {
        viewSurveys = new JPanel();
        scrollPaneViewSurvey = new JScrollPane(viewSurveys);
        viewSurveys.setLayout(new GridLayout(100, 2));
        viewSurveys.setBackground(Color.DARK_GRAY);
        introViewSurveys.setForeground(Color.orange);
        introViewSurveys.setFont(new Font("Helvetica PLAIN", Font.BOLD, 20));
        viewSurveys.add(introViewSurveys);
        viewSurveys.add(updateSurveys);
        setUpButtonListenerViewUpdatedSurveys();
        return scrollPaneViewSurvey;
    }

    //MODIFIES: this
    // EFFECTS: constructs labels for each survey and adds it to the tab
    public void showUpdatedSurveys() {
        resetViewAllSurveys();
        viewSurveys.revalidate();
        for (int i = 0; i < allSurveys.getNumOfAllSurveys(); i++) {
            JLabel title = new JLabel(allSurveys.getWithIndexOf(i).getTitle());
            title.setForeground(Color.PINK);
            title.setFont(new Font("Helvetica PLAIN", Font.BOLD, 15));
            title.setForeground(Color.orange);
            wasteLabels.add(title);
            viewSurveys.add(title);

        }
        viewSurveys.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: resets view surveys
    public void resetViewAllSurveys() {
        for (JLabel label : wasteLabels) {
            viewSurveys.remove(label);
        }

    }

    //EFFECT: do when window is opened
    @Override
    public void windowOpened(WindowEvent e) {

    }

    //EFFECTS: Action when window is closing
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event ev: EventLog.getInstance()) {
            System.out.println(ev.toString());
        }
    }

    //EFFECTS: Action when window closed
    @Override
    public void windowClosed(WindowEvent e) {

    }

    //EFFECTS: Actions during window icon
    @Override
    public void windowIconified(WindowEvent e) {

    }

    //EFFECTS: Action when window has no icon
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    //EFFECTS: Action when window activated
    @Override
    public void windowActivated(WindowEvent e) {

    }

    //EFFECTS: Action when window activated
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
