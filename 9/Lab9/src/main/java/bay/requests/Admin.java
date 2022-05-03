package bay.requests;

import bay.dao.DAO;
import bay.errors.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin {
    public final Connection connection;

    public Admin() throws SQLException {
        DAO dao = new DAO();
        connection = dao.getConnection();
    }

    public Statement getStatement() throws SQLException {
        Statement statement = connection.createStatement();
        return statement;
    }

    public void addUser(String login, String password, String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into Users values(?, ?, ?, ?)");
        statement.setString(1, login);
        statement.setString(2, String.valueOf(password.hashCode()));
        statement.setString(3, "user");
        statement.setString(4, email);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteUser(String login) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from Users where login = ?");
        statement.setString(1, login);
        statement.executeUpdate();
        statement.close();
    }
}
