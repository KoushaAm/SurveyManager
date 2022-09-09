package persistence;

// This class has been created with help of JsonWriterTest Class in JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
// (Paul Carter, 2020)

import model.AllSurveys;
import model.Survey;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {



    // (Paul Carter, 2020)
    @Test
    void testWriterInvalidFileDestinationForUser() {
        try {
            User user = new User("someoneTest", "abc");
            JsonWriter writer = new JsonWriter("./data/my\0invalid:fileName.json");
            writer.open();
            fail("IOException not caught.");

        } catch (FileNotFoundException e) {
            //expected
        }
    }


    // (Paul Carter, 2020)
    @Test
    void testWriterInvalidFileDestinationForAllSurvey() {
        try {
            AllSurveys allSurveys = new AllSurveys();
            JsonWriter writer = new JsonWriter("./data/my\0invalid:fileName.json");
            writer.open();
            fail("IOException not caught.");

        } catch (FileNotFoundException e) {
            //expected
        }
    }

    // (Paul Carter, 2020)
    @Test
    void testWritingEmptyUser() {
        try {
            User user = new User("", "");
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyUser.json");
            writer.open();
            writer.writeUser(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyUser.json");
            User userCopy = reader.readUser();
            assertEquals("", userCopy.getUserName());
            assertEquals("", userCopy.getPassword());

        }
        catch (IOException e) {
            fail("can't find empty user file");
        }
    }

    // (Paul Carter, 2020)
    @Test
    void testWritGeneralUser() {
        try {
            User user = new User("someone", "abc");
            JsonWriter writer = new JsonWriter("./data/testWriteGeneralUser.json");
            writer.open();
            writer.writeUser(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/GeneralUserTest.json");
            User userCopy = reader.readUser();
            assertEquals("someone", userCopy.getUserName());
            assertEquals("abc", userCopy.getPassword());

        }
        catch (IOException e) {
            fail("can't find empty user file");
        }
    }

    // (Paul Carter, 2020)
    @Test
    void testWriteEmptyAllSurveys() {
        try {
            AllSurveys emptyAllSurveys = new AllSurveys();
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyAllSurveys.json");
            writer.open();
            writer.writeAllSurveys(emptyAllSurveys);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyAllSurveys.json");
            AllSurveys emptyAllSurveysCopy = reader.readAllSurveys();
            assertEquals(0, emptyAllSurveys.getNumOfAllSurveys());

        } catch (IOException e) {
            fail("Exception shouldn't be caught");
        }
    }


    // (Paul Carter, 2020)
    @Test
    void testWriteGeneralAllSurveys() {
        try {
            AllSurveys allSurveys = new AllSurveys();
            Survey s1 = createSurveyForWriterTest("s1", "abc", "Q1");
            Survey s2 = createSurveyForWriterTest("s2", "bdc", "Q2");
            allSurveys.addSurveyToAllSurveys(s1);
            allSurveys.addSurveyToAllSurveys(s2);
            JsonWriter writer = new JsonWriter("./data/testWriteGeneralAllSurveys.json");
            writer.open();
            writer.writeAllSurveys(allSurveys);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteGeneralAllSurveys.json");
            AllSurveys allSurveysCopy = reader.readAllSurveys();



            assertEquals(2, allSurveysCopy.getNumOfAllSurveys());
            assertEquals("s1", allSurveysCopy.getWithIndexOf(0).getTitle());
            assertEquals("s2", allSurveysCopy.getWithIndexOf(1).getTitle());

            //password
            assertEquals("abc", allSurveysCopy.getWithIndexOf(0).getPassword());
            assertEquals("bdc", allSurveysCopy.getWithIndexOf(1).getPassword());

            //question
            assertEquals(1, allSurveysCopy.getWithIndexOf(0).getNumOfQuestions());
            assertEquals("Q1", allSurveysCopy.getWithIndexOf(0).getQuestions().get(0));
            assertEquals("a1", allSurveysCopy.getWithIndexOf(0).getAnswers().get(0).get(0));
            assertEquals("a2", allSurveysCopy.getWithIndexOf(0).getAnswers().get(0).get(1));
            assertEquals(0, allSurveysCopy.getWithIndexOf(0).getAnswerEntries().get(0).get(0));
            assertEquals(0, allSurveysCopy.getWithIndexOf(0).getAnswerEntries().get(0).get(1));

            //question
            assertEquals(1, allSurveysCopy.getWithIndexOf(1).getNumOfQuestions());
            assertEquals("Q2", allSurveysCopy.getWithIndexOf(1).getQuestions().get(0));

            //answers
            assertEquals("a1", allSurveysCopy.getWithIndexOf(1).getAnswers().get(0).get(0));
            assertEquals("a2", allSurveysCopy.getWithIndexOf(1).getAnswers().get(0).get(1));

            //answerEntries
            assertEquals(0, allSurveysCopy.getWithIndexOf(1).getAnswerEntries().get(0).get(0));
            assertEquals(0, allSurveysCopy.getWithIndexOf(1).getAnswerEntries().get(0).get(1));




        } catch (IOException e) {
            fail("IOException shouldn't be caught");
        }
    }


    Survey createSurveyForWriterTest(String title, String password, String question) {
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<ArrayList<String>> answers = new ArrayList<>();
        ArrayList<ArrayList<Integer>> allAnswerEntries = new ArrayList<>();
        Survey surveyTest;
        ArrayList<Integer> answerEntries = new ArrayList<>();
        ArrayList<String> answerForQuestion = new ArrayList<>();

        //first set
        answerForQuestion.add("a1");
        answerForQuestion.add("a2");
        answerEntries.add(0);
        answerEntries.add(0);
        answers.add(answerForQuestion);
        allAnswerEntries.add(answerEntries);

        // add questions
        questions.add(question);
        surveyTest = new Survey(title,1, password, questions, answers, allAnswerEntries);
        return surveyTest;
    }
}
