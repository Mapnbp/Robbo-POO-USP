package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Posicao;
import Controler.Tela;
import java.awt.Graphics;
import java.io.Serializable;

public class Fogo extends Animado{

    public Fogo(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bMortal = true;
    }

    private boolean validaPosicao() {
        if (!Desenho.getTelaJogo().getControleDeJogo().ehPosicaoValida(
                Desenho.getTelaJogo().getFaseAtual(), this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;
    }

    public void voltaAUltimaPosicao() {pPosicao.volta();}

    @Override
    public boolean moveRight() {
        if (this.pPosicao.moveRight())
            return validaPosicao();
        return false;
    }

    @Override
    public void autoDesenho() {
        if(!this.moveRight()) {
            Desenho.getTelaJogo().removerPersonagem(this);
        }
        super.autoDesenho();
    }

}
