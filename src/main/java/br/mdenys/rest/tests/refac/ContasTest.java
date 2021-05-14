package br.mdenys.rest.tests.refac;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.Test;

import br.mdenys.rest.core.BaseTest;
import br.mdenys.rest.utils.BarrigaUtils;

public class ContasTest extends BaseTest{

	@Test
	public void deveIncluirContaComSucesso() {		
	

		 given()
			.body("{\"nome\": \"Conta inserida\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			.extract().path("id")
		;

	}
	
	@Test
	public void deveAlterarContaComSucesso() {		
		Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para alterar");
		given()
			.body("{\"nome\": \"Conta alterada\" }")
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", Matchers.is("Conta alterada"))
		;

	}
	@Test
	public void naoDeveInsericContaMesmoNome() {		
	
		given()
			.body("{\"nome\": \"Conta mesmo nome\" }")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", Matchers.is("Já existe uma conta com esse nome!"))
		;
	}
}
