package br.mdenys.rest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import br.mdenys.rest.core.BaseTest;

public class BarrigaTest extends BaseTest{

	private String TOKEN;
	
	@Before
	public void login() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "skateboards35@gmail.com");
		login.put("senha", "12345");

		 TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token")
			;
	}
	
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
	

		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
			.body("{\"nome\": \"conta qualquer\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;

	}
	@Test
	public void deveAlterarContaComSucesso() {		
	
		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
			.body("{\"nome\": \"conta alterada\"}")
		.when()
			.put("/contas/589509")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", Matchers.is("conta alterada"))
		;

	}
	@Test
	public void naoDeveInsericContaMesmoNome() {		
	
		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
			.body("{\"nome\": \"conta alterada\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", Matchers.is("Já existe uma conta com esse nome!"))
		;
	}
	@Test
	public void deveInserirMovimentacaoSucesso() {		
		Movimentacao mov = getMovimentacaoValida();
		
		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
		;
	}
	@Test
	public void deveValidarCamposObrigatoriosMovimentacao() {		
		
		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", Matchers.hasSize(8))
			.body("msg", Matchers.hasItems(
					"Data da Movimentação é obrigatório",
					"Data do pagamento é obrigatório",
					"Descrição é obrigatório",
					"Interessado é obrigatório",
					"Valor é obrigatório",
					"Valor deve ser um número",
					"Conta é obrigatório",
					"Situação é obrigatório"))
		;
	}
	
	@Test
	public void naoDeveInserirMovimentacaoComDataFutura() {		
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao("20/05/2022");
		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", Matchers.hasSize(1))
			.body("msg", Matchers.hasItem("Data da Movimentação deve ser menor ou igual à data atual"))

		;
	}
	@Test
	public void naoDeveRemoverContaComMovimentacao() {		
		
		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
		.when()
			.delete("/contas/589509")
		.then()
			.statusCode(500)
			.body("constraint", Matchers.is("transacoes_conta_id_foreign"))
		;
	}
	@Test
	public void deveCalcularSaldoContas() {		
		
		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == 589509}.saldo", Matchers.is("100.00"))
		;
	}
	@Test
	public void deveRemoverMovimentacao() {		
		
		given()
			.header("Authorization","JWT " + TOKEN)//ao inves de JWT pode ser bearer//nao esquecer espaco depois JWT
		.when()
			.delete("/transacoes/547610")
		.then()
			.statusCode(204)
		;
	}
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(589509);
		//mov.setUsuario_id(usuario_id);
		mov.setDescricao("Descricao da movimentacao");
		mov.setEnvolvido("Envolvido na mov");
		mov.setTipo("REC");
		mov.setData_transacao("01/01/2000");
		mov.setData_pagamento("10/05/2010");
		mov.setValor(100f);
		mov.setStatus(true);
		return mov;
	}
}
