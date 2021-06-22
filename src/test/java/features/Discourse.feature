@Discourse
Feature: Discourse

   @PrintAllClosedTopics
   Scenario: Print the title of all closed topics
   Given open the page 'Discourse'
   |Option|
   |Demo	|
   When I click on the Demo option in main menu
	 And I Scroll to the bottom of the page
	 Then I Print the title of all closed topics
	 And end the test
   
  