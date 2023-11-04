
package Facade;

import DAO.DAOConta;
import DAO.DAOExtrato;
import DAO.FabricaConexao;
import Modelo.ContaClass;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author gualt
 */
public class ContaFACADE {

    public static void executarOperacao(Connection conexao, ContaClass objConta) throws SQLException {
        DAOConta DAOConta = new DAOConta();
        DAOExtrato DAOExtrato = new DAOExtrato();
        DAOConta.Salvar(objConta, conexao);
        for (int i = 0; i < objConta.getTamanhoExtrato(); i++) {
            DAOExtrato.Salvar(objConta.getExtrato(i), conexao);
        }
    }

    public static boolean AtualizarConta(ContaClass objConta) throws SQLException {
        Connection conexao = FabricaConexao.getConexaoCUSTON();

        try {
            executarOperacao(conexao, objConta);
            conexao.commit();
            return true;
        } catch (Exception e) {

            conexao.rollback();

            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

}
