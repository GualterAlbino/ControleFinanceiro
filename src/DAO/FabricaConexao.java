package DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class FabricaConexao {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //private static final String STR_DRIVER = "org.gjt.mm.mysql.Driver";  // definição de qual banco será utilizado
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DATABASE = "carteira";
    private static final String IP = "127.0.0.1";
    private static final String PORTA = "3306";
    private static final String URL = "jdbc:mysql://" + IP + ":" + PORTA + "/" + DATABASE;
    //private static final String STR_CON = "jdbc:mysql://" + IP + ":3306/" + DATABASE; // string de conexao com o banco de dados

    private static Connection conexao = null;

    private static Connection minhaConexao = null;

    private static Connection abrirConexao() {
        Connection objConexao = null;
        try {
            Class.forName(DRIVER);
            objConexao = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Deu certo!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Deu errado!" + ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objConexao;
    }

    public static Connection getConexao() {
        if (minhaConexao == null) {
            minhaConexao = abrirConexao();
            return minhaConexao;
        }
        return minhaConexao;
    }

    public static Connection getConexaoCUSTON() {
        Connection cnx = null;
        try {
            cnx = abrirConexao();
            cnx.setAutoCommit(false);
            cnx.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (Exception ex) {
            System.out.println("erro conection");
        }
        return cnx;
    }
}
