package command;



public interface Commands {
int EXIT = 0;
    int ADD_EVENT = 1;
    int ADD_USER = 2;
    int SHOE_EVENT = 3;
    int SHOW_USER = 4;

    static void printCommands() {
        System.out.println("please input " + EXIT + " for exit");
        System.out.println("please input " + ADD_EVENT + " for add event");
        System.out.println("please input " + ADD_USER + " for add user");
        System.out.println("please input " + SHOE_EVENT + " for Print All Event");
        System.out.println("please input " + SHOW_USER + " for Print All User");

    }
}

