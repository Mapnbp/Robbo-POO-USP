package Controler;

import Modelo.*;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultPersonagemFactory implements IPersonagemFactory {
    private static final Logger logger = Logger.getLogger(DefaultPersonagemFactory.class.getName());
    private Hero heroTarget; // Adiciona a dependência do Hero

    public DefaultPersonagemFactory(Hero heroTarget) {
        this.heroTarget = heroTarget;
    }

    @Override
    public Personagem criarPersonagem(String className, String imageName, Properties config) {
        Personagem personagem = null;
        try {
            // Converte o nome da classe para o formato totalmente qualificado (ex: "Modelo.Villan_1")
            String fullClassName = "Modelo." + className;
            Class<?> pClass = Class.forName(fullClassName);

            // Procura por um construtor que aceite String (nome da imagem)
            Constructor<?> constructor = pClass.getConstructor(String.class);
            personagem = (Personagem) constructor.newInstance(imageName);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Classe: " + className + " nao encontrada");
            return null;
        } catch (NoSuchMethodException e) {
            logger.log(Level.WARNING, "Sem construtor " + className);
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro" + e.getMessage(), e);
            return null;
        }
        return personagem;
    }
}
