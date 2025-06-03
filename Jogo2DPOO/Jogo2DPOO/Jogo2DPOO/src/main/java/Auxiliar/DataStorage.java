package Auxiliar;

import Personagens.Fase;
import Personagens.Heroi;
import java.io.Serializable;


public class DataStorage implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    Heroi heroi; 
    Fase faseAtual;

}
