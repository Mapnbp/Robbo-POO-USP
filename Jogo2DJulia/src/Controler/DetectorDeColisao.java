package Controler;

import Auxiliar.Consts;
import Auxiliar.Posicao;
import Personagens.Personagem;

public class DetectorDeColisao  {
    
    //private static final long serialVersionUID = 1L;
    
    GamePanel gp;
    ControleFase cFase;
    
    public DetectorDeColisao(GamePanel gp){
        this.gp = gp;
        this.cFase = gp.cFase;
    }
    
    // Posicao valida (dentro do mapa, sem ser parede)
    public boolean posicaoValida(Posicao novaPosicao){
        int linha = novaPosicao.getLinha();
        int coluna = novaPosicao.getColuna();
        
        // Verifica se esta dentro dos limites do mapa
        if (linha < 0 || linha > Consts.MUNDO_ALTURA - 1 ||
            coluna < 0 || coluna > Consts.MUNDO_LARGURA - 1)
            return false;
       
        // Verifica se tem parede (1)ou chao (0)
        return cFase.numBloco[coluna][linha] == 0;
    }

    // Interacao entre heroi e personagens
    public void colisaoHeroi(Personagem p){
        
        if(gp.heroi.getVidas() < 1) {
            cFase.faseAtual.clear();
            gp.gameState = gp.fimState;
        }
        
        if (gp.heroi.posicao.igual(p.posicao)) {
            if (p.ehTransponivel()) {
                switch (p.getClass().getSimpleName()) {
                    case "Chaser":
                        gp.heroi.setVidas(0);
                        break;
                    case "Ingrediente":
                        gp.heroi.coletouIng();
                        cFase.faseAtual.remove(p); // ingrediente coletado
                        break;
                    default:
                        if(p.ehMortal()) 
                            gp.heroi.setVidas(gp.heroi.getVidas() - 1);
                        break;
                }
            }
            else
                gp.heroi.posicao.volta();
        }
        
    }
    
    public void colisaoPersonagens(Personagem p, Personagem outro) {
        if(p.posicao.igual(outro.posicao)){
            p.posicao.volta();
        }
    }    
}
