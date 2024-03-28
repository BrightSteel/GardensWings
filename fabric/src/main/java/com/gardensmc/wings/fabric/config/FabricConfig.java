package com.gardensmc.wings.fabric.config;

import lombok.Data;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Data
public class FabricConfig {

    // create config items with defaults
    private double boostMultiplier = 2.5;
    private double launchMultiplier = 3.0;
    private Wings wings = new Wings();

    @Data
    public static class Wings {
        private boolean isUnbreakable = true;
    }

    public static FabricConfig loadConfig(File configFile) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(FabricConfig.class, new LoaderOptions()));
        FileInputStream fileInputStream = new FileInputStream(configFile);
        return yaml.load(fileInputStream);
    }
}
