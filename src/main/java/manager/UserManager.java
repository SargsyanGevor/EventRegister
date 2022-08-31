package manager;

import db.DBConnectionProvider;
import model.Event;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {

    private final Connection connection = DBConnectionProvider.getInstance().getConnection();
    ;

    private EventManager eventManager = new EventManager();

    public void addUser(User user) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user(`name`, surname, email, event_id) Values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setInt(4, user.getEventId().getId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);
        }

    }

    public List<User> getAllUser() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            int eventId = resultSet.getInt("event_id");
            Event event = eventManager.getById(eventId);
            user.setEventId(event);
            users.add(user);
        }
        return users;
    }
}
