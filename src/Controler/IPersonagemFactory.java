package Controler;

import Modelo.Personagem;
import java.util.Properties;
public interface IPersonagemFactory {
    // Agora o PersonagemFactory precisa do nome da classe para instanciar dinamicamente.
    Personagem criarPersonagem(String className, String imageName, Properties config);
}
