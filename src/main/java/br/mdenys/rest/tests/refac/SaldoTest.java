package br.mdenys.rest.tests.refac;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.Test;

import br.mdenys.rest.core.BaseTest;
import br.mdenys.rest.utils.BarrigaUtils;

public class SaldoTest extends BaseTest{

	@Test
	public void deveCalcularSaldoContas() {		
		Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para saldo");

		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+CONTA_ID+"}.saldo", Matchers.is("534.00"))
		;
	}
	
	
}
