package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BichinhoVaiVemHorizontal extends Animado {

    private boolean bRight;
    int iContador;

    public BichinhoVaiVemHorizontal(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bRight = true;
        iContador = 0;
    }

    public void autoDesenho() {
        if (iContador == 5) {
            iContador = 0;
            if (bRight) {
                moveRight();
            } else {
                moveLeft();
            }

            bRight = !bRight;
        }
        super.autoDesenho();
        iContador++;
    }
}
