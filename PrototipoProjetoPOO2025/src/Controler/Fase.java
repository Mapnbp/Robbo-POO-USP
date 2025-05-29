package Controler;

import Modelo.Ingrediente;
import Modelo.Personagem;
import java.util.ArrayList;
import java.io.*;

public class Fase implements Serializable {
    private static final long serialVersionUID = 1L;
    public ArrayList<Personagem> personagens;
    private int iContaIngredientesRestantes = 0;
    private int contadorDeFases;
    public Fase(ArrayList<Personagem> personagens, int fasesQtd) {
        this.personagens = personagens;
        this.contadorDeFases = fasesQtd;
    }

    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    public void add(Personagem umPersonagem) {
        if (umPersonagem instanceof Ingrediente) {
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

    // Salva a fase atual no arquivo correspondente (com base no contador de fases)
    public void save(int fasesQtd) {
        String caminhoArquivo = "fase" + fasesQtd + ".dat";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(this);
            System.out.println("Fase " + fasesQtd + " salva com sucesso no arquivo " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a fase: " + e.getMessage());
        }
    }

    // Metodo estático para carregar uma fase com base no número da fase
    public static Fase load(ArrayList<Personagem> personagens, int fasesQtd) {
        String caminhoArquivo = "fase" + fasesQtd + ".dat";
        File file = new File(caminhoArquivo);

        if (!file.exists()) {
            System.out.println("Arquivo " + caminhoArquivo + " não encontrado. Criando nova fase " + fasesQtd);
            // Cria uma nova fase
            return new Fase(personagens, fasesQtd);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            Object obj = ois.readObject();

            if (obj instanceof Fase) {
                System.out.println("Fase " + fasesQtd + " carregada com sucesso do arquivo " + caminhoArquivo);
                return (Fase) obj;
            } else {
                System.err.println("Erro: objeto no arquivo não é uma Fase válida.");
                return new Fase(personagens, fasesQtd);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar a fase: " + e.getMessage());
            return new Fase(personagens, fasesQtd);
        }
    }

}