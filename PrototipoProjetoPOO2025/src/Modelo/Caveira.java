package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import Controler.Tela;
import java.awt.Graphics;
import java.io.Serializable;

public class Caveira extends Estatico {
    private int iContaIntervalos;
    
    public Caveira(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bMortal = false;
        this.iContaIntervalos = 0;
    }

    public void autoDesenho() {
        super.autoDesenho();

        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER){
            this.iContaIntervalos = 1;
            Fogo f = new Fogo("fire.png");
            f.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()+1);
            Desenho.getTelaJogo().adicionarPersonagem(f);
        }
    }    
}
