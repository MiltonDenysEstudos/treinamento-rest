package br.mdenys.rest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.mdenys.rest.core.BaseTest;
import br.mdenys.rest.utils.DataUtils;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarrigaTest extends BaseTest{

	
	private static String CONTA_NAME = "Conta " + System.nanoTime();
	private static Integer CONTA_ID;
	private static Integer MOV_ID;

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
	}
	
	
	@Test
	public void t02_deveIncluirContaComSucesso() {		
	

		CONTA_ID = given()
			.body("{\"nome\": \""+CONTA_NAME+"\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			.extract().path("id")
		;

	}
	@Test
	public void t03_deveAlterarContaComSucesso() {		
	
		given()
			.body("{\"nome\": \""+CONTA_NAME+" alterada\" }")
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", Matchers.is(CONTA_NAME+" alterada"))
		;

	}
	@Test
	public void t04_naoDeveInsericContaMesmoNome() {		
	
		given()
			.body("{\"nome\": \""+CONTA_NAME+" alterada\" }")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", Matchers.is("J� existe uma conta com esse nome!"))
		;
	}
	@Test
	public void t05_deveInserirMovimentacaoSucesso() {		
		Movimentacao mov = getMovimentacaoValida();
		
		MOV_ID = given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			.extract().path("id")
		;
	}
	@Test
	public void t06_deveValidarCamposObrigatoriosMovimentacao() {		
		
		given()
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", Matchers.hasSize(8))
			.body("msg", Matchers.hasItems(
					"Data da Movimenta��o � obrigat�rio",
					"Data do pagamento � obrigat�rio",
					"Descri��o � obrigat�rio",
					"Interessado � obrigat�rio",
					"Valor � obrigat�rio",
					"Valor deve ser um n�mero",
					"Conta � obrigat�rio",
					"Situa��o � obrigat�rio"))
		;
	}
	
	@Test
	public void t07_aoDeveInserirMovimentacaoComDataFutura() {		
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao(DataUtils.getDataDiferencaDias(2));
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", Matchers.hasSize(1))
			.body("msg", Matchers.hasItem("Data da Movimenta��o deve ser menor ou igual � data atual"))

		;
	}
	@Test
	public void t08_naoDeveRemoverContaComMovimentacao() {		
		
		given()
			.pathParam("id", CONTA_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint", Matchers.is("transacoes_conta_id_foreign"))
		;
	}
	@Test
	public void t09_deveCalcularSaldoContas() {		
		
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+CONTA_ID+"}.saldo", Matchers.is("100.00"))
		;
	}
	@Test
	public void t10_deveRemoverMovimentacao() {		
		
		given()
			.pathParam("id", MOV_ID)

		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
		;
	}
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
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(CONTA_ID);
		//mov.setUsuario_id(usuario_id);
		mov.setDescricao("Descricao da movimentacao");
		mov.setEnvolvido("Envolvido na mov");
		mov.setTipo("REC");
		mov.setData_transacao(DataUtils.getDataDiferencaDias(-1));
		mov.setData_pagamento(DataUtils.getDataDiferencaDias(5));
		mov.setValor(100f);
		mov.setStatus(true);
		return mov;
	}
}
