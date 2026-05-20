package dev.zestyblaze.demeter.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.zestyblaze.demeter.data.CropBlockData;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public record CropsInChunkAttachment(Map<BlockPos, CropBlockData> locations) {
    public static final Codec<CropsInChunkAttachment> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.unboundedMap(Codec.STRING.flatXmap(
                            s -> {
                                String[] parts = s.split(",");
                                if (parts.length == 3) {
                                    try {
                                        return DataResult.success(new BlockPos(
                                                Integer.parseInt(parts[0]),
                                                Integer.parseInt(parts[1]),
                                                Integer.parseInt(parts[2])
                                        ));
                                    } catch (NumberFormatException e) {
                                        return DataResult.error(() -> "Invalid input: " + s + "(" + e.getMessage() + ")");
                                    }
                                }
                                return DataResult.error(() -> "Invalid input: " + s);
                            },
                            p -> DataResult.success(p.getX() + "," + p.getY() + "," + p.getZ())
                    ),
                    CropBlockData.CODEC).xmap(HashMap::new, Function.identity())
                    .fieldOf("locations").forGetter(o -> (HashMap<BlockPos, CropBlockData>) o.locations)
    ).apply(func, CropsInChunkAttachment::new));

    public CropsInChunkAttachment() {
        this(new HashMap<>());
    }

    public void addLocation(BlockPos pos) {
        if (!locations.containsKey(pos)) {
            locations.put(pos, new CropBlockData());
        }
    }

    public void removeLocation(BlockPos pos) {
        locations.remove(pos);
    }

    public void incrementDays(BlockPos pos) {
        if (locations.containsKey(pos)) {
            CropBlockData data = locations.get(pos);
            int old = data.age;
            locations.replace(pos, new CropBlockData(++old));
        }
    }

    public int getDays(BlockPos pos) {
        return locations.get(pos).age;
    }
}
