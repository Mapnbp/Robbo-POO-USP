package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Posicao;

public class Animado extends Personagem{
    
    public Animado(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }
    
    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }
    
    /*TO-DO: este metodo pode ser interessante a todos os personagens que se movem*/
    private boolean validaPosicao() {
        if (!Desenho.getTelaJogo().getControleDeJogo().ehPosicaoValida(
                Desenho.getTelaJogo().getFaseAtual(), this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;
    }
    
    public boolean moveUp() {
        if (this.pPosicao.moveUp())
            return validaPosicao();
        return false;
    }

    public boolean moveDown() {
        if (this.pPosicao.moveDown())
            return validaPosicao();
        return false;
    }

    public boolean moveRight() {
        if (this.pPosicao.moveRight())
            return validaPosicao();
        return false;
    }

    public boolean moveLeft() {
        if (this.pPosicao.moveLeft())
            return validaPosicao();
        return false;
    }
    
}
