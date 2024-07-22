package MySQL.MySql;

import java.sql.*;

public class OutroMetodo {
    public static void main(String[] args) {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/";
        String db = "funcionariosdb";
        String driver = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String pass = "rootroot";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url+db, user, pass);
            // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/utilizadoresDB", "root", "root");
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM utilizadores");
            System.out.println("Nome: " + "\t" + "Nome completo: ");
            while (res.next()) {
                String n = res.getString("nome");
                String c = res.getString("nomecompleto");
                System.out.println(n + "\t\t" + c);
            }
        }
        catch (SQLException s){
            System.out.println("Falha ao executar.");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {

        }
    }
}
