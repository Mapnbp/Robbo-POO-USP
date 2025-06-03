package Controler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {
    
    GamePanel gp;
    public boolean cima, baixo, direita, esquerda,
                   andou, trocaFase, carrega, salva;
    
    
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        switch (tecla) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                cima = true;
                andou = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                baixo = true;
                andou = true;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                esquerda = true;
                andou = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                direita = true;
                andou = true;
                break;
            case KeyEvent.VK_C:
                if(gp.gameState == gp.pauseState || 
                   gp.gameState == gp.fimState)
                    trocaFase = true;
                break;
            case KeyEvent.VK_E: // salva
                if(gp.gameState == gp.playState)
                    salva = true;
                break;
            case KeyEvent.VK_L: // carrega o jogo salvo
                if(gp.gameState == gp.playState)
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
                andou = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                baixo = false;
                andou = false;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                esquerda = false;
                andou = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                direita = false;
                andou = false;
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
