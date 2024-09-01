import services.UserService;
import ui.Menu;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {



    public static void main(String[] args) {
        UserService userService = new UserService();
        Menu menu = new Menu(userService);
        menu.start();

    }
}