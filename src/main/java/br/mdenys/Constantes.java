package br.mdenys;

import io.restassured.http.ContentType;

public interface Constantes {
	String APP_BASE_URL = "https://barrigarest.wcaquino.me/";
	Integer APP_PORT = 443;//http->80
	String APP_BASE_PATH = "";
	
	ContentType APP_CONTENT_TYPE = ContentType.JSON;
	Long Max_TIMEOUT = 1000L;
	
	
}
