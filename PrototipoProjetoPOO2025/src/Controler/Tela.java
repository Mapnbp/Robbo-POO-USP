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

// - java.awt.event.*: Para lidar com eventos de teclado (KeyListener).
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import java.io.FileReader;

// Classe principal da janela do jogo que representa a tela de exibicao e controle do jogo.
// Implementa KeyListener para capturar os eventos de teclado.
public class Tela extends JFrame implements KeyListener {

    private Hero heroi;
    private Fase faseAtual;

    // ControleDeJogo centraliza regras e processamento da fase
    private ControleDeJogo controleDeJogo = new ControleDeJogo();

    // Buffer grafico para desenhar na tela
    private Graphics graphicsBuffer;

    // Coordenadas da "camera" que controla qual parte do mapa esta sendo exibida na tela
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    private int contadorDeFases = 1;

    // Construtor
    public Tela() {
        Desenho.setTelaJogo(this);
        this.inicializaComponentes();
        this.addKeyListener(this);

        // Define o tamanho da janela considerando as bordas e insets
        this.setSize(750 + this.getInsets().left + this.getInsets().right, 750 + this.getInsets().top + this.getInsets().bottom);

        this.faseAtual = new Fase(new ArrayList<>(), contadorDeFases);
        this.carregarFase("imgs/fase" + contadorDeFases + ".txt");
    }

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
            if (linha.equalsIgnoreCase("Fim;")) break;

            linha = linha.trim();
            if (linha.isEmpty()) continue;

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
            if (linha.equalsIgnoreCase("Fim;")) break;

            String[] partes = linha.split(";");
            if (partes.length == 13) {
                for (int coluna = 0; coluna < 13; coluna++) {
                    if (partes[coluna].equals("1")) {
                        try {
                            Personagem parede = new Estatico("parede.png");
                            parede.setPosicao(linhaMatriz, coluna+1);
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
                if ((mapaLinha != 0 && mapaLinha < 39) && (mapaColuna !=0 && mapaColuna < 14)) {
                    try {
                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                        String caminhoBase = (new File(".")).getCanonicalPath();

                        // Carrega a imagem do bloco (chao)
                        Image imagemBloco = toolkit.getImage(caminhoBase + Consts.PATH + "bricks.png");

                        // Desenha a imagem do bloco na posicao correta
                        this.graphicsBuffer.drawImage(imagemBloco, j * 50, i * 50, 50, 50, (ImageObserver)null);
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
                        this.graphicsBuffer.drawImage(imagemBloco, j * 50, i * 50, 50, 50, (ImageObserver)null);
                    } catch (IOException ex) {
                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        // Se houver personagens na fase, desenha e processa eles
        if (!this.faseAtual.getPersonagens().isEmpty()) {
            this.controleDeJogo.desenhaTudo(this.faseAtual);
            this.controleDeJogo.processaTudo(this.faseAtual);
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
    private void atualizaCamera() {
        int linhaHeroi = this.heroi.getPosicao().getLinha();
        int colunaHeroi = this.heroi.getPosicao().getColuna();

        // Mantem a camera dentro dos limites do mapa
        this.cameraLinha = Math.max(0, Math.min(linhaHeroi - 7, 25));
        this.cameraColuna = Math.max(0, Math.min(colunaHeroi - 7, 0));
        this.setTitle("Vidas: " + (heroi.getVidas()));
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
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        switch (tecla) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                heroi.moveUp();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                heroi.moveDown();
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                heroi.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                heroi.moveRight();
                break;
            case KeyEvent.VK_C:
                contadorDeFases++;
                if(contadorDeFases < 6) {
                    this.faseAtual.clear();
                    this.carregarFase("imgs/fase" + contadorDeFases + ".txt");
                    if (!faseAtual.getPersonagens().isEmpty()) {
                        this.heroi = (Hero) this.faseAtual.get(0);
                    }
                } else {
                    System.exit(0);
                }
                break;
            case KeyEvent.VK_E: // salva
                this.faseAtual.save(contadorDeFases);
                break;
            case KeyEvent.VK_L: // carrega o jogo salvo
                Fase faseCarregada = Fase.load(faseAtual.getPersonagens(), contadorDeFases);
                if (faseCarregada != null) {
                    this.faseAtual = faseCarregada;
                    this.heroi = (Hero) this.faseAtual.get(0); // Reatribuir o herói
                    System.out.println("Fase carregada via tecla L.");
                }
                break;
        }

        // Atualiza a camera para acompanhar o heroi
        this.atualizaCamera();
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

    // Obrigatorios da interface KeyListener - nao pode apagar se nao da pau (nao sao usados por enquanto mas tbm nao sei se vamos usar)
    public void keyTyped(KeyEvent e) {
        // Nao implementado
    }

    public void keyReleased(KeyEvent e) {
        // Não implementado
    }
}
