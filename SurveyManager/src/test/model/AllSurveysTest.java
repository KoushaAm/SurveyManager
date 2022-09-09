package model;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



import java.util.ArrayList;

// Test class for AllSurvey class

public class AllSurveysTest {

    private AllSurveys allSurveysTest;

    private ArrayList<String> questions = new ArrayList<String>();
    private ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<Integer>> allAnswerEntries = new ArrayList<ArrayList<Integer>>();
    private Survey surveyTest1;
    private Survey surveyTest2;
    private Survey surveyTest3;


    Survey createSurvey(String title, String password) {
        Survey surveyTest;
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

        surveyTest = new Survey(title,1, password, questions, answers, allAnswerEntries);;

        return surveyTest;
    }

    @BeforeEach
    void runBeforeEach() {
        allSurveysTest = new AllSurveys();
        surveyTest1 = createSurvey("s1", "123");
        surveyTest2 = createSurvey("s2", "123");
        surveyTest3 = createSurvey("s3", "a123");

        allSurveysTest.addSurveyToAllSurveys(surveyTest1);
        allSurveysTest.addSurveyToAllSurveys(surveyTest2);
    }

    @Test
    void testAddSurveyToAllSurveys(){
        assertEquals(2, allSurveysTest.getAllSurveys().size());
        assertEquals(2, allSurveysTest.getNumOfAllSurveys());
        allSurveysTest.addSurveyToAllSurveys(surveyTest3);
        assertEquals("s3", allSurveysTest.getWithIndexOf(2).getTitle());
        assertEquals(3 , allSurveysTest.getNumOfAllSurveys());
    }


    @Test
    void testRemoveSurveyFromAllSurveys() {
        allSurveysTest.removeSurveyFromAllSurveys(0);
        assertEquals(1, allSurveysTest.getNumOfAllSurveys());
        assertEquals("s2", allSurveysTest.getWithIndexOf(0).getTitle());

        allSurveysTest.removeSurveyFromAllSurveys(0);
        assertEquals(0, allSurveysTest.getNumOfAllSurveys());
        assertTrue(allSurveysTest.isItEmpty());
    }

    @Test
    void testIsItEmpty() {
        AllSurveys allSurveysTestEmpty = new AllSurveys();
        assertTrue(allSurveysTestEmpty.isItEmpty());
        assertFalse(allSurveysTest.isItEmpty());
    }

    @Test
    void testMergeTwoAllSurveysWithDifferentSurveys() {
        AllSurveys allSurveys1 = new AllSurveys();
        AllSurveys allSurveys2 = new AllSurveys();
        Survey s1 = createSurvey("s1", "123");
        Survey s2 = createSurvey("s2", "2881");
        Survey s3 = createSurvey("s3", "ksj");
        Survey s4 = createSurvey("s4", "his");

        allSurveys1.addSurveyToAllSurveys(s1);
        allSurveys1.addSurveyToAllSurveys(s2);
        allSurveys2.addSurveyToAllSurveys(s3);
        allSurveys2.addSurveyToAllSurveys(s4);

        allSurveys1.mergeTwoAllSurveys(allSurveys2);

        assertEquals(4, allSurveys1.getNumOfAllSurveys());
        assertEquals("s1", allSurveys1.getWithIndexOf(0).getTitle());
        assertEquals("s2", allSurveys1.getWithIndexOf(1).getTitle());
        assertEquals("s3", allSurveys1.getWithIndexOf(2).getTitle());
        assertEquals("s4", allSurveys1.getWithIndexOf(3).getTitle());

    }


    @Test
    void testMergeTwoAllSurveysWithOneRepeatedSurvey() {
        AllSurveys allSurveys1 = new AllSurveys();
        AllSurveys allSurveys2 = new AllSurveys();
        Survey s1 = createSurvey("s1", "123");
        Survey s2 = createSurvey("s2", "2881");
        Survey s3 = createSurvey("s1", "123");
        Survey s4 = createSurvey("s4", "his");

        allSurveys1.addSurveyToAllSurveys(s1);
        allSurveys1.addSurveyToAllSurveys(s2);
        allSurveys2.addSurveyToAllSurveys(s3);
        allSurveys2.addSurveyToAllSurveys(s4);

        allSurveys1.mergeTwoAllSurveys(allSurveys2);
        allSurveys2.mergeTwoAllSurveys(allSurveys1);

        assertEquals(3, allSurveys1.getNumOfAllSurveys());
        assertEquals("s1", allSurveys1.getWithIndexOf(0).getTitle());
        assertEquals("s2", allSurveys1.getWithIndexOf(1).getTitle());
        assertEquals("s4", allSurveys1.getWithIndexOf(2).getTitle());

        assertEquals("s1", allSurveys2.getWithIndexOf(0).getTitle());
        assertEquals("s4", allSurveys2.getWithIndexOf(1).getTitle());
        assertEquals("s2", allSurveys2.getWithIndexOf(2).getTitle());

    }

