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

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "SELECT Id, Book_Name, Book_Edition, Book_Price FROM book1";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            pw.println("<h2>Oracle JDBC Driver not found: " + e.getMessage() + "</h2>");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sys as sysdba", "admin");
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            pw.println("<html><head><title>Book List</title></head><body>");
            pw.println("<h2>Book List</h2>");
            pw.println("<table border='1'>");
            pw.println("<tr>"
            		+ "<th>Book ID</th>"
            		+ "<th>Book Name</th>"
            		+ "<th>Book Edition</th>"
            		+ "<th>Book Price</th>"
            		+ "<th>Edit</th>"
            		+ "<th>Delete</th>"
            		+ "</tr>");

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Book_Name");
                String edition = rs.getString("Book_Edition");
                float price = rs.getFloat("Book_Price");

                pw.println("<tr>");
                pw.println("<td>" + id + "</td>");
                pw.println("<td>" + name + "</td>");
                pw.println("<td>" + edition + "</td>");
                pw.println("<td>" + price + "</td>");
                pw.println("<td><a href='editScreen?id=" + id + "'>Edit</a></td>"); 
                pw.println("<td><a href='deleteBook?id=" + id + "'>Delete</a></td>");
                pw.println("</tr>");
            }


            pw.println("</table>");
            pw.println("<br><a href='home.html'>Home</a>");
            pw.println("</body></html>");

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

