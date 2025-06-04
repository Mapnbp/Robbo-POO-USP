package Personagens;

import Controler.GamePanel;
import Controler.KeyHandler;

public class Heroi extends Personagem {

    private transient GamePanel gp;
    private transient KeyHandler keyH;
    private int vidas = 5;
    private int ingColetados = 0;
    
    // Construtor
    public Heroi(GamePanel gp, KeyHandler keyH, String nomeImagem){
        super(nomeImagem);
        this.gp = gp;
        this.keyH = keyH;
    }
    
    public int getVidas(){
        return vidas;
    }
    
    public void setVidas(int novaVida){
        vidas = novaVida;
    }
    
    public int getQtddIng(){
        return ingColetados;
    }
    
    public void resetaIng(){
        ingColetados = 0;
    }
    
    public void coletouIng(){
        ingColetados++;
    }
    
    // Atualiza a posicao do heroi
    public void update(){
        if(keyH.cima){ 
            posicao.moveUp(); 
        }
        else if(keyH.baixo){ 
            posicao.moveDown(); 
        }
        else if(keyH.esquerda){ 
            posicao.moveLeft(); 
        }
        else if(keyH.direita){ 
            posicao.moveRight();
        }
        
        gp.atualizaCamera();
    }

    public void setControladores(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }
}
