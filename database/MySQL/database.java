package MySQL;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class database {
    static String databaseName = "funcionariosdb"; // funcionariosdb
    static String url = "jdbc:mysql://localhost:3306/"+databaseName;
    static String username = "root";
    static String password = "rootroot";
    private static int selectedId = -1;

    public static int winWidth = 1000;
    public static int winHeight = 800;

    public static void gestaoFuncionarios(){
        // Criar a janela
        try {
            // Criar a janela de gestão de funcionários
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Gestão de Funcionários");
            frame.setSize(winWidth, winHeight);
            frame.getContentPane().setLayout(null);
            frame.setLocation(new Point(200, 200));
            frame.setResizable(false);

            // Criar a barra do menu
            JMenuBar menuBar = new JMenuBar();

            // Criar o menu
            JMenu menu = new JMenu("Menu");
            menuBar.add(menu);

            // Criar o item de menu para Home
            JMenuItem menuItemHome = new JMenuItem("Home");
            menu.add(menuItemHome);
            menuItemHome.addActionListener(e -> {
                // Fechar a janela de gestão de funcionários e ir para "home"
                frame.dispose();
                home();
            });

            // Criar o item de menu para Gestão de Funcionários
            JMenuItem menuItemGestao = new JMenuItem("Gestão de Funcionários");
            menu.add(menuItemGestao);
            menuItemGestao.setEnabled(false); // Desabilitar o item já ativo
            menu.add(menuItemGestao);

            // Adicionar a barra de menu à janela
            frame.setJMenuBar(menuBar);

            // Caixa do lado esquerdo

            // Inputs ------------------

            // Botão visual
            JButton found = new JButton();
            found.setBounds(300, 60, 25, 25);
            found.setBackground(null);
            found.setFocusPainted(false);
            found.setBorderPainted(false);
            found.setEnabled(false);
            frame.add(found);

            // Input de nome
            JLabel labelNome = new JLabel("Nome:");
            labelNome.setBounds(20, 60, 80, 25);
            frame.add(labelNome);

            JTextField textNome = new JTextField();
            textNome.setBounds(100, 60, 160, 25);
            frame.add(textNome);

            // Input de idade
            JLabel labelIdade = new JLabel("Idade:");
            labelIdade.setBounds(20, 100, 80, 25);
            frame.add(labelIdade);

            JTextField textIdade = new JTextField();
            textIdade.setBounds(100, 100, 160, 25);
            frame.add(textIdade);

            // Input de Cidade
            JLabel labelCidade = new JLabel("Cidade:");
            labelCidade.setBounds(20, 140, 80, 25);
            frame.add(labelCidade);

            JTextField textCidade = new JTextField();
            textCidade.setBounds(100, 140, 160, 25);
            frame.add(textCidade);

            // Input sexo
            JLabel labelSexo = new JLabel("Sexo:");
            labelSexo.setBounds(20, 180, 80, 25);
            frame.add(labelSexo);

            // Botões radius
            JRadioButton radioMasculino = new JRadioButton("Masculino");
            radioMasculino.setBounds(100, 180, 100, 25);
            radioMasculino.setFocusable(false);
            frame.add(radioMasculino);

            JRadioButton radioFeminino = new JRadioButton("Feminino");
            radioFeminino.setBounds(200, 180, 100, 25);
            radioFeminino.setFocusable(false);
            frame.add(radioFeminino);

            // Criar o grupo para que só se possa selecionar um botão
            ButtonGroup groupSexo = new ButtonGroup();
            groupSexo.add(radioMasculino);
            groupSexo.add(radioFeminino);

            // Input COMBOBOX de profissão
            JLabel labelCargo = new JLabel("Cargo:");
            labelCargo.setBounds(20, 220, 80, 25);
            frame.add(labelCargo);

            JComboBox<String> comboCargo = new JComboBox<>();
            comboCargo.setBounds(100, 220, 160, 25);
            frame.add(comboCargo);
            comboCargo.addItem("-- Indefenido --");
            comboCargo.addItem("Administrador");
            comboCargo.addItem("Técnico");
            comboCargo.addItem("Atendimento");
            comboCargo.addItem("Cliente");
            frame.add(comboCargo);


            // Botões -------------------
            // Botão Guardar
            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(20, 500, 100, 25);
            btnGuardar.setFocusable(false);
            frame.add(btnGuardar);

            // Botão Atualizar
            JButton btnAtualizar = new JButton("Atualizar");
            btnAtualizar.setBounds(130, 500, 100, 25);
            btnAtualizar.setFocusable(false);
            frame.add(btnAtualizar);

            // Botão procurar
            JButton btnProcurar = new JButton("Procurar");
            btnProcurar.setBounds(240, 500, 100, 25);
            btnProcurar.setFocusable(false);
            frame.add(btnProcurar);

            // Botão Cancelar
            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.setBounds(130, 550, 100, 25);
            btnCancelar.setFocusable(false);
            frame.add(btnCancelar);

            // Botão reset à tabela
            JButton btnReset = new JButton("Reset DB ↺");
            btnReset.setBounds(600, winHeight-150, 100, 25);
            btnReset.setBackground(Color.red);
            btnReset.setFocusable(false);
            frame.add(btnReset);

            // Caixa do lado direito
            JTable table = new JTable();
            CustomTableModel tabelaDireita = new CustomTableModel(new String[]{"ID", "Sexo", "Nome", "Idade", "Cidade", "Cargo"}, 0);
            table.setModel(tabelaDireita);

            // Não mostrar a coluna do ID
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(0).setWidth(0);

            JScrollPane pane = new JScrollPane(table);
            pane.setBounds(400, 40, 500, 490);
            frame.add(pane);

            // Carregar dados da base de dados
            load(tabelaDireita);

            // Adicionar os listeners aos botões
            // Botão reset è tabela
            btnReset.addActionListener(e ->{
                reset();
                load(tabelaDireita);
                limpar(textNome, textIdade, textCidade, groupSexo, comboCargo);
                selectedId = -1; // Tirar a seleção de um funcionario;
            });

            // Botão Guardar
            btnGuardar.addActionListener(e -> {
                String nome = textNome.getText().trim();
                String idade = textIdade.getText().trim();
                String cidade = textCidade.getText().trim();
                String sexo = radioMasculino.isSelected() ? "M" : radioFeminino.isSelected() ? "F" : null;
                String cargo = (String) comboCargo.getSelectedItem();

                if (nome.isEmpty() || cidade.isEmpty() || sexo == null || cargo.equals("-- Indefenido --")) {
                    JOptionPane.showMessageDialog(null, "Todos os campos são necessários.", "Erro", JOptionPane.WARNING_MESSAGE);
                }else {
                    guardar(sexo, nome, idade, cidade, cargo);
                    load(tabelaDireita);
                    limpar(textNome, textIdade, textCidade, groupSexo, comboCargo);
                    selectedId = -1; // Tirar a seleção de um funcionario;
                }
            });

            // Botão Atualizar
            btnAtualizar.addActionListener(e -> {
                String nome = textNome.getText().trim();
                String idade = textIdade.getText().trim();
                String cidade = textCidade.getText().trim();
                String sexo = radioMasculino.isSelected() ? "M" : radioFeminino.isSelected() ? "F" : null;
                String cargo = (String) comboCargo.getSelectedItem();

                if (nome.isEmpty() || cidade.isEmpty() || sexo == null || cargo.equals("-- Escolha --")) {
                    JOptionPane.showMessageDialog(null, "Todos os campos são necessários.", "Erro", JOptionPane.WARNING_MESSAGE);
                } else if (selectedId == -1) {
                    JOptionPane.showMessageDialog(null, "Nenhum funcionario selecionado", "Erro", JOptionPane.WARNING_MESSAGE);
                } else {
                    atualizar(sexo, nome, idade, cidade, cargo);
                    load(tabelaDireita);
                    limpar(textNome, textIdade, textCidade, groupSexo, comboCargo);
                    selectedId = -1; // Tirar a seleção de um funcionario
                }
            });

            // Botão Cancelar
            btnCancelar.addActionListener(e -> {
                limpar(textNome, textIdade, textCidade, groupSexo, comboCargo);
                selectedId = -1; // Tirar a seleção de um funcionario;
            });

            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = table.getSelectedRow();
                    selectedId = (int) tabelaDireita.getValueAt(i, 0);
                    textNome.setText(tabelaDireita.getValueAt(i, 2).toString());
                    textIdade.setText(tabelaDireita.getValueAt(i, 3).toString());
                    textCidade.setText(tabelaDireita.getValueAt(i, 4).toString());

                    String sexo = tabelaDireita.getValueAt(i, 1).toString();
                    if (sexo.equals("M")) {
                        radioMasculino.setSelected(true);
                    } else if (sexo.equals("F")) {
                        radioFeminino.setSelected(true);
                    }

                    comboCargo.setSelectedItem(tabelaDireita.getValueAt(i, 5).toString());
                }
            });

            // Mostrar janela
            frame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }
    }

    public static void home() {
        // Criar a janela principal
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Home");
        frame.getContentPane().setLayout(null);
        frame.setSize(winWidth, winHeight);
        frame.setResizable(false);
        frame.setLocation(new Point(200, 200));

        // Criar a barra de menu
        JMenuBar menuBar = new JMenuBar();

        // Criar o menu
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        // Criar o item de menu para Gestão de Funcionários
        JMenuItem menuItemGestao = new JMenuItem("Gestão de Funcionários");
        menu.add(menuItemGestao);
        menuItemGestao.addActionListener(e -> {
            // Fechar a janela do menu e abrir a de gestão de funcionarios
            frame.dispose();
            gestaoFuncionarios();
        });

        // Criar o item de menu para Home
        JMenuItem menuItemHome = new JMenuItem("Home");
        menuItemHome.setEnabled(false);
        menu.add(menuItemHome);

        // Adicionar a barra de menu à janela
        frame.setJMenuBar(menuBar);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBounds(winWidth/2-115, winHeight/2-100, 300, 100);
        textArea.setFont(new Font("Arial", Font.BOLD, 40));
        textArea.setForeground(Color.blue);
        textArea.setBackground(null);
        textArea.setText("Bem-Vind@!");
        frame.add(textArea);

        JButton btnGestao = new JButton(">> Gestão de Funcionários <<");
        btnGestao.setBounds(winWidth/2-110, winHeight/2, 220, 25);
        btnGestao.setFocusable(false);
        btnGestao.setFocusPainted(false); // Remove a borda de foco
        frame.add(btnGestao);

        btnGestao.addActionListener(e -> {
            // Fechar a janela do menu e abrir a de gestão de funcionarios
            frame.dispose();
            gestaoFuncionarios();
        });

        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(database::home);
    }

    public static void reset(){
        String query = "delete from funcionariosdb.funcionarios where id < 100";
        String query2 = "alter table funcionariosdb.funcionarios auto_increment=1";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query);
             PreparedStatement pstmt2 = conn.prepareStatement(query2);){
            int resposta = JOptionPane.showConfirmDialog(null, "Reset database?","Reset", JOptionPane.YES_NO_OPTION);
            if (resposta == 0){
                pstmt.executeUpdate();
                pstmt2.executeUpdate();
                JOptionPane.showMessageDialog(null, "Reset à database bem sucedido!", "Reset", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }

    public static void load(DefaultTableModel model) {
        model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, sexo, nome, idade, cidade, cargo FROM funcionarios")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("sexo"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("cidade"),
                        rs.getString("cargo")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }
    }

    public static void guardar(String sexo, String nome, String idade, String cidade, String cargo) {
        String query = "INSERT INTO funcionarios (sexo, nome, idade, cidade, cargo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            try {
                pstmt.setString(1, sexo);
                pstmt.setString(2, nome);
                pstmt.setInt(3, Integer.parseInt(idade));
                pstmt.setString(4, cidade);
                pstmt.setString(5, cargo);
                pstmt.executeUpdate();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Idade | Parametro inválido: A idade deve ser um número inteiro.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }

    public static void atualizar(String sexo, String nome, String idade, String cidade, String cargo) {
        String query = "UPDATE funcionarios SET sexo = ?, nome = ?, idade = ?, cidade = ?, cargo = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            try {
                pstmt.setString(1, sexo);
                pstmt.setString(2, nome);
                pstmt.setInt(3, Integer.parseInt(idade));
                pstmt.setString(4, cidade);
                pstmt.setString(5, cargo);
                pstmt.setInt(6, selectedId);
                pstmt.executeUpdate();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Parametros inválidos: A idade deve ser um número inteiro.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }

    public static void limpar(JTextField nome, JTextField idade, JTextField cidade, ButtonGroup sexoGroup, JComboBox<String> cargoBox) {
        nome.setText("");
        idade.setText("");
        cidade.setText("");
        sexoGroup.clearSelection();
        cargoBox.setSelectedIndex(0);
    }
}

// Fazer com que as colunas não sejam editáveis
class CustomTableModel extends DefaultTableModel {
    public CustomTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

