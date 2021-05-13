package br.mdenys.rest.tests;

import org.junit.Test;

import br.mdenys.rest.core.BaseTest;

import static io.restassured.RestAssured.given;

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
}
