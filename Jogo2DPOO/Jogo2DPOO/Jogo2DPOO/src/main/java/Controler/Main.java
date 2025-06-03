/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Controler;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author julia
 */
public class Main {

    public static void main(String[] args) throws IOException {
        JFrame tela = new JFrame();
                tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tela.setResizable(false);
                tela.setTitle("Nome do Jogo");
                
                GamePanel gamePanel = new GamePanel();
                tela.add(gamePanel);
                tela.pack();
                
                tela.setLocationRelativeTo(null); // centraliza a tela
                tela.setVisible(true);
                
                gamePanel.iniciaJogo();
    }
}
