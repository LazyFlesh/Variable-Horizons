package com.LazyFlesh.variablehorizons.variants;

import java.util.List;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.LazyFlesh.variablehorizons.variants.runtime.IRuntimeVariant;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

public abstract class VariantLoader {

    public static void loadActiveVariants() {
        List<String> active = VariantNames.getActiveVariantNames();

        for (String var : active) {
            VariantNames variant = VariantNames.getVariantFromID(var);
            if (variant == null) {
                VariableHorizons.LOG.warn("Turning off undefined variant: {}", var);
                active.remove(var);
                continue;
            }
            if (variant.incompatible != null) {
                for (VariantNames incompatible : variant.incompatible) {
                    if (active.contains(incompatible.id)) {
                        VariableHorizons.LOG.warn("A variant incompatible with another active variant was detected.");
                        VariableHorizons.LOG.warn("Turning off incompatible variant: {}", incompatible.id);
                        active.remove(incompatible.id);
                        // don't break, so all incompatible variants are removed.
                    }
                }
            }

            if (variant.loaderClass instanceof VariantLoader && !variant.hasLoaded) {
                variant.loaderClass.loadVariant();
            } else if (variant.loaderClass == null) {
                // nothing to load, so just mark it as loaded.
                variant.hasLoaded = true;
            }
        }
        GeneralConfig.activeVariants = active.toArray(new String[0]); // save removed variants
        ConfigurationManager.save(GeneralConfig.class);
    }

    public abstract void loadVariant(VariantNames... activeVariants);

    public static String toggleVariant(VariantNames name, boolean state) {
        if (state) {
            if (VariantNames.activeContains(name.id)) {
                return "Variant already active.";
            } else {
                if (name.incompatible != null) {
                    for (VariantNames incompatible : name.incompatible) {
                        if (VariantNames.activeContains(incompatible.id)) {
                            return "Variant is incompatible with an active variant.";
                        }
                    }
                }
                // aaand now check none of the active are incompatible with *it*
                for (String var : VariantNames.getActiveVariantNames()) {
                    VariantNames v = VariantNames.getVariantFromID(var);
                    if (v == null) {
                        VariableHorizons.LOG.warn("Undefined variant in active variants: {}", var);
                        continue;
                    }
                    if (v.incompatible != null) {
                        if (v.incompatible.contains(name)) {
                            return "Variant is incompatible with an active variant.";
                        }
                    }
                }
                // both passes made, add it
                List<String> active = VariantNames.getActiveVariantNames();
                active.add(name.id);
                GeneralConfig.activeVariants = active.toArray(new String[0]);
                ConfigurationManager.save(GeneralConfig.class);
                if (name.loaderClass instanceof IRuntimeVariant) {
                    name.loaderClass.loadVariant();
                    return "Client/world restart may be required for change to take effect.";
                }
                return "Server/instance restart required for change to take effect.";
            }
        } else {
            if (!VariantNames.activeContains(name.id)) {
                return "Variant already inactive.";
            } else {
                List<String> active = VariantNames.getActiveVariantNames();
                active.remove(name.id);
                GeneralConfig.activeVariants = active.toArray(new String[0]);
                ConfigurationManager.save(GeneralConfig.class);
                return "Server/instance restart required for change to take effect.";
            }
        }
    }
}
