package com.LazyFlesh.varioushorizons;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config.RequiresMcRestart
@Config(modid = VariousHorizons.MODID)
public class Config {

    @Config.Comment("Disables all changes made by Various Horizons.")
    @Config.DefaultBoolean(false)
    public static boolean disableVariants;


}
