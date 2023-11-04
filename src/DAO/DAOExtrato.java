package DAO;

import Modelo.ExtratoClass;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author saulo
 */
public class DAOExtrato {

    public boolean Salvar(Object Obj, Connection cnx) throws SQLException {
        PreparedStatement st;
        String SQL;
        ExtratoClass objExtrato = (ExtratoClass) Obj;

        if (objExtrato.getCodigo() > 0) {
            SQL = "UPDATE conta_extrato SET CONTA_EXT=?, DATA_EXT=?, DESCRICAO_EXT=?, VALOR_EXT=? where CODIGO_EXT=?";
        } else {
            SQL = "INSERT INTO conta_extrato(CONTA_EXT, DATA_EXT, DESCRICAO_EXT, VALOR_EXT) VALUES (?, ?, ?, ?)";
        }
        st = cnx.prepareStatement(SQL);

        st.setInt(1, objExtrato.getConta().getCodigo());
        st.setDate(2, null);
        st.setString(3, objExtrato.getDescricao());
        st.setDouble(4, objExtrato.getValor());
        if (objExtrato.getCodigo() > 0) {
            st.setInt(2, objExtrato.getCodigo());
        }
        st.executeUpdate();
        st.close();

        return true;

    }

}
