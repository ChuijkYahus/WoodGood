package net.mehvahdjukaar.every_compat.modules.neoforge.woodster;

import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.salju.woodster.WoodsterMod;
import net.salju.woodster.block.BookshelfBlock;
import net.salju.woodster.init.WoodsterBlocks;
import net.salju.woodster.init.WoodsterTabs;

public class WoodsterModule extends SimpleModule {
    public final SimpleEntrySet<WoodType, Block> chiseled_books;
    public final SimpleEntrySet<WoodType, Block> books;
    public final SimpleEntrySet<WoodType, Block> ladders;

    public WoodsterModule(String modId) {
        super(modId, "wdst");
        ResourceLocation tab = modRes(WoodsterMod.MODID);

        chiseled_books = SimpleEntrySet.builder(WoodType.class, "chiseled_bookshelf",
                        getModBlock("dark_oak_chiseled_bookshelf"), () -> WoodTypeRegistry.getValue(ResourceLocation.parse("dark_oak")),
                        w -> new ChiseledBookShelfBlock(Utils.copyPropertySafe(w.planks))
                )
                .addTextureM(modRes("block/dark_oak_chiseled_bookshelf_6"),modRes("block/everycomp_chiseled_bookshelf_6"))
                .addTexture(modRes("block/dark_oak_chiseled_bookshelf_side"))
                .addTexture(modRes("block/dark_oak_chiseled_bookshelf_top"))
                .addTexture(modRes("block/dark_oak_chiseled_bookshelf_0"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTabKey(tab)
                .copyParentDrop()
                .defaultRecipe()
                .build();

        this.addEntry(chiseled_books);

        books = SimpleEntrySet.builder(WoodType.class, "bookshelf",
                        getModBlock("acacia_bookshelf"), () -> WoodTypeRegistry.getValue(ResourceLocation.parse("acacia")),
                        w -> new BookshelfBlock(Utils.copyPropertySafe(w.planks))
                )
                .addTextureM(EveryCompat.res("block/acacia_bookshelf"), EveryCompat.res("block/acacia_bookshelf_m"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTabKey(tab)
                .copyParentDrop()
                .defaultRecipe()
                .build();

        this.addEntry(books);

        ladders = SimpleEntrySet.builder(WoodType.class, "ladder",
                        getModBlock("spruce_ladder"), () -> WoodTypeRegistry.getValue(ResourceLocation.parse("spruce")),
                        w -> new LadderBlock(Utils.copyPropertySafe(getModBlock("spruce_ladder").get()))
                )
                .addTag(ResourceLocation.parse("minecraft:ladders"), Registries.BLOCK)
                .addTag(ResourceLocation.parse("minecraft:ladders"), Registries.ITEM)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(BlockTags.CLIMBABLE, Registries.BLOCK)
                .addTexture(EveryCompat.res("block/spruce_ladder"))
                .setTabKey( tab)
                .defaultRecipe()
                .build();

        this.addEntry(ladders);
    }
}