package com.LazyFlesh.variablehorizons.variants.invasive;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.GTMod;
import gregtech.common.config.Worldgen;

public class GardenOfGrind extends VariantLoader {

    public GardenOfGrind() {}

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        // gog doesn't need anything loaded (early mixins load on restart)
        VariantNames.GARDEN_OF_GRIND.hasLoaded = true;

        // but it does need to overwrite some things
        // the hodgepodge mixins have been ported here, because early mixins are hard to toggle config on.
        Worldgen.endAsteroids.generateEndAsteroids = false;
        // disable entity cramming (makes mob farms behave better)
        GTMod.proxy.mMaxEqualEntitiesAtOneSpot = -1;
    }
}
