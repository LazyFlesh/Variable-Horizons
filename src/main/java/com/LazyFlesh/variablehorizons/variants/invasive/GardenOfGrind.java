package com.LazyFlesh.variablehorizons.variants.invasive;

import java.util.List;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class GardenOfGrind extends VariantLoader {

    public GardenOfGrind() {}

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        // gog doesn't need anything loaded (early mixins load on restart), but the variants it's composed of might.
        // No rocket does, for example, and Void World has to be active for mixins to take efect.
        VariantNames.GARDEN_OF_GRIND.hasLoaded = true;

        List<String> active = VariantNames.getActiveVariantNames();
        for (VariantNames name : VariantNames.GARDEN_OF_GRIND.composedOf) {
            active.add(name.id);
            if (name.loaderClass instanceof VariantLoader) {
                name.loaderClass.loadVariant(activeVariants);
            }
        }
        // add variants to active variants, for checks for incompatibility and state
        GeneralConfig.activeVariants = active.toArray(new String[0]);

    }
}
