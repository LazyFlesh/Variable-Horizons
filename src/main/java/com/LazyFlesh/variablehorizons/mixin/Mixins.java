package com.LazyFlesh.variablehorizons.mixin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.Config.GogConfig;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;
import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import com.gtnewhorizon.gtnhmixins.builders.IBaseTransformer;
import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.ITargetMod;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder;

import cpw.mods.fml.common.versioning.ComparableVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public enum Mixins implements IMixins {

    DISABLE_CHUNK_TERRAIN_GENERATION(new MixinBuilder().addCommonMixins("MixinChunkProviderServer_DisableTerrain")
        .setApplyIf(() -> VariantNames.activeContains(VariantNames.VOID_WORLD.id) && !GeneralConfig.disableVariants)
        .addExcludedMod(TargetedMod.ENDLESSIDS)
        .setPhase(IBaseTransformer.Phase.EARLY)),
    DISABLE_CHUNK_TERRAIN_GENERATION_ENDLESS_IDS(
        new MixinBuilder().addCommonMixins("MixinChunkProviderServer_DisableTerrain_EndlessIDs")
            .setApplyIf(() -> VariantNames.activeContains(VariantNames.VOID_WORLD.id) && !GeneralConfig.disableVariants)
            .addRequiredMod(TargetedMod.ENDLESSIDS)
            .setPhase(IBaseTransformer.Phase.EARLY)),
    DISABLE_WORLD_TYPE_CHUNK_POPULATION(
        new MixinBuilder("Disable chunk population tied to chunk generation (ores/structure)")
            .addCommonMixins("MixinChunkProviderServer_DisablePopulation")
            .setApplyIf(() -> VariantNames.activeContains(VariantNames.VOID_WORLD.id) && !GeneralConfig.disableVariants)
            .setPhase(IBaseTransformer.Phase.EARLY)),
    DISABLE_MODDED_CHUNK_POPULATION(new MixinBuilder("Disable all other mod chunk population (e.g. Natura clouds")
        .addCommonMixins("MixinChunkProviderServer_DisableModGeneration")
        .setApplyIf(
            () -> VariantNames.activeContains(VariantNames.VOID_WORLD.id) && !GogConfig.dragonTime
                && !GeneralConfig.disableVariants)
        .setPhase(IBaseTransformer.Phase.EARLY));

    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return builder;
    }

    public enum TargetedMod implements ITargetMod {

        ENDLESSIDS("com.falsepattern.endlessids.asm.EndlessIDsCore", "endlessids");

        private final TargetModBuilder builder;

        TargetedMod(TargetModBuilder builder) {
            this.builder = builder;
        }

        TargetedMod(String modId) {
            this(null, modId, null);
        }

        TargetedMod(String coreModClass, String modId) {
            this(coreModClass, modId, null);
        }

        TargetedMod(String coreModClass, String modId, String targetClass) {
            this.builder = new TargetModBuilder().setCoreModClass(coreModClass)
                .setModId(modId)
                .setTargetClass(targetClass);
        }

        @Nonnull
        @Override
        public TargetModBuilder getBuilder() {
            return builder;
        }

        private static boolean isVersionLessThan(String version, String target) {
            return new ComparableVersion(version).compareTo(new ComparableVersion(target)) < 0;
        }
    }

    @LateMixin
    public static class LateMixinLoader implements ILateMixinLoader {

        @Override
        public String getMixinConfig() {
            return "mixins.variablehorizons.late.json";
        }

        @Override
        public @NotNull List<String> getMixins(Set<String> loadedMods) {
            return IMixins.getLateMixins(Mixins.class, loadedMods);
        }
    }

    public static class EarlyMixinLoader implements IEarlyMixinLoader, IFMLLoadingPlugin {

        @Override
        public String getMixinConfig() {
            return "mixins.variablehorizons.early.json";
        }

        @Override
        public @NotNull List<String> getMixins(Set<String> loadedMods) {
            return IMixins.getEarlyMixins(Mixins.class, loadedMods);
        }

        @Override
        public String[] getASMTransformerClass() {
            return null;
        }

        @Override
        public String getModContainerClass() {
            return null;
        }

        @Override
        public String getSetupClass() {
            return null;
        }

        @Override
        public void injectData(Map<String, Object> data) {

        }

        @Override
        public String getAccessTransformerClass() {
            return null;
        }
    }

}
