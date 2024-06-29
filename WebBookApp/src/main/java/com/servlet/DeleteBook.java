package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/deleteBook")
public class DeleteBook extends HttpServlet {
	private static final String query = "Delete from book1 where id=?";

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
        	int count = ps.executeUpdate();
        	if(count==1) {
        		pw.print("<h2>Record is Deleted Successfully...</h2>");
        	}
        	else {
        		pw.print("<h2>Record is Not Deleted successfully..!</h2>");

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
