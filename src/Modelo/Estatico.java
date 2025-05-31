package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Posicao;

public class Estatico extends Personagem {
    
    public Estatico(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false;
    }
}
