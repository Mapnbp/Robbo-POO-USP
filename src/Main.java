import Auxiliar.Menu;
import Controler.Tela;
import java.io.File;
import java.util.ArrayList;
public class Main {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tela tTela = new Tela();
                tTela.setVisible(true);
                tTela.createBufferStrategy(2);
                tTela.iniciarJogo();
            }
        });
    }
}

