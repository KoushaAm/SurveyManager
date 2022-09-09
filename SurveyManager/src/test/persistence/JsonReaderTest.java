package persistence;
// This class has been created with help of JsonReader Class in JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
// (Paul Carter, 2020)

import model.AllSurveys;
import model.User;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//test class for JsonReader class
public class JsonReaderTest {

    //(Paul Carter, 2020)
    @Test
    void testReadNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noFile.json");

        try {
            User user = reader.readUser();
            fail("IOException Not caught");
        } catch (IOException e) {
            //expected
        }


        try {
            AllSurveys allSurvey = reader.readAllSurveys();
            fail("IOException Not caught");
        } catch (IOException e) {
            //expected
        }
    }

    //(Paul Carter, 2020)
    @Test
    void testReadUserFile() {
        try {
            JsonReader reader = new JsonReader("./data/testEmptyUser.json");
            User user = reader.readUser();
            assertEquals("", user.getUserName());
            assertEquals("", user.getPassword());
        } catch (IOException e) {
            fail("can't read the files");
        }
    }


    @Test
    void testReadUserGeneralSignUp() {
        try {
            JsonReader reader = new JsonReader("./data/testSignUpUser&123.json");
            User user = reader.readUser();
            assertEquals("signUpUser", user.getUserName());
            assertEquals("123", user.getPassword());
        } catch (IOException e) {
            fail("can't read the files");
        }
    }

    //(Paul Carter, 2020)
    @Test
    void testEmptyAllSurveys() {
        try {
            JsonReader reader = new JsonReader("./data/testWriteEmptyAllSurveys.json");
            AllSurveys emptyAllSurveys = reader.readAllSurveys();
            assertEquals(0, emptyAllSurveys.getNumOfAllSurveys());

        } catch (IOException e) {
            fail("can't find the file");
        }
    }

    //(Paul Carter, 2020)
    @Test
    void testReadGeneralAllSurveys() {
        try {
            JsonReader reader = new JsonReader("./data/testAllSurveys.json");
            AllSurveys allSurveysTest = reader.readAllSurveys();
            assertEquals(2, allSurveysTest.getNumOfAllSurveys());
            assertEquals("s2", allSurveysTest.getWithIndexOf(0).getTitle());
            assertEquals("s1", allSurveysTest.getWithIndexOf(1).getTitle());

            //password
            assertEquals("123", allSurveysTest.getWithIndexOf(0).getPassword());
            assertEquals("123", allSurveysTest.getWithIndexOf(1).getPassword());

            //question
            assertEquals(1, allSurveysTest.getWithIndexOf(0).getNumOfQuestions());
            assertEquals("q1", allSurveysTest.getWithIndexOf(0).getQuestions().get(0));
            assertEquals("1", allSurveysTest.getWithIndexOf(0).getAnswers().get(0).get(0));
            assertEquals("2", allSurveysTest.getWithIndexOf(0).getAnswers().get(0).get(1));
            assertEquals(0, allSurveysTest.getWithIndexOf(0).getAnswerEntries().get(0).get(0));
            assertEquals(0, allSurveysTest.getWithIndexOf(0).getAnswerEntries().get(0).get(1));



            //password
            assertEquals("123", allSurveysTest.getWithIndexOf(0).getPassword());
            assertEquals("123", allSurveysTest.getWithIndexOf(1).getPassword());

            //question
            assertEquals(1, allSurveysTest.getWithIndexOf(1).getNumOfQuestions());
            assertEquals("q2", allSurveysTest.getWithIndexOf(1).getQuestions().get(0));

            //answers
            assertEquals("1", allSurveysTest.getWithIndexOf(1).getAnswers().get(0).get(0));
            assertEquals("2", allSurveysTest.getWithIndexOf(1).getAnswers().get(0).get(1));

            //answerEntries
            assertEquals(0, allSurveysTest.getWithIndexOf(1).getAnswerEntries().get(0).get(0));
            assertEquals(0, allSurveysTest.getWithIndexOf(1).getAnswerEntries().get(0).get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
