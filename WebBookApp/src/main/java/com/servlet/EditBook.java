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

@WebServlet("/editBook")
public class EditBook extends HttpServlet {
	  private static final String query = "UPDATE book1 set Book_Name=?, Book_Edition=?, Book_Price=? where id=?";

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        resp.setContentType("text/html");
	        PrintWriter pw = resp.getWriter();
	        
	        int id=Integer.parseInt(req.getParameter("id"));
	        String bookName = req.getParameter("bookName");
	        String bookEdition = req.getParameter("bookEdition");
	        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	        } catch (ClassNotFoundException e) {
	            pw.println("<h2>Oracle JDBC Driver not found: " + e.getMessage() + "</h2>");
	            return;
	        }

	        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sys as sysdba", "admin");
	             PreparedStatement ps = con.prepareStatement(query)) {
	        	ps.setString(1, bookName);
	        	ps.setString(2, bookEdition);
	        	ps.setFloat(3, bookPrice);
	        	ps.setInt(4, id);
	        	int count = ps.executeUpdate();
	        	if(count==1) {
	        		pw.print("<h2>Record is Edited Successfully...</h2>");
	        	}
	        	else {
	        		pw.print("<h2>Record is Not Edited successfully..!</h2>");

	        	}
	            
	        } catch (SQLException e) {
	            pw.println("<h2>SQL Exception: " + e.getMessage() + "</h2>");
	        } catch (Exception e) {
	            pw.println("<h2>Exception: " + e.getMessage() + "</h2>");
	        }
	            pw.println("<a href='home.html'>Home</a>");
	            pw.println("<br>");
	            pw.println("<a href='bookList'>Book List</a>");	        
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        doGet(req, resp);
	    }
}
