package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;

@Mixin({ ChunkProviderServer.class })
public class MixinChunkProviderServer_DisableTerrain_EndlessIDs {

    @Unique
    private static final int CHUNK_WIDTH = 16;
    @Unique
    private static final int CHUNK_LENGTH = 16;
    @Unique
    private static final int CHUNK_HEIGHT = 256;
    @Unique
    private static final int BLOCKS_TOTAL = 65536;

    public MixinChunkProviderServer_DisableTerrain_EndlessIDs() {}

    @ModifyVariable(
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/chunk/IChunkProvider;provideChunk(II)Lnet/minecraft/world/chunk/Chunk;"),
        method = { "originalLoadChunk" })
    public Chunk hodgepodge$disableTerrainEndlessIds(Chunk chunk) {
        Block[] ids = new Block[65536];
        byte[] metadata = new byte[65536];
        Arrays.fill(ids, Blocks.air);
        short[] biomeArray = ((ChunkBiomeHook) chunk).getBiomeShortArray();
        Chunk newChunk = new Chunk(chunk.worldObj, ids, metadata, chunk.xPosition, chunk.zPosition);
        ((ChunkBiomeHook) newChunk).setBiomeShortArray(biomeArray);
        return newChunk;
    }
}
