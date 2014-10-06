package testeservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/*
		 * HashMap<String, String> h = new HashMap<String, String>();
		 * 
		 * h.put("contextPath", req.getContextPath());
		 * 
		 * h.put("serverName", req.getServerName()); h.put("serverPort",
		 * Integer.toString(req.getServerPort()));
		 * 
		 * h.put("serverInfo", this.getServletContext().getServerInfo());
		 * resp.setContentType("text/html"); // Actual logic goes here.
		 * PrintWriter out = resp.getWriter();
		 * out.println("<h1>Hello World</h1>");
		 * out.println(this.getClass().getName().replace(".", "/"));
		 * out.println("<br/>" + this.toString());
		 */
		Context ctx = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup("java:/comp/env/jdbc/SisnewsInternetDS1");

			con = ds.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT TOP 10 * FROM Enquete");

			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.print("<html><body><h2>Lista de Enquete</h2>");
			out.print("<table border=\"1\" cellspacing=10 cellpadding=5>");
			out.print("<th>Enquete ID</th>");
			out.print("<th>Titulo</th>");

			while (rs.next()) {
				out.print("<tr>");
				out.print("<td>" + rs.getString("ideEnquete") + "</td>");
				out.print("<td>" + rs.getString("texTitulo") + "</td>");
				out.print("</tr>");
			}
			out.print("</table></body><br/>");

			// lets print some DB information
			out.print("<h3>Database Details</h3>");
			out.print("Database Product: "
					+ con.getMetaData().getDatabaseProductName() + "<br/>");
			out.print("Database Driver: " + con.getMetaData().getDriverName());
			out.print("</html>");

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
				ctx.close();
			} catch (SQLException e) {
				System.out.println("Exception in closing DB resources");
			} catch (NamingException e) {
				System.out.println("Exception in closing Context");
			}

		}

	}

}
