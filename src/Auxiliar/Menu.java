/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auxiliar;

import Controler.Tela;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author julia
 */
public class Menu extends JPanel{
    
    public Menu(Tela tela){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);
        
        JButton startButton = new JButton("Iniciar Jogo");
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        startButton.addActionListener(e -> tela.iniciarJogo());
    }
}
