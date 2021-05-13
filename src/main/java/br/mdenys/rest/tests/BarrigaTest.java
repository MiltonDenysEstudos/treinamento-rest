package br.mdenys.rest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.mdenys.rest.core.BaseTest;

public class BarrigaTest extends BaseTest{

	
	@Test
	public void naoDeveAcessarApiSemToken() {
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
		
	}
	@Test
	public void deveIncluirContaComSucesso() {		
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "skateboards35@gmail.com");
		login.put("senha", "12345");

		String token = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token")
			;
		System.out.println(token);

		given()
			.header("Authorization","JWT " + token)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
			.body("{\"nome\": \"conta qualquer\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;

	}
}
