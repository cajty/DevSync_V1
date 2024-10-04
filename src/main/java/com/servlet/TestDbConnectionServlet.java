package com.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.config.DbConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/testDbConnection")
public class TestDbConnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {
            DbConnection dbConnection = DbConnection.getInstance();
            EntityManager entityManager = dbConnection.getEntityManager();

            if (entityManager.isOpen()) {
                out.println("Connected to the database.");

                // Query to retrieve table names and row counts
                Query query = entityManager.createNativeQuery(
                        "SELECT table_name, table_rows FROM information_schema.tables WHERE table_schema = 'your_database_name'"
                );

                // Replace 'your_database_name' with the actual name of your database
                List<Object[]> results = query.getResultList();

                // Check if results are not empty
                if (results.isEmpty()) {
                    out.println("No tables found in the database.");
                } else {
                    out.println("Tables and their row counts:");
                    for (Object[] row : results) {
                        String tableName = (String) row[0];
                        Long rowCount = ((Number) row[1]).longValue();
                        out.println("Table: " + tableName + ", Row Count: " + rowCount);
                    }
                }
            } else {
                out.println("Failed to open database connection.");
            }

            dbConnection.closeEntityManager();
        } catch (Exception e) {
            throw new ServletException("Error testing database connection", e);
        } finally {
            out.close();
        }
    }
}
