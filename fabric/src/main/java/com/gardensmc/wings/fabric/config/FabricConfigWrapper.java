package com.gardensmc.wings.fabric.config;

import com.gardensmc.wings.common.config.WingsConfig;
import com.gardensmc.wings.fabric.WingsFabric;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FabricConfigWrapper implements WingsConfig {

    private FabricConfig fabricConfig;
    private final File configFile = new File(WingsFabric.GARDENS_CONFIG_DIR, "config.yml");

    public FabricConfigWrapper() {
        loadConfig();
    }

    @Override
    public double getBoostMultiplier() {
        return fabricConfig.getBoostMultiplier();
    }

    @Override
    public double getLaunchMultiplier() {
        return fabricConfig.getLaunchMultiplier();
    }

    @Override
    public boolean isWingsUnbreakable() {
        return fabricConfig.getWings().isUnbreakable();
    }

    @Override
    public void reload() {
        loadConfig();
    }

    private void loadConfig() {
        if (!configFile.exists()) {
            createDefaultConfig();
        }
        try {
            this.fabricConfig = FabricConfig.loadConfig(configFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not load wings config!", e);
        }
    }

    private void createDefaultConfig() {
        WingsFabric.GARDENS_CONFIG_DIR.mkdirs(); // create directories if necessary
        // create configFile
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        // remove !!class tag from output
        Representer representer = new Representer(dumperOptions);
        representer.addClassTag(FabricConfig.class, Tag.MAP);
        Yaml yaml = new Yaml(representer);
        try {
            yaml.dump(new FabricConfig(), new FileWriter(configFile));
        } catch (IOException e) {
            throw new RuntimeException("Could not create default wings config!", e);
        }
    }
}
