package com.LazyFlesh.variablehorizons.Config;

import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.gtnewhorizon.gtnhlib.config.Config;

@Config.RequiresMcRestart
@Config(
    modid = VariableHorizons.MODID,
    configSubDirectory = "VariableHorizons",
    filename = "GardenOfGrind",
    category = "Garden of Grind")
public class GogConfig {

    @Config.Comment("Re-enables modded chunk population, so the chaos island generates")
    @Config.DefaultBoolean(false)
    public static boolean dragonTime;

}
