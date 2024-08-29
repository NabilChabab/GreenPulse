import java.util.HashMap;
import java.util.Scanner;



public class UserManger {

    private Scanner scanner = new Scanner(System.in);
    private HashMap<Integer , User> userMap = new HashMap<>();

    public void createUser(){

        System.out.print("Enter user name: ");
        String name = scanner.next();
        System.out.print("Enter user age : ");
        int age = scanner.nextInt();

        User user = new User(name, age);
        userMap.put(user.getId(), user);
        System.out.println("User "+ user.getId() + " - " + user.getName() + " created successfully");
    }


    public void showUser(){
        System.out.print("Enter user Id to view details : ");
        int userId = scanner.nextInt();

        User user = userMap.get(userId);

        if (user != null) {
            System.out.println(user);
        }else {
            System.out.println("User not found");
        }
    }

    public void updateUser(){
        System.out.print("Enter user Id to update details : ");
        int userId = scanner.nextInt();
        User user = userMap.get(userId);
        if (user != null) {
            System.out.println("What would you like to update?");
            System.out.println("1-Name");
            System.out.println("2-Age");
            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
        switch (choice){
            case 1:
                System.out.print("Enter new name: ");
                String name = scanner.next();
                user.setName(name);
                break;
            case 2:
                System.out.print("Enter new age: ");
                int age = scanner.nextInt();
                user.setAge(age);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        }else {
            System.out.println("User not found");
        }
    }

    public void deleteUser(){
        System.out.print("Enter the Id to delete a user : ");
        int userId = scanner.nextInt();
        User user = userMap.get(userId);
        if (user != null){
            userMap.remove(userId);
            System.out.println("User " + user.getName() + " deleted successfully");
        }else {
            System.out.println("User not found");
        }
    }

    public void calculateConsumption(){

    }


}
