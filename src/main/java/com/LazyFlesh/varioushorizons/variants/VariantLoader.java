package com.LazyFlesh.varioushorizons.variants;

import java.util.Arrays;
import java.util.List;

import com.LazyFlesh.varioushorizons.VariousHorizons;

public abstract class VariantLoader {

    public void loadVariant(VariantNames... activeVariants) {
        List<VariantNames> active = Arrays.asList(activeVariants);

        for (VariantNames variant : activeVariants) {
            if (variant.incompatible != null) {
                for (VariantNames incompat : variant.incompatible) {
                    if (active.contains(incompat)) {
                        VariousHorizons.LOG.warn("A variant incompatible with another active variant was detected.");
                        VariousHorizons.LOG.warn("Turning off incompatible variant: {}", incompat.name);
                        active.remove(incompat);
                        // don't break, so all incompatible variants are removed.
                    }
                }
            }
            if (variant.loaderClass instanceof VariantLoader) {
                variant.loaderClass.loadVariant(activeVariants);
            }
        }
    }
}
