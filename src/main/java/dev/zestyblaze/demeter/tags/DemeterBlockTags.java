package dev.zestyblaze.demeter.tags;

import dev.zestyblaze.demeter.Demeter;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class DemeterBlockTags {
    public static final TagKey<Block> MAPLE_LOGS = TagKey.create(Registries.BLOCK, Demeter.createId("maple_logs"));
}
