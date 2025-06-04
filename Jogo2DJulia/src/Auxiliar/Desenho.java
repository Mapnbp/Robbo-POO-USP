package Auxiliar;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import Controler.GamePanel;

public class Desenho {
    
    static GamePanel gp;
    
    public static void setGamePanel(GamePanel gamePanel) {
        gp = gamePanel;
    }
    
    public static ImageIcon getImagem(String nomeImagem){
        ImageIcon imagem = null;
        try{
            String caminhoCompleto = new File(".").getCanonicalPath() + Consts.PATH + nomeImagem;
            BufferedImage imagemBI = ImageIO.read(new File(caminhoCompleto));
            imagem = new ImageIcon(imagemBI);
            
        } catch (IOException e){
            System.out.println("Nao pegou imagem: " + e.getMessage());
        }
        
        return imagem;
    }
    
    public static void draw(ImageIcon imagem, int coluna, int linha, Graphics2D g2) {
        Image img = imagem.getImage();
        BufferedImage imagemBI = new BufferedImage(Consts.CELL_SIDE,Consts.CELL_SIDE,BufferedImage.TYPE_INT_ARGB);
        Graphics g = imagemBI.createGraphics();
        //imagem.paintIcon(null,g,0,0);
        g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
        g.dispose();
        
        // Calcula as coordenadas na tela ajustando para a posicao da camera
        int posTelaX = (linha - gp.cameraColuna) * Consts.CELL_SIDE;
        int posTelaY = (coluna - gp.cameraLinha) * Consts.CELL_SIDE;

        // Verifica se a imagem esta dentro da area visivel para evitar desenho desnecessario
        if (posTelaX >= 0 && posTelaX < Consts.RES * Consts.CELL_SIDE
                && posTelaY >= 0 && posTelaY < Consts.RES * Consts.CELL_SIDE) {
            // Desenha o icone na posicao calculada
            g2.drawImage(imagemBI,posTelaX, posTelaY,Consts.CELL_SIDE,Consts.CELL_SIDE, null);
        }
    }
    
    
    public static void drawFimFase(ImageIcon imagem, Graphics g2) {
        Image img = imagem.getImage();
        BufferedImage imagemBI = new BufferedImage(Consts.CELL_SIDE,Consts.CELL_SIDE,BufferedImage.TYPE_INT_ARGB);
        Graphics g = imagemBI.createGraphics();
        g.drawImage(img, 0, 0, 15*Consts.CELL_SIDE, 15*Consts.CELL_SIDE, null);
        g.dispose();
        
       g2.drawImage(imagemBI,0, 0, null);
    }
}
