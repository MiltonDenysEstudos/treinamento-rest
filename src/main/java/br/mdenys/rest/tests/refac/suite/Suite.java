package br.mdenys.rest.tests.refac.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.mdenys.rest.core.BaseTest;
import br.mdenys.rest.tests.Movimentacao;
import br.mdenys.rest.tests.refac.AuthTest;
import br.mdenys.rest.tests.refac.ContasTest;
import br.mdenys.rest.tests.refac.MovimentacaoTest;
import br.mdenys.rest.tests.refac.SaldoTest;
import io.restassured.RestAssured;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacaoTest.class,
	SaldoTest.class,
	AuthTest.class
	
})
public class Suite extends BaseTest{
	@BeforeClass
	public static void login() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "skateboards35@gmail.com");
		login.put("senha", "12345");

		String TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token")
			;
		//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
		 RestAssured.requestSpecification.header("Authorization","JWT " + TOKEN);
		 RestAssured.get("/reset").then().statusCode(200);
	}

}
