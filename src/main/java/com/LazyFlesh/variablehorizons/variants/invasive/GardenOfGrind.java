package com.LazyFlesh.variablehorizons.variants.invasive;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class GardenOfGrind extends VariantLoader {

    public GardenOfGrind() {}

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        // gog doesn't need anything loaded (early mixins load on restart)
        VariantNames.GARDEN_OF_GRIND.hasLoaded = true;
    }
}
