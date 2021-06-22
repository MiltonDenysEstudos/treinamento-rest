package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DiscourseSteps {

	
	@Given("open the page 'Discourse'")
    public void openPage(DataTable data) {
		page.BasePage.openHomePage(data);
 }
	@When("I click on the Demo option in main menu")
    public void clickDemo() {
		page.DiscoursePage.clicarOpcaoDemo();
	}
	@And("I Scroll to the bottom of the page")
    public void scrollDownPage() {		
 }
	@Then("I Print the title of all closed topics")
    public void PrintTitleAllClosedTopics() {
		
 }
	@And("end the test")
    public void endTest() {
		page.BasePage.quitDriver();
 }
	 
	
	
	
}
