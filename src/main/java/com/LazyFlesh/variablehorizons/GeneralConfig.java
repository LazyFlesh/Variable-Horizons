package com.LazyFlesh.variablehorizons;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config.RequiresMcRestart
@Config(modid = VariableHorizons.MODID)
public class GeneralConfig {

    @Config.Comment("Disables all changes made by Various Horizons.")
    @Config.DefaultBoolean(false)
    public static boolean disableVariants;

    @Config.Comment("List of all active Variants' names. You can use in-game commands to change it, as well as see all options.")
    @Config.DefaultStringList({ "NORMAL" })
    public static String[] activeVariants;

}
