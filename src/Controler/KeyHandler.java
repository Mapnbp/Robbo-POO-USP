/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author julia
 */
public class KeyHandler implements KeyListener {

    public boolean cima, baixo, direita, esquerda,
                   trocaFase, carrega, salva;
    
    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        switch (tecla) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                cima = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                baixo = true;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                esquerda = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                direita = true;
                break;
            case KeyEvent.VK_C:
                trocaFase = true;
                break;
            case KeyEvent.VK_E: // salva
                salva = true;
                break;
            case KeyEvent.VK_L: // carrega o jogo salvo
                carrega = true;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {
    int tecla = e.getKeyCode();
        switch (tecla) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                cima = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                baixo = false;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                esquerda = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                direita = false;
                break;
            case KeyEvent.VK_C:
                trocaFase = false;
                break;
            case KeyEvent.VK_E: // salva
                salva = false;
                break;
            case KeyEvent.VK_L: // carrega o jogo salvo
                carrega = false;
                break;
        }
    }
    
}
