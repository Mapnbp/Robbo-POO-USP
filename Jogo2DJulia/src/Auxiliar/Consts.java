package Auxiliar;

import java.io.File;

public class Consts {
    public static final int CELL_SIDE = 50;
    public static final int RES = 15; // visível na tela
    public static final int MUNDO_LARGURA = 15 ; // total do mundo 750
    public static final int MUNDO_ALTURA = 40;
    public static final int LARGURA = MUNDO_LARGURA * CELL_SIDE;
    public static final int ALTURA = MUNDO_ALTURA * CELL_SIDE;
    public static final int PERIOD = 150;
    public static final String PATH = File.separator+"imgs"+File.separator; // "/imgs/"
    public static final int TIMER = 10;
    public static final int CHASER_TIMER = 3;
}
