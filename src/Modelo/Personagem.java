package Modelo;

import Auxiliar.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;

public abstract class Personagem implements Serializable {

    protected ImageIcon iImage;
    protected Posicao pPosicao;
    protected boolean bTransponivel; /*Pode passar por cima?*/
    protected boolean bMortal;       /*Se encostar, morre?*/

    protected Personagem(String sNomeImagePNG) {
        this.pPosicao = new Posicao(1,1);
        this.bTransponivel = true;
        this.bMortal = false;
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean isbMortal() {
        return bMortal;
    }

    public boolean isbTransponivel() {
        return bTransponivel;
    }

    public Posicao getPosicao() {
        return pPosicao;
    }
    
    public boolean setPosicao(int linha, int coluna) {
        return pPosicao.setPosicao(linha, coluna);
    }
    
    public void autoDesenho(){
        Desenho.desenharImagem(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
    }

 }
