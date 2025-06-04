package Controler;

import Auxiliar.Consts;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.List;

public class MouseHandler implements DropTargetListener {
    

    public boolean arquivoSolto; // Sinaliza que um arquivo foi solto
    public String caminhoArquivoSolto; // Armazena o caminho do arquivo
    public int x, y; // Posição onde o arquivo foi solto (coordenadas da tela)

    public MouseHandler() {
        this.arquivoSolto = false;
        this.caminhoArquivoSolto = null;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        // Sinaliza que o arrastar entrou na área de destino.
        // Opcional: pode mudar o cursor ou exibir um feedback visual.
        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        // Sinaliza que o arrastar está sobre a área de destino.
        // Opcional: pode fornecer feedback visual contínuo.
        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
            // Captura a ultima posicao antes de soltar
            this.x = dtde.getLocation().x / Consts.CELL_SIDE;
            this.y = dtde.getLocation().y / Consts.CELL_SIDE;
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        // Ação de soltar foi alterada (ex: de copiar para mover).
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        // O arrastar saiu da área de destino sem soltar.
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        // Este é o método mais importante, chamado quando um arquivo é solto.
        try {
            dtde.acceptDrop(DnDConstants.ACTION_COPY); // Aceita a ação de soltar

            // Verifica se os dados transferidos são uma lista de arquivos
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                // Obtém a lista de arquivos soltos
                List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                if (!files.isEmpty()) {
                    File droppedFile = files.get(0); // Pega o primeiro arquivo (assumindo um arquivo por vez)
                    this.caminhoArquivoSolto = droppedFile.getAbsolutePath(); // Armazena o caminho completo
                    this.arquivoSolto = true; // Sinaliza que um arquivo foi solto com sucesso                    
                }
            } else {
                dtde.rejectDrop(); // Rejeita o drop se o tipo de dado não for suportado
                this.arquivoSolto = false;
                this.caminhoArquivoSolto = null;
            }
        } catch (Exception ex) {
            this.arquivoSolto = false;
            this.caminhoArquivoSolto = null;
        } finally {
            dtde.dropComplete(this.arquivoSolto); // Finaliza a operação de drop
        }
    }
}
