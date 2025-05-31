package Controler;

import Auxiliar.Consts;
import Modelo.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonagemLoader {
    private static final String TEMP_DIR = "temp_personagens";
    private static final Logger logger = Logger.getLogger(PersonagemLoader.class.getName());

    private Tela controller;
    private Hero hero;
    private IPersonagemFactory defaultFactory; // Agora usa apenas uma factory

    public PersonagemLoader(Tela controller, Hero hero) {
        this.controller = controller;
        this.hero = hero;
        this.defaultFactory = new DefaultPersonagemFactory(this.hero); // Instancia a fábrica padrão

        // Inicializa o diretório temporário
        File tempDir = new File(TEMP_DIR);
        if (!tempDir.exists()) {
            tempDir.mkdir();
            logger.info("Diretorio temporario criado: " + TEMP_DIR);
        }
    }

    // Carrega personagens de um arquivo ZIP
    public ArrayList<Personagem> loadPersonagensFromZip(String zipFilePath) throws IOException {
        ArrayList<Personagem> loadedPersonagens = new ArrayList<>();

        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Map<String, Properties> personagemConfigs = extractPersonagemConfigs(zipFile);

            extractPersonagemImages(zipFile); // Extrai todas as imagens primeiro

            for (Map.Entry<String, Properties> entry : personagemConfigs.entrySet()) {
                String configName = entry.getKey(); // Nome do arquivo .properties
                Properties config = entry.getValue();

                // Pega o nome da classe do .properties, ou tenta inferir
                String className = config.getProperty("class");
                if (className == null || className.isEmpty()) {
                    // Se a classe não estiver presennte tenta como type
                    String type = config.getProperty("type");
                    if (type != null && !type.isEmpty()) {
                        // Converte o tipo para o nome da classe esperado (ex: "caveira1" -> "Caveira1")
                        className = type.substring(0, 1).toUpperCase() + type.substring(1);
                    } else {
                        logger.warning("Erro");
                        continue; // Pula este personagem
                    }
                }

                String imageName = config.getProperty("image");
                if (imageName == null || imageName.isEmpty()) {
                    logger.warning("nao achou a imagem");
                    continue; // Pula este personagem
                }

                Personagem personagem = defaultFactory.criarPersonagem(className, imageName, config);
                if (personagem != null) {
                    // Define a posição, que é comum a todos os Personagens
                    int linha = 1;
                    int coluna = 1;

                    try {
                        String linhaStr = config.getProperty("linha", "1").trim();
                        if (linhaStr.contains("#")) linhaStr = linhaStr.substring(0, linhaStr.indexOf("#")).trim();
                        linha = Integer.parseInt(linhaStr);
                    } catch (NumberFormatException e) {
                        logger.warning("erro" + e.getMessage());
                    }

                    try {
                        String colunaStr = config.getProperty("coluna", "1").trim();
                        if (colunaStr.contains("#")) colunaStr = colunaStr.substring(0, colunaStr.indexOf("#")).trim();
                        coluna = Integer.parseInt(colunaStr);
                    } catch (NumberFormatException e) {
                        logger.warning("Coluna nao encontrada " + e.getMessage());
                    }
                    personagem.setPosicao(linha, coluna);

                    loadedPersonagens.add(personagem);
                    controller.adicionarPersonagem(personagem);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao carregar personagens do ZIP: " + zipFilePath, e);
            throw e;
        }

        return loadedPersonagens;
    }

    // Extrai arquivos de propriedades de personagens do ZIP.
    private Map<String, Properties> extractPersonagemConfigs(ZipFile zipFile) throws IOException {
        Map<String, Properties> configs = new HashMap<>();

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            if (entry.getName().endsWith(".properties")) {
                String personagemName = entry.getName().replace(".properties", "");

                Properties props = new Properties();
                try (InputStream is = zipFile.getInputStream(entry)) {
                    props.load(is);
                    configs.put(personagemName, props);
                    logger.log(Level.INFO, "Configuracao carregada para: " + personagemName);
                }
            }
        }
        return configs;
    }

    // Extrai as imagens
    private void extractPersonagemImages(ZipFile zipFile) throws IOException {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            if (isImageFile(entry.getName())) {
                String fileName = new File(entry.getName()).getName();

                String imgsPath;
                try {
                    imgsPath = new File(".").getCanonicalPath() + Consts.PATH;
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Erro ao obter caminho.", e);
                    imgsPath = "imgs" + File.separator;
                }

                File imgDir = new File(imgsPath);
                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                    logger.info("Diretorio de imagens criado: " + imgsPath);
                }

                Path targetPath = Paths.get(imgsPath, fileName);
                logger.log(Level.INFO, "Extraindo imagem para: " + targetPath.toAbsolutePath());

                try (InputStream is = zipFile.getInputStream(entry)) {
                    Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    private boolean isImageFile(String fileName) {
        String lowerCaseName = fileName.toLowerCase();
        return lowerCaseName.endsWith(".png") ||
                lowerCaseName.endsWith(".jpg") ||
                lowerCaseName.endsWith(".jpeg") ||
                lowerCaseName.endsWith(".gif");
    }

    // Limp
    public void cleanup() {
        try {
            File tempDir = new File(TEMP_DIR);
            if (tempDir.exists()) {
                for (File file : tempDir.listFiles()) {
                    if (!file.delete()) {
                        logger.warning("FFalhou em deletar" + file.getAbsolutePath());
                    }
                }
                if (!tempDir.delete()) {
                    logger.warning("Falhou em achar o direotio " + tempDir.getAbsolutePath());
                } else {
                    logger.info("diretorio limpo: " + TEMP_DIR);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "erro ao limpar: " + TEMP_DIR, e);
        }
    }
}