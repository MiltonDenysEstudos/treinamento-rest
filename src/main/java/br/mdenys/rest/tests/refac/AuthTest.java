package br.mdenys.rest.tests.refac;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import br.mdenys.rest.core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTest extends BaseTest{

	
	@Test
	public void t11_naoDeveAcessarApiSemToken() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
		
	}
}
