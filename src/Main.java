//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    private static UserManger userManger = new UserManger();

    public static void main(String[] args) {

        userManger.createUser();
        userManger.showUser();


    }
}