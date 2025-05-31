
package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Posicao;
import java.util.Random;

public class BichinhoVaiVemVertical extends Animado {
    boolean bUp;
    public BichinhoVaiVemVertical(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bUp = true;
    }

    public void autoDesenho(){
        if(bUp)
            moveUp();
        else
            moveDown();

        super.autoDesenho();
        bUp = !bUp;
    }  
}
