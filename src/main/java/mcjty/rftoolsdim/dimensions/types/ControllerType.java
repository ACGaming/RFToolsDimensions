package mcjty.rftoolsdim.dimensions.types;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public enum ControllerType {
    CONTROLLER_DEFAULT(0, null),
    CONTROLLER_SINGLE(1, null),
    CONTROLLER_CHECKERBOARD(2, null),
    CONTROLLER_COLD(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return biome.getTempCategory() == BiomeGenBase.TempCategory.COLD;
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, false, true, false);
        }
    }),
    CONTROLLER_MEDIUM(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return biome.getTempCategory() == BiomeGenBase.TempCategory.MEDIUM;
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, false, true, false);
        }
    }),
    CONTROLLER_WARM(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return biome.getTempCategory() == BiomeGenBase.TempCategory.WARM;
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, false, true, false);
        }
    }),
    CONTROLLER_DRY(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return biome.rainfall < 0.1;
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, true, false, false);
        }
    }),
    CONTROLLER_WET(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return biome.isHighHumidity();
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, true, false, false);
        }
    }),
    CONTROLLER_FIELDS(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return false;//@todo
//            return biome.heightVariation < 0.11 && biome.rootHeight < 0.25f;
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, false, false, true);
        }
    }),
    CONTROLLER_MOUNTAINS(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return false;//@todo
//            return biome.heightVariation > 0.45f;
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, false, false, true);
        }
    }),
    CONTROLLER_FILTERED(-1, null),
    CONTROLLER_MAGICAL(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MAGICAL) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SPOOKY);
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, false, false, false);
        }
    }),
    CONTROLLER_FOREST(0, new BiomeFilter() {
        @Override
        public boolean match(BiomeGenBase biome) {
            return biome.theBiomeDecorator.treesPerChunk >= 5;
        }

        @Override
        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b) {
            return calculateBiomeDistance(a, b, false, false, false);
        }
    });

    private final int neededBiomes;
    private final BiomeFilter filter;

    ControllerType(int neededBiomes, BiomeFilter filter) {
        this.neededBiomes = neededBiomes;
        this.filter = filter;
    }

    /**
     * Return the amount of biomes needed for this controller. -1 means that it can use any number of biomes.
     */
    public int getNeededBiomes() {
        return neededBiomes;
    }

    public BiomeFilter getFilter() {
        return filter;
    }

    public abstract static class BiomeFilter {
        /**
         * Return true if this biome should be selected by this filter.
         */
        public abstract boolean match(BiomeGenBase biome);

        /**
         * Return the similarity distance between two biomes.
         */
        public abstract double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b);

        public double calculateBiomeDistance(BiomeGenBase a, BiomeGenBase b, boolean ignoreRain, boolean ignoreTemperature, boolean ignoreHeight) {
            float dr = a.rainfall - b.rainfall;
            if (ignoreRain) {
                dr = 0.0f;
            }
            float dt = a.temperature - b.temperature;
            if (ignoreTemperature) {
                dt = 0.0f;
            }
            float dv = 0;//@todo a.heightVariation - b.heightVariation;
            float dh = 0;//@todo a.rootHeight - b.rootHeight;
            if (ignoreHeight) {
                dv = 0.0f;
                dh = 0.0f;
            }
            return Math.sqrt(dr * dr + dt * dt + dv * dv + dh * dh);
        }
    }
}
