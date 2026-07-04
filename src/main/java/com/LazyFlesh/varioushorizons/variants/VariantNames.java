package com.LazyFlesh.varioushorizons.variants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.LazyFlesh.varioushorizons.GeneralConfig;
import com.LazyFlesh.varioushorizons.variants.unInvasive.NoRocket;

public enum VariantNames {

    // sub-variants/modifiers
    // modifies one thing, can be stacked with each other (barring incompats)
    // i.e. turns off quests; makes it hardcore; halves all processing time, etc.
    NO_RECIPE_ADDITIONS("noRecipeAdditions", "No Recipe Additions"), // Specifies additions, since no rocket removes
                                                                     // nasa bench recipe.
    VOID_WORLD("voidWorld", "Void World"), // no land anywhere
    VOID_ISLAND("voidIsland", "Void Island"), // OW is a sky island. Think Botania Garden of Glass or normal Skyblock
    NO_ROCKET("rocketless", "No Rocket", new NoRocket()), // disable nasa bench
    NO_QUEST_REWARDS("questless", "No Quest Rewards"),
    HALF_TIME("halfTime", "Halve Recipe Time"),
    QUARTER_TIME("quarterTime", "Quarter Recipe Time"),
    DOUBLE_TIME("doubleTime", "Double Recipe Time"),
    HALF_EFFICIENCY("halfEfficiency", "Halve Powergen Efficiency"),
    INFINITE_POWER("infPower", "Infinite Power"),
    NETHER_START("netherStart", "Nether Start"), // sets Nether as the spawn dimension instead of OW

    // full variants
    // i.e. defines both world type and recipes
    // unlikely to be compatible with each other
    NORMAL("normal", "Normal", true, "none"), // no changes
    GARDEN_OF_GRIND("gog", "Garden of Grind", true, "composedOf", VOID_WORLD, NO_RECIPE_ADDITIONS, NO_ROCKET),
    NETHER_ONLY("netherOnly", "Nether Only", true, "composedOf", NETHER_START, NO_ROCKET, "incompatible",
        NO_RECIPE_ADDITIONS),
    SKYBLOCK("skyblock", "Skyblock", true, "composedOf", VOID_ISLAND, "incompatible", NO_RECIPE_ADDITIONS),

    ;

    public final String id; //
    public final String name; // human-readable name
    public final boolean compositionVariant; // is it made of several modifications
    public List<VariantNames> incompatible;
    public List<VariantNames> composedOf;
    public VariantLoader loaderClass;

    VariantNames(String id, String name) {
        this.id = id;
        this.name = name;
        this.compositionVariant = false;
    }

    VariantNames(String id, String name, VariantLoader loaderClass) {
        this.loaderClass = loaderClass;
        this.id = id;
        this.name = name;
        this.compositionVariant = false;
    }

    VariantNames(String id, String name, boolean compositionVariant, Object... relations) {
        this.id = id;
        this.name = name;
        this.compositionVariant = true;

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

    VariantNames(String id, String name, VariantLoader loaderClass, Object... relations) {
        this(id, name, true, relations);
        if (loaderClass != null) {
            this.loaderClass = loaderClass;
        }
    }

    public static List<String> getVariantNames() {
        List<String> names = new ArrayList<>();
        for (VariantNames name : VariantNames.values()) {
            names.add(name.name);
        }
        return names;
    }

    public static String getVariantNamesFormatted() {
        StringBuilder names = new StringBuilder();
        for (VariantNames name : VariantNames.values()) {
            names.append(name.name)
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

    public static VariantNames getVariantFromName(String name) {
        for (VariantNames vars : VariantNames.values()) {
            if (name.equalsIgnoreCase(vars.name)) return vars;
        }
        return null;
    }

    // does the id match a variant's id
    public static boolean contains(String id) {
        for (VariantNames name : VariantNames.values()) {
            if (id.equalsIgnoreCase(name.name)) return true;
        }
        return false;
    }

    // does the id match a variant's id
    public static boolean activeContains(String id) {
        for (String name : GeneralConfig.activeVariants) {
            if (id.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

}
