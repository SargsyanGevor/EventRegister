
import command.Commands;
import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.EventType;
import model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main implements Commands {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static EventManager eventManager = new EventManager();

    private static UserManager userManager = new UserManager();

    public static void main(String[] args) throws SQLException {
        boolean isRun = true;
        while (isRun) {
            Commands.printCommands();
            int command = Integer.parseInt(SCANNER.nextLine());
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_EVENT:
                    addNewEvent();
                    break;
                case ADD_USER:
                    addNewUser();
                    break;
                case SHOE_EVENT:
                    shoeAllEvent();
                    break;
                case SHOW_USER:
                    showAllUser();
                    break;
                default:
                    System.out.println("Invalid Command");
            }
        }
    }

    private static void showAllUser() {


        List<User> allUser = null;
        try {
            allUser = userManager.getAllUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (User user : allUser) {
            System.out.println(user);
        }

    }

    private static void addNewEvent() throws SQLException {

        System.out.println("Please input event`s name,place,isOnline,price,eventType(CITY_DAY,BEER_FESTIVAL,FILM_FESTIVAL)");
        String eventDataStr = SCANNER.nextLine();
        String[] eventData = eventDataStr.split(",");
        Event event = Event.builder()
                .name(eventData[0])
                .place(eventData[1])
                .isOnline(Boolean.valueOf(eventData[2]))
                .price(Double.parseDouble(eventData[3]))
                .eventType(EventType.valueOf(eventData[4]))
                .build();

        eventManager.addEvent(event);
        System.out.println("Event created");
    }

    private static void addNewUser() throws SQLException {
        shoeAllEvent();
        System.out.println("Please input event id");
        int eventid = Integer.parseInt(SCANNER.nextLine());

        System.out.println("Please input user`s name,surname,email");
        String userDataStr = SCANNER.nextLine();
        String[] userData = userDataStr.split(",");
        User user = User.builder()
                .name(userData[0])
                .surname(userData[1])
                .email(userData[2])
                .eventId(eventManager.getById(eventid))
                .build();
            userManager.addUser(user);

        System.out.println("User created");

    }

    private static void shoeAllEvent() throws SQLException {

        List<Event> allEvent = eventManager.getAllEvent();
        for (Event event : allEvent) {
            System.out.println(event);
        }
    }
}








