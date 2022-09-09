# My Personal Project 
##Survey Manager

### What will the application do?

The Project is a survey app where different users can sign up and use the four 
main functionalities of the app which include:  

- **Creating a Survey**
- **Doing a Survey**
- **Viewing the result of Survey of choice**
- **Deleting a Survey**
- **Viewing all the available Surveys.**

A user who is new to the application must enter their name and create a password.
As a new member, the user must first create a Survey by assigning the questions and
options for each question in the Survey. Once the Survey is complete. The user can do 
the Survey and later view its result at each instance. The surveys can also 
be viewed and be deleted by the creator of the Survey. The result of the survey are spontaneously 
updated once the user enters their answer. 



 
### Who will use it?

the application is usable by all people, but it's especially useful
for team members, classrooms and any kind of social events where people's 
opinion can be collected inside a survey, and then the results can be presented
for each question in a simple visual representation

### Why is this project of interest to you?
Collecting datasets and displaying the results in a simple 
form is a skill that has always interested me. This project allows me to practice the organization of data
in a complex structure such as a survey that stores multiple data types. The app also provides me the opportunity to 
explore the nature of 2D arrays and Storing objects in their structure. 

### As a user I want to... 
- Create username and password for myself when I open the application. 
- Create survey with specified questions and options for answers.
- Do the surveys that are created in the application.
- Load and View the Survey's created by previous users including me.
- View the results of all surveys created in the app and see how many times each answer has been chosen. 
- Create an account that the app remembers in future sessions.
- View the result of other survey that have been saved in the app.
- Save my survey activity including the surveys I have made, and the surveys I have completed.
- Delete a survey that is only created by me by choosing a password for my survey. 

### Phase 4: Task 3
Summary of the design: 
The user will be first direct to the login page which consists of one my super class called RegisterPage and its
subclass loginOrSignUp page. While the Register page contains the important fields such as JComponents 
the LoginOrSignPage contains the action listener for each button including the actual loading or saving of the user
from/to a json file. Once the login/sign up button or pressed a new class will be instantiated while 
the current frame is disposed. The main class which is called Visual survey app is JFrame which represents overall
structure of the application once user enters. This class contains JComponents and 4 other fields that are panel 
classes for 4 main functionalities of this app. These panels consist of: CreateSurveyPanel, DoSurveyPanel,
DeleteSurveyPanel and ViewResultsPanel. JsonReader and Writer are also associated with this class since they
are required for save and load buttons. This structure will allow the 4 panel classes to have only the fields that are 
specific to their own functionality. They may also have Json Writer and Json reader field such as in 
DeleteSurveyPanel, since each deleted survey that was previously load must also be deleted from Json.
In the Model package, the 3 main models only required to implement the wratable interface which is part of the reading
and writing of these model into json.


My program is currently able to satisfy all user stories, however the design of this
application can be more organized. As an example, all four main pages of the app should provide
the user with list of all surveys. This is, in fact, a common behaviour which can be refactored into an abstract class
and then extended by all the four classes. Additionally, I have idea of establishing an association between each survey
and user. This will all surveys to have reference of the user that created them. Establishing this relationship also 
makes the user part of the survey which signifies aggregation.

This session involves loading 3 survey from JSON and then constructing a new survey. Later 2 of the surveys 
have been done. Then one of the surveys was deleted. Since the survey must also be deleted from Json. 
the remove survey log has been repeated twice which is a natural behaviour considering that the method
of removeSurveyFromAllSurveys() has been called multiple times in the code. 
Finally all the survey activity has been saved by the user before quiting the application.

### An example of the Event log:

Fri Apr 01 01:00:48 PDT 2022
Constructed a survey titled 'student'

Fri Apr 01 01:00:48 PDT 2022
Added 'student' to all surveys

Fri Apr 01 01:00:48 PDT 2022
Constructed a survey titled 'survey 2'

Fri Apr 01 01:00:48 PDT 2022
Added 'survey 2' to all surveys

Fri Apr 01 01:01:08 PDT 2022
Constructed a survey titled 'new Survey'

Fri Apr 01 01:01:08 PDT 2022
Added 'new Survey' to all surveys

Fri Apr 01 01:01:18 PDT 2022
Updated the results of 'student'.

Fri Apr 01 01:01:26 PDT 2022
Updated the results of 'new Survey'.

Fri Apr 01 01:01:53 PDT 2022
Constructed a survey titled 'student'

Fri Apr 01 01:01:53 PDT 2022
Added 'student' to all surveys

Fri Apr 01 01:01:53 PDT 2022
Constructed a survey titled 'survey 2'

Fri Apr 01 01:01:53 PDT 2022
Added 'survey 2' to all surveys

Fri Apr 01 01:01:53 PDT 2022
Removed 'survey 2' from all surveys

Fri Apr 01 01:01:53 PDT 2022
saved all surveys

Fri Apr 01 01:01:53 PDT 2022
Removed 'survey 2' from all surveys

Fri Apr 01 01:01:56 PDT 2022
Constructed a survey titled 'student'

Fri Apr 01 01:01:56 PDT 2022
Added 'student' to all surveys

Fri Apr 01 01:01:56 PDT 2022
saved all surveys
