package com.LazyFlesh.varioushorizons.variants.runtime;

import com.LazyFlesh.varioushorizons.variants.VariantLoader;
import com.LazyFlesh.varioushorizons.variants.VariantNames;

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
