package testeservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HashMap<String, String> h = new HashMap<String, String>();
		
		h.put("contextPath", req.getContextPath());
		
		h.put("serverName", req.getServerName());
		h.put("serverPort", Integer.toString(req.getServerPort()));
		
		h.put("serverInfo", this.getServletContext().getServerInfo());
		resp.setContentType("text/html");
        // Actual logic goes here.
        PrintWriter out = resp.getWriter();
        out.println("<h1>Hello World</h1>");
        out.println(this.getClass().getName().replace(".", "/"));
        out.println("<br/>" + this.toString());
        
	}

}
