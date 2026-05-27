package dev.zestyblaze.demeter.config;

import dev.zestyblaze.demeter.Demeter;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.api.SaveType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

@Version(version = 1)
public class DemeterConfig extends Config {
    public DemeterConfig() {
        super(Demeter.createId("demeter"));
    }

    //TODO Removing crop wilting for now
    //public CropConfig cropConfig = new CropConfig();
    public FarmlandConfig farmlandConfig = new FarmlandConfig();
    public MiscConfig miscConfig = new MiscConfig();
    public QualityConfig qualityConfig = new QualityConfig();

    public static class CropConfig extends ConfigSection {
        @Comment("Defines if crops will wilt after a certain number of days")
        public ValidatedBoolean doCropsWilt = new ValidatedBoolean(true);
        @Comment("Defines how many days it takes for crops to wilt once reaching maturity")
        public ValidatedInt daysToWilt = new ValidatedInt(3);
    }

    public static class FarmlandConfig extends ConfigSection {
        @Comment("The percent chance that farmland will turn back into dirt if not hydrated")
        public ValidatedInt morningDirtChance = new ValidatedInt(75, 100, 0);
        @Comment("Re-enables the Vanilla feature of water sources hydrating nearby farmland")
        public ValidatedBoolean waterIrrigationEnabled = new ValidatedBoolean(false);
    }

    public static class MiscConfig extends ConfigSection {
        @Comment("Allows the player to sleep at any time of the day. Can be disabled if causing mod conflicts")
        public ValidatedBoolean canSleepWhenever = new ValidatedBoolean(true);
        @Comment("Switches all blocks off of Polymer Textured Blocks if mod conflicts occur, will have a more negative impact on performance")
        public ValidatedBoolean useFullTexturedBlocks = new ValidatedBoolean(false);
    }

    public static class QualityConfig extends ConfigSection {
        public ValidatedInt copperQualityChance = new ValidatedInt(25, 100, 0);
        public ValidatedInt ironQualityChance = new ValidatedInt(10, 100, 0);
        public ValidatedInt netheriteQualityChance = new ValidatedInt(1, 100, 0);
    }

    @Override
    public int defaultPermLevel() {
        return 4;
    }

    @Override
    public SaveType saveType() {
        return SaveType.SEPARATE;
    }
}
