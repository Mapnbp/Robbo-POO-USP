package Modelo;

import Auxiliar.Desenho;

public class Hero extends Animado {
    private int vidas = 5;
    
    public Hero(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bMortal = false;
    }

    @Override
    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.getTelaJogo().getControleDeJogo().ehPosicaoValida(
                    Desenho.getTelaJogo().getFaseAtual(), this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;       
    }
    
    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }
}
