package page;

import java.util.HashMap;
import java.util.Map;

public class DiscoursePage extends BasePage{

	
	//*[@id="main"]/ul/li[4]
	
	
	public static String comboHeaderOptions(String headerOption) {
		Map<String, String> xpaths = new HashMap<String, String>();
		xpaths.put("About", "//*[@id=\"main\"]/ul/li[1]/a");
		xpaths.put("Features", "//*[@id=\"main\"]/ul/li[2]/a");
		xpaths.put("Community", "//*[@id=\"main\"]/ul/li[3]/a");
		xpaths.put("Demo", "//*[@id=\"main\"]/ul/li[4]/a");
		xpaths.put("Pricing", "//*[@id=\"main\"]/ul/li[5]/a");
		return xpaths.get(headerOption).toLowerCase().toString();
	}
	
	
	public static void clicarOpcaoDemo() {		
		String option = issue_info.get(0).get("Option");
		clicar(comboHeaderOptions(option));
		System.out.println(option);				
	}
	
}
