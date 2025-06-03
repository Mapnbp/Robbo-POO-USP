package Auxiliar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import Controler.GamePanel;

public class SaveLoad {
    
    GamePanel gp;

    public SaveLoad() {
    }
    
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }
    
    public void save(){
        String caminhoArquivo = "fase.dat";
        
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo));
            
            DataStorage ds = new DataStorage();
            ds.heroi = gp.heroi;
            ds.faseAtual = gp.faseAtual;
            
            oos.writeObject(ds);
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar a fase: " + e.getMessage());
        }
    }
    
    public void load(){
    
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("fase.dat")));
            
            DataStorage ds = (DataStorage)ois.readObject();
            
            gp.heroi = ds.heroi;
            gp.faseAtual = ds.faseAtual;
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fase: " + e.getMessage());
        }
    }

}
