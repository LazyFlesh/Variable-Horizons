package com.LazyFlesh.varioushorizons;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config.RequiresMcRestart
@Config(modid = VariousHorizons.MODID)
public class GeneralConfig {

    @Config.Comment("Disables all changes made by Various Horizons.")
    @Config.DefaultBoolean(false)
    public static boolean disableVariants;

    @Config.Comment("List of all active Variants' names. You can use in-game commands to change it, as well as see all options.")
    @Config.DefaultStringList({ "Normal" })
    public static String[] activeVariants;

}
