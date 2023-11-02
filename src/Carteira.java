
import DAO.FabricaConexao;
import Visao.TelaPrincipal;


public class Carteira {

    public static void main(String[] args) {
        //Instancia a tela
        var Tela = new TelaPrincipal();

        //Define ela ao centro
        Tela.setLocationRelativeTo(null);

        //Define o titulo da janela
        Tela.setTitle("Carteira");

        // Impedir o redimensionamento do JFrame
        Tela.setResizable(false);

        Tela.setVisible(true);
        
        var conexao = new FabricaConexao();
        
        conexao.getConexao();
    }

}
