package MySQL;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class database {
    static String databaseName = "funcionariosdb"; // funcionariosdb
    static String url = "jdbc:mysql://localhost:3306/" + databaseName;
    static String username = "root";
    static String password = "rootroot";
    private static int selectedId = -1;

    public static int winWidth = 1000;
    public static int winHeight = 800;

    public static void main(String[] args) {
        home();
    }

    // Home ---------------------------------------
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

        // Criar o item de menu para Home
        JMenuItem menuItemHome = new JMenuItem("Home");
        menuItemHome.setEnabled(false);
        menu.add(menuItemHome);

        // Criar o ‘item’ de menu para Gestão de Funcionários
        JMenuItem menuItemGestao = new JMenuItem("Gestão de Funcionários");
        menu.add(menuItemGestao);
        menuItemGestao.addActionListener(e -> {
            // Fechar a janela do menu e abrir a de gestão de funcionarios
            frame.dispose();
            gestaoFuncionarios();
        });

        // Criar o ‘item’ de menu para Gestão de Funcionários
        JMenuItem menuItemCargos = new JMenuItem("Gestão de Cargos");
        menu.add(menuItemCargos);
        menuItemCargos.addActionListener(e -> {
            // Fechar a janela do menu e abrir a de gestão de funcionarios
            frame.dispose();
            cargos();
        });

        // Adicionar a barra de menu à janela
        frame.setJMenuBar(menuBar);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBounds(winWidth / 2 - 115, winHeight / 2 - 100, 300, 100);
        textArea.setFont(new Font("Arial", Font.BOLD, 40));
        textArea.setForeground(Color.blue);
        textArea.setBackground(null);
        textArea.setText("Bem-Vind@!");
        frame.add(textArea);

        JButton btnGestao = new JButton(">> Gestão de Funcionários <<");
        btnGestao.setBounds(winWidth / 2 - 110, winHeight / 2, 220, 25);
        //btnGestao.setFocusable(false);
        btnGestao.setFocusPainted(false); // Remove a borda de foco
        frame.add(btnGestao);

        btnGestao.addActionListener(e -> {
            // Fechar a janela do menu e abrir a de gestão de funcionarios
            frame.dispose();
            gestaoFuncionarios();
        });

        frame.setVisible(true);
    }


    // Cargos ---------------------------------------
    public static void cargos() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gestão de Cargos");
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
        menuItemHome.setEnabled(true);
        menu.add(menuItemHome);
        menuItemHome.addActionListener(e -> {
            // Fechar a janela do menu e abrir a de gestão de funcionarios
            frame.dispose();
            home();
        });

        // Criar o ‘item’ de menu para Gestão de Funcionários
        JMenuItem menuItemGestao = new JMenuItem("Gestão de Funcionários");
        menu.add(menuItemGestao);
        menuItemGestao.addActionListener(e -> {
            // Fechar a janela do menu e abrir a de gestão de funcionarios
            frame.dispose();
            gestaoFuncionarios();
        });

        // Criar o ‘item’ de menu para Gestão de Funcionários
        JMenuItem menuItemCargos = new JMenuItem("Gestão de Cargos");
        menu.add(menuItemCargos);
        menuItemCargos.setEnabled(false);
        menu.add(menuItemCargos);

        // Adicionar a barra de menu à janela
        frame.setJMenuBar(menuBar);



        // Inputs, botões e texto

        // Titulo
        JTextArea titulo = new JTextArea();
        titulo.setEditable(false);
        titulo.setFocusable(false);
        titulo.setForeground(Color.BLUE);
        titulo.setBackground(null);
        titulo.setText("Cargos");
        titulo.setBounds(winWidth/2, 10, 150, 30);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titulo);

        // Tabela para meter todos os elementos no meio da janela
        JTable tabela = new JTable();
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setBackground(null);
        tabela.setBounds((winWidth/2)-160, winHeight/2-300, winWidth/2-110, winHeight/2);
        tabela.setBorder(new LineBorder(Color.GRAY, 1));

        JLabel labelCargo = new JLabel("Novo Cargo:");
        labelCargo.setBounds(20, 40, 80, 25);
        frame.add(labelCargo);

        JTextField textCargo = new JTextField();
        textCargo.setBounds(100, 40, 160, 25);
        frame.add(textCargo);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(270, 40, 100, 25);
        btnAdicionar.setFocusable(false);
        frame.add(btnAdicionar);

        DefaultListModel<String> tabelaCargos = new DefaultListModel<>();
        JList<String> listCargos = new JList<>(tabelaCargos);
        JScrollPane scrollPane = new JScrollPane(listCargos);
        scrollPane.setBounds(20, 80, 350, 150);
        frame.add(scrollPane);

        // Texto de remvoer cargo
        JTextArea textoRemover = new JTextArea("(Duplo clique num cargo para remover)");
        textoRemover.setBounds(60, 250, 300, 35);
        textoRemover.setFont(new Font("Arial", Font.BOLD, 15));
        textoRemover.setForeground(Color.gray);
        textoRemover.setBackground(null);
        textoRemover.setFocusable(false);
        frame.add(textoRemover);

        // Botão reset à tabela
        JButton btnReset = new JButton("Reset Cargos");
        btnReset.setBounds(90, 300, 200, 35);
        btnReset.setFont(new Font("Arial", Font.BOLD, 20));
        btnReset.setBackground(Color.red);
        btnReset.setForeground(Color.white);
        btnReset.setFocusable(false);
        frame.add(btnReset);
        btnReset.addActionListener(e -> {
            resetCargos();
            carregarCargos(tabelaCargos);
        });

        // Carregar cargos do banco de dados
        carregarCargos(tabelaCargos);

        tabela.add(labelCargo);
        tabela.add(textCargo);
        tabela.add(btnAdicionar);
        tabela.add(scrollPane);
        tabela.add(textoRemover);
        tabela.add(btnReset);
        tabela.setVisible(true);
        frame.add(tabela);

        btnAdicionar.addActionListener(e -> {
            String cargo = textCargo.getText().trim();
            if (!cargo.isEmpty()) {
                adicionarCargo(cargo);
                carregarCargos(tabelaCargos);
                textCargo.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Nome de cargo inválido!.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        });

        listCargos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listCargos.locationToIndex(e.getPoint());
                    String cargoSelecionado = tabelaCargos.getElementAt(index);
                    int confirm = JOptionPane.showConfirmDialog(null, "Remover o cargo '" + cargoSelecionado + "'?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        removerCargo(cargoSelecionado);
                        carregarCargos(tabelaCargos);
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private static void cargosParaCombobox(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("-- Indefinido --");
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT cargo FROM cargos");
            while (rs.next()) {
                comboBox.addItem(rs.getString("cargo"));
            }
        } catch (SQLException e) {
            System.out.println("Erro: "+ e);
        }
    }

    private static void carregarCargos(DefaultListModel<String> listModel) {
        listModel.clear();
        try (Connection conn = DriverManager.getConnection(url, username, password); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT cargo FROM cargos");
            while (rs.next()) {
                listModel.addElement(rs.getString("cargo"));
            }
        } catch (SQLException e) {
            System.out.println("Erro: "+ e);
        }
    }

    private static void adicionarCargo(String cargo) {
        String query = "INSERT INTO cargos (cargo) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cargo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro: "+ e);
        }
    }

    private static void removerCargo(String cargo) {
        String query = "DELETE FROM cargos WHERE cargo = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cargo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro: "+ e);
        }
    }

    public static void resetCargos() {
        String query = "delete from funcionariosdb.cargos where id < 100";
        String query2 = "alter table funcionariosdb.cargos auto_increment=1";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query);
             PreparedStatement pstmt2 = conn.prepareStatement(query2)) {
            int resposta = JOptionPane.showConfirmDialog(null, "Reset database de cargos?", "Reset", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                pstmt.executeUpdate();
                pstmt2.executeUpdate();
                JOptionPane.showMessageDialog(null, "Reset à database de cargos bem sucedido!", "Reset", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }


    // Funcionários ---------------------------------------
    public static void gestaoFuncionarios() {
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
            menuItemHome.setEnabled(true);
            menu.add(menuItemHome);
            menuItemHome.addActionListener(e -> {
                // Fechar a janela do menu e abrir a de gestão de funcionarios
                frame.dispose();
                home();
            });

            // Criar o ‘item’ de menu para Gestão de Funcionários
            JMenuItem menuItemGestao = new JMenuItem("Gestão de Funcionários");
            menu.add(menuItemGestao);
            menuItemGestao.setEnabled(false);
            menu.add(menuItemGestao);

            // Criar o ‘item’ de menu para Gestão de Funcionários
            JMenuItem menuItemCargos = new JMenuItem("Gestão de Cargos");
            menu.add(menuItemCargos);
            menuItemCargos.addActionListener(e -> {
                // Fechar a janela do menu e abrir a de gestão de funcionarios
                frame.dispose();
                cargos();
            });

            // Adicionar a barra de menu à janela
            frame.setJMenuBar(menuBar);

            // Caixa do lado esquerdo

            // Titulo
            JTextArea titulo = new JTextArea();
            titulo.setEditable(false);
            titulo.setFocusable(false);
            titulo.setForeground(Color.BLUE);
            titulo.setBackground(null);
            titulo.setText("Funcionários");
            titulo.setBounds(10, 10, 150, 20);
            titulo.setFont(new Font("Arial", Font.BOLD, 20));
            frame.add(titulo);

            JTable funcionarios = new JTable();
            funcionarios.setBackground(null);
            funcionarios.setForeground(Color.BLUE);
            funcionarios.setBorder(new LineBorder(Color.gray));
            funcionarios.setBounds(10, 10, winWidth-30, winHeight-80);
            frame.add(funcionarios);

            funcionarios.add(titulo);

            // Inputs ------------------

            // Botão visual
            JButton found = new JButton();
            found.setBounds(300, 60, 25, 25);
            found.setBackground(null);
            found.setFocusPainted(false);
            found.setBorderPainted(false);
            found.setEnabled(false);
            frame.add(found);
            funcionarios.add(found);

            // Input de nome
            JLabel labelNome = new JLabel("Nome:");
            labelNome.setBounds(20, 60, 80, 25);
            frame.add(labelNome);
            funcionarios.add(labelNome);

            JTextField textNome = new JTextField();
            textNome.setBounds(100, 60, 160, 25);
            frame.add(textNome);
            funcionarios.add(textNome);

            // ‘Input’ de idade
            JLabel labelIdade = new JLabel("Idade:");
            labelIdade.setBounds(20, 100, 80, 25);
            frame.add(labelIdade);
            funcionarios.add(labelIdade);

            JTextField textIdade = new JTextField();
            textIdade.setBounds(100, 100, 160, 25);
            frame.add(textIdade);
            funcionarios.add(textIdade);

            // ‘Input’ de Cidade
            JLabel labelCidade = new JLabel("Cidade:");
            labelCidade.setBounds(20, 140, 80, 25);
            frame.add(labelCidade);
            funcionarios.add(labelCidade);

            JTextField textCidade = new JTextField();
            textCidade.setBounds(100, 140, 160, 25);
            frame.add(textCidade);
            funcionarios.add(textCidade);

            // Input sexo
            JLabel labelSexo = new JLabel("Sexo:");
            labelSexo.setBounds(20, 180, 80, 25);
            frame.add(labelSexo);
            funcionarios.add(labelSexo);

            // Botões radius
            JRadioButton radioMasculino = new JRadioButton("Masculino");
            radioMasculino.setBounds(100, 180, 100, 25);
            radioMasculino.setFocusable(false);
            frame.add(radioMasculino);
            funcionarios.add(radioMasculino);

            JRadioButton radioFeminino = new JRadioButton("Feminino");
            radioFeminino.setBounds(200, 180, 100, 25);
            radioFeminino.setFocusable(false);
            frame.add(radioFeminino);
            funcionarios.add(radioFeminino);

            // Criar o grupo para que só se possa selecionar um botão
            ButtonGroup groupSexo = new ButtonGroup();
            groupSexo.add(radioMasculino);
            groupSexo.add(radioFeminino);

            // ‘Input’ COMBOBOX de profissão
            JLabel labelCargo = new JLabel("Cargo:");
            labelCargo.setBounds(20, 220, 80, 25);
            frame.add(labelCargo);
            funcionarios.add(labelCargo);

            JComboBox<String> comboCargo = new JComboBox<>();
            comboCargo.setBounds(100, 220, 160, 25);
            frame.add(comboCargo);
            comboCargo.addItem("-- Indefinido --");
            // Carregar cargos do banco de dados
            cargosParaCombobox(comboCargo);
            funcionarios.add(comboCargo);

            // Botões -------------------
            // Botão Guardar
            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(20, 500, 100, 25);
            btnGuardar.setFocusable(false);
            frame.add(btnGuardar);
            funcionarios.add(btnGuardar);

            // Botão Atualizar
            JButton btnAtualizar = new JButton("Atualizar");
            btnAtualizar.setBounds(130, 500, 100, 25);
            btnAtualizar.setFocusable(false);
            frame.add(btnAtualizar);
            funcionarios.add(btnAtualizar);

            // Botão procurar
            JButton btnProcurar = new JButton("Procurar");
            btnProcurar.setBounds(240, 500, 100, 25);
            btnProcurar.setFocusable(false);
            frame.add(btnProcurar);
            funcionarios.add(btnProcurar);

            // Botão Cancelar
            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.setBounds(130, 550, 100, 25);
            btnCancelar.setFocusable(false);
            frame.add(btnCancelar);
            funcionarios.add(btnCancelar);

            // Botão reset à tabela
            JButton btnReset = new JButton("Reset DB ↺");
            btnReset.setBounds(600, winHeight - 150, 100, 25);
            btnReset.setBackground(Color.red);
            btnReset.setFocusable(false);
            frame.add(btnReset);
            funcionarios.add(btnReset);

            // Caixa do lado direito
            JTable table = new JTable();
            CustomTableModel tabelaDireita = new CustomTableModel(new String[]{"ID", "Sexo", "Nome", "Idade", "Cidade", "Cargo"}, 0);
            table.setModel(tabelaDireita);
            funcionarios.add(table);

            // Não mostrar a coluna do ‘ID’
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(0).setWidth(0);

            JScrollPane pane = new JScrollPane(table);
            pane.setBounds(400, 40, 500, 500);
            frame.add(pane);
            funcionarios.add(pane);

            // Carregar dados da base de dados
            load(tabelaDireita);

            // Listeners dos botões
            btnProcurar.addActionListener(e -> {
                String query = "Select sexo, nome, idade, cidade, cargo from funcionariosdb.funcionarios WHERE nome = '" + textNome.getText() +"'";
                ResultSet rs;
                try (Connection conn = DriverManager.getConnection(url, username, password);
                     PreparedStatement pstmt = conn.prepareStatement(query)) {
                    try {
                        load(tabelaDireita); // Carregar tabela tudo para a tabela da direita, para caso não seja econtrado nenhum funcionario com esse nome
                        found.setBackground(null); // Para caso não encontre
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            found.setBackground(Color.green);  // Para caso encontre
                            textCidade.setText(rs.getString("cidade"));
                            loadSearch(tabelaDireita, textNome.getText()); // Carregar funcionarios encontrados para a tabela da direita, para caso não econtrado nenhum funcionario com esse nome
                        }
                        //textIdade.setText(rs.getInt("idade"));
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Erro ao aceder à B.D.", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException exc) {
                    JOptionPane.showMessageDialog(null, "Erro: " + e);
                }
            });

            btnReset.addActionListener(e -> {
                reset();
                load(tabelaDireita);
                limpar(textNome, textIdade, textCidade, groupSexo, comboCargo, tabelaDireita, found);
                selectedId = -1; // Tirar a seleção de um funcionario;
            });

            btnGuardar.addActionListener(e -> {
                String nome = textNome.getText().trim();
                String idade = textIdade.getText().trim();
                String cidade = textCidade.getText().trim();
                String sexo = radioMasculino.isSelected() ? "M" : radioFeminino.isSelected() ? "F" : null;
                String cargo = (String) comboCargo.getSelectedItem();

                if (nome.isEmpty() || cidade.isEmpty() || sexo == null || cargo.equals("-- Indefinido --")) {
                    JOptionPane.showMessageDialog(null, "Todos os campos são necessários.", "Erro", JOptionPane.WARNING_MESSAGE);
                } else {
                    guardar(sexo, nome, idade, cidade, cargo);
                    load(tabelaDireita);
                    limpar(textNome, textIdade, textCidade, groupSexo, comboCargo, tabelaDireita, found);
                    selectedId = -1; // Tirar a seleção de um funcionario;
                }
            });

            btnAtualizar.addActionListener(e -> {
                String nome = textNome.getText().trim();
                String idade = textIdade.getText().trim();
                String cidade = textCidade.getText().trim();
                String sexo = radioMasculino.isSelected() ? "M" : radioFeminino.isSelected() ? "F" : null;
                String cargo = (String) comboCargo.getSelectedItem();

                if (selectedId == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um funcionário na tabela para atualizar.", "Erro", JOptionPane.WARNING_MESSAGE);
                } else if (nome.isEmpty() || cidade.isEmpty() || sexo == null || cargo.equals("-- Indefinido --")) {
                    JOptionPane.showMessageDialog(null, "Todos os campos são necessários.", "Erro", JOptionPane.WARNING_MESSAGE);
                } else {
                    atualizar(sexo, nome, idade, cidade, cargo);
                    load(tabelaDireita);
                    limpar(textNome, textIdade, textCidade, groupSexo, comboCargo, tabelaDireita, found);
                    selectedId = -1; // Tirar a seleção de um funcionario;
                }
            });

            btnCancelar.addActionListener(e -> {
                limpar(textNome, textIdade, textCidade, groupSexo, comboCargo, tabelaDireita, found);
                selectedId = -1; // Tirar a seleção de um funcionario;
            });

            table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    selectedId = (int) tabelaDireita.getValueAt(table.getSelectedRow(), 0);
                    textNome.setText((String) tabelaDireita.getValueAt(table.getSelectedRow(), 2));
                    textIdade.setText(tabelaDireita.getValueAt(table.getSelectedRow(), 3).toString());
                    textCidade.setText((String) tabelaDireita.getValueAt(table.getSelectedRow(), 4));
                    String sexo = (String) tabelaDireita.getValueAt(table.getSelectedRow(), 1);
                    if ("M".equals(sexo)) {
                        radioMasculino.setSelected(true);
                    } else if ("F".equals(sexo)) {
                        radioFeminino.setSelected(true);
                    }
                    String cargo = (String) tabelaDireita.getValueAt(table.getSelectedRow(), 5);
                    comboCargo.setSelectedItem(cargo);
                    found.setBackground(Color.green); // Para caso encontre
                }
            });

            // Mostrar a janela
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Erro: "+ e);
        }
    }

    public static void reset() {
        String query = "delete from funcionariosdb.funcionarios where id < 100";
        String query2 = "alter table funcionariosdb.funcionarios auto_increment=1";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query);
             PreparedStatement pstmt2 = conn.prepareStatement(query2)) {
            int resposta = JOptionPane.showConfirmDialog(null, "Reset database?", "Reset", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                pstmt.executeUpdate();
                pstmt2.executeUpdate();
                JOptionPane.showMessageDialog(null, "Reset à database bem sucedido!", "Reset", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }

    public static void loadSearch(DefaultTableModel model, String nome) {
        model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, sexo, nome, idade, cidade, cargo FROM funcionarios where nome = '"+nome+"'")) {
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

    public static void limpar(JTextField nome, JTextField idade, JTextField cidade, ButtonGroup sexoGroup, JComboBox<String> cargoBox, DefaultTableModel tabelaDireita, JButton found) {
        nome.setText("");
        idade.setText("");
        cidade.setText("");
        sexoGroup.clearSelection();
        cargoBox.setSelectedIndex(0);
        load(tabelaDireita);
        found.setBackground(null);
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