    @Test
    void testMergeTwoAllSurveysWithOnlyADifferentPassword() {
        AllSurveys allSurveys1 = new AllSurveys();
        AllSurveys allSurveys2 = new AllSurveys();
        Survey s1 = createSurvey("s1", "123");
        Survey s2 = createSurvey("s2", "2881");
        Survey s3 = createSurvey("s1", "1344");
        Survey s4 = createSurvey("s4", "his");

        allSurveys1.addSurveyToAllSurveys(s1);
        allSurveys1.addSurveyToAllSurveys(s2);
        allSurveys2.addSurveyToAllSurveys(s3);
        allSurveys2.addSurveyToAllSurveys(s4);

        allSurveys1.mergeTwoAllSurveys(allSurveys2);
        allSurveys2.mergeTwoAllSurveys(allSurveys1);

        assertEquals(4, allSurveys1.getNumOfAllSurveys());
        assertEquals("s1", allSurveys1.getWithIndexOf(0).getTitle());
        assertEquals("s2", allSurveys1.getWithIndexOf(1).getTitle());
        assertEquals("s1", allSurveys1.getWithIndexOf(2).getTitle());
        assertEquals("s4", allSurveys1.getWithIndexOf(3).getTitle());

        assertEquals("s1", allSurveys2.getWithIndexOf(0).getTitle());
        assertEquals("s4", allSurveys2.getWithIndexOf(1).getTitle());
        assertEquals("s1", allSurveys2.getWithIndexOf(2).getTitle());
        assertEquals("s2", allSurveys2.getWithIndexOf(3).getTitle());

    }

    @Test
    void testMergeTwoAllSurveysWithTwoRepeatedSurvey() {
        AllSurveys allSurveys1 = new AllSurveys();
        AllSurveys allSurveys2 = new AllSurveys();
        Survey s1 = createSurvey("s1", "123");
        Survey s2 = createSurvey("s2", "2881");
        Survey s3 = createSurvey("s2", "2881");
        Survey s4 = createSurvey("s1", "123");

        allSurveys1.addSurveyToAllSurveys(s1);
        allSurveys1.addSurveyToAllSurveys(s2);
        allSurveys2.addSurveyToAllSurveys(s3);
        allSurveys2.addSurveyToAllSurveys(s4);

        allSurveys1.mergeTwoAllSurveys(allSurveys2);
        allSurveys2.mergeTwoAllSurveys(allSurveys1);

        assertEquals(2, allSurveys1.getNumOfAllSurveys());
        assertEquals("s1", allSurveys1.getWithIndexOf(0).getTitle());
        assertEquals("s2", allSurveys1.getWithIndexOf(1).getTitle());

        assertEquals("s2", allSurveys2.getWithIndexOf(0).getTitle());
        assertEquals("s1", allSurveys2.getWithIndexOf(1).getTitle());

    }


    @Test
    void testMergeTwoAllSurveysWithOneEmpty() {
        AllSurveys allSurveys1 = new AllSurveys();
        AllSurveys allSurveys2 = new AllSurveys();
        Survey s1 = createSurvey("s1", "123");
        Survey s2 = createSurvey("s2", "2881");


        allSurveys1.addSurveyToAllSurveys(s1);
        allSurveys1.addSurveyToAllSurveys(s2);

        allSurveys1.mergeTwoAllSurveys(allSurveys2);

        allSurveys2.mergeTwoAllSurveys(allSurveys1);

        assertEquals(2, allSurveys1.getNumOfAllSurveys());
        assertEquals("s1", allSurveys1.getWithIndexOf(0).getTitle());
        assertEquals("s2", allSurveys1.getWithIndexOf(1).getTitle());
        assertEquals("s1", allSurveys2.getWithIndexOf(0).getTitle());
        assertEquals("s2", allSurveys2.getWithIndexOf(1).getTitle());
    }







}
