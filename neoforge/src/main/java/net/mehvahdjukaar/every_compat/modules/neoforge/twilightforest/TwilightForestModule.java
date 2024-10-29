package net.mehvahdjukaar.every_compat.modules.neoforge.twilightforest;

import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import twilightforest.block.BanisterBlock;
import twilightforest.block.ClimbableHollowLogBlock;
import twilightforest.block.HorizontalHollowLogBlock;
import twilightforest.block.VerticalHollowLogBlock;
import twilightforest.enums.HollowLogVariants;
import twilightforest.init.TFBlocks;
import twilightforest.item.HollowLogItem;

import java.util.function.Supplier;

//SUPPORT: v4.3.1750+
public class TwilightForestModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, BanisterBlock> banisters;
    public final SimpleEntrySet<WoodType, VerticalHollowLogBlock> hollowLogsVertical;
    public final SimpleEntrySet<WoodType, HorizontalHollowLogBlock> hollowLogsHorizontal;
    public final SimpleEntrySet<WoodType, ClimbableHollowLogBlock> hollowLogsClimbable;

    public TwilightForestModule(String modId) {
        super(modId, "tf");
        var tab = modRes("blocks");

        banisters = SimpleEntrySet.builder(WoodType.class, "banister",
                        TFBlocks.OAK_BANISTER, () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new BanisterBlock(Utils.copyPropertySafe(w.planks).noOcclusion())
                )
                .addTag(modRes("banisters"), Registries.BLOCK)
                .addTag(modRes("banisters"), Registries.ITEM)
                .addRecipe(modRes("wood/oak_banister"))
                .copyParentDrop()
                .setTabKey(tab)
                .build();
        this.addEntry(banisters);


        hollowLogsHorizontal = SimpleEntrySet.builder(WoodType.class, "log_horizontal", "hollow",
                        TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL, getBirch(),
                        w -> new HorizontalHollowLogBlock(Utils.copyPropertySafe(w.log))
                )
                .requiresChildren("stripped_log") //REASON: Textures
                .addTag(modRes("hollow_logs_horizontal"), Registries.BLOCK)
                .noItem().noTab() //REASON: it's using the hollowLogsVertical's tab/item as the main
                .setRenderType(RenderLayer.CUTOUT_MIPPED)
                .build();
        this.addEntry(hollowLogsHorizontal);


        hollowLogsVertical = SimpleEntrySet.builder(WoodType.class, "log_vertical", "hollow",
                        TFBlocks.HOLLOW_BIRCH_LOG_VERTICAL, getBirch(),
                        w -> {
                            var id = EveryCompat.res(this.shortenedId() + "/" + w.getVariantId("hollow", true) + "_log_climbable");
                            return new VerticalHollowLogBlock(Utils.copyPropertySafe(w.log), DeferredHolder.create(Registries.BLOCK, id));
                        })
                .requiresChildren("stripped_log") //REASON: Textures
                .addTag(modRes("hollow_logs_vertical"), Registries.BLOCK)
                .noItem()
                .setTabKey(tab)
                .addRecipe(modRes("stonecutting/birch_log/hollow_birch_log"))
                .build();
        this.addEntry(hollowLogsVertical);

        hollowLogsClimbable = SimpleEntrySet.builder(WoodType.class, "log_climbable", "hollow",
                        TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE, getBirch(),
                        w -> new ClimbableHollowLogBlock(
                                DeferredHolder.create(Registries.BLOCK, Utils.getID(hollowLogsVertical.blocks.get(w))),
                                Utils.copyPropertySafe(w.log))
                )
                .requiresChildren("stripped_log") //REASON: Textures
                .addTag(modRes("hollow_logs_climbable"), Registries.BLOCK)
                .noItem().noTab() //REASON: it's using the hollowLogsVertical's tab/item as the main
                .setRenderType(RenderLayer.CUTOUT_MIPPED)
                .build();
        this.addEntry(hollowLogsClimbable);

    }

    @NotNull
    private static Supplier<WoodType> getBirch() {
        return () -> WoodTypeRegistry.getValue(ResourceLocation.parse("birch"));
    }

    @Override
    public void registerItems(Registrator<Item> registry) {
        super.registerItems(registry);
        hollowLogsVertical.blocks.forEach((w, b) -> {
            String itemName = Utils.getID(b).getPath().replace("_vertical", "");
            String childKey = this.getModId() + ":hollow_log";
            Item i = new HollowLogItem(
                    DeferredHolder.create(Registries.BLOCK, EveryCompat.res(itemName + "_horizontal")),
                    DeferredHolder.create(Registries.BLOCK, Utils.getID(b)),
                    DeferredHolder.create(Registries.BLOCK, EveryCompat.res(itemName + "_climbable")),
                    new Item.Properties());
            hollowLogsVertical.items.put(w, i);
            w.addChild(childKey, i);
            registry.register(EveryCompat.res(itemName), i);
        });
    }

    @Override
    public void registerBlockColors(ClientHelper.BlockColorEvent event) {
        event.register(
                (s, l, pos, i) -> s.getValue(ClimbableHollowLogBlock.VARIANT) != HollowLogVariants.Climbable.VINE ? -1 :
                        l != null && pos != null ?
                                BiomeColors.getAverageFoliageColor(l, pos) : FoliageColor.getDefaultColor(),
                hollowLogsClimbable.blocks.values().toArray(Block[]::new));
        event.register(
                (s, l, pos, i) -> l != null && pos != null ?
                        BiomeColors.getAverageGrassColor(l, pos) : -1,
                hollowLogsHorizontal.blocks.values().toArray(Block[]::new));
    }

}
