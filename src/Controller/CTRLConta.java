package Controller;

import DAO.DAOConta;
import DAO.FabricaConexao;
import Modelo.ContaClass;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gualter Albino
 */
public class CTRLConta {

    public static int salvar(String[] dados) {
        try {

            ContaClass conta = new ContaClass();
            conta.arrayTo(dados);

            DAOConta dao = new DAOConta();
            return dao.inserir(conta);
        } finally {
        }

    }

    public static void atualizar(String[] dados) {

        ContaClass conta = new ContaClass();
        conta.arrayTo(dados);

        DAOConta dao = new DAOConta();

        dao.editar(conta);

    }

    public static void deletar(int codigo) {
        DAOConta dao = new DAOConta();

        if (codigo == 0) {
            System.out.println("Codigo para exclusão não informado!");
        } else {
            dao.excluir(codigo);
        }

    }

    public static String[] recuperar(String codigo) {

        DAOConta dao = new DAOConta();
        ContaClass objConta = dao.recuperar(codigo);

        if (objConta == null) {
            return null;
        }

        return objConta.toArray();

    }

    public static String[][] recuperarTodos() {

        DAOConta dao = new DAOConta();

        ArrayList<ContaClass> listaContatos;

        listaContatos = dao.recuperarTodos();

        var lista = new ArrayList<>();

        for (var i = 0; i < listaContatos.size(); i++) {
            lista.add(listaContatos.get(i));
        }

        return (String[][]) lista.toArray();

    }

    public static String[] navegarUltimo() {

        DAOConta dao = new DAOConta();
        ContaClass objConta = dao.navegarUltimo();

        if (objConta == null) {
            return null;
        }

        return objConta.toArray();

    }

    public static String[] navegarAnterior(String codigo) {

        DAOConta dao = new DAOConta();
        ContaClass objConta = dao.navegarAnterior(codigo);

        if (objConta == null) {
            return null;
        }

        return objConta.toArray();

    }

    public static String[] navegarProximo(String codigo) {

        DAOConta dao = new DAOConta();
        ContaClass objConta = dao.navegarProximo(codigo);

        if (objConta == null) {
            return null;
        }

        return objConta.toArray();

    }

    public static String[] navegarPrimeiro() {

        DAOConta dao = new DAOConta();
        ContaClass objConta = dao.navegarPrimeiro();

        if (objConta == null) {
            return null;
        }

        return objConta.toArray();

    }

    public static String[][] consultarPorNumero(String numero) {

        DAOConta dao = new DAOConta();
        ArrayList<ContaClass> listaContatos = dao.consultarPorNumero(numero);

        String[][] resultado = new String[listaContatos.size()][5];

        for (int i = 0; i < listaContatos.size(); i++) {
            ContaClass conta = listaContatos.get(i);
            resultado[i][0] = String.valueOf(conta.getCodigo());
            resultado[i][1] = conta.getNome();
            resultado[i][2] = String.valueOf(conta.getAgencia());
            resultado[i][3] = String.valueOf(conta.getNumero());
            resultado[i][4] = String.valueOf(conta.getSaldo());
        }

        return resultado;

    }

    public static String[][] consultarPorNome(String nome) {
        DAOConta dao = new DAOConta();
        ArrayList<ContaClass> listaContatos = dao.consultarPorNome(nome);

        String[][] resultado = new String[listaContatos.size()][5];

        for (int i = 0; i < listaContatos.size(); i++) {
            ContaClass conta = listaContatos.get(i);
            resultado[i][0] = String.valueOf(conta.getCodigo());
            resultado[i][1] = conta.getNome();
            resultado[i][2] = String.valueOf(conta.getAgencia());
            resultado[i][3] = String.valueOf(conta.getNumero());
            resultado[i][4] = String.valueOf(conta.getSaldo());
        }

        return resultado;
    }

}
