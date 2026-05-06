package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.attachment.CropsInChunkAttachment;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class DemeterAttachments {
    public static final AttachmentType<CropsInChunkAttachment> CROPS_IN_CHUNK = AttachmentRegistry.create(
            Demeter.createId("crops_in_chunk"), builder -> builder.initializer(CropsInChunkAttachment::new)
                    .persistent(CropsInChunkAttachment.CODEC)
    );

    public static void init() {}
}
