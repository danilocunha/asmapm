package local.teste;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Hello extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html");
        String estourar = request.getParameter("estourar");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Success</title></head><body>");
        out.println("<h1> It works </h1>");
        out.println("</body>");
        out.println("</html>");
        out.close();
        if(estourar.equals("1")) {
        List<long[]> list = new LinkedList<long[]>();
        while (true) {
          list.add(new long[65536]); // an arbitrary number
          // sleep(1) perhaps?
        }
        }
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
