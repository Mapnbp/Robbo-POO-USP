package Auxiliar;

import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;
import Controler.Tela;

/**
 * Classe auxiliar para desenho na tela do jogo.
 * Esta classe gerencia o acesso ao objeto Tela e facilita o desenho dos elementos na tela.
 */
public class Desenho implements Serializable {

    // Referencia estatica para a Tela principal do jogo
    static Tela telaJogo;

    /*
     * Define a Tela principal do jogo para que os desenhos possam ser feitos nela.
     * @param umaTela tela do jogo
     */
    public static void setTelaJogo(Tela umaTela) {
        telaJogo = umaTela;
    }

    /**
     * Retorna a referencia da Tela do jogo atualmente setada.
     * @return tela do jogo
     */
    public static Tela getTelaJogo() {
        return telaJogo;
    }

    /**
     * Retorna o objeto Graphics usado para desenho na tela.
     * @return objeto Graphics da tela
     */
    public static Graphics getGraphicsDaTela() {
        return telaJogo.getBufferGrafico();
    }

    /**
     * Desenha uma imagem (icone) na posicao da matriz, ajustando para a camera.
     * Verifica se a posicao esta dentro da area visivel da camera para desenhar.
     * @param imagem imagem a ser desenhada (ImageIcon)
     * @param coluna posicao da coluna na matriz do jogo
     * @param linha posicao da linha na matriz do jogo
     */
    public static void desenharImagem(ImageIcon imagem, int coluna, int linha) {
        // Calcula as coordenadas na tela ajustando para a posicao da camera
        int posTelaX = (coluna - telaJogo.getCameraColuna()) * Consts.CELL_SIDE;
        int posTelaY = (linha - telaJogo.getCameraLinha()) * Consts.CELL_SIDE;

        // Verifica se a imagem esta dentro da area visivel para evitar desenho desnecessario
        if (posTelaX >= 0 && posTelaX < Consts.RES * Consts.CELL_SIDE
                && posTelaY >= 0 && posTelaY < Consts.RES * Consts.CELL_SIDE) {
            // Desenha o icone na posicao calculada
            imagem.paintIcon(telaJogo, getGraphicsDaTela(), posTelaX, posTelaY);
        }
    }

}
