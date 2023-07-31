package org.echo.chatformattingpremium.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.echo.chatformattingpremium.ChatFormatting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BadWords {

    private ChatFormatting plugin;
    private File file;
    private YamlConfiguration yaml;
    private boolean isEnable;
    private boolean isUltraFiltering;
    private String badWordsReplacement;
    private String defaultLanguage;
    private List<String> badWords;

    public BadWords(ChatFormatting plugin, String filename) {

        this.plugin = plugin;
        this.badWords = new ArrayList<>();
        this.yaml = loadConfig(filename);
        loadBadWords();
    }

    private YamlConfiguration loadConfig(String file_name) {

        if(!this.plugin.getDirectory().exists()) {
            this.plugin.getDirectory().mkdir();
        }

        this.file = new File(this.plugin.getDataFolder(), file_name);

        if (!this.file.exists()) {
            this.plugin.saveResource(file_name, false);
        }

        return YamlConfiguration.loadConfiguration(this.file);
    }

    private void saveConfig() {
        try {
            this.yaml.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBadWords() {
        this.isEnable = yaml.getBoolean("badwords-filtering", true);
        this.isUltraFiltering = yaml.getBoolean("ultra-filtering", true);
        this.badWordsReplacement = yaml.getString("badwords-replacement", "***");
        this.defaultLanguage = yaml.getString("default-language", "en");
        if (!yaml.isSet("languages." + defaultLanguage) && this.isEnable == true) {
            System.out.println("§c[ChatFormatting] Error: Badwords cannot be filtered because the language \"" + defaultLanguage + "\" does not exist, in badwords.yml create it or change the default-language value.");
        }
        else {
            this.badWords = yaml.getStringList("languages." + defaultLanguage);
        }
    }

    public boolean isEnable() {
        return isEnable;
    }

    public boolean isUltraFiltering() {
        return isUltraFiltering;
    }

    public List<String> getBadWords() {
        return badWords;
    }

    public String getBadWordsReplacement() {
        return badWordsReplacement;
    }
}
