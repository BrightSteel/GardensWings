package com.gardensmc.wings.fabric.particle;

import com.gardensmc.wings.common.particle.GardensParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ParticleAdapter {

    public static DefaultParticleType adaptParticle(GardensParticle.ParticleType particleType) {
        ParticleType<?> fabricParticle = Registries.PARTICLE_TYPE.get(new Identifier(particleType.getFabricName()));
        if (fabricParticle == null) {
            throw new RuntimeException("Could not adapt particle: " + particleType);
        }
        return (DefaultParticleType) fabricParticle;
    }
}
