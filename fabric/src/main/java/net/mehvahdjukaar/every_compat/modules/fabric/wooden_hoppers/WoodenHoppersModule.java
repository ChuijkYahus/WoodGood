package net.mehvahdjukaar.every_compat.modules.fabric.wooden_hoppers;

import io.github.haykam821.woodenhoppers.Main;
import io.github.haykam821.woodenhoppers.block.WoodenHopperBlock;
import io.github.haykam821.woodenhoppers.tag.WoodenHoppersBlockTags;
import io.github.haykam821.woodenhoppers.tag.WoodenHoppersItemTags;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

//SUPPORT: v1.3.1-FINAL
public class WoodenHoppersModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> hoppers;

    public WoodenHoppersModule(String modId) {
        super(modId, "wh");

        hoppers = SimpleEntrySet.builder(WoodType.class, "hopper",
                        () -> getModBlock("oak_hopper"), () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new WoodenHopperBlock(Utils.copyPropertySafe(w.planks).strength(2))
                )
                .addTile(() -> Main.WOODEN_HOPPER_BLOCK_ENTITY_TYPE)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(WoodenHoppersBlockTags.WOODEN_HOPPERS, Registry.BLOCK_REGISTRY)
                .addTag(WoodenHoppersItemTags.WOODEN_HOPPERS, Registry.ITEM_REGISTRY)
                .defaultRecipe()
                .build();
        this.addEntry(hoppers);

    }
}