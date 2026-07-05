package com.LazyFlesh.variablehorizons.variants.runtime;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.Mods;
import gregtech.api.util.GTModHandler;

public class NoRocket extends VariantLoader implements IRuntimeVariant {

    VariantNames variant = VariantNames.NO_ROCKET;

    public NoRocket() {}

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        GTModHandler.removeRecipeByOutput(GTModHandler.getModItem(Mods.GalacticraftCore.ID, "tile.rocketWorkbench"));
    }
}
