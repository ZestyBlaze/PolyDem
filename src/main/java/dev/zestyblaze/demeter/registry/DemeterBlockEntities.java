package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.block.entity.BrickGrillBlockEntity;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class DemeterBlockEntities {
    public static final BlockEntityType<BrickGrillBlockEntity> BRICK_GRILL = FabricBlockEntityTypeBuilder.create(BrickGrillBlockEntity::new, DemeterBlocks.BRICK_GRILL).build();

    public static void init() {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Demeter.createId("brick_grill"), BRICK_GRILL);
        PolymerBlockUtils.registerBlockEntity(BRICK_GRILL);
    }
}
