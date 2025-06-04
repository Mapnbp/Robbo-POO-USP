package Controler;

import Auxiliar.Posicao;
import Personagens.*;

public class ControleDeJogo  {
    
    GamePanel gp;
    ControleFase cFase;
    DetectorDeColisao dColisao;
    Fase faseAtual;
    
    public ControleDeJogo(GamePanel gp) {
        this.gp = gp;
        this.cFase = gp.cFase;
        faseAtual = cFase.getFaseAtual();
        this.dColisao = gp.dColisao;
    }
    
    public boolean ehPosicaoValida(Fase fase, Posicao pos) {
        Personagem personagemAtual;
        for (int i = 0; i < fase.size(); i++) {
            personagemAtual = fase.get(i);
            if (!personagemAtual.ehTransponivel()) { // verifica se o personagem eh solido
                if (personagemAtual.posicao.igual(pos)) {
                    return false; // posicao ocupada, nao valida
                }
            }
        }
        return true; // posicao livre para o heroi
    }
    
    // Novas posicoes
    // nova posicao do heroi ocorre antes 
    public void update(){
        
        // novas posicoes dos personagens animados 
        for(int i = 0; i < faseAtual.size();i++){
            Personagem p = faseAtual.get(i);
            if(p instanceof Animado) {
                if(p instanceof Chaser) {
                        ((Chaser) p ).calculaDirecao(gp.heroi.posicao);
                }
                ((Animado) p).update();
            }
        }
        
        // Verifica colisao entre heroi e blocos
        if(!dColisao.posicaoValida(gp.heroi.posicao))
            gp.heroi.posicao.volta();
        
        // Colisao do Heroi com personagem
        for(int i = 0; i < faseAtual.size();i++){
            Personagem p = faseAtual.get(i);
            dColisao.colisaoHeroi(p);
        }
        
        // Verifica colisao entre personagens
        int n = faseAtual.size();
        
        // Colisao personagens
        for(int i = 0; i < n; i++){
            Personagem p = faseAtual.get(i);
            
            //Personagem parede
            if(!dColisao.posicaoValida(p.posicao)) {
                p.posicao.volta();
                
                // Fazer o chaser menos burro (acho que aqui eh o caminho)
                if(p instanceof Chaser) {
                    // alterar a funcao caluladirecao com uma flag que coloca uma ordem de movimentos para o chaser tentar fazer
                }
            }
            
            // Personagem parede
            for(int j = i + 1; j < n - 1; j++){
                Personagem outro = faseAtual.get(j);
                dColisao.colisaoPersonagens(p,outro);
            }
        }
        // Verifica se perdeu todas as vidas
        if(gp.heroi.getVidas() == 0)
            gp.gameState = gp.perdeuState;
        
        // Verifica se completou a fase
        else if(faseAtual.concluida(gp.heroi)){
            if(faseAtual.getNroFase() < 5)
                gp.gameState = gp.pauseState;
            else
                gp.gameState = gp.fimState;
        }
        
    }
}
