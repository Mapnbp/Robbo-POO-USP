package Controler;

// Bibliotecas importadas:
// - Auxiliar.*: Classes auxiliares como constantes, desenho e posicoes no jogo.
import Auxiliar.*;

// - Modelo.*: Classes dos personagens e da fase do jogo.
import Modelo.*;

// - java.awt.*: Para manipulacao grafica (Graphics, Image, Toolkit).
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

// - java.awt.image.ImageObserver: Interface para observar imagens carregadas.
import java.awt.image.ImageObserver;

// - java.io.*: Para manipulacao de arquivos, tratamento de excecoes IO.
import java.io.File;
import java.io.IOException;

// - java.util.*: Utilitarios como listas, timer e logger.
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

// - javax.swing.*: Biblioteca Swing para interface grafica (JFrame, GroupLayout).
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;

// Para ler as fases
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

// Classe principal da janela do jogo que representa a tela de exibicao e controle do jogo.
// Implementa KeyListener para capturar os eventos de teclado.
public class Tela extends JFrame { //implements KeyListener, MouseListener {

    private Hero heroi;
    private Fase faseAtual;

    // ControleDeJogo centraliza regras e processamento da fase
    private ControleDeJogo controleDeJogo = new ControleDeJogo();

    // Buffer grafico para desenhar na tela
    private Graphics graphicsBuffer;
    
    // KeyHandler para leitura do teclado
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();

    // Coordenadas da "camera" que controla qual parte do mapa esta sendo exibida na tela
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    private int contadorDeFases = 1;
    private int faseSalva = 1;
    
    

    // Construtor
    public Tela() {
        Desenho.setTelaJogo(this);
        this.inicializaComponentes();
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseH);

        // Define o tamanho da janela considerando as bordas e insets
        this.setSize(750 + this.getInsets().left + this.getInsets().right, 750 + this.getInsets().top + this.getInsets().bottom);

