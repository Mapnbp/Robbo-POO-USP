package Personagens;

import Auxiliar.Desenho;
import Auxiliar.Posicao;
import java.awt.Graphics2D;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class Personagem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public ImageIcon imagem;
    public Posicao posicao = new Posicao(1,1);
    protected boolean transponivel = false;        // Pode passar por cima?
    protected boolean mortal = false;               // Se encostar, morre?
    
    public Personagem(String nomeImagem){
        getImagem(nomeImagem);
    }
    
    // Pega a imagem certa do personagem
    public void getImagem(String nomeImagem){
        imagem = Desenho.getImagem(nomeImagem);
    }
    
    public boolean ehTransponivel(){
        return transponivel;
    }
    
    public boolean ehMortal(){
        return mortal;
    }
    
    // Movimento do personagem
    
    public void setPosicao(int linha, int coluna){
        posicao.setPosicao(linha, coluna);
    }
    public void desenhaPersonagem(Graphics2D g2){
        Desenho.draw(imagem, posicao.getLinha(),posicao.getColuna(),g2);
    }
    
}
