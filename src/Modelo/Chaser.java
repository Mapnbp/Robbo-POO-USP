/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import java.io.Serializable;

/**
 *
 * @author 2373891
 */
public class Chaser extends Animado {

    private boolean iDirectionV;
    private boolean iDirectionH;
    private int iContador;
    public Chaser(String sNomeImagePNG) {
        super(sNomeImagePNG);
        iDirectionV = true;
        iDirectionH = true;
        iContador = 0;
        this.bTransponivel = true;
    }

    public void computeDirection(Posicao heroPos) {
        if (heroPos.getColuna() < this.getPosicao().getColuna()) {
            iDirectionH = true;
        } else if (heroPos.getColuna() > this.getPosicao().getColuna()) {
            iDirectionH = false;
        }
        if (heroPos.getLinha() < this.getPosicao().getLinha()) {
            iDirectionV = true;
        } else if (heroPos.getLinha() > this.getPosicao().getLinha()) {
            iDirectionV = false;
        }
    }

    public void autoDesenho() {
        if(iContador == 10) { // Velocidade de movimento do personagem baseado nas atualizações da tela
            iContador = 0;
            if (iDirectionH) {
                this.moveLeft();
            } else {
                this.moveRight();
            }
            if (iDirectionV) {
                this.moveUp();
            } else {
                this.moveDown();
            }
        }
        super.autoDesenho();
        iContador++;
    }

}
