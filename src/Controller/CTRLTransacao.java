package Controller;

import DAO.DAOConta;
import DAO.DAOTransacao;
import DAO.FabricaConexao;
import Modelo.ContaClass;
import Modelo.TransacaoClass;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Gualter Albino
 */
public class CTRLTransacao {

    public static boolean Transferencia(String[] contaRemetente, String[] contaDestinatario, double valor, String descricao) throws SQLException {
        Connection conexao = FabricaConexao.getConexaoCUSTON();
        boolean transf = false;
        try {
            if (contaRemetente[0] != "0" && contaDestinatario[0] != "0") {
                System.out.println("Aqui: " + contaRemetente[0] + " - " + contaRemetente[1] + " - " + contaRemetente[2] + " - " + contaRemetente[3] + " - " + contaRemetente[4]);

                DAOTransacao daocnx = new DAOTransacao(conexao);
                DAOConta daoconta = new DAOConta(conexao);

                if (Double.parseDouble(contaRemetente[4]) >= valor) {

                    contaRemetente[4] = (String.valueOf(Double.parseDouble(contaRemetente[4]) - valor));
                    contaDestinatario[4] = (String.valueOf(Double.parseDouble(contaRemetente[4]) + valor));

                    // Obtenha a data atual
                    Date dataAtual = new Date();

                    // Crie um formato para a data no estilo brasileiro
                    SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                    // Formate a data atual no formato brasileiro
                    String dataFormatada = formatoBrasileiro.format(dataAtual);

                    String[] transacaoRemetente = new String[5];
                    transacaoRemetente[0] = contaRemetente[0];
                    transacaoRemetente[1] = descricao;
                    transacaoRemetente[2] = "SAIDA";
                    transacaoRemetente[3] = Double.toString(valor);
                    transacaoRemetente[4] = dataFormatada;

                    String[] transacaoDestinatario = new String[5];
                    transacaoDestinatario[0] = contaDestinatario[0];
                    transacaoDestinatario[1] = descricao;
                    transacaoDestinatario[2] = "ENTRADA";
                    transacaoDestinatario[3] = Double.toString(valor);
                    transacaoDestinatario[4] = dataFormatada;

                    TransacaoClass transRemete = new TransacaoClass();
                    TransacaoClass transDestino = new TransacaoClass();

                    daocnx.inserir(transRemete.arrayTo(transacaoRemetente));
                    daocnx.inserir(transDestino.arrayTo(transacaoDestinatario));

                    ContaClass contaRemete = new ContaClass();
                    ContaClass contaDestino = new ContaClass();

                    daoconta.editar(contaRemete.arrayTo(contaRemetente));
                    daoconta.editar(contaDestino.arrayTo(contaDestinatario));

                    conexao.commit();
                    transf = true;
                }
            }

        } catch (Exception ex) {
            System.out.println("Erro: " + ex);
            conexao.rollback();
            transf = false;
        } finally {
            conexao.close();
        }
        return transf;
    }

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

    public static String[][] consultarPorCodigo(String codigo) {
        try {
            DAOTransacao dao = new DAOTransacao();
            ArrayList<TransacaoClass> listaTransacoes = dao.consultarPorNumero(codigo);

            String[][] resultado = new String[listaTransacoes.size()][6];

            for (int i = 0; i < listaTransacoes.size(); i++) {
                TransacaoClass transacao = listaTransacoes.get(i);
                resultado[i][0] = String.valueOf(transacao.getCodigo());
                resultado[i][1] = String.valueOf(transacao.getCodigoConta());
                resultado[i][2] = String.valueOf(transacao.getDescricao());
                resultado[i][3] = String.valueOf(transacao.getTipo());
                resultado[i][4] = transacao.getValor().toString();
                resultado[i][5] = String.valueOf(transacao.getData());
            }

            return resultado;
        } catch (Exception e) {
            System.out.println("Erro na camada CONTROLLER: " + e);
            return null;
        }

    }
}
