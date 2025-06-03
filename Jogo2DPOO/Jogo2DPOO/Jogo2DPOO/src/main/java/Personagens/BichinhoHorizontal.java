package Personagens;

public class BichinhoHorizontal extends Animado {
    private int contaPassos = 0;
    public BichinhoHorizontal(String nomeImagem) {
        super(nomeImagem);
        right = true;
    }
    
    public void update(){
        if(contaPassos > 13) {
            right = !right;
            contaPassos = 0;
        }
        if(right)
            posicao.moveRight();
        else
            posicao.moveLeft();
        contaPassos++;
    }
}
