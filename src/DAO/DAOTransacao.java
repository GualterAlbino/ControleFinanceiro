package DAO;

import Modelo.ContaClass;
import Modelo.TransacaoClass;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class DAOTransacao {

    private Connection cnx;

    public DAOTransacao() {
        cnx = FabricaConexao.getConexao();
    }

    public DAOTransacao(Connection pCnx) {
        cnx = pCnx;
    }

    /**
     * Insere um objeto TransacaoClass na tabela 'transacao' do banco de dados.
     *
     * @param objtransacao O objeto TransacaoClass a ser inserido.
     * @return true se a inserção foi bem-sucedida, false caso contrário.
     */
    public int inserir(TransacaoClass transacao) {
        int registrosAfetados = 0;
        int id = 0;
        try {
            //Connection conexao = FabricaConexao.getConexao();

            // Define a consulta SQL para inserir uma nova transação na tabela
            String sql = "INSERT INTO `carteira`.`transacao`"
                    + "(`codigoConta`,\n"
                    + "`descricao`,\n"
                    + "`tipo`,\n"
                    + "`valor`,\n"
                    + "`data`)"
                    + "VALUES (?, ?, ?, ?, ?);";

            // Cria um PreparedStatement com a opção de recuperar as chaves geradas
            PreparedStatement st = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Define os valores dos parâmetros na consulta SQL
            st.setInt(1, transacao.getCodigoConta());
            st.setString(2, transacao.getDescricao());
            st.setString(3, transacao.getTipo());
            st.setDouble(4, transacao.getValor());
            st.setString(5, transacao.getData());

            // Executa a consulta SQL e obtém o número de registros afetados
            registrosAfetados = st.executeUpdate();

            // Verifica se pelo menos um registro foi inserido
            if (registrosAfetados > 0) {
                // Obtém as chaves geradas, incluindo o último ID inserido
                ResultSet generatedKeys = st.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    System.out.println("Último ID inserido: " + id);

                }
            } else {
                System.out.println("Nenhum registro foi inserido.");
            }

            return (id);

        } catch (SQLException ex) {
            // Captura exceções de SQL e lança uma exceção personalizada
            throw new Error("Erro ao inserir registro: ", ex);
        }

    }

    public TransacaoClass recuperar(String codigo) {
        TransacaoClass transacao = null;
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT * FROM `carteira`.`transacao` WHERE codigo = ?";
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(codigo));
            ResultSet resultado = st.executeQuery();

            //Valida se algo foi entransacaordo
            if (resultado.next()) {
                transacao = new TransacaoClass();

                transacao.setCodigo(resultado.getInt("codigo"));
                transacao.setCodigoConta(resultado.getInt("codigoConta"));
                transacao.setDescricao(resultado.getString("descricao"));
                transacao.setTipo(resultado.getString("tipo"));
                transacao.setValor(resultado.getDouble("valor"));
                transacao.setData(resultado.getString("data"));

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return transacao;
    }

    public ArrayList<TransacaoClass> recuperarTodos() {
        ArrayList<TransacaoClass> transacoes = new ArrayList<>();
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT * FROM `carteira`.`transacao`";
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                var transacao = new TransacaoClass();

                transacao.setCodigo(resultado.getInt("codigo"));
                transacao.setCodigoConta(resultado.getInt("codigoConta"));
                transacao.setDescricao(resultado.getString("descricao"));
                transacao.setTipo(resultado.getString("tipo"));
                transacao.setValor(resultado.getDouble("valor"));
                transacao.setData(resultado.getString("data"));

                transacoes.add(transacao);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return transacoes;
    }

    public void editar(TransacaoClass transacao) {
        try {

            //Connection conexao = FabricaConexao.getConexao();
            String sql = "UPDATE `carteira`.`transacao` SET codigotransacao=?, descricao=?, tipo=?, valor=?, data=? WHERE codigo = ?";

            PreparedStatement st = cnx.prepareStatement(sql);

            // Define os valores dos parâmetros na instrução SQL
            st.setInt(1, transacao.getCodigoConta());
            st.setString(2, transacao.getDescricao());
            st.setString(3, transacao.getTipo());
            st.setDouble(4, transacao.getValor());
            st.setString(5, transacao.getData());

            // Executa a instrução SQL de atualização
            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Registro atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro foi atualizado.");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar registro: " + ex.getMessage());
        }
    }

    public void excluir(int id) {
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "DELETE FROM `carteira`.`transacao` WHERE codigo=?";
            PreparedStatement st = cnx.prepareStatement(sql);

            st.setInt(1, id);

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Registro excluído com sucesso!");
            } else {
                System.out.println("Nenhum registro foi excluído.");
            }

        } catch (SQLException ex) {
            throw new Error("Erro ao excluir registro: " + ex.getMessage());

        }
    }

    public ArrayList<TransacaoClass> consultarPorNumero(String codigo) {
        ArrayList<TransacaoClass> transacoes = new ArrayList<>();
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT * FROM carteira.transacao WHERE codigoConta = ?";
            PreparedStatement st = cnx.prepareStatement(sql);

            st.setInt(1, Integer.parseInt(codigo));
            ResultSet resultado = st.executeQuery();
            // Valida se algo foi encontrado
            while (resultado.next()) {
                var transacao = new TransacaoClass();

                transacao.setCodigo(resultado.getInt("codigo"));
                transacao.setCodigoConta(resultado.getInt("codigoConta"));
                transacao.setDescricao(resultado.getString("descricao"));
                transacao.setTipo(resultado.getString("tipo"));
                transacao.setValor(resultado.getDouble("valor"));
                transacao.setData(resultado.getString("data"));

                transacoes.add(transacao);
            }

        } catch (SQLException ex) {
            System.out.println("Erro na camada DAO: " + ex.getMessage());
        }
        return transacoes;
    }

    /*
    public static boolean Transferencia(ContaClass conta1, ContaClass conta2, double valor, String descricao) throws SQLException {
        Connection conexao = FabricaConexao.getConexaoCUSTON();
        boolean transf = false;
        try {
            if (conta1.getCodigo() != 0 && conta2.getCodigo() != 0) {
                ContaClass contaRemetente = new ContaClass();

                ContaClass contaDestinatario = new ContaClass();

                DAOTransacao daocnx = new DAOTransacao(conexao);
                DAOConta daoconta = new DAOConta(conexao);

                if (contaRemetente.getSaldo() >= valor) {

                    contaRemetente.setSaldo(contaRemetente.getSaldo() - valor);
                    contaDestinatario.setSaldo(contaRemetente.getSaldo() + valor);

                    TransacaoClass transacaoRemetente = new TransacaoClass();
                    transacaoRemetente.setCodigoConta(contaRemetente.getCodigo());
                    transacaoRemetente.setData(Calendar.getInstance().toString());
                    transacaoRemetente.setDescricao(descricao);
                    //transacaoRemetente.setTipo('E');
                    transacaoRemetente.setValor(valor);

                    TransacaoClass transacaoDestinatario = new TransacaoClass();
                    transacaoDestinatario.setCodigoConta(contaDestinatario.getCodigo());
                    transacaoDestinatario.setData(Calendar.getInstance().toString());
                    transacaoDestinatario.setDescricao(descricao);
                    // transacaoDestinatario.setTipo('R');
                    transacaoDestinatario.setValor(valor);

                    daocnx.inserirTransacao(transacaoRemetente);
                    daocnx.inserirTransacao(transacaoDestinatario);

                    daoconta.editar(contaDestinatario);
                    daoconta.editar(contaRemetente);

                    conexao.commit();
                    transf = true;
                }
            }

        } catch (Exception ex) {
            conexao.rollback();
            transf = false;
        } finally {
            conexao.close();
        }
        return transf;
    }
     */
}
