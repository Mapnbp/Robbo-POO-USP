package Controler;

// Bibliotecas importadas:
// - Auxiliar.*: Classes auxiliares como constantes, desenho e posicoes no jogo.
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;

// - Modelo.*: Classes dos personagens e da fase do jogo.
import Modelo.BichinhoVaiVemHorizontal;
import Modelo.BichinhoVaiVemVertical;
import Modelo.Caveira;
import Modelo.Chaser;
import Modelo.Fase;
import Modelo.Hero;
import Modelo.Personagem;
import Modelo.ZigueZague;

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

// Classe principal da janela do jogo que representa a tela de exibicao e controle do jogo.
// Implementa KeyListener para capturar os eventos de teclado.
public class Tela extends JFrame implements KeyListener {

    // Heroi principal controlado pelo jogador
    private Hero heroi;

    // Fase atual do jogo, que contem todos os personagens e elementos
    private Fase faseAtual;

    // ControleDeJogo centraliza regras e processamento da fase
    private ControleDeJogo controleDeJogo = new ControleDeJogo();

    // Buffer grafico para desenhar na tela
    private Graphics graphicsBuffer;

    // Coordenadas da "camera" que controla qual parte do mapa esta sendo exibida na tela
    private int cameraLinha = 0;
    private int cameraColuna = 0;

    // Construtor
    public Tela() {
        Desenho.setTelaJogo(this);
        this.inicializaComponentes();
        this.addKeyListener(this);

        // Define o tamanho da janela considerando as bordas e insets
        this.setSize(750 + this.getInsets().left + this.getInsets().right, 750 + this.getInsets().top + this.getInsets().bottom);

        // Cria uma fase vazia
        this.faseAtual = new Fase(new ArrayList<>());

        // Cria o heroi e posiciona no mapa
        this.heroi = new Hero("Robbo.png");
        this.heroi.setPosicao(0, 7);
        this.faseAtual.add(this.heroi);
        this.atualizaCamera();

        // Cria outros personagens e os adiciona a fase
        ZigueZague inimigoZigZag = new ZigueZague("robo.png");
        inimigoZigZag.setPosicao(5, 5);
        this.faseAtual.add(inimigoZigZag);

        BichinhoVaiVemHorizontal bichinhoHorizontal1 = new BichinhoVaiVemHorizontal("roboPink.png");
        bichinhoHorizontal1.setPosicao(3, 3);
        this.faseAtual.add(bichinhoHorizontal1);

        BichinhoVaiVemHorizontal bichinhoHorizontal2 = new BichinhoVaiVemHorizontal("roboPink.png");
        bichinhoHorizontal2.setPosicao(6, 6);
        this.faseAtual.add(bichinhoHorizontal2);

        BichinhoVaiVemVertical bichinhoVertical = new BichinhoVaiVemVertical("Caveira.png");
        bichinhoVertical.setPosicao(10, 10);
        this.faseAtual.add(bichinhoVertical);

        Caveira caveira = new Caveira("caveira.png");
        caveira.setPosicao(9, 1);
        this.faseAtual.add(caveira);

        Chaser perseguidor = new Chaser("chaser.png");
        perseguidor.setPosicao(12, 12);
        this.faseAtual.add(perseguidor);
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
                if (mapaLinha < 40 && mapaColuna < 15) {
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
        // Codigo 67 = 'C' limpa a fase atual (remove todos personagens)
        if (e.getKeyCode() == 67) {
            this.faseAtual.clear();

            // Setas cima(38) ou W(87) movem o heroi para cima
        } else if (e.getKeyCode() == 38 || e.getKeyCode() == 87) {
            this.heroi.moveUp();

            // Setas baixo(40) ou S(83) movem o heroi para baixo
        } else if (e.getKeyCode() == 40 || e.getKeyCode() == 83) {
            this.heroi.moveDown();

            // Setas esquerda(37) ou A(65) movem o heroi para esquerda
        } else if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
            this.heroi.moveLeft();

            // Setas direita(39) ou D(68) movem o heroi para direita
        } else if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
            this.heroi.moveRight();
        }

        // Atualiza a camera para acompanhar o heroi
        this.atualizaCamera();

        // Atualiza o título da janela com informacoes de vidas e posicoo atual do heroi
        int vidasHeroi = this.heroi.getVidas();
        this.setTitle("Vidas: " + vidasHeroi + " -> Celula: " + this.heroi.getPosicao().getColuna() + ", " + this.heroi.getPosicao().getLinha());
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
    }

    // Obrigatorios da interface KeyListener - nao pode apagar se nao da pau (nao sao usados por enquanto mas tbm nao sei se vamos usar)
    public void keyTyped(KeyEvent e) {
        // Nao implementado
    }

    public void keyReleased(KeyEvent e) {
        // Não implementado
    }

    // ---------------------------
    // O QUE AINDA FALTA IMPLEMENTAR:
    // - Inicializar o BufferStrategy para evitar problemas graficos (falta chamar createBufferStrategy)
    // - Implementar controle de colisoes entre personagens e elementos do cenario
    // - Implementar sistema de pontuacao e eventos do jogo (como derrota e vitoria)
    // - Melhorar carregamento das imagens para evitar repeticao a cada frame (cachear imagens)
    // - Adicionar sons e efeitos visuais
    // - Criar interface para reiniciar fase, pausar e salvar progresso
    // - Tratar limites de mapa mais flexível para o movimento da câmera
}
