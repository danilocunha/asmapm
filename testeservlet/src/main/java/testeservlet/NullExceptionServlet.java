package testeservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NullExceptionServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
        // Actual logic goes here.
        PrintWriter out = resp.getWriter();
        out.println("<h1>Hello World</h1>");
        
        out.println("<br/>" + this.toString());
        
        String parametro = req.getParameter("parametro");
        if(parametro.equals("1")) {
        	out.println("Tem parametro 1");
        }
        
	}

}
