import java.sql.*;
import java.util.Scanner;

/**
 * Created by Gerta on 10/01/2017.
 */
public class Reports {
    public Connection connection;

    public static void main(String[] args) {
        int id;
        Connection connection = null;
        String command = "";
        try{
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL driver registered");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://mod-intro-databases.cs.bham.ac.uk/gxs572",
                            "gxs572", "1twjiz235h");
            if (connection != null) {
                System.out.println("Database accessed!");
            } else {
                System.out.println("Failed to make connection");
            }
        } catch(ClassNotFoundException ex) {
            System.out.println("Driver not found");
        } catch (SQLException e) {
            System.out.println("Connection has failed");
        }

        System.out.print(
                "Select one of the following options to retrieve data: \n " +
                "- child - to et child's report \n - helper - to get helper's report \n" +
                        "- exit - to exit the program. \n");

        System.out.println("Enter command: ");
        Scanner sc = new Scanner(System.in);
        command = sc.nextLine();

        while (command != "exit"){
            switch (command) {
                case "child":
                    System.out.println("Enter child's id: ");
                    id = sc.nextInt();
                    SelectChild(connection, id);
                    break;
                case "helper":
                    System.out.println("Enter helper's id: ");
                    id = sc.nextInt();
                    SelectHelper(connection, id);
                    break;
                case "exit":
                    System.exit(0);
            }

            System.out.println("Enter command: ");
            command = sc.nextLine();
        }

    }

    private static void SelectChild(Connection connection, int id) {
        Statement statement;

        try {
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(
                    "SELECT Child.Cid, Child.Name, Child.Address, Gift.gid, Gift.Description " +
                            "FROM Present " +
                            "INNER JOIN Child " +
                            "ON Present.Cid = Child.Cid " +
                            "INNER JOIN Gift " +
                            "ON Present.Cid  = Gift.Gid " +
                            "WHERE Child.Cid=" + id);

            while (result.next()) {
                System.out.println("id = " + result.getInt("cid"));
                System.out.println("cid = " + result.getInt("cid"));
                System.out.println("name = " + result.getString("name"));
                System.out.println("address = " + result.getString("address"));
                System.out.println("gid = " + result.getString("gid"));
                System.out.println("description = " + result.getString("description"));
                System.out.println();
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        System.out.println("Report has been produced!");
    }

    private static void SelectHelper(Connection connection, int id) {
        Statement statement;

        try {
            statement = connection.createStatement();

            ResultSet result2 = statement.executeQuery(
                    "SELECT SantasLittleHelper.Slhid, SantasLittleHelper.Name, Child.Name, Child.Address, Gift.Gid, Gift.Description " +
                            "FROM Present " +
                            "INNER JOIN Child " +
                            "ON Present.Cid = Child.Cid " +
                            "INNER JOIN Gift " +
                            "ON Present.Cid  = Gift.Gid " +
                            "INNER jOIN SantasLittleHelper " +
                            "ON Present.Slhid = SantasLittleHelper.Slhid " +
                            "WHERE SantasLittleHelper.Slhid=" + id);


            while (result2.next()) {
                System.out.println("slhid = " + result2.getInt("slhid"));
                System.out.println("name = " + result2.getString("name"));
                System.out.println("name = " + result2.getString("name"));
                System.out.println("address = " + result2.getString("address"));
                System.out.println("gid = " + result2.getString("gid"));
                System.out.println("description = " + result2.getString("description"));
                System.out.println();

            }

            result2.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        System.out.println("Report has been produced!");
    }

}

