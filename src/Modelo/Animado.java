package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Posicao;

public class Animado extends Personagem {

    public Animado(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bMortal = true;
    }

    /* Verifica se uma posição simulada é válida */
    protected boolean ehPosicaoValidaSimulada(int novaLinha, int novaColuna) {
        Posicao posSimulada = new Posicao(novaLinha, novaColuna);
        return Desenho.getTelaJogo()
                .getControleDeJogo()
                .ehPosicaoValida(Desenho.getTelaJogo().getFaseAtual(), posSimulada);
    }

    public boolean moveUp() {
        int novaLinha = this.pPosicao.getLinha() - 1;
        int novaColuna = this.pPosicao.getColuna();
        if (ehPosicaoValidaSimulada(novaLinha, novaColuna)) {
            this.pPosicao.setPosicao(novaLinha, novaColuna);
            return true;
        }
        return false;
    }

    public boolean moveDown() {
        int novaLinha = this.pPosicao.getLinha() + 1;
        int novaColuna = this.pPosicao.getColuna();
        if (ehPosicaoValidaSimulada(novaLinha, novaColuna)) {
            this.pPosicao.setPosicao(novaLinha, novaColuna);
            return true;
        }
        return false;
    }

    public boolean moveRight() {
        int novaLinha = this.pPosicao.getLinha();
        int novaColuna = this.pPosicao.getColuna() + 1;
        if (ehPosicaoValidaSimulada(novaLinha, novaColuna)) {
            this.pPosicao.setPosicao(novaLinha, novaColuna);
            return true;
        }
        return false;
    }

    public boolean moveLeft() {
        int novaLinha = this.pPosicao.getLinha();
        int novaColuna = this.pPosicao.getColuna() - 1;
        if (ehPosicaoValidaSimulada(novaLinha, novaColuna)) {
            this.pPosicao.setPosicao(novaLinha, novaColuna);
            return true;
        }
        return false;
    }
}