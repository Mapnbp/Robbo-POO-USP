package Personagens;

import Auxiliar.Posicao;

public class Chaser extends Animado{
    private int iContador;

    public Chaser(String nomeImagem) {
        super(nomeImagem);
        up = true;
        left = true;
        iContador = 0;
        this.transponivel = true;
    }
    
    public void calculaDirecao (Posicao heroPos) {
        if (heroPos.getColuna() < posicao.getColuna()) {
            left = true;
        } else if (heroPos.getColuna() > posicao.getColuna()) {
            left = false;
        }
        if (heroPos.getLinha() < posicao.getLinha()) {
            up = true;
        } else if (heroPos.getLinha() > posicao.getLinha()) {
            up = false;
        }
    }

        
    public void update() {
        if(iContador == 10) { // Velocidade de movimento do personagem baseado nas atualizaÃ§Ãµes da tela
            iContador = 0;
            if (left) {
                posicao.moveLeft();
            } else {
                posicao.moveRight();
            }
            if (up) {
                posicao.moveUp();
            } else {
                posicao.moveDown();
            }
        }
        iContador++;
    }

}

