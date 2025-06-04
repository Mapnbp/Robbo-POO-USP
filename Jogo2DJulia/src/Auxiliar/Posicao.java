package Auxiliar;

import java.io.Serializable;

public class Posicao implements Serializable {
    
    protected int linha, coluna ;
    protected int linhaAntes, colunaAntes;
    
    public Posicao(int linha, int coluna){
        setPosicao(linha,coluna);
    }
    
    public void defaultPosicao(){
        linha = 1;
        coluna = 1;
    }
    
    public void setPosicao(int novaLinha, int novaColuna){
        linhaAntes = this.linha;
        this.linha = novaLinha;
        
        colunaAntes = this.coluna;
        this.coluna = novaColuna;
    }
    
    // Acessar as coordenadas
    public int getLinha() {
        return linha;
    }
    public int getColuna() {
        return coluna;
    }
    
    // Compara posicoes
    public boolean igual(Posicao posicao) {
        return (linha == posicao.getLinha() && coluna == posicao.getColuna());
    }
    
    // Volta para a posicao anterior
    public void volta(){
        linha = linhaAntes;
        coluna = colunaAntes;
    }
    
    // Atualiza as coordenadas do personagem
    public void moveUp() {
        setPosicao(this.linha - 1 , this.coluna);
    }

    public void moveDown() {
        setPosicao(this.linha + 1 , this.coluna);
    }

    public void moveRight() {
        setPosicao(this.linha, this.coluna + 1);
    }

    public void moveLeft() {
        setPosicao(this.linha, this.coluna - 1);
    }
}
