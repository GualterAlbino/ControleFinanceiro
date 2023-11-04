package DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class FabricaConexao {

    //private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String STR_DRIVER = "org.gjt.mm.mysql.Driver";  // definição de qual banco será utilizado
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DATABASE = "carteira";
    private static final String IP = "127.0.0.1";
    private static final String PORTA = "3306";
    //private static final String URL = "jdbc:mysql://" + IP + ":" + PORTA + "/" + DATABASE;
    private static final String STR_CON = "jdbc:mysql://" + IP + ":3306/" + DATABASE; // string de conexao com o banco de dados
    private static Connection objConexao = null;

    private static Connection conexao = null;

    public FabricaConexao() {
        try {
            Class.forName(STR_DRIVER);
            objConexao = DriverManager.getConnection(STR_CON, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            String errorMsg = "Driver nao encontrado: " + e.getMessage();
            JOptionPane.showMessageDialog(null, errorMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            String errorMsg = "Erro ao obter a conexao: " + e.getMessage();
            JOptionPane.showMessageDialog(null, errorMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Connection getConexaoDEFAULT() throws SQLException {
        if (objConexao == null) {
            FabricaConexao objGlobal = new FabricaConexao();
        }
        return objConexao;
    }

    public static Connection getConexaoCUSTON() {
        Connection cnx = null;
        try {
            Class.forName(STR_DRIVER);
            cnx = DriverManager.getConnection(STR_CON, USER, PASSWORD);

            cnx.setAutoCommit(false);
            cnx.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        } catch (Exception ex) {
            Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cnx;
    }
}
