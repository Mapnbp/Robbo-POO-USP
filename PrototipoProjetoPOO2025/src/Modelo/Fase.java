package Modelo;

import java.util.ArrayList;
import java.io.Serializable;

public class Fase implements Serializable {
    public ArrayList<Personagem> personagens;

    public Fase(ArrayList<Personagem> personagens) {
        this.personagens = personagens;
    }
    
    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    public void add(Personagem umPersonagem) {
        personagens.add(umPersonagem);
    }

    public int size() {
        return personagens.size();
    }

    public Personagem get(int i) {
        return personagens.get(i);
    }

    public void remove(Personagem umPersonagem) {
        personagens.remove(umPersonagem);
    }

    public void clear() {
        personagens.clear();
    }
   
}
