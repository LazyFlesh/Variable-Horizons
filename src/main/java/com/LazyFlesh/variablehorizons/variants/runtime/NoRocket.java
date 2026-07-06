package com.LazyFlesh.variablehorizons.variants.runtime;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class NoRocket extends VariantLoader implements IRuntimeVariant {

    public NoRocket() {}

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_ROCKET.hasLoaded = true;
        // its not working. GOnna have to figure something out later.
        // GTModHandler.removeRecipeByOutput(new ItemStack(GCBlocks.nasaWorkbench));
    }
}
