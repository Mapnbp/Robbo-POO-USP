package Controler;

import Auxiliar.Bloco;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Personagens.Fase;
import Personagens.Personagem;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ControleFase {
    
    //private static final long serialVersionUID = 1L;
    
    GamePanel gp;
    
    int nroFaseAtual = 1;
    Bloco[] bloco;
    
    public Fase faseAtual;
    public int numBloco[][];

    // Construtor
    public ControleFase(GamePanel gp) throws IOException{
        this.gp = gp;
                
        faseAtual = new Fase(new ArrayList<>());
                
        bloco = new Bloco[2];
        numBloco = new int[Consts.MUNDO_LARGURA][Consts.MUNDO_ALTURA];

        getImagemBloco();
        carregarFase("fases/fase1.txt");
        faseAtual.setNroFase(1);
    }
    
    public Fase getFaseAtual(){
        return faseAtual;
    }
    
    // Carrega uma fase: mapa e personagens
    public void carregarFase(String caminhoArquivo) {
        if (nroFaseAtual < 6) {
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                carregarPersonagens(br);
                carregarMapa(br);
                br.close();
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo: " + e.getMessage());
            }
            faseAtual.setNroFase(nroFaseAtual);
        } else {
            System.exit(0);
        }
    }
    
    private void carregarPersonagens(BufferedReader br) throws IOException {
        String linha;
        // Processar os personagens ate o "Fim"
        while ((linha = br.readLine()) != null) {
            if (linha.equalsIgnoreCase("Fim;")) {
                break;
            }

            linha = linha.trim();
            if (linha.isEmpty()) {
                continue;
            }

            String[] partes = linha.split(";");
            if (partes.length < 4) {
                System.out.println("Linha de persongagem com formato indevido");
                continue; // Vai ignorar caso a linha nao seja valida e ira prosseguir para o proximo
            }

            String tipo = partes[0].trim();
            String imagem = partes[1].trim();
            int x = Integer.parseInt(partes[2].trim());
            int y = Integer.parseInt(partes[3].trim());

            try {
                Class<?> clazz = Class.forName("Personagens." + tipo);

                if (Personagem.class.isAssignableFrom(clazz)) {
                    Personagem personagem = (Personagem) clazz
                            .getDeclaredConstructor(String.class)
                            .newInstance(imagem);
                    personagem.setPosicao(x, y);
                    this.faseAtual.add(personagem);
                } else {
                    System.err.println(tipo + " não e um Personagem valido");
                }
            } catch (ClassNotFoundException e) {
                System.err.println("ERRO: Classe Modelo." + tipo + " nao encontrada.");
            } catch (Exception e) {
                System.err.println("Erro ao criar " + tipo + ": " + e.toString());
            }
        } 
    }

    public void getImagemBloco(){

            bloco[0] = new Bloco();
            bloco[0].imagem = Desenho.getImagem("chao.png");
            bloco[0].transponivel = true;

            bloco[1] = new Bloco();
            bloco[1].imagem = Desenho.getImagem("parede.png");

    }
    
    public void carregarMapa(BufferedReader br) throws IOException {

        String linhaMatriz;
        int linha = 0;
            
        // Leitura da matriz das paredes
        while ((linhaMatriz = br.readLine()) != null) {
                               
            if (linhaMatriz.equalsIgnoreCase("Fim;")) 
                break;
                
            String[] partes = linhaMatriz.split(";");
                if (partes.length == 15) {
                    for (int coluna = 0; coluna < 15; coluna++) {
                        int num = Integer.parseInt(partes[coluna]);
                        numBloco[coluna][linha] = num;
                    }
                    linha++;
                } 
                else 
                    System.err.println("Aqui Linha da matriz com numero incorreto de colunas: " + linhaMatriz);                
        }
            
    }
    
    public void desenhaFase(Graphics2D g2){
        
        for(int linha = 0; linha < Consts.MUNDO_ALTURA; linha++){
            for(int coluna = 0; coluna < Consts.MUNDO_LARGURA; coluna++){
            
                int num = numBloco[coluna][linha];
                Desenho.draw(bloco[num].imagem,linha,coluna,g2);
            }
        }
        
        for(Personagem p : faseAtual.getPersonagens()){
            p.desenhaPersonagem(g2);
        }
    }
    
    public void proxFase() throws IOException{
        nroFaseAtual++;
        faseAtual.setNroFase(nroFaseAtual);
        faseAtual.resetaTotalIng();
        if (nroFaseAtual < 6){
            this.faseAtual.clear();
            carregarFase("fases/fase" + nroFaseAtual + ".txt");
        }
        else {
            gp.gameState = gp.fimState;
        }
        //saveLoad.save();
    }

    
    
}
