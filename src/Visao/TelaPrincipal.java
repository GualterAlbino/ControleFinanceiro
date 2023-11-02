package Visao;

import Controller.CTRLConta;
import DAO.FabricaConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends javax.swing.JFrame {

    private CTRLConta contaCtrl;

    private String[][] listaContas;

    private String RegistroAtual;

    public TelaPrincipal() {
        initComponents();

        //Inicia com os campos desabilitados
        CamposEditaveis(false);

        //Inicia o registro atual
        RegistroAtual = "0";

        //Instancia o controlador
        contaCtrl = new CTRLConta();
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
    }

    private void AbrirConsulta() {
        //Instancia a tela
        var TelaConsulta = new TelaConsulta();

        //Define ela ao centro
        TelaConsulta.setLocationRelativeTo(null);

        //Define o titulo da janela
        TelaConsulta.setTitle("Consulta de Contas");

        // Impedir o redimensionamento do JFrame
        TelaConsulta.setResizable(false);

        TelaConsulta.setVisible(true);
    }

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

    //Exclui o registro selecionado
    private void ExcluirConta() {
        if (RegistroAtual.equals("0")) {
            JOptionPane.showMessageDialog(this, "Selecione um registro a ser eliminado!", "Ação impossivel!", HEIGHT);
            return;
        } else {
            contaCtrl.deletar(Integer.parseInt(RegistroAtual));
            LimparCampos();
        }

    }

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jPanel1 = new javax.swing.JPanel();
        NovoButton = new javax.swing.JButton();
        SalvarButton = new javax.swing.JButton();
        CancelarButton = new javax.swing.JButton();
        ExcluirButton = new javax.swing.JButton();
        PesqusiarButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        TransacaoPanel = new javax.swing.JPanel();
        ScrollPanel = new javax.swing.JScrollPane();
        TabelaHistorico = new javax.swing.JTable();

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

        javax.swing.GroupLayout MainLayout = new javax.swing.GroupLayout(Main);
        Main.setLayout(MainLayout);
        MainLayout.setHorizontalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainLayout.createSequentialGroup()
                        .addComponent(AgenciaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgenciaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MainLayout.createSequentialGroup()
                        .addComponent(NomeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NomeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NumeroLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SaldoLabel, javax.swing.GroupLayout.Alignment.TRAILING))
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
                .addGap(34, 34, 34)
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgenciaLabel)
                    .addComponent(AgenciaInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaldoLabel)
                    .addComponent(SaldoInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        PesqusiarButton.setText("Pesquisar");
        PesqusiarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PesqusiarButtonMouseClicked(evt);
            }
        });

        jButton1.setText("Editar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
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
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(SalvarButton)
                .addGap(18, 18, 18)
                .addComponent(CancelarButton)
                .addGap(18, 18, 18)
                .addComponent(ExcluirButton)
                .addGap(18, 18, 18)
                .addComponent(PesqusiarButton)
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
                    .addComponent(PesqusiarButton)
                    .addComponent(jButton1))
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
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
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

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Editar();
    }//GEN-LAST:event_jButton1MouseClicked

    private void CancelarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelarButtonMouseClicked
        Cancelar();
    }//GEN-LAST:event_CancelarButtonMouseClicked

    private void PrimeiroRegistroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrimeiroRegistroButtonMouseClicked
        NavegarPrimeiro();
    }//GEN-LAST:event_PrimeiroRegistroButtonMouseClicked

    private void RetornaRegistroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RetornaRegistroButtonMouseClicked
        NavegarAnterior();
    }//GEN-LAST:event_RetornaRegistroButtonMouseClicked

    private void PesqusiarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PesqusiarButtonMouseClicked
        AbrirConsulta();
    }//GEN-LAST:event_PesqusiarButtonMouseClicked

    private void AvancaRegistroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AvancaRegistroButtonMouseClicked
        NavegarProximo();
    }//GEN-LAST:event_AvancaRegistroButtonMouseClicked

    private void UltimoRegistroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UltimoRegistroButtonMouseClicked
        NavegarUltimo();
    }//GEN-LAST:event_UltimoRegistroButtonMouseClicked

    private void CodigoInputInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_CodigoInputInputMethodTextChanged
       
    }//GEN-LAST:event_CodigoInputInputMethodTextChanged

    private void TabbedPanelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TabbedPanelFocusGained
        System.out.println("Mudou: " + RegistroAtual);
    }//GEN-LAST:event_TabbedPanelFocusGained

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton CancelarButton;
    private javax.swing.JTextField CodigoInput;
    private javax.swing.JLabel CodigoLabel;
    private javax.swing.JPanel ContaPanel;
    private javax.swing.JButton ExcluirButton;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Main;
    private javax.swing.JTextField NomeInput;
    private javax.swing.JLabel NomeLabel;
    private javax.swing.JButton NovoButton;
    private javax.swing.JTextField NumeroInput;
    private javax.swing.JLabel NumeroLabel;
    private javax.swing.JButton PesqusiarButton;
    private javax.swing.JButton PrimeiroRegistroButton;
    private javax.swing.JButton RetornaRegistroButton;
    private javax.swing.JTextField SaldoInput;
    private javax.swing.JLabel SaldoLabel;
    private javax.swing.JButton SalvarButton;
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JTabbedPane TabbedPanel;
    private javax.swing.JTable TabelaHistorico;
    private javax.swing.JPanel TransacaoPanel;
    private javax.swing.JButton UltimoRegistroButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