        this.faseAtual = new Fase(new ArrayList<>(),contadorDeFases);
        this.carregarFase("fases/fase" + contadorDeFases + ".txt");
        this.faseAtual.setiIngredientesRestantes(this.faseAtual.getiTotalIngredientes());
        //this.carregaFaseSalva(); nao ta funcionando
    }

    // Carrega uma fase: mapa e personagens
    public void carregarFase(String caminhoArquivo) {
        if (contadorDeFases < 6) {
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                carregarPersonagem(br);
                carregarParedes(br);
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo: " + e.getMessage());
            }
        } else {
            System.exit(0);
        }
    }

    private void carregarPersonagem(BufferedReader br) throws IOException {
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

            if (tipo.equals("Hero")) {
                this.heroi = new Hero(imagem);
                this.heroi.setPosicao(x, y);
                this.faseAtual.add(this.heroi);
            } else {
                try {
                    Class<?> clazz = Class.forName("Modelo." + tipo);

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
    }

    private void carregarParedes(BufferedReader br) throws IOException {
        String linha;
        int linhaMatriz = 1;

        // Processar a matriz das paredes
        while ((linha = br.readLine()) != null) {
            if (linha.equalsIgnoreCase("Fim;")) {
                break;
            }

            String[] partes = linha.split(";");
            if (partes.length == 13) {
                for (int coluna = 0; coluna < 13; coluna++) {
                    if (partes[coluna].equals("1")) {
                        try {
                            Personagem parede = new Estatico("parede.png");
                            parede.setPosicao(linhaMatriz, coluna + 1);
                            this.faseAtual.add(parede);
                        } catch (Exception e) {
                            System.err.println("Erro ao criar parede: " + e.getMessage());
                        }
                    }
                }
            } else {
                System.err.println("Linha da matriz com numero incorreto de colunas: " + linhaMatriz);
            }
            linhaMatriz++;
        }
    }

    // Getters para a posicao atual da camera
    public int getCameraLinha() {
        return this.cameraLinha;
    }

    public int getCameraColuna() {
        return this.cameraColuna;
    }

    public Fase getFaseAtual() {
        return faseAtual;
    }

    public ControleDeJogo getControleDeJogo() {
        return controleDeJogo;
    }

    // Verifica se uma posicao no mapa e valida para movimentacao
    public boolean posicaoEhValida(Posicao posicao) {
        return this.controleDeJogo.ehPosicaoValida(this.faseAtual, posicao);
    }

    // Adiciona um personagem na fase atual
    public void adicionarPersonagem(Personagem personagem) {
        this.faseAtual.add(personagem);
    }

    // Remove um personagem da fase atual
    public void removerPersonagem(Personagem personagem) {
        this.faseAtual.remove(personagem);
    }

    // Retorna o buffer grafico usado para desenhar na tela
    public Graphics getBufferGrafico() {
        return this.graphicsBuffer;
    }

    // Sobrescreve o metodo paint para controlar o desenho da tela
    public void paint(Graphics gAntigo) {
        // Verifica se precisar trocar de fase
        if(keyH.trocaFase && this.heroi.getVidas() != 0 && faseAtual.getiIngredientesRestantes() == 0){
            this.proximaFase();
            keyH.trocaFase = false;
        }
        
        // Verifica se precisar salvar ou carregar o jogo
        if(keyH.salva){
            this.faseAtual.save();
            faseSalva = contadorDeFases;
        }
        if(keyH.carrega){
            this.carregaFaseSalva();
            System.out.println(contadorDeFases);
        }
        
        // Verifica se algum personagem .zip foi arrastado para o jogo
        if(mouseH.soltouArquivo){
           try {
            String filePath = "personagensSalvos";
            FileInputStream canoOut = new FileInputStream(filePath);
            GZIPInputStream compactador = new GZIPInputStream(canoOut);
            ObjectInputStream serializador = new ObjectInputStream(compactador);
            Personagem p = (Personagem) serializador.readObject();
            p.setPosicao(mouseH.x, mouseH.y);
            this.faseAtual.add(p);
            } catch (IOException | ClassNotFoundException exc) {
                System.out.println(exc.getMessage());
            }
           finally {
               mouseH.soltouArquivo = false;
           }
        }
        
        // Obtém o grafico para desenhar na tela usando BufferStrategy
        Graphics g = this.getBufferStrategy().getDrawGraphics();

        this.graphicsBuffer = g.create(this.getInsets().left, this.getInsets().top,
                this.getWidth() - this.getInsets().right, this.getHeight() - this.getInsets().top);

        // Desenha o cenario base (blocos)
        for (int i = 0; i < 15; ++i) {
            for (int j = 0; j < 15; ++j) {
                int mapaLinha = this.cameraLinha + i;
                int mapaColuna = this.cameraColuna + j;

                // Verifica se a posição ta dentro dos limites do mapa
                if ((mapaLinha != 0 && mapaLinha < 39) && (mapaColuna != 0 && mapaColuna < 14)) {
                    try {
                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                        String caminhoBase = (new File(".")).getCanonicalPath();

                        // Carrega a imagem do bloco (chao)
                        Image imagemBloco = toolkit.getImage(caminhoBase + Consts.PATH + "bricks.png");

                        // Desenha a imagem do bloco na posicao correta
                        this.graphicsBuffer.drawImage(imagemBloco, j * 50, i * 50, 50, 50, (ImageObserver) null);
                    } catch (IOException ex) {
                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                        String caminhoBase = (new File(".")).getCanonicalPath();

                        // Carrega a imagem do bloco (chao)
                        Image imagemBloco = toolkit.getImage(caminhoBase + Consts.PATH + "parede.png");

                        // Desenha a imagem do bloco na posicao correta
                        this.graphicsBuffer.drawImage(imagemBloco, j * 50, i * 50, 50, 50, (ImageObserver) null);
                    } catch (IOException ex) {
                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        this.setTitle("FASE: " + contadorDeFases + "         " +
                      "Vidas: " + heroi.getVidas() + "         " +
                      "Ingredientes restantes: " + faseAtual.getiIngredientesRestantes());

        // Se houver personagens na fase, desenha e processa eles
        if (!this.faseAtual.getPersonagens().isEmpty() && !(faseAtual.getiIngredientesRestantes() == 0)) {
            this.moveHeroi();
            this.controleDeJogo.desenhaTudo(this.faseAtual);
            this.controleDeJogo.processaTudo(this.faseAtual);
        } else if (this.heroi.getVidas() == 0){
            g.drawString("VOCE PERDEU! Aperte L para recomeçar",this.getWidth()/2,this.getHeight()/2);
        }
        else if(contadorDeFases < 5){
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image imagem = toolkit.getImage("imgs/fim" + contadorDeFases + ".png"); //
            int x = (this.getWidth() - imagem.getWidth(rootPane))/2;
            int y = (this.getHeight() - imagem.getHeight(rootPane))/2;
            g.drawImage(imagem, x, y, rootPane);
            /*
            String texto = "Fase completa! \n Clique C para continuar.";
            int x = this.getWidth() / 2;
            int y = this.getHeight() / 2;
            g.drawString(texto, x, y);
            */
        } else {
            g.drawString("FIM DE JOGO!",this.getWidth()/2,this.getHeight()/2);
        }

        // Libera recursos graficos
        g.dispose();
        this.graphicsBuffer.dispose();

        // Exibe o buffer na tela se não foi perdido o conteudo
        if (!this.getBufferStrategy().contentsLost()) {
            this.getBufferStrategy().show();
        }
    }

    // Atualiza a posicao da camera para centralizar no heroi
    public void atualizaCamera() {
        int linhaHeroi = this.heroi.getPosicao().getLinha();
        int colunaHeroi = this.heroi.getPosicao().getColuna();

        // Mantem a camera dentro dos limites do mapa
        this.cameraLinha = Math.max(0, Math.min(linhaHeroi - 7, 25));
        this.cameraColuna = Math.max(0, Math.min(colunaHeroi - 7, 0));
    }

    // Inicia o loop do jogo para atualizar a tela
    public void iniciarJogo() {
        TimerTask tarefaRepetitiva = new TimerTask() {
            public void run() {
                Tela.this.repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(tarefaRepetitiva, 0L, 200L);
    }

        
    // Controla o movimento do heroi
    public void moveHeroi(){
        if(keyH.cima){ this.heroi.moveUp(); }
        else if(keyH.baixo){ this.heroi.moveDown(); }
        else if(keyH.esquerda){ this.heroi.moveLeft(); }
        else if(keyH.direita){ this.heroi.moveRight(); }
        this.atualizaCamera();
    }
    
    // Troca de fase
    public void proximaFase(){
        contadorDeFases++;
        faseAtual.setNroFase(contadorDeFases);
        if (contadorDeFases < 6){
            this.faseAtual.clear();
        }
        this.carregarFase("fases/fase" + contadorDeFases + ".txt");
        this.faseAtual.setiIngredientesRestantes(this.faseAtual.getiTotalIngredientes());
        if (!faseAtual.getPersonagens().isEmpty()) {
            this.heroi = (Hero) this.faseAtual.get(0);
        } else {
            System.exit(0);
        }
        faseAtual.save();
    }

    // Carrega fase
    public void carregaFaseSalva(){
        Fase faseCarregada = Fase.load(faseAtual.getPersonagens(), contadorDeFases);
        if (faseCarregada != null) {
            this.faseAtual = faseCarregada;
            this.heroi = (Hero) this.faseAtual.get(0);
            this.contadorDeFases = faseCarregada.getNroFase();
        }
    }


    // Inicializa componentes da janela - layout basico, config da janela
    private void inicializaComponentes() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("POO2023-1 - Skooter");
        this.setAlwaysOnTop(true);
        this.setAutoRequestFocus(false);
        this.setResizable(false);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGap(0, 500, Short.MAX_VALUE)
        );

        this.pack();
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGap(0, 561, Short.MAX_VALUE)
        );

        this.pack();
        this.setLocationRelativeTo(null); // Centraliza a janela na tela
        this.setVisible(true);
        this.createBufferStrategy(2); // Cria dupla estratégia de buffer para evitar flickering
    }
/*
    public static void main(String[] args) {
        try {
            File tanque = new File("Chaser.zip");
            Chaser chaser = new Chaser("Chaser.png");
            tanque.createNewFile();
            FileOutputStream canoOut = new FileOutputStream(tanque);
            GZIPOutputStream compactador = new GZIPOutputStream(canoOut);
            ObjectOutputStream serializador = new ObjectOutputStream(compactador);
            serializador.writeObject(chaser);
            serializador.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Oi");
    }
*/

}
