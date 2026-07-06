package com.LazyFlesh.variablehorizons.variants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.invasive.GardenOfGrind;
import com.LazyFlesh.variablehorizons.variants.runtime.NoRocket;

public enum VariantNames {

    // sub-variants/modifiers
    // modifies one thing, can be stacked with each other (barring incompats)
    // i.e. turns off quests; makes it hardcore; halves all processing time, etc.
    NO_RECIPE_ADDITIONS("NO_RECIPE_ADDITIONS"), // Specifies additions, since no rocket removes
                                                // nasa bench recipe.
    VOID_WORLD("VOID_WORLD" /* no class, just mixin */), // no land anywhere
    VOID_ISLAND("VOID_ISLAND"), // OW is a sky island. Think Botania Garden of Glass or normal Skyblock
    NO_ROCKET("NO_ROCKET", new NoRocket()), // disable nasa bench
    NO_QUEST_REWARDS("NO_QUEST_REWARDS"),
    HALF_TIME("HALF_TIME"),
    QUARTER_TIME("QUARTER_TIME"),
    DOUBLE_TIME("DOUBLE_TIME"),
    HALF_EFFICIENCY("HALF_EFFICIENCY"),
    INFINITE_POWER("INFINITE_POWER"),
    NETHER_START("NETHER_START"), // sets Nether as the spawn dimension instead of OW

    // full variants
    // i.e. defines both world type and recipes
    // unlikely to be compatible with each other
    NORMAL("NORMAL", true, "none"), // does nothing
    GARDEN_OF_GRIND("GARDEN_OF_GRIND", new GardenOfGrind(), "composedOf", VOID_WORLD, NO_RECIPE_ADDITIONS, NO_ROCKET),
    NETHER_ONLY("NETHER_ONLY", true, "composedOf", NETHER_START, NO_ROCKET, "incompatible", NO_RECIPE_ADDITIONS),
    SKYBLOCK("SKYBLOCK", true, "composedOf", VOID_ISLAND, "incompatible", NO_RECIPE_ADDITIONS),
    // only OW is void, w/ sky island
    // if you want Skyblock with no recipe additions, do Garden of Grind + Void Island.

    ;

    public final String id;
    public final boolean compositionVariant; // is it made of several modifications
    public List<VariantNames> incompatible;
    public List<VariantNames> composedOf;
    public VariantLoader loaderClass;
    public boolean hasLoaded = false;

    VariantNames(String id) {
        this.id = id;
        this.compositionVariant = false;
    }

    VariantNames(String id, VariantLoader loaderClass) {
        this.loaderClass = loaderClass;
        this.id = id;
        this.compositionVariant = false;
    }

    VariantNames(String id, boolean compositionVariant, Object... relations) {
        this.id = id;
        this.compositionVariant = compositionVariant;

        List<VariantNames> incompatible = new ArrayList<>();
        List<VariantNames> composedOf = new ArrayList<>();

        int writeMode = 0;
        for (Object in : relations) {
            if (in instanceof String s) {
                if (s.equalsIgnoreCase("incompatible")) {
                    writeMode = 1;
                } else if (s.equalsIgnoreCase("composedOf")) {
                    writeMode = 2;
                } else if (s.equalsIgnoreCase("none")) break;
            } else if (in instanceof VariantNames v) {
                if (writeMode == 1) {
                    incompatible.add(v);
                } else if (writeMode == 2) {
                    composedOf.add(v);
                }
            }
        }

        if (!incompatible.isEmpty()) this.incompatible = incompatible;

        if (!composedOf.isEmpty()) this.composedOf = composedOf;
    }

    VariantNames(String id, VariantLoader loaderClass, Object... relations) {
        this(id, true, relations);
        if (loaderClass != null) {
            this.loaderClass = loaderClass;
        }
    }

    public static List<String> getVariantNames() {
        List<String> names = new ArrayList<>();
        for (VariantNames name : VariantNames.values()) {
            names.add(name.id);
        }
        return names;
    }

    public static String getVariantNamesFormatted() {
        StringBuilder names = new StringBuilder();
        for (VariantNames name : VariantNames.values()) {
            names.append(name.id)
                .append(", ");
        }
        return names.toString();
    }

    public static List<String> getActiveVariantNames() {
        return new ArrayList<>(Arrays.asList(GeneralConfig.activeVariants));
    }

    public static VariantNames getVariantFromID(String id) {
        for (VariantNames name : VariantNames.values()) {
            if (id.equalsIgnoreCase(name.id)) return name;
        }
        return null;
    }

    // does the id match a variant's id
    public static boolean contains(String... id) {
        List<String> active = getVariantNames();
        for (String s : id) {
            if (active.contains(s.toUpperCase())) return true;
        }
        return false;
    }

    // does the id match a variant's id
    public static boolean activeContains(String... id) {
        List<String> active = Arrays.asList(GeneralConfig.activeVariants);
        for (String s : id) {
            if (active.contains(s.toUpperCase())) return true;
        }
        return false;
    }

}
