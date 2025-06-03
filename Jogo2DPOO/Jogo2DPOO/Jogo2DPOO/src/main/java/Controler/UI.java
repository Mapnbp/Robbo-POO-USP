package Controler;

import Auxiliar.Desenho;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class UI {
    
    GamePanel gp;
    Font arial_40;
    
    Graphics2D g2;
    
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 20);
    }
    
    public void draw(Graphics2D g2){
        
        this.g2 =g2;
        
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        
        if(gp.gameState == gp.playState){
            
            int fase = gp.faseAtual.getNroFase();
            String infoFase = "FASE " + fase;
            g2.drawString(infoFase , 10, 25);
            
            int vidas = gp.heroi.getVidas();
            String infoVida = "Vidas: " + vidas;
            g2.drawString(infoVida , 250, 25);

            int n = gp.heroi.getQtddIng();
            int N = gp.faseAtual.getTotalIng();
            
            String infoIng = "Ingredientes coletados: " + n + " / " + N;
            g2.drawString(infoIng , 480 , 25);
        }
        
        if(gp.gameState == gp.pauseState){
            int fase = gp.faseAtual.getNroFase();
            drawTrocaFase(fase);
        }
        
        if(gp.gameState == gp.perdeuState){
            String text = "Você perdeu.";
            g2.drawString(text,50,100);
        }
        
        if(gp.gameState == gp.fimState){
            String text = "FIM DE JOGO";
            g2.drawString(text,100,50);
        }
    }
    
    public void drawTrocaFase(int fase){
        String nomeImagem = "final" + fase + ".png";
        ImageIcon img = Desenho.getImagem(nomeImagem);
        Desenho.drawFimFase(img, g2);
    }
}
