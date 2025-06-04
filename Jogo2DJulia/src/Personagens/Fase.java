package Personagens;

import java.io.Serializable;
import java.util.ArrayList;


public class Fase implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public ArrayList<Personagem> personagens;
    private int totalIng = 0;
    private int nroFase;
    
    // Construtor
    public Fase(ArrayList<Personagem> personagens) {
        this.personagens = personagens;
        //this.nroFase = fasesQtd;
    }
    
    // Sobreescreve os metodos de ArrayList
    public void add(Personagem umPersonagem) {
        // Determina quantos ingredientes devem ser coletados
        if (umPersonagem instanceof Ingrediente) {
            totalIng++;
        }
        personagens.add(umPersonagem);
    }
    
    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }
    
    public Personagem get(int i) {
        return personagens.get(i);
    }
    
    public int size() {
        return personagens.size();
    }

    public void remove(Personagem umPersonagem) {
        personagens.remove(umPersonagem);
    }

    public void clear() {
        personagens.clear();
    }
    
    // Verifica se todos os ingredientes da fase foram coletados
    public boolean concluida(Heroi heroi){
        return totalIng == heroi.getQtddIng();
    }
    
    // Get para os dados da fase atual
    public int getTotalIng(){
        return totalIng;
    }
    
    public void resetaTotalIng(){
        totalIng = 0;
    }
    
    public int getNroFase(){
        return nroFase;
    }
    
    public void setNroFase(int fase){
        nroFase = fase;
    }

}
