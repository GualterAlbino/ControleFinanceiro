package Visao;

import Controller.CTRLConta;
import Controller.CTRLTransacao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends javax.swing.JFrame {
    
    private CTRLConta contaCtrl;
    
    private CTRLTransacao transaCtrl;
    
    private String RegistroAtual;
    
    public TelaPrincipal() {
        initComponents();

        //Inicia com os campos desabilitados
        CamposEditaveis(false);

        //Inicia o registro atual
        RegistroAtual = "0";

        //Instancia o controlador
        contaCtrl = new CTRLConta();
        
        transaCtrl = new CTRLTransacao();
        
       
        
    }

    //--------------------------------------------
    //
    // UTILITARIOS
    //
    //--------------------------------------------
    /*
    *Se verdadeiro, CAMPOS EDITAVEIS
    *Se falso, CAMPOS NÃO EDITAVEIS
     */
    private void camposEditaveis(boolean valor) {
        NomeInput.setEnabled(valor);
        AgenciaInput.setEnabled(valor);
        NumeroInput.setEnabled(valor);
        SaldoInput.setEnabled(valor);
        
    }


    /*
    *Se verdadeiro, MODO EDIÇÃO
    *Se falso, MODO LEITURA
     */
    private void modo(boolean valor) {
        
        camposEditaveis(valor);
        //Desabilitar/Habilitar Botoes
        NovoButton.setEnabled(!valor);
        EditarButton.setEnabled(!valor);
        ExcluirButton.setEnabled(!valor);
        PesquisarButton.setEnabled(!valor);

        //Define esses botoes com valores contrarios aos demaias
        SalvarButton.setEnabled(valor);
        CancelarButton.setEnabled(valor);
        
    }

    //==>Limpa os campos
    private void limparCampos() {
        NomeInput.setText("");
        AgenciaInput.setText("");
        NumeroInput.setText("");
        SaldoInput.setText("");
        
    }
    
    public void AlimentarTabela(String[][] registros) {
        DefaultTableModel table = (DefaultTableModel) TabelaConsulta.getModel();
        
        table.setRowCount(0);
        for (var i = 0; i < registros.length; i++) {
            table.addRow(new Object[]{
                registros[i][0], // Codigo
                registros[i][1], // Nome
                registros[i][2], // Agencia
                registros[i][3], // Numero
                registros[i][4], // Saldo
            });
            
        }
        
    }
    
    public void AlimentarTabelaDeTransacoes(String[][] registros) {
        try {
            DefaultTableModel table = (DefaultTableModel) TabelaHistorico.getModel();
            
            table.setRowCount(0);
            for (var i = 0; i < registros.length; i++) {
                table.addRow(new Object[]{
                    registros[i][0], // Codigo
                    //registros[i][x], //Codigo da conta
                    registros[i][2], // Descricao
                    registros[i][3], // Tipo 
                    registros[i][4], // Data           
                    registros[i][5], // Valor
                });
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
    }
    
    public void SetarRegistro() {
        int linhaSelecionada = TabelaConsulta.getSelectedRow();
        
        if (linhaSelecionada != -1) {
            DefaultTableModel tabela = (DefaultTableModel) TabelaConsulta.getModel();
            String codigo = tabela.getValueAt(linhaSelecionada, 0).toString();
            
            RegistroAtual = codigo;
            DestinoInput.setText(codigo);
            Pesquisar();
            FrameConsulta.setVisible(false);
            
        }
        
    }

    //Limpa os campos e seta o foco no primeiro campo
    private void LimparCampos() {
        CodigoInput.setText("0");
        NomeInput.setText("");
        AgenciaInput.setText("");
        NumeroInput.setText("");
        SaldoInput.setText("");
        NomeInput.requestFocus();
    }

    //Se verdadeiro, CAMPOS EDITAVEIS
    //Se falso, CAMPOS NÃO EDITAVEIS
    private void CamposEditaveis(boolean valor) {
        NomeInput.setEnabled(valor);
        AgenciaInput.setEnabled(valor);
        NumeroInput.setEnabled(valor);
        SaldoInput.setEnabled(valor);
    }
    
    private void SetarConta(String[] registro) {
        
        CodigoInput.setText(registro[0]);
        NomeInput.setText(registro[1]);
        AgenciaInput.setText(registro[2]);
        NumeroInput.setText(registro[3]);
        SaldoInput.setText(registro[4]);
        
        RegistroAtual = CodigoInput.getText();
    }

    //--------------------------------------------
    //
    // MANIPULAÇÃO DE FRAMES
    //
    //--------------------------------------------
    private void AbrirConsulta() {

        //Define ela ao centro
        FrameConsulta.setLocationRelativeTo(null);

        //Define o titulo da janela
        FrameConsulta.setTitle("Consulta de Contas");

        // Impedir o redimensionamento do JFrame
        FrameConsulta.setResizable(false);
        
        FrameConsulta.setVisible(true);
        
    }
    
    private void AbrirConsultaTransferencia() {
        //Define ela ao centro
        FrameTransferencia.setLocationRelativeTo(null);

        //Define o titulo da janela
        FrameTransferencia.setTitle("Transferência");

        // Impedir o redimensionamento do JFrame
        FrameTransferencia.setResizable(false);
        
        FrameTransferencia.setVisible(true);
        
        OrigemInput.setText(RegistroAtual);
        
    }
    
    private void AbrirPesquisaDeTransferencia() {

        //Define ela ao centro
        FrameConsulta.setLocationRelativeTo(null);

        //Define o titulo da janela
        FrameConsulta.setTitle("Consulta de Contas");

        // Impedir o redimensionamento do JFrame
        FrameConsulta.setResizable(false);
        
        FrameConsulta.setVisible(true);
    }

    //--------------------------------------------
    //
    // CRUD DE CONTAS
    //
    //--------------------------------------------
    //Função que delega se será uma edição ou inclusão
    private void Gravar() {
        String registroAtual = CodigoInput.getText();
        
        if (registroAtual.equals("0")) {
            System.out.println("O codigo recebido é: " + registroAtual);
            GravarNovaConta();
        } else {
            GravarContaEditada(registroAtual);
        }
        
        CamposEditaveis(false);
        
    }

    //Grava uma nova conta
    private void GravarNovaConta() {
        String[] novaConta = new String[5];
        novaConta[0] = "0";
        novaConta[1] = NomeInput.getText();
        novaConta[2] = AgenciaInput.getText();
        novaConta[3] = NumeroInput.getText();
        novaConta[4] = SaldoInput.getText();

        //Salva no banco
        int codigo = contaCtrl.salvar(novaConta);
        
        CodigoInput.setText(Integer.toString(codigo));
        
    }

    //Atualiza uma conta existente
    private void GravarContaEditada(String codigo) {
        
        String[] contaSelecionada = new String[5];
        contaSelecionada[0] = codigo;
        contaSelecionada[1] = NomeInput.getText();
        contaSelecionada[2] = AgenciaInput.getText();
        contaSelecionada[3] = NumeroInput.getText();
        contaSelecionada[4] = SaldoInput.getText();

        //Atualiza no banco de dados
        contaCtrl.atualizar(contaSelecionada);
        
    }

    //Busca um registro pelo seu codigo
    private void Pesquisar() {
        if (RegistroAtual == "0") {
            return;
        }
        
        var registro = contaCtrl.recuperar(RegistroAtual);
        if (registro == null) {
            JOptionPane.showMessageDialog(this, "Registro não encontardo!");
            return;
        }
        SetarConta(registro);
        
    }
    
    public void ConsultarRegistros() {
        String conteudo = ConteudoInput.getText();
        if (CamposSelect.getSelectedItem().equals("Nome")) {
            ConsultarPorNome(conteudo);
        } else {
            ConsultarPorNumero(conteudo);
        }
        
    }
    
    public void ConsultarPorNumero(String conteudo) {
        String[][] registros = contaCtrl.consultarPorNumero(conteudo);
        AlimentarTabela(registros);
    }
    
    public void ConsultarPorNome(String conteudo
    ) {
        String[][] registros = contaCtrl.consultarPorNome(conteudo);
        AlimentarTabela(registros);
    }
    
    private void Editar() {
        if (RegistroAtual == "0") {
            JOptionPane.showMessageDialog(this, "Selecione um registro a ser editado!");
            return;
        }
        CamposEditaveis(true);
    }
    
    private void Cancelar() {
        CamposEditaveis(false);
        Pesquisar();
    }
    
    private void ExcluirConta() {
        if (RegistroAtual.equals("0")) {
            JOptionPane.showMessageDialog(this, "Selecione um registro a ser eliminado!", "Ação impossivel!", HEIGHT);
            return;
        } else {
            contaCtrl.deletar(Integer.parseInt(RegistroAtual));
            LimparCampos();
        }
        
    }

    //--------------------------------------------
    //
    // NAVEGAÇÃO DE REGISTROS
    //
    //--------------------------------------------
    private void NavegarUltimo() {
        
        var registro = contaCtrl.navegarUltimo();
        if (registro == null) {
            JOptionPane.showMessageDialog(this, "Registro não encontardo!");
            return;
        }
        SetarConta(registro);
        
    }
    
    private void NavegarAnterior() {
        RegistroAtual = CodigoInput.getText();
        
        if (RegistroAtual.equals("0")) {
            NavegarPrimeiro();
            
            return;
        }
        
        var registro = contaCtrl.navegarAnterior(RegistroAtual);
        if (registro == null) {
            JOptionPane.showMessageDialog(this, "Registro não encontardo!");
            return;
        }
        SetarConta(registro);
        
    }
    
    private void NavegarProximo() {
        RegistroAtual = CodigoInput.getText();
        
        var registro = contaCtrl.navegarProximo(RegistroAtual);
        if (registro == null) {
            JOptionPane.showMessageDialog(this, "Registro não encontardo!");
            return;
        }
        SetarConta(registro);
        
    }
    
    private void NavegarPrimeiro() {
        
        var registro = contaCtrl.navegarPrimeiro();
        if (registro == null) {
            JOptionPane.showMessageDialog(this, "Registro não encontardo!");
            return;
        }
        SetarConta(registro);
        
    }

    //--------------------------------------------
    //
    // OPERAÇÕES DE TRANSFERÊNCIA
    //
    //--------------------------------------------
    private void RealizarTransferencia() {
        try {
            String[] remetente = new String[5];
            remetente = contaCtrl.recuperar(OrigemInput.getText());
            
            String[] destino = new String[5];
            destino = contaCtrl.recuperar(DestinoInput.getText());
            
            Double valor = Double.parseDouble(ValorInput.getText());
            
            System.out.println("Origem : " + OrigemInput.getText() + " Destino: " + DestinoInput.getText() + " valor: " + valor);
            
            var result = transaCtrl.Transferencia(remetente, destino, valor, "Descricao");
            
            FrameTransferencia.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
    }
    
    private void RecuperarTodasAsTransacoes() {
        try {
            if (RegistroAtual.equals("0")) {
                return;
            }
            System.out.println(RegistroAtual);
            String[][] registros = transaCtrl.consultarPorCodigo(RegistroAtual);
            AlimentarTabelaDeTransacoes(registros);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    //--------------------------------------------
    //
    // EVENTOS PADRÃO DO SWING
    //
    //--------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FrameConsulta = new javax.swing.JFrame();
        ScrollPanel1 = new javax.swing.JScrollPane();
        TabelaConsulta = new javax.swing.JTable();
        CamposSelect = new javax.swing.JComboBox<>();
        ConteudoInput = new javax.swing.JTextField();
        CampoLabel = new javax.swing.JLabel();
        ConteudoLabel = new javax.swing.JLabel();
        ConsultarButton = new javax.swing.JToggleButton();
        FrameTransferencia = new javax.swing.JFrame();
        TrasnferenciaPanel = new javax.swing.JPanel();
        OrigemLabel = new javax.swing.JLabel();
        OrigemInput = new javax.swing.JTextField();
        DestinoLabel = new javax.swing.JLabel();
        DestinoInput = new javax.swing.JTextField();
        DestinoPesquisaButton = new javax.swing.JToggleButton();
        Panel = new javax.swing.JPanel();
        ValorLabel = new javax.swing.JLabel();
        ValorInput = new javax.swing.JTextField();
        TransferirValorButton = new javax.swing.JButton();
        TabbedPanel = new javax.swing.JTabbedPane();
        ContaPanel = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        CodigoLabel = new javax.swing.JLabel();
        CodigoInput = new javax.swing.JTextField();
        PrimeiroRegistroButton = new javax.swing.JButton();
        RetornaRegistroButton = new javax.swing.JButton();
        AvancaRegistroButton = new javax.swing.JButton();
        UltimoRegistroButton = new javax.swing.JButton();
        Main = new javax.swing.JPanel();
        NomeLabel = new javax.swing.JLabel();
        NomeInput = new javax.swing.JTextField();
        AgenciaLabel = new javax.swing.JLabel();
        AgenciaInput = new javax.swing.JTextField();
        NumeroLabel = new javax.swing.JLabel();
        NumeroInput = new javax.swing.JTextField();
        SaldoLabel = new javax.swing.JLabel();
        SaldoInput = new javax.swing.JTextField();
        TransferirButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        NovoButton = new javax.swing.JButton();
        SalvarButton = new javax.swing.JButton();
        CancelarButton = new javax.swing.JButton();
        ExcluirButton = new javax.swing.JButton();
        PesquisarButton = new javax.swing.JButton();
        EditarButton = new javax.swing.JButton();
        TransacaoPanel = new javax.swing.JPanel();
        ScrollPanel = new javax.swing.JScrollPane();
        TabelaHistorico = new javax.swing.JTable();

        FrameConsulta.setMinimumSize(new java.awt.Dimension(440, 350));
        FrameConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FrameConsultaMouseClicked(evt);
            }
        });

        TabelaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nome", "Agencia", "Numero", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaConsultaMouseClicked(evt);
            }
        });
        ScrollPanel1.setViewportView(TabelaConsulta);

        CamposSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Número" }));
        CamposSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CamposSelectActionPerformed(evt);
            }
        });

        CampoLabel.setText("Campo:");

        ConteudoLabel.setText("Conteudo:");

        ConsultarButton.setText("Consultar");
        ConsultarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ConsultarButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout FrameConsultaLayout = new javax.swing.GroupLayout(FrameConsulta.getContentPane());
        FrameConsulta.getContentPane().setLayout(FrameConsultaLayout);
        FrameConsultaLayout.setHorizontalGroup(
            FrameConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FrameConsultaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(FrameConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CamposSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CampoLabel))
                .addGap(73, 73, 73)
                .addGroup(FrameConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ConteudoLabel)
                    .addComponent(ConteudoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
            .addGroup(FrameConsultaLayout.createSequentialGroup()
                .addGroup(FrameConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FrameConsultaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ScrollPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FrameConsultaLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(ConsultarButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FrameConsultaLayout.setVerticalGroup(
            FrameConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FrameConsultaLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(FrameConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CampoLabel)
                    .addComponent(ConteudoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FrameConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConteudoInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CamposSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(ConsultarButton)
                .addGap(18, 18, 18)
                .addComponent(ScrollPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        FrameTransferencia.setMinimumSize(new java.awt.Dimension(390, 300));

        TrasnferenciaPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        OrigemLabel.setText("Conta de Origem:");

        OrigemInput.setEditable(false);

        DestinoLabel.setText("Conta de Destino:");

        DestinoPesquisaButton.setText("Pesquisar");
        DestinoPesquisaButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DestinoPesquisaButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout TrasnferenciaPanelLayout = new javax.swing.GroupLayout(TrasnferenciaPanel);
        TrasnferenciaPanel.setLayout(TrasnferenciaPanelLayout);
        TrasnferenciaPanelLayout.setHorizontalGroup(
            TrasnferenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TrasnferenciaPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(TrasnferenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OrigemLabel)
                    .addComponent(OrigemInput, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addGroup(TrasnferenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DestinoPesquisaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DestinoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DestinoLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TrasnferenciaPanelLayout.setVerticalGroup(
            TrasnferenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TrasnferenciaPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(TrasnferenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrigemLabel)
                    .addComponent(DestinoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TrasnferenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrigemInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DestinoInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DestinoPesquisaButton)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        Panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ValorLabel.setText("Valor:");

        TransferirValorButton.setText("Trasnferir");
        TransferirValorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TransferirValorButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TransferirValorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ValorInput)))
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(ValorLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ValorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ValorInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TransferirValorButton)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FrameTransferenciaLayout = new javax.swing.GroupLayout(FrameTransferencia.getContentPane());
        FrameTransferencia.getContentPane().setLayout(FrameTransferenciaLayout);
        FrameTransferenciaLayout.setHorizontalGroup(
            FrameTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FrameTransferenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FrameTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TrasnferenciaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        FrameTransferenciaLayout.setVerticalGroup(
            FrameTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FrameTransferenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TrasnferenciaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TabbedPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TabbedPanelFocusGained(evt);
            }
        });

        Header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        CodigoLabel.setText("Codigo:");

        CodigoInput.setText("0");
        CodigoInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                CodigoInputFocusLost(evt);
            }
        });
        CodigoInput.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                CodigoInputInputMethodTextChanged(evt);
            }
        });

        PrimeiroRegistroButton.setText("<<");
        PrimeiroRegistroButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PrimeiroRegistroButtonMouseClicked(evt);
            }
        });

        RetornaRegistroButton.setText("<");
        RetornaRegistroButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RetornaRegistroButtonMouseClicked(evt);
            }
        });

        AvancaRegistroButton.setText(">");
        AvancaRegistroButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AvancaRegistroButtonMouseClicked(evt);
            }
        });

        UltimoRegistroButton.setText(">>");
        UltimoRegistroButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UltimoRegistroButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CodigoLabel)
                .addGap(18, 18, 18)
                .addComponent(CodigoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PrimeiroRegistroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RetornaRegistroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AvancaRegistroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(UltimoRegistroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CodigoLabel)
                        .addComponent(CodigoInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(UltimoRegistroButton)
                        .addComponent(AvancaRegistroButton)
                        .addComponent(RetornaRegistroButton)
                        .addComponent(PrimeiroRegistroButton)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Main.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        NomeLabel.setText("Nome:");

        AgenciaLabel.setText("Agência:");

        NumeroLabel.setText("Número:");

        NumeroInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumeroInputActionPerformed(evt);
            }
        });

        SaldoLabel.setText("Saldo:");

        TransferirButton.setText("Transferir");
        TransferirButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TransferirButtonMouseClicked(evt);
            }
        });
        TransferirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransferirButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MainLayout = new javax.swing.GroupLayout(Main);
        Main.setLayout(MainLayout);
        MainLayout.setHorizontalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainLayout.createSequentialGroup()
                        .addComponent(NomeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NomeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NumeroLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SaldoLabel, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(MainLayout.createSequentialGroup()
                        .addComponent(AgenciaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgenciaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(TransferirButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(NumeroInput, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(SaldoInput))
                .addGap(24, 24, 24))
        );
        MainLayout.setVerticalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NomeLabel)
                    .addComponent(NomeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NumeroLabel)
                    .addComponent(NumeroInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AgenciaLabel)
                            .addComponent(AgenciaInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SaldoLabel)
                            .addComponent(SaldoInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(MainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TransferirButton)))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        NovoButton.setText("Novo");
        NovoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NovoButtonMouseClicked(evt);
            }
        });

        SalvarButton.setText("Salvar");
        SalvarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalvarButtonMouseClicked(evt);
            }
        });

        CancelarButton.setText("Cancelar");
        CancelarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CancelarButtonMouseClicked(evt);
            }
        });

        ExcluirButton.setText("Excluir");
        ExcluirButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExcluirButtonMouseClicked(evt);
            }
        });

        PesquisarButton.setText("Pesquisar");
        PesquisarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PesquisarButtonMouseClicked(evt);
            }
        });

        EditarButton.setText("Editar");
        EditarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditarButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(NovoButton)
                .addGap(18, 18, 18)
                .addComponent(EditarButton)
                .addGap(18, 18, 18)
                .addComponent(SalvarButton)
                .addGap(18, 18, 18)
                .addComponent(CancelarButton)
                .addGap(18, 18, 18)
                .addComponent(ExcluirButton)
                .addGap(18, 18, 18)
                .addComponent(PesquisarButton)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NovoButton)
                    .addComponent(SalvarButton)
                    .addComponent(CancelarButton)
                    .addComponent(ExcluirButton)
                    .addComponent(PesquisarButton)
                    .addComponent(EditarButton))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ContaPanelLayout = new javax.swing.GroupLayout(ContaPanel);
        ContaPanel.setLayout(ContaPanelLayout);
        ContaPanelLayout.setHorizontalGroup(
            ContaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ContaPanelLayout.setVerticalGroup(
            ContaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        TabbedPanel.addTab("Conta", ContaPanel);

        TabelaHistorico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descrição", "Tipo", "Valor", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ScrollPanel.setViewportView(TabelaHistorico);

        javax.swing.GroupLayout TransacaoPanelLayout = new javax.swing.GroupLayout(TransacaoPanel);
        TransacaoPanel.setLayout(TransacaoPanelLayout);
        TransacaoPanelLayout.setHorizontalGroup(
            TransacaoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransacaoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addContainerGap())
        );
        TransacaoPanelLayout.setVerticalGroup(
            TransacaoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransacaoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        TabbedPanel.addTab("Transação", TransacaoPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedPanel)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NumeroInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumeroInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NumeroInputActionPerformed

    private void NovoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NovoButtonMouseClicked
        LimparCampos();
        CamposEditaveis(true);
    }//GEN-LAST:event_NovoButtonMouseClicked

    private void SalvarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalvarButtonMouseClicked
        Gravar();
    }//GEN-LAST:event_SalvarButtonMouseClicked

    private void ExcluirButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExcluirButtonMouseClicked
        ExcluirConta();
    }//GEN-LAST:event_ExcluirButtonMouseClicked

    private void CodigoInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodigoInputFocusLost
        RegistroAtual = CodigoInput.getText();
        
        Pesquisar();
    }//GEN-LAST:event_CodigoInputFocusLost

    private void EditarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditarButtonMouseClicked
        Editar();
    }//GEN-LAST:event_EditarButtonMouseClicked

    private void CancelarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelarButtonMouseClicked
        Cancelar();
    }//GEN-LAST:event_CancelarButtonMouseClicked

    private void PrimeiroRegistroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrimeiroRegistroButtonMouseClicked
        NavegarPrimeiro();
    }//GEN-LAST:event_PrimeiroRegistroButtonMouseClicked

    private void RetornaRegistroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RetornaRegistroButtonMouseClicked
        NavegarAnterior();
    }//GEN-LAST:event_RetornaRegistroButtonMouseClicked

    private void PesquisarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PesquisarButtonMouseClicked
        AbrirConsulta();
    }//GEN-LAST:event_PesquisarButtonMouseClicked

    private void AvancaRegistroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AvancaRegistroButtonMouseClicked
        NavegarProximo();
    }//GEN-LAST:event_AvancaRegistroButtonMouseClicked

    private void UltimoRegistroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UltimoRegistroButtonMouseClicked
        NavegarUltimo();
    }//GEN-LAST:event_UltimoRegistroButtonMouseClicked

    private void CodigoInputInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_CodigoInputInputMethodTextChanged

    }//GEN-LAST:event_CodigoInputInputMethodTextChanged

    private void TabbedPanelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TabbedPanelFocusGained
        RecuperarTodasAsTransacoes();
    }//GEN-LAST:event_TabbedPanelFocusGained

    private void TabelaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaConsultaMouseClicked
        SetarRegistro();

    }//GEN-LAST:event_TabelaConsultaMouseClicked

    private void ConsultarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConsultarButtonMouseClicked
        ConsultarRegistros();
    }//GEN-LAST:event_ConsultarButtonMouseClicked

    private void FrameConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FrameConsultaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FrameConsultaMouseClicked

    private void TransferirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransferirButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransferirButtonActionPerformed

    private void TransferirButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TransferirButtonMouseClicked
        if (RegistroAtual.equals("0")) {
            JOptionPane.showMessageDialog(this, "Selecione um registro de origem antes de tentar realizar uma transferência!");
            return;
        }
        
        AbrirConsultaTransferencia();
    }//GEN-LAST:event_TransferirButtonMouseClicked

    private void DestinoPesquisaButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DestinoPesquisaButtonMouseClicked
        AbrirPesquisaDeTransferencia();
    }//GEN-LAST:event_DestinoPesquisaButtonMouseClicked

    private void CamposSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CamposSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CamposSelectActionPerformed

    private void TransferirValorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TransferirValorButtonMouseClicked
        RealizarTransferencia();
    }//GEN-LAST:event_TransferirValorButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                    
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AgenciaInput;
    private javax.swing.JLabel AgenciaLabel;
    private javax.swing.JButton AvancaRegistroButton;
    private javax.swing.JLabel CampoLabel;
    private javax.swing.JComboBox<String> CamposSelect;
    private javax.swing.JButton CancelarButton;
    private javax.swing.JTextField CodigoInput;
    private javax.swing.JLabel CodigoLabel;
    private javax.swing.JToggleButton ConsultarButton;
    private javax.swing.JPanel ContaPanel;
    private javax.swing.JTextField ConteudoInput;
    private javax.swing.JLabel ConteudoLabel;
    private javax.swing.JTextField DestinoInput;
    private javax.swing.JLabel DestinoLabel;
    private javax.swing.JToggleButton DestinoPesquisaButton;
    private javax.swing.JButton EditarButton;
    private javax.swing.JButton ExcluirButton;
    private javax.swing.JFrame FrameConsulta;
    private javax.swing.JFrame FrameTransferencia;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Main;
    private javax.swing.JTextField NomeInput;
    private javax.swing.JLabel NomeLabel;
    private javax.swing.JButton NovoButton;
    private javax.swing.JTextField NumeroInput;
    private javax.swing.JLabel NumeroLabel;
    private javax.swing.JTextField OrigemInput;
    private javax.swing.JLabel OrigemLabel;
    private javax.swing.JPanel Panel;
    private javax.swing.JButton PesquisarButton;
    private javax.swing.JButton PrimeiroRegistroButton;
    private javax.swing.JButton RetornaRegistroButton;
    private javax.swing.JTextField SaldoInput;
    private javax.swing.JLabel SaldoLabel;
    private javax.swing.JButton SalvarButton;
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JScrollPane ScrollPanel1;
    private javax.swing.JTabbedPane TabbedPanel;
    private javax.swing.JTable TabelaConsulta;
    private javax.swing.JTable TabelaHistorico;
    private javax.swing.JPanel TransacaoPanel;
    private javax.swing.JButton TransferirButton;
    private javax.swing.JButton TransferirValorButton;
    private javax.swing.JPanel TrasnferenciaPanel;
    private javax.swing.JButton UltimoRegistroButton;
    private javax.swing.JTextField ValorInput;
    private javax.swing.JLabel ValorLabel;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
