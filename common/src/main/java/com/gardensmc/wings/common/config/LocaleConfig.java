package com.gardensmc.wings.common.config;

import com.gardensmc.wings.common.GardensWings;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.HashMap;

public class LocaleConfig {

    private static HashMap<String, String> localeMap;

    public LocaleConfig(String locale) {
        loadLocale(locale);
    }

    public String getLocaleMessage(String id) {
        return localeMap.get(id);
    }

    private void loadLocale(String config) {
        File locale = new File(
                GardensWings.getGardensServer().getServerFolder(),
                String.format("locales/%s.yml", config)
        );
        if (locale.exists()) {
            loadLocaleFromFile(locale);
        } else {
            if (config.equalsIgnoreCase("en_US")) { // default - we provide it
                loadLocaleFromResource();
                saveLocaleFile();
            } else {
                throw new RuntimeException(String.format(
                        "Could not locate file %s in locales folder!",
                        config
                ));
            }
        }
    }

    private void loadLocaleFromFile(File file) {
        Yaml yaml = new Yaml();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            localeMap = yaml.load(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to load file " + file.getPath());
            // shouldn't happen since we already checked it exists
        }
    }

    private void loadLocaleFromResource() {
        // load yaml from resources
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("locales/en_US.yml");
        localeMap = yaml.load(inputStream);
    }

    private void saveLocaleFile() {
        // save yaml to server's locale directory
        File localesFolder = new File(GardensWings.getGardensServer().getServerFolder(), "locales");
        localesFolder.mkdirs(); // create directories if necessary
        // create configFile
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        // remove !!class tag from output
        Representer representer = new Representer(dumperOptions);
        representer.addClassTag(Object.class, Tag.MAP);
        Yaml yaml = new Yaml(representer);
        try {
            yaml.dump(localeMap, new FileWriter(new File(localesFolder, "en_US.yml")));
        } catch (IOException e) {
            throw new RuntimeException("Could not create default wings locale!", e);
        }
    }
}
