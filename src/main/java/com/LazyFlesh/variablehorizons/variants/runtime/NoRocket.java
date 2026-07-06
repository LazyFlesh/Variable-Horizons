package com.LazyFlesh.variablehorizons.variants.runtime;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.Mods;
import gregtech.api.util.GTModHandler;

public class NoRocket extends VariantLoader implements IRuntimeVariant {

    public NoRocket() {}

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_ROCKET.hasLoaded = true;
        GTModHandler
            .removeRecipeByOutput(GTModHandler.getModItem(Mods.GalacticraftCore.ID, "rocketWorkbench", 1, 32767));
    }
}
