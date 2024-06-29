package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditBookServlet extends HttpServlet {
	    private static final String query = "SELECT Book_Name, Book_Edition, Book_Price FROM book1 where id=?";

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        resp.setContentType("text/html");
	        PrintWriter pw = resp.getWriter();
	        
	        int id=Integer.parseInt(req.getParameter("id"));
	        
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	        } catch (ClassNotFoundException e) {
	            pw.println("<h2>Oracle JDBC Driver not found: " + e.getMessage() + "</h2>");
	            return;
	        }

	        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sys as sysdba", "admin");
	             PreparedStatement ps = con.prepareStatement(query)) {

	            ps.setInt(1, id);
	            ResultSet rs=ps.executeQuery();
	            rs.next();
	            pw.println("<form action='editBook?id="+id+"' method='post'>");
	            pw.println("<table align='center'>");
	            pw.println("<tr>");
	            pw.println("<td>Book Name</td>");
	            pw.println("<td><input type = 'text' name='bookName' value='"+rs.getString(1)+"'></td>");
	            pw.println("</tr>");
	            pw.println("<tr>");
	            pw.println("<td>Book Edition</td>");
	            pw.println("<td><input type = 'text' name='bookEdition' value='"+rs.getString(2)+"'></td>");
	            pw.println("</tr>");
	            pw.println("<tr>");
	            pw.println("<td>Book Price</td>");
	            pw.println("<td><input type = 'text' name='bookPrice' value='"+rs.getFloat(3)+"'></td>");
	            pw.println("</tr>");
	            pw.println("<tr>");
	            pw.println("<td><input type='submit' value='Edit'></td>");
	            pw.println("<td><input type='reset' value='Cancel'></td>");
	            pw.println("</tr>");
	            pw.println("</table>");
	            pw.print("</form>");
	        } catch (SQLException e) {
	            pw.println("<h2>SQL Exception: " + e.getMessage() + "</h2>");
	        } catch (Exception e) {
	            pw.println("<h2>Exception: " + e.getMessage() + "</h2>");
	        }
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        doGet(req, resp);
	    }
}
