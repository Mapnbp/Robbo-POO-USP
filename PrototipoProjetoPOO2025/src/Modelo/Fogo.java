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

    @Override
    public void autoDesenho() {
        super.autoDesenho();
        if(!this.moveRight())
            Desenho.getTelaJogo().removerPersonagem(this);
    }

}
