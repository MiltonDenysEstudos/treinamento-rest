package tests;


import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;



@RunWith(Cucumber.class)
@CucumberOptions(
features = "src/test/java/features/Discourse.feature",
glue = "",
tags = "@Discourse")

public class Tests {
	
}
