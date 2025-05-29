import Controler.Tela;
import java.io.File;
import java.util.ArrayList;
public class Main {

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("A JVM esta sendo encerrada. Tentando apagar o arquivo de jogo salvo...");
            deleteSavedGameFile(); // Apaga o arquivo da fase atual
        }));

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

    private static void deleteSavedGameFile() {
        for(int i = 0; i < 6; ++i) {
            String fileName = "fase" + i + ".dat";
            File file = new File(fileName);

            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Excluído com sucesso: " + fileName);
                } else {
                    System.err.println("Falha ao excluir: " + fileName);
                }
            }
        }
    }
}

