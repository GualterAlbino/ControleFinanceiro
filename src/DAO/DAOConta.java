package DAO;

import Modelo.ContaClass;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOConta {

    /**
     * Insere um objeto TransacaoClass na tabela 'transacao' do banco de dados.
     *
     * @param objConta O objeto TransacaoClass a ser inserido.
     * @return true se a inserção foi bem-sucedida, false caso contrário.
     */
    public int inserir(ContaClass objConta, Connection cnx) {
        int registrosAfetados = 0;
        int id = 0;
        try {
            //Connection conexao = FabricaConexao.getConexao();

            // Define a consulta SQL para inserir uma nova transação na tabela
            String sql = "INSERT INTO `carteira`.`conta`"
                    + "(`nome`,\n"
                    + "`agencia`,\n"
                    + "`numero`,\n"
                    + "`saldo`)"
                    + "VALUES (?, ?, ?, ?);";

            // Cria um PreparedStatement com a opção de recuperar as chaves geradas
            PreparedStatement st = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Define os valores dos parâmetros na consulta SQL
            st.setString(1, objConta.getNome());
            st.setInt(2, objConta.getAgencia());
            st.setInt(3, objConta.getNumero());
            st.setDouble(4, objConta.getSaldo());

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

    public ContaClass recuperar(String codigo, Connection cnx) {
        ContaClass conta = null;
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT * FROM `carteira`.`conta` WHERE codigo = ?";
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(codigo));
            ResultSet resultado = st.executeQuery();

            //Valida se algo foi encontardo
            if (resultado.next()) {
                conta = new ContaClass();
                conta.setCodigo(resultado.getInt("codigo"));
                conta.setNome(resultado.getString("nome"));
                conta.setAgencia(resultado.getInt("agencia"));
                conta.setNumero(resultado.getInt("numero"));
                conta.setSaldo(resultado.getDouble("saldo"));

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return conta;
    }

    public ContaClass navegarProximo(String codigo, Connection cnx) {
        ContaClass conta = null;
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT *\n"
                    + "FROM `carteira`.`conta`\n"
                    + "WHERE codigo > (SELECT codigo FROM `carteira`.`conta` WHERE codigo = ?)\n"
                    + "ORDER BY codigo ASC\n"
                    + "LIMIT 1;";
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(codigo));
            ResultSet resultado = st.executeQuery();

            //Valida se algo foi encontardo
            if (resultado.next()) {
                conta = new ContaClass();
                conta.setCodigo(resultado.getInt("codigo"));
                conta.setNome(resultado.getString("nome"));
                conta.setAgencia(resultado.getInt("agencia"));
                conta.setNumero(resultado.getInt("numero"));
                conta.setSaldo(resultado.getDouble("saldo"));

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return conta;
    }

    public ContaClass navegarAnterior(String codigo, Connection cnx) {
        ContaClass conta = null;
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT *\n"
                    + "FROM `carteira`.`conta`\n"
                    + "WHERE codigo < (SELECT codigo FROM `carteira`.`conta` WHERE codigo = ?)\n"
                    + "ORDER BY codigo DESC\n"
                    + "LIMIT 1;";
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(codigo));
            ResultSet resultado = st.executeQuery();

            //Valida se algo foi encontardo
            if (resultado.next()) {
                conta = new ContaClass();
                conta.setCodigo(resultado.getInt("codigo"));
                conta.setNome(resultado.getString("nome"));
                conta.setAgencia(resultado.getInt("agencia"));
                conta.setNumero(resultado.getInt("numero"));
                conta.setSaldo(resultado.getDouble("saldo"));

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return conta;
    }

    public ContaClass navegarUltimo(Connection cnx) {
        ContaClass conta = null;
        try {
            //Connection conexao = FabricaConexao.getConexao();

            String sql = "SELECT *\n"
                    + "FROM `carteira`.`conta`\n"
                    + "ORDER BY codigo DESC\n"
                    + "LIMIT 1;";

            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet resultado = st.executeQuery();

            //Valida se algo foi encontardo
            if (resultado.next()) {
                conta = new ContaClass();
                conta.setCodigo(resultado.getInt("codigo"));
                conta.setNome(resultado.getString("nome"));
                conta.setAgencia(resultado.getInt("agencia"));
                conta.setNumero(resultado.getInt("numero"));
                conta.setSaldo(resultado.getDouble("saldo"));

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return conta;
    }

    public ContaClass navegarPrimeiro(Connection cnx) {
        ContaClass conta = null;
        try {
            //Connection conexao = FabricaConexao.getConexao();

            String sql = "SELECT *\n"
                    + "FROM `carteira`.`conta`\n"
                    + "ORDER BY codigo ASC \n"
                    + "LIMIT 1;";

            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet resultado = st.executeQuery();

            //Valida se algo foi encontardo
            if (resultado.next()) {
                conta = new ContaClass();
                conta.setCodigo(resultado.getInt("codigo"));
                conta.setNome(resultado.getString("nome"));
                conta.setAgencia(resultado.getInt("agencia"));
                conta.setNumero(resultado.getInt("numero"));
                conta.setSaldo(resultado.getDouble("saldo"));

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return conta;
    }

    public ArrayList<ContaClass> recuperarTodos(Connection cnx) {
        ArrayList<ContaClass> contas = new ArrayList<>();
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT * FROM `carteira`.`conta`";
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                var conta = new ContaClass();

                conta.setCodigo(resultado.getInt("codigo"));
                conta.setNome(resultado.getString("nome"));
                conta.setAgencia(resultado.getInt("agencia"));
                conta.setNumero(resultado.getInt("numero"));
                conta.setSaldo(resultado.getDouble("saldo"));

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return contas;
    }

    public void editar(ContaClass conta, Connection cnx) {
        try {

            //onnection conexao = FabricaConexao.getConexao();
            String sql = "UPDATE `carteira`.`conta` SET nome=?, agencia=?, numero=?, saldo=? WHERE codigo = ?";

            PreparedStatement st = cnx.prepareStatement(sql);

            // Define os valores dos parâmetros na instrução SQL
            st.setString(1, conta.getNome());
            st.setInt(2, conta.getAgencia());
            st.setInt(3, conta.getNumero());
            st.setDouble(4, conta.getSaldo());
            st.setInt(5, conta.getCodigo());

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

    public void excluir(int id, Connection cnx) {
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "DELETE FROM `carteira`.`conta` WHERE codigo=?";
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

    public ArrayList<ContaClass> consultarPorNumero(String numero, Connection cnx) {
        ArrayList<ContaClass> contas = new ArrayList<>();
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT * FROM carteira.conta WHERE CAST(numero AS CHAR) LIKE ?";
            PreparedStatement st = cnx.prepareStatement(sql);

            // Usamos '%' para representar qualquer sequência de caracteres antes ou depois do número
            st.setString(1, "%" + numero + "%");
            ResultSet resultado = st.executeQuery();

            // Valida se algo foi encontrado
            while (resultado.next()) {
                var conta = new ContaClass();

                conta.setCodigo(resultado.getInt("codigo"));
                conta.setNome(resultado.getString("nome"));
                conta.setAgencia(resultado.getInt("agencia"));
                conta.setNumero(resultado.getInt("numero"));
                conta.setSaldo(resultado.getDouble("saldo"));

                contas.add(conta);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return contas;
    }

    public ArrayList<ContaClass> consultarPorNome(String nome, Connection cnx) {
        ArrayList<ContaClass> contas = new ArrayList<>();
        try {
            //Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT * FROM carteira.conta WHERE nome LIKE ?";
            PreparedStatement st = cnx.prepareStatement(sql);

            // '%' para representar qualquer sequência de caracteres antes ou depois do nome
            st.setString(1, "%" + nome + "%");
            ResultSet resultado = st.executeQuery();

            // Valida se algo foi encontrado
            while (resultado.next()) {
                var conta = new ContaClass();

                conta.setCodigo(resultado.getInt("codigo"));
                conta.setNome(resultado.getString("nome"));
                conta.setAgencia(resultado.getInt("agencia"));
                conta.setNumero(resultado.getInt("numero"));
                conta.setSaldo(resultado.getDouble("saldo"));

                contas.add(conta);
                System.out.println("Indice: " + contas.size());
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar: " + ex.getMessage());
        }
        return contas;
    }

}
