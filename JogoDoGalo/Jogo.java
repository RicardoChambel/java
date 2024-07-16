package JogoDoGalo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class Jogo {
    private static boolean jogador1Vez = true; // Para saber quem está a jogar
    private static JButton[] buttons = new JButton[9]; // Botões do tabuleiro
    private static int pontosJ1 = 0;
    private static int pontosJ2 = 0;
    private static JLabel pontos;
    private static int jogadas = 0; // Contador de jogadas

    public static void main(String[] args) {
        // Frame
        JFrame frame = new JFrame("Jogo do Galo");
        frame.setSize(800, 800);
        frame.setResizable(false);
        Color customColor = new Color(0, 110, 227);
        frame.getContentPane().setBackground(customColor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Titulo
        JLabel titulo = new JLabel("Jogo do Galo");
        titulo.setBounds(290, 50, 300, 100);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 30));
        titulo.setForeground(Color.white);

        // Jogador 1
        JLabel j1 = new JLabel("Jogador 1: o");
        j1.setBounds(20, 20, 200, 100);
        j1.setFont(new Font("Arial", Font.BOLD, 20));
        j1.setForeground(Color.white);

        // Jogador 2
        JLabel j2 = new JLabel("Jogador 2: x");
        j2.setBounds(20, 50, 200, 100);
        j2.setFont(new Font("Arial", Font.BOLD, 20));
        j2.setForeground(Color.white);

        // Info de pontos
        pontos = new JLabel("J1 "+pontosJ1 + ":" + pontosJ2+" J2");
        pontos.setBounds(290, 600, 250, 100);
        pontos.setFont(new Font("Arial", Font.BOLD, 50));
        pontos.setForeground(Color.white);

        // Painel para a grade de jogo do galo
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));
        gridPanel.setBounds(225, 200, 350, 350);
        gridPanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.white));

        // Adicionando botões à grade
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton("");
            button.setFont(new Font("Arial", Font.PLAIN, 50));
            button.setBorder(new MatteBorder(2, 2, 2, 2, Color.white));
            button.setBackground(customColor);
            button.setFocusPainted(false); // Para não ficar selecionado (focado) ao carregar no quadrado

            // Event Listener para adicionar X ou O ao quadrado
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (button.getText().equals("")) {
                        if (jogador1Vez) {
                            button.setText("O");
                            animateHighlight(j2, j1);
                        } else {
                            button.setText("X");
                            animateHighlight(j1, j2);
                        }
                        verificar(); // Verificar se com esta jogada ganhou o jogo
                        jogador1Vez = !jogador1Vez; // Mudar a vez do jogador
                        jogadas++;
                        System.out.println(jogadas);
                        if (jogadas == 9) { // Se todas as jogadas foram feitas e ninguém ganhou (empate)
                            JOptionPane.showMessageDialog(frame, "Empate!");
                            reset();
                            jogadas = 0;
                        }
                    }
                }
            });
            buttons[i] = button;
            gridPanel.add(button);
        }

        // Adicionar elementos ao Frame
        frame.add(titulo);
        frame.add(j1);
        frame.add(j2);
        frame.add(gridPanel);
        frame.add(pontos);

        frame.setLayout(null);
        frame.setVisible(true);

        // Destacar inicialmente o jogador 1
        animateHighlight(j1, j2);
    }

    private static void verificar() {
        String[][] combinacoes = { // Combinações possíveis no jogo do galo
                {buttons[0].getText(), buttons[1].getText(), buttons[2].getText()}, // Horizontal primeira linha
                {buttons[3].getText(), buttons[4].getText(), buttons[5].getText()}, // Horizontal segunda linha
                {buttons[6].getText(), buttons[7].getText(), buttons[8].getText()}, // Horizontal terceira linha
                {buttons[0].getText(), buttons[3].getText(), buttons[6].getText()}, // Vertical primeira coluna
                {buttons[1].getText(), buttons[4].getText(), buttons[7].getText()}, // Vertical segunda coluna
                {buttons[2].getText(), buttons[5].getText(), buttons[8].getText()}, // Vertical terceira coluna
                {buttons[0].getText(), buttons[4].getText(), buttons[8].getText()}, // Diagonal principal
                {buttons[2].getText(), buttons[4].getText(), buttons[6].getText()}  // Diagonal secundária
        };

        for (String[] combinacao : combinacoes) {
            if (combinacao[0].equals("O") && combinacao[1].equals("O") && combinacao[2].equals("O")) {
                pontosJ1++;
                JOptionPane.showMessageDialog(null, "Vecendor: Jogador 1");
                atualizarPontos();
                reset();
                return;
            } else if (combinacao[0].equals("X") && combinacao[1].equals("X") && combinacao[2].equals("X")) {
                pontosJ2++;
                JOptionPane.showMessageDialog(null, "Vecendor: Jogador 2");
                atualizarPontos();
                reset();
                return;
            }
        }
    }

    private static void atualizarPontos() {
        pontos.setText("J1 "+pontosJ1 + ":" + pontosJ2+" J2");
    }

    private static void reset() {
        jogadas = 0;
        for (JButton button : buttons) {
            button.setText("");
        }
    }

    private static void animateHighlight(JLabel toHighlight, JLabel toDiminuir) {
        Timer timer = new Timer(50, null); // Timer de 50 ms
        timer.addActionListener(new ActionListener() {
            float alpha = 0f; // Transparência

            public void actionPerformed(ActionEvent e) {
                alpha += 0.5f; // Aumentar a transparência
                if (alpha >= 1f) {
                    alpha = 1f;
                    timer.stop();
                }
                toHighlight.setForeground(new Color(1f, 1f, 1f, alpha)); // Destacar o jogador atual
                toDiminuir.setForeground(new Color(1f, 1f, 1f, 1.3f - alpha)); // Diminuir o destaque do jogador anterior
            }
        });
        timer.start();
    }
}
