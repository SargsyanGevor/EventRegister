package manager;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class EventManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();


    public void addEvent(Event event) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("insert into event(`name`,place,is_online,price,event_type) Values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getPlace());
        preparedStatement.setBoolean(3, event.isOnline());
        preparedStatement.setDouble(4, event.getPrice());
        preparedStatement.setString(5, event.getEventType().name());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            event.setId(id);
        }

    }

    public List<Event> getAllEvent() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM event");
        List<Event> events = new LinkedList<>();
        while (resultSet.next()) {
            Event event = new Event();
            event.setId(resultSet.getInt("id"));
            event.setName(resultSet.getString("name"));
            event.setPlace(resultSet.getString("place"));
            event.setOnline(resultSet.getBoolean("is_online"));
            event.setPrice(resultSet.getDouble("price"));
            event.setEventType(EventType.valueOf(resultSet.getString("event_type")));

            events.add(event);
        }
        return events;
    }

    public Event getById(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM event WHERE id = " + id);

        if (resultSet.next()) {
            Event event = new Event();
            event.setId(resultSet.getInt("id"));
            event.setName(resultSet.getString("name"));
            event.setPlace(resultSet.getString("place"));
            event.setOnline(resultSet.getBoolean("is_online"));
            event.setPrice(resultSet.getDouble("price"));
            event.setEventType(EventType.valueOf(resultSet.getString("event_type")));

            return event;
        }
        return null;
    }


}
