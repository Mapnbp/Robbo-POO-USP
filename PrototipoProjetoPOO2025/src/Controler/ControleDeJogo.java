package Controler;

import Modelo.Chaser;
import Modelo.Personagem;
import Modelo.Hero;
import Modelo.Fase;
import Auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
    
    public void desenhaTudo(Fase e) {
        for (int i = 0; i < e.size(); i++) {
            e.get(i).autoDesenho();
        }
    }
    
    public void processaTudo(Fase umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                /*TO-DO: verificar se o personagem eh mortal antes de retirar*/
                if (pIesimoPersonagem.isbTransponivel()) {
                    if (pIesimoPersonagem.isbMortal())
                    umaFase.remove(pIesimoPersonagem);
                }
            }
        }
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            // se for um Chaser procura nova posicao
            if (pIesimoPersonagem instanceof Chaser) {
                ((Chaser) pIesimoPersonagem).computeDirection(hero.getPosicao());
            }
        }
    }

    /*Retorna true se a posicao p é válida para Hero 
    com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(Fase umaFase, Posicao p) {
        Personagem pIesimoPersonagem;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (!pIesimoPersonagem.isbTransponivel()) {
                if (pIesimoPersonagem.getPosicao().igual(p)) {
                    return false;
                }
            }
        }
        return true;
    }
}
