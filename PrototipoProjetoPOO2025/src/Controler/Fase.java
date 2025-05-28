package Controler;

import Modelo.Ingrediente;
import Modelo.Personagem;
import java.util.ArrayList;
import java.io.Serializable;

public class Fase implements Serializable {
    public ArrayList<Personagem> personagens;
    private int iContaIngredientesRestantes = 0;

    public Fase(ArrayList<Personagem> personagens) {
        this.personagens = personagens;
    }
    
    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    public void add(Personagem umPersonagem) {
        if(umPersonagem instanceof Ingrediente) {
            iContaIngredientesRestantes++;
        }
        personagens.add(umPersonagem);
    }

    public int getiContaIngredientesRestantes() {
        return iContaIngredientesRestantes;
    }

    public void setiContaIngredientesRestantes(int iContaIngredientesRestantes) {
        this.iContaIngredientesRestantes = iContaIngredientesRestantes;
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
