package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Posicao;
import java.util.Random;

public class ZigueZague extends Animado {
    
    public ZigueZague(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }

    public void autoDesenho(){
        Random rand = new Random();
        int iDirecao = rand.nextInt(4);
        
        if(iDirecao == 1)
            moveRight();
        else if(iDirecao == 2)
            moveDown();
        else if(iDirecao == 3)
            moveLeft();
        else if(iDirecao == 4)
            moveUp();
        
        super.autoDesenho();
    }    
}
