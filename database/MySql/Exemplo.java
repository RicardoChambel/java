package MySQL.MySql;

import java.sql.*;

public class Exemplo {
    static String databaseName = ""; // funcionariosdb.funcionarios
    static String url = "jdbc:mysql://localhost:3306/"+databaseName;
    static String username = "root";
    static String password = "rootroot";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement("insert into `funcionariosdb`.`funcionarios`(`nome`) values ('teste')");
            int status = ps.executeUpdate();

            if (status != 1) {
                System.out.println("Conexão bem sucedida");
                System.out.println("Registo inserido");
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }
}
