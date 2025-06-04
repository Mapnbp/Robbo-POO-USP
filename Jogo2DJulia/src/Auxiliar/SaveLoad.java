package Auxiliar;

import java.io.*;
import Controler.GamePanel;
import Personagens.Fase;
import Personagens.Heroi;


public class SaveLoad {
    private GamePanel gp;
    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public static void save(Heroi heroi, Fase fase, String caminhoArquivo) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(heroi);
            oos.writeObject(fase);
            System.out.println("Jogo salvo");

        } catch (IOException e) {
            System.err.println("Erro ao salvar o jogo: " + e.getMessage());
        }
    }

    public static Object[] load(String caminhoArquivo) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            Heroi heroi = (Heroi) ois.readObject();
            Fase fase = (Fase) ois.readObject();
            System.out.println("Jogo carregado");
            return new Object[]{heroi, fase};

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de save nao encontrado: " + caminhoArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de save: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar as classes do save: " + e.getMessage());
        }
        return null;
    }
}
