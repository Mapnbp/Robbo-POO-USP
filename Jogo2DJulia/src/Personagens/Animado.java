package Personagens;

public abstract class Animado extends Personagem{
    
    boolean up = false,
            down = false,
            left = false,
            right = false; 
    
    public Animado(String nomeImagem){
        super(nomeImagem);
    }

    
    // Atualiza posicao do personagem animado
    // Atualiza a posicao do heroi
    public void update(){
        if(up){ 
            posicao.moveUp(); 
        }
        else if(down){ 
            posicao.moveDown(); 
        }
        else if(left){ 
            posicao.moveLeft(); 
        }
        else if(right){ 
            posicao.moveRight();
        }
    }    
}
