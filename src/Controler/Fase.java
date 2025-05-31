package Controler;

import Modelo.Ingrediente;
import Modelo.Personagem;
import java.util.ArrayList;
import java.io.*;

public class Fase implements Serializable {
    private static final long serialVersionUID = 1L;
    public ArrayList<Personagem> personagens;
    private int iTotalIngredientes = 0;
    private int iIngredientesRestantes = 0;
    private int nroFase;

    public Fase(ArrayList<Personagem> personagens, int fasesQtd) {
        this.personagens = personagens;
        this.nroFase = fasesQtd;
    }

    public int getNroFase() {
        return nroFase;
    }

    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    public void add(Personagem umPersonagem) {
        if (umPersonagem instanceof Ingrediente) {
            iTotalIngredientes++;
        }
        personagens.add(umPersonagem);
    }

    public int getFaseQtdd(){
        return nroFase;
    }

    public int getiTotalIngredientes() {
        return iTotalIngredientes;
    }

    public void setiTotalIngredientes(int iTotalIngredientes) {
        this.iTotalIngredientes = iTotalIngredientes;
    }

    public int getiIngredientesRestantes() {
        return iTotalIngredientes;
    }

    public void setiIngredientesRestantes(int iTotalIngredientes) {
        this.iTotalIngredientes = iTotalIngredientes;
    }

    public void setNroFase(int nroFase) {
        this.nroFase = nroFase;
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

    // Corrigido: salva o próprio objeto, sem precisar de fasesQtd como parâmetro
    public void save() {
        String caminhoArquivo = "fase.dat";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(this);
            System.out.println("Fase " + nroFase + " salva com sucesso no arquivo " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a fase: " + e.getMessage());
        }
    }

    // Corrigido: usa o objeto carregado diretamente
    public static Fase load(ArrayList<Personagem> personagens, int fasesQtd) {
        String caminhoArquivo = "fase.dat";
        File file = new File(caminhoArquivo);

        if (!file.exists()) {
            System.out.println("Arquivo " + caminhoArquivo + " não encontrado. Criando nova fase " + fasesQtd);
            return new Fase(personagens, fasesQtd);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            Object obj = ois.readObject();

            if (obj instanceof Fase) {
                Fase fase = (Fase) obj;
                System.out.println("Fase " + fase.getNroFase() + " carregada com sucesso do arquivo " + caminhoArquivo);
                return fase;
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