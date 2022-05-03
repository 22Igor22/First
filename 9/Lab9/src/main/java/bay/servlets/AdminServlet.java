package bay.servlets;

import bay.errors.Errors;
import bay.requests.Admin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/adminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Admin admin = new Admin();
            admin.deleteUser(request.getParameter("login"));
        } catch (SQLException e) {
            new Errors(e.getMessage());
            response.sendRedirect("errorPage.jsp");
            e.printStackTrace();
        }
    }
}
