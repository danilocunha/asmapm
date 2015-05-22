package testeservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;

public class HtmlUnitTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);

		webClient.setRedirectEnabled(false);

		webClient.setJavaScriptEnabled(true);

		webClient.setCssEnabled(false);

		webClient.setThrowExceptionOnFailingStatusCode(false);

		webClient.setThrowExceptionOnScriptError(false);

		webClient.setPrintContentOnFailingStatusCode(true);
		// webClient.setTimeout(1000);
		ProxyConfig p = new ProxyConfig("localhost", 3128);
		p.addHostsToProxyBypass("localhost");
		p.addHostsToProxyBypass("www.camara.*");
		p.addHostsToProxyBypass("www2.camara.*");
		webClient.setProxyConfig(new ProxyConfig("localhost", 3128));
		
		webClient.getPage("http://localhost/camaranoticias/home_preview1427224413933.html");
	}
}
