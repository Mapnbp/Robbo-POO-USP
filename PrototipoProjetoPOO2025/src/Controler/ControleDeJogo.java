package Controler;

// Importa classes de modelo que representam personagens do jogo
import Modelo.Chaser;
import Modelo.Personagem;
import Modelo.Hero;
import Modelo.Fase;
import Auxiliar.Posicao;

import java.util.ArrayList;

public class ControleDeJogo {

    // Metodo para desenhar todos os personagens da fase
    public void desenhaTudo(Fase fase) {
        // Percorre todos os personagens da fase e chama o metodo autoDesenho deles
        for (int i = 0; i < fase.size(); i++) {
            fase.get(i).autoDesenho();
        }
    }

    // Metodo para processar a logica de todos os personagens da fase
    public void processaTudo(Fase fase) {
        Hero heroi = (Hero) fase.get(0);  // Assume que o heroi esta sempre na posicao 0 da lista
        Personagem personagemAtual;

        // Verifica se heroi colidiu com algum personagem mortal para remover esse personagem
        for (int i = 1; i < fase.size(); i++) {
            personagemAtual = fase.get(i);
            if (heroi.getPosicao().igual(personagemAtual.getPosicao())) {
                /* TO-DO: implementar verificacao completa se o personagem eh mortal antes de remover */
                if (personagemAtual.isbTransponivel()) {
                    if (personagemAtual.isbMortal()) {
                        fase.remove(personagemAtual);
                        i--; // Ajusta o indice apos remover para nao pular elementos
                    }
                }
            }
        }

        // Para cada personagem do tipo Chaser, calcula nova direcao para perseguir o heroi
        for (int i = 1; i < fase.size(); i++) {
            personagemAtual = fase.get(i);
            if (personagemAtual instanceof Chaser) {
                ((Chaser) personagemAtual).computeDirection(heroi.getPosicao());
            }
        }
    }

    /* Retorna true se a posicao p eh valida para o heroi,
       ou seja, se nao existe nenhum personagem solido nessa posicao */
    public boolean ehPosicaoValida(Fase fase, Posicao pos) {
        Personagem personagemAtual;
        for (int i = 1; i < fase.size(); i++) {
            personagemAtual = fase.get(i);
            if (!personagemAtual.isbTransponivel()) { // verifica se o personagem eh solido
                if (personagemAtual.getPosicao().igual(pos)) {
                    return false; // posicao ocupada, nao valida
                }
            }
        }
        return true; // posicao livre para o heroi
    }
}
