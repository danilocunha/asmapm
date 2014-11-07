package testeservlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloFilter implements Filter {

	private FilterConfig config;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		System.out.println("serverName: " + req.getServerName());
		System.out.println("serverPort: " + req.getServerPort());

		if (config != null) {
			System.out.println("contextPath: "
					+ config.getServletContext().getContextPath());
			System.out.println("ServerInfo: "
					+ config.getServletContext().getServerInfo());
		}

	}

	public void init(FilterConfig config) throws ServletException {

		this.config = config;
	}

}
