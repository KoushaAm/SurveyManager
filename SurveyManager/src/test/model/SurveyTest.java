package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

// Test for Survey class
public class SurveyTest {

    private Survey mainSurveyTest;
    private Survey untitledSurveyTest;
    private ArrayList<String> questions = new ArrayList<String>();
    private ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<Integer>> allAnswerEntries = new ArrayList<ArrayList<Integer>>();

     void createSurvey() {
        ArrayList<Integer> answerEntries = new ArrayList<Integer>();
        ArrayList<String> answerForQuestion = new ArrayList<String>();

        //first set
        answerForQuestion.add("a1");
        answerForQuestion.add("a2");
        answerEntries.add(0);
        answerEntries.add(0);
        answers.add(answerForQuestion);
        allAnswerEntries.add(answerEntries);

        //second set
        answerEntries = new ArrayList<Integer>();
        answerForQuestion = new ArrayList<String>();
        answerForQuestion.add("a3");
        answerForQuestion.add("a4");
        answerEntries.add(0);
        answerEntries.add(0);
        answers.add(answerForQuestion);
        allAnswerEntries.add(answerEntries);

        // add questions
        questions.add("Q1");
        questions.add("Q2");

        mainSurveyTest = new Survey("title", 1, "123", questions, answers, allAnswerEntries);
        untitledSurveyTest = new Survey("", 2,  "456", questions, answers, allAnswerEntries);
    }

    @BeforeEach
    void runBeforeEach() {
        createSurvey();
    }

    @Test
    void testSurveyConstructor() {

        //test for title
        assertEquals("title", mainSurveyTest.getTitle());
        assertEquals("Untitled Survey", untitledSurveyTest.getTitle());

        //test for number of questions
        assertEquals(1 , mainSurveyTest.getNumOfQuestions());
        assertEquals(2, mainSurveyTest.getQuestions().size());


        //test for questions
        assertEquals("Q1", mainSurveyTest.getQuestions().get(0));
        assertEquals("Q2", mainSurveyTest.getQuestions().get(1));

        //test for answers
        assertEquals(2, mainSurveyTest.getAnswers().size());
        assertEquals("a1", mainSurveyTest.getAnswers().get(0).get(0));
        assertEquals("a2", mainSurveyTest.getAnswers().get(0).get(1));
        assertEquals("a3", mainSurveyTest.getAnswers().get(1).get(0));
        assertEquals("a4", mainSurveyTest.getAnswers().get(1).get(1));


        //test for answer entries
        assertEquals(0, mainSurveyTest.getAnswerEntries().get(0).get(0));
        assertEquals(0, mainSurveyTest.getAnswerEntries().get(0).get(1));
        assertEquals(0, mainSurveyTest.getAnswerEntries().get(1).get(0));
        assertEquals(0, mainSurveyTest.getAnswerEntries().get(1).get(1));

    }




    @Test
    void testAddAnswer() {
        String answer1 = "a5";
        String answer2 = "a6";
        ArrayList<String> answersForOneQuestion = new ArrayList<String>();
        answersForOneQuestion.add(answer1);
        answersForOneQuestion.add(answer2);

        answers.add(answersForOneQuestion);


        assertEquals(3, answers.size());
        assertEquals("a5", answers.get(2).get(0));
        assertEquals("a6", answers.get(2).get(1));
        assertEquals(2, answers.get(0).size());

    }

    @Test
    void testChangeResultOfEntries() {
         mainSurveyTest.changeResultOfEntries(mainSurveyTest, 0,1);
         assertEquals(1 , mainSurveyTest.getAnswerEntries().get(0).get(0));

         mainSurveyTest.changeResultOfEntries(mainSurveyTest, 0,2);
        assertEquals(1 , mainSurveyTest.getAnswerEntries().get(0).get(0));

        mainSurveyTest.changeResultOfEntries(mainSurveyTest, 0,1);
        assertEquals(2 , mainSurveyTest.getAnswerEntries().get(0).get(0));
    }







}