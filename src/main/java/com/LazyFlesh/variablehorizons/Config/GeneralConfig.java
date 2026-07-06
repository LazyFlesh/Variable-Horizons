package com.LazyFlesh.variablehorizons.Config;

import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.gtnewhorizon.gtnhlib.config.Config;

@Config.RequiresMcRestart
@Config(
    modid = VariableHorizons.MODID,
    configSubDirectory = "VariableHorizons",
    filename = "GeneralConfig",
    category = "General")
public class GeneralConfig {

    @Config.Comment("Disables all changes made by Variable Horizons.")
    @Config.DefaultBoolean(false)
    public static boolean disableVariants;

    @Config.Comment("List of all active Variants' names. You can use in-game commands to change it, as well as see all options.")
    @Config.DefaultStringList({ "NORMAL" })
    public static String[] activeVariants;

}
