package MySQL;

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

    public static void main(String[] args) {
        // Criar a janela
        try {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Gestão de funcionários");
            frame.setSize(1000, 700);
            frame.getContentPane().setLayout(null);
            frame.setLocation(new Point(500, 200));
            frame.setResizable(false);

            // Caixa do lado esquerdo---------------------------------------------------------

            // -- Inputs --
            // Input de ID (não editável)
            JLabel labelID = new JLabel("ID:");
            labelID.setBounds(20, 20, 80, 25);
            frame.add(labelID);

            JTextField textID = new JTextField();
            textID.setBounds(100, 20, 160, 25);
            textID.setEditable(false);
            frame.add(textID);

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


            // -- Botões --
            // Botão Guardar
            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(20, 180, 100, 25);
            frame.add(btnGuardar);

            // Botão Atualizar
            JButton btnAtualizar = new JButton("Atualizar");
            btnAtualizar.setBounds(130, 180, 100, 25);
            frame.add(btnAtualizar);

            // Botão Cancelar
            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.setBounds(240, 180, 100, 25);
            frame.add(btnCancelar);

            // Caixa do lado direito ---------------------------------------------------------
            JTable table = new JTable();
            CustomTableModel tabelaDireita = new CustomTableModel(new String[]{"ID", "Nome", "Idade", "Cidade"}, 0);
            table.setModel(tabelaDireita);

            JScrollPane pane = new JScrollPane(table);
            pane.setBounds(400, 20, 500, 600);
            frame.add(pane);

            // Carregar dados da base de dados
            load(tabelaDireita);

            // Adicionar os listeners aos botões
            // Botão Guardar
            btnGuardar.addActionListener(e -> {
                guardar(textNome.getText(), textIdade.getText(), textCidade.getText());
                load(tabelaDireita);
                limpar(textID, textNome, textIdade, textCidade);
            });

            // Botão Atualizar
            btnAtualizar.addActionListener(e -> {
                atualizar(textID.getText(), textNome.getText(), textIdade.getText(), textCidade.getText());
                    load(tabelaDireita);
                    limpar(textID, textNome, textIdade, textCidade);
            });

            // Botão Cancelar
            btnCancelar.addActionListener(e -> limpar(textID, textNome, textIdade, textCidade));

            // Listener quando é clicado em alguma row na tabela da direita
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = table.getSelectedRow();
                    textID.setText(tabelaDireita.getValueAt(i, 0).toString());
                    textNome.setText(tabelaDireita.getValueAt(i, 1).toString());
                    textIdade.setText(tabelaDireita.getValueAt(i, 2).toString());
                    textCidade.setText(tabelaDireita.getValueAt(i, 3).toString());
                }
            });

            // Mostrar janela
            frame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }

    }

    public static void load(DefaultTableModel model) {
        model.setRowCount(0); // Apagar as rows todas da tabela
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM funcionarios")) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("nome"), rs.getInt("idade"), rs.getString("cidade")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }
    }

    public static void guardar(String nome, String idade, String cidade) {
        String query = "INSERT INTO funcionarios (nome, idade, cidade) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            try {
                pstmt.setString(1, nome);
                pstmt.setInt(2, Integer.parseInt(idade));
                pstmt.setString(3, cidade);
                pstmt.executeUpdate();
            } catch (NumberFormatException e) { // Caso seja exception relacionado à idade
                JOptionPane.showMessageDialog(null, "Idade | Parametro inválido: A idade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }

    public static void atualizar(String id, String nome, String idade, String cidade) {
        String query = "UPDATE funcionarios SET nome = ?, idade = ?, cidade = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = conn.prepareStatement(query)) {
            try {
                pstmt.setString(1, nome);
                pstmt.setInt(2, Integer.parseInt(idade));
                pstmt.setString(3, cidade);
                pstmt.setInt(4, Integer.parseInt(id));
                pstmt.executeUpdate();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Idade ou ID | Parametros inválidos: A idade e o ID devem ser números inteiros.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }

    public static void limpar(JTextField id, JTextField nome, JTextField idade, JTextField cidade) {
        id.setText("");
        nome.setText("");
        idade.setText("");
        cidade.setText("");
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

