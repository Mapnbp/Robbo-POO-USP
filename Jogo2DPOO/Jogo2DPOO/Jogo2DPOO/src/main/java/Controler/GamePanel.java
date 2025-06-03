package Controler;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.SaveLoad;
import Personagens.Fase;
import Personagens.Heroi;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;


public class GamePanel extends JPanel {
    
    
    // Sistema
    public KeyHandler keyH = new KeyHandler(this);
    public MouseHandler mouseH = new MouseHandler();
    public UI ui = new UI(this);
   
    public SaveLoad saveLoad = new SaveLoad(this);
    ControleFase cFase = new ControleFase(this);
    DetectorDeColisao dColisao = new DetectorDeColisao(this);
    
    ControleDeJogo cJogo = new ControleDeJogo(this);
    
    // Heroi e outros Personagens
    public Heroi heroi;
    public Fase faseAtual;
    
    // Estado de jogo
    public int gameState;
    public final int playState = 0;
    public final int pauseState = 1; // pause: troca de fase
    public final int perdeuState = 2;
    public final int fimState = 3; 
    
    // Camera
    public int cameraLinha = 0;
    public int cameraColuna = 7;
    
    // Construtor
    public GamePanel() throws IOException{        
        Desenho.setGamePanel(this);
        this.setPreferredSize(new Dimension(Consts.LARGURA,Consts.LARGURA));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setupGame() throws IOException{
        gameState = playState;
        heroi = new Heroi(this,keyH,"heroi.png");
        faseAtual = cFase.getFaseAtual();
    }
    
    // Inicia o loop do jogo - redesenha na tala 
    public void iniciaJogo() throws IOException{
        setupGame();
        TimerTask tarefaRepetitiva = new TimerTask() {
            public void run() {
                try {
                    update();
                } catch (IOException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(tarefaRepetitiva, 0L, Consts.PERIOD);
    }
    
    
    // Atualiza o que aparece na tela 
    public void update() throws IOException{
        
        if(gameState == playState){
            if(keyH.salva)
                saveLoad.save();
            
            if(keyH.carrega)
                saveLoad.load();
            /*
            if(mouseH.arquivoSolto){
                try {
                String zipFilePath = mouseH.caminhoArquivoSolto; // Obtém o caminho do arquivo diretamente do MouseHandler
                System.out.println("Tentando carregar personagens do ZIP: " + zipFilePath);

                // Verifica se o arquivo solto é realmente um ZIP antes de tentar carregar
                if (zipFilePath != null && zipFilePath.toLowerCase().endsWith(".zip")) {
                    ArrayList<Personagem> novosPersonagens = personagemLoader.loadPersonagensFromZip(zipFilePath);
                    if (!novosPersonagens.isEmpty()) {
                        System.out.println("Carregados " + novosPersonagens.size() + " novos personagens do ZIP.");
                    } else {
                        System.out.println("Nenhum personagem foi carregado do ZIP.");
                    }
                } else {
                    System.out.println("O arquivo solto não é um arquivo ZIP válido: " + zipFilePath);
                }
            } catch (IOException exc) {
                System.err.println("Erro ao carregar personagens via arrastar e soltar do ZIP: " + exc.getMessage());
                exc.printStackTrace();
            }
            finally {
                // Reseta as flags pq se nao da pau
                mouseH.arquivoSolto = false;
                mouseH.caminhoArquivoSolto = null;
            }
            }
               
            */
            
            heroi.update();
            cJogo.update();
        }
        
        if(gameState == pauseState){
           if(keyH.trocaFase){
               heroi.resetaIng();
               cFase.proxFase();
               gameState = playState;
           }
        }
        
        if(gameState == fimState){
            if(keyH.trocaFase){
                System.exit(0); 
            }
        }
    }
    
    // Desenha os personagens na tela
    public void paint(Graphics g){
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        cFase.desenhaFase(g2);
        
        heroi.desenhaPersonagem(g2);
        
        ui.draw(g2);
        
        g2.dispose();
    }
    
    // Atualiza a posicao da camera para centralizar no heroi
    public void atualizaCamera() {
        int linhaHeroi = this.heroi.posicao.getLinha();
        int colunaHeroi = this.heroi.posicao.getColuna();

        // Mantem a camera dentro dos limites do mapa
        cameraLinha = Math.max(0, Math.min(linhaHeroi - 7, 25));
        cameraColuna = Math.max(0, Math.min(colunaHeroi - 7, 0));
    }
}
