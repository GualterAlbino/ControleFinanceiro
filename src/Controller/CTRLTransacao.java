package Controller;

import DAO.DAOTransacao;
import Modelo.TransacaoClass;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gualter Albino
 */
public class CTRLTransacao {

    public static int salvar(String[] dados) {
        TransacaoClass transacao = new TransacaoClass();
        transacao.arrayTo(dados);

        DAOTransacao dao = new DAOTransacao();
        return dao.inserir(transacao);
    }

    public static void atualizar(String[] dados) {

        TransacaoClass conta = new TransacaoClass();
        conta.arrayTo(dados);

        DAOTransacao dao = new DAOTransacao();

        dao.editar(conta);

    }

    public static void deletar(int codigo) {
        DAOTransacao dao = new DAOTransacao();

        if (codigo == 0) {
            System.out.println("Codigo para exclusão não informado!");
        } else {
            dao.excluir(codigo);
        }

    }

    public static String[] recuperar(String codigo) {

        DAOTransacao dao = new DAOTransacao();
        TransacaoClass objConta = dao.recuperar(codigo);

        if (objConta == null) {
            return null;
        }

        return objConta.toArray();

    }

    public static String[][] recuperarTodos() {

        DAOTransacao dao = new DAOTransacao();

        ArrayList<TransacaoClass> listaContatos;

        listaContatos = dao.recuperarTodos();

        var lista = new ArrayList<>();

        for (var i = 0; i < listaContatos.size(); i++) {
            lista.add(listaContatos.get(i));
        }

        return (String[][]) lista.toArray();

    }

}
