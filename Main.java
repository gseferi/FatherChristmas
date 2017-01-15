import java.sql.*;

public class Main {

        public static void main(String[] args) {

            try{
                Class.forName("org.postgresql.Driver");
            } catch(ClassNotFoundException ex) {
                System.out.println("Driver not found");
            }

            System.out.println("PostgreSQL driver registered");

            Connection connection = null;
            Statement statement = null;

            try {
                connection = DriverManager
                        .getConnection("jdbc:postgresql://mod-intro-databases.cs.bham.ac.uk/gxs572",
                                "gxs572", "1twjiz235h");
                if (connection != null) {
                    System.out.println("Database accessed!");
                } else {
                    System.out.println("Failed to make connection");
                }

                statement = connection.createStatement();

                String child = "CREATE TABLE IF NOT EXISTS Child " +
                        "(cid INT PRIMARY KEY NOT NULL," +
                        " name Text NOT NULL, " +
                        " address Text NOT NULL) ";

                String slh = "CREATE TABLE IF NOT EXISTS SantasLittleHelper " +
                        "(slhid INT PRIMARY KEY NOT NULL," +
                        " name Text NOT NULL)";

                String gift = "CREATE TABLE IF NOT EXISTS Gift " +
                        "(gid INT PRIMARY KEY NOT NULL," +
                        " description text NOT NULL) ";

                String present = "CREATE TABLE IF NOT EXISTS Present " +
                        "(gid INT REFERENCES Gift (gid) NOT NULL," +
                        " cid INT REFERENCES Child (cid) NOT NULL, " +
                        " slhid INT REFERENCES SantasLittleHelper (slhid) NOT NULL) ";

                statement.executeUpdate(child);
                statement.executeUpdate(slh);
                statement.executeUpdate(gift);
                statement.executeUpdate(present);

                statement.close();

            Statement statementChild = connection.createStatement();

            for (int i=1; i<=1000; i++)
            {
                int id = 0;
                String name = "";
                String address = "";

                if (i == 1) {
                    id = 1;
                    name = "'Gerta'";
                    address = "'160 Bournbrook'";
                } else {
                    id = i;
                    name = "'name" + i + "'";
                    address = "'address" + i + "'";
                }

                statementChild.executeUpdate(
                        "INSERT INTO Child (cid, name, address) " +
                        "SELECT " + id + ", " + name + ", " + address + " " +
                        "WHERE NOT EXISTS (" +
                        "SELECT 1 FROM Child " +
                                "WHERE cid=" + id + ")");
            }

            statementChild.close();

                Statement statementSantasLittleHelper = connection.createStatement();

                for (int i=1; i<=10; i++)
                {
                    int id = 0;
                    String name = "";

                    if (i == 1) {
                        id = 1;
                        name = "'Boyan'";
                    } else {
                        id = i;
                        name = "'name" + i + "'";
                    }

                    statementSantasLittleHelper.executeUpdate(
                            "INSERT INTO SantasLittleHelper (slhid, name) " +
                                    "SELECT " + id + ", "  + name + " " +
                                    "WHERE NOT EXISTS (" +
                                    "SELECT 1 FROM SantasLittleHelper " +
                                    "WHERE slhid=" + id + ")");
                }
                statementSantasLittleHelper.close();

                Statement statementGift = connection.createStatement();

                for (int i=1; i<=10; i++)
                {
                    int id = 0;
                    String description = "";

                    if (i == 1) {
                        id = 1;
                        description = "'iPad'";
                    } else {
                        id = i;
                        description = "'description" + i + "'";
                    }

                    statementGift.executeUpdate(
                            "INSERT INTO Gift (gid, description) " +
                                    "SELECT " + id + ", "  + description + " " +
                                    "WHERE NOT EXISTS (" +
                                    "SELECT 1 FROM Gift " +
                                    "WHERE gid=" + id + ")");
                }
                statementGift.close();

                Statement statementPresent = connection.createStatement();

                for (int i=1; i<=100; i++)
                {
                    int gid = 0;
                    int cid = 0;
                    int slhid = 0;

                    if (i == 1) {
                        gid = 1;
                        cid = 1;
                        slhid = 1;
                    } else {
                        gid = (i+9)/10;
                        cid = i;
                        slhid = (i+9)/10;

                    }

                    statementPresent.executeUpdate(
                            "INSERT INTO Present (gid, cid, slhid) " +
                                    "SELECT " + gid + ", " + cid + ", " + slhid + " " +
                                    "WHERE NOT EXISTS (" +
                                    "SELECT 1 FROM Present " +
                                    "WHERE gid=" + gid + " AND cid=" + cid + " AND slhid=" + slhid + ")");
                }
                connection.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
            System.out.println("Tables created!");
            System.out.println("Test data inserted!");
        }
    }
