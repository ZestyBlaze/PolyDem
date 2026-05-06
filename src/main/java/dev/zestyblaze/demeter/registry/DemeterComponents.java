package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.component.QualityComponent;
import eu.pb4.polymer.core.api.other.PolymerComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class DemeterComponents {
    public static final DataComponentType<QualityComponent> QUALITY = DataComponentType.<QualityComponent>builder()
            .persistent(QualityComponent.CODEC).build();

    public static void init() {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Demeter.createId("quality"), QUALITY);
        PolymerComponent.registerDataComponent(QUALITY);
    }
}
