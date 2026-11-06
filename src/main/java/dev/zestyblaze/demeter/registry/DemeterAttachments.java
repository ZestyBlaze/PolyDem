package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.attachment.CropsInChunkAttachment;
import dev.zestyblaze.demeter.attachment.DemeterAnimalAttachment;
import dev.zestyblaze.demeter.attachment.SeasonAttachment;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class DemeterAttachments {
    public static final AttachmentType<DemeterAnimalAttachment> ANIMAL_DATA = AttachmentRegistry.create(
            Demeter.createId("animal_data"), builder -> builder.initializer(DemeterAnimalAttachment::new)
                    .persistent(DemeterAnimalAttachment.CODEC)
    );
    public static final AttachmentType<CropsInChunkAttachment> CROPS_IN_CHUNK = AttachmentRegistry.create(
            Demeter.createId("crops_in_chunk"), builder -> builder.initializer(CropsInChunkAttachment::new)
                    .persistent(CropsInChunkAttachment.CODEC)
    );
    public static final AttachmentType<SeasonAttachment> SEASON = AttachmentRegistry.create(
            Demeter.createId("season"), builder -> builder.initializer(SeasonAttachment::new)
                    .persistent(SeasonAttachment.CODEC)
    );

    public static void init() {}
}
