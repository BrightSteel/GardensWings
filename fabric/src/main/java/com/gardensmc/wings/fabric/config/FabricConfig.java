package com.gardensmc.wings.fabric.config;

import com.gardensmc.wings.common.particle.GardensParticle;
import lombok.Data;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Data
public class FabricConfig {

    // create config items with defaults
    private Wings wings = new Wings();
    private Boost boost = new Boost();
    private Launch launch = new Launch();

    @Data
    public static class Wings {
        private String displayName = "<!italic><blue>Wings</blue>";
        private List<String> lore = List.of("<!italic><gold>Unbreakable wings!</gold>");
        private boolean isUnbreakable = true;
    }

    @Data
    public static class Boost {
        private double velocityMultiplier = 2.5;
        private GardensParticle particle = new GardensParticle(
                true, GardensParticle.ParticleType.FLAME, 5, 1
        );
    }

    @Data
    public static class Launch {
        private double velocityMultiplier = 3.0;
        private GardensParticle particle = new GardensParticle(
                true, GardensParticle.ParticleType.EXPLOSION, 5, 1
        );
        private Sound sound = new Sound();
    }

    @Data
    public static class Sound {
        private boolean enabled = true;
        private float volume = 0.5f;
    }

    public static FabricConfig loadConfig(File configFile) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(FabricConfig.class, new LoaderOptions()));
        FileInputStream fileInputStream = new FileInputStream(configFile);
        return yaml.load(fileInputStream);
    }
}
