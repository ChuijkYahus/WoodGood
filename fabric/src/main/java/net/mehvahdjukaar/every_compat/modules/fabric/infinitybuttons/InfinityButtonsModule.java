package net.mehvahdjukaar.every_compat.modules.fabric.infinitybuttons;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.larsmans.infinitybuttons.block.InfinityButtonsBlocks;
import net.larsmans.infinitybuttons.block.custom.button.WoodenButton;
import net.larsmans.infinitybuttons.block.custom.secretbutton.PlankSecretButton;
import net.larsmans.infinitybuttons.item.InfinityButtonsItemGroup;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class InfinityButtonsModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> largeButton;
    public final SimpleEntrySet<WoodType, Block> plankSecretButton;

    public InfinityButtonsModule(String modId) {
        super(modId, "ib");

        largeButton = SimpleEntrySet.builder(WoodType.class, "large_button",
                        () -> InfinityButtonsBlocks.OAK_LARGE_BUTTON, () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new WoodenButton(FabricBlockSettings.of(Material.DECORATION).strength(0.5f).collidable(false).nonOpaque().sounds(SoundType.WOOD), true))
                .addTag(modRes("wooden_large_buttons"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("wooden_large_buttons"), Registry.ITEM_REGISTRY)
                .setTab(() -> InfinityButtonsItemGroup.INFINITYBUTTONS)
                .defaultRecipe()
                .build();

        this.addEntry(largeButton);

        plankSecretButton = SimpleEntrySet.builder(WoodType.class, "plank_secret_button",
                        () -> InfinityButtonsBlocks.OAK_PLANK_SECRET_BUTTON, () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new PlankSecretButton(Utils.copyPropertySafe(w.planks).strength(2.0f, 3.0f).noOcclusion().sound(SoundType.WOOD), w.planks))
                .addTag(modRes("wooden_secret_buttons"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("wooden_secret_buttons"), Registry.ITEM_REGISTRY)
                .setTab(() -> InfinityButtonsItemGroup.INFINITYBUTTONS)
                .defaultRecipe()
                .build();

        this.addEntry(plankSecretButton);
    }
}

