package Personagens;

public class BichinhoVertical extends Animado {
    private int contaPassos = 0;
    private int iContador = 0;
    public BichinhoVertical(String nomeImagem) {
        super(nomeImagem);
        up = true;
    }
    
    public void update(){
        if(iContador > 3) {
            if(contaPassos > 38) {
                up = !up;
                contaPassos = 0;
            }
            if(up)
                posicao.moveUp();
            else
                posicao.moveDown();
            contaPassos++;
            iContador = 0;
        }
        iContador++;
    }
}
