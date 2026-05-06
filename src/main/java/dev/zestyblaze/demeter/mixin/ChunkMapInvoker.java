package dev.zestyblaze.demeter.mixin;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

@Mixin(ChunkMap.class)
public interface ChunkMapInvoker {
    @Invoker("forEachBlockTickingChunk")
    void demeter$invokeForEachBlockTickingChunk(Consumer<LevelChunk> tickingChunkConsumer);
}
