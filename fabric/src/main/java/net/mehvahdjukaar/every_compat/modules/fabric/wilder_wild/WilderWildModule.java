package net.mehvahdjukaar.every_compat.modules.fabric.wilder_wild;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.lib.axe.api.AxeBehaviors;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.entity.TermiteManager;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.every_compat.dynamicpack.ServerDynamicResourcesHandler;
import net.mehvahdjukaar.every_compat.misc.SpriteHelper;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.SimpleTagBuilder;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//SUPPORT: v2.0.7-FINAL
public class WilderWildModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> hollow_log;
    public final SimpleEntrySet<WoodType, Block> stripped_hollow_log;

    public WilderWildModule(String modId) {
        super(modId, "ww");
        var tab = CreativeModeTab.TAB_BUILDING_BLOCKS;

        hollow_log = SimpleEntrySet.builder(WoodType.class, "log", "hollowed",
                        () -> RegisterBlocks.HOLLOWED_OAK_LOG, () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new HollowedLogBlock(Utils.copyPropertySafe(RegisterBlocks.HOLLOWED_OAK_LOG))
                )
                .requiresChildren("stripped_log") // Textures
                .createPaletteFromChild(pallete -> {}, "log", SpriteHelper.LOOKS_LIKE_SIDE_LOG_TEXTURE)
                .addTexture(modRes("block/hollowed_oak_log"))
                .addTexture(modRes("block/hollowed_oak_log_top"))
                // TEXTURE: using stripped_hollowed_oak_log from below
                .setRenderType(() -> RenderType::cutout)
                .setTab(() -> tab)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.LOGS_THAT_BURN, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.LOGS, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.PARROTS_SPAWNABLE_ON, Registry.BLOCK_REGISTRY)
                .addTag(modRes("hollowed_logs"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("splits_coconut"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("hollowed_logs_that_burn"), Registry.BLOCK_REGISTRY)
                //TAG: wilderwild:hollowed_<type>_logs
                .addTag(modRes("hollowed_logs"), Registry.ITEM_REGISTRY)
                .addTag(modRes("hollowed_logs_that_burn"), Registry.ITEM_REGISTRY)
                .addTag(ItemTags.LOGS_THAT_BURN, Registry.ITEM_REGISTRY)
                .addTag(ItemTags.LOGS, Registry.ITEM_REGISTRY)
                .addTag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL, Registry.ITEM_REGISTRY)
                .addRecipe(modRes("oak_wood_from_hollowed"))
                // Below: Both logs doesn't have the standard 16x16, take a look. You'll get why. It will be excluded
                .addCondition(w -> !(w.getId().toString().equals("terrestria:sakura") ||
                        w.getId().toString().equals("terrestria:yucca_palm")))
                .build();
        this.addEntry(hollow_log);

        stripped_hollow_log = SimpleEntrySet.builder(WoodType.class, "log", "stripped_hollowed",
                        () -> RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG, () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new HollowedLogBlock(Utils.copyPropertySafe(RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG))
                )
                .requiresChildren("stripped_log") // Textures
                .createPaletteFromChild(pallete -> {}, "stripped_log", SpriteHelper.LOOKS_LIKE_SIDE_LOG_TEXTURE)
                .addTexture(modRes("block/stripped_hollowed_oak_log"))
                .addTexture(modRes("block/stripped_hollowed_oak_log_top"))
                .setRenderType(() -> RenderType::cutout)
                .setTab(() -> tab)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.LOGS_THAT_BURN, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.LOGS, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.PARROTS_SPAWNABLE_ON, Registry.BLOCK_REGISTRY)
                .addTag(modRes("hollowed_logs"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("hollowed_logs_that_burn"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("stripped_hollowed_logs_that_burn"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("termite_breakable"), Registry.BLOCK_REGISTRY)
                .addTag(WilderBlockTags.STRIPPED_HOLLOWED_LOGS, Registry.BLOCK_REGISTRY)
                .addTag(WilderBlockTags.SPLITS_COCONUT, Registry.BLOCK_REGISTRY)
                //TAG: wilderwild:hollowed_<type>_logs
                .addTag(modRes("hollowed_logs"), Registry.ITEM_REGISTRY)
                .addTag(modRes("hollowed_logs_that_burn"), Registry.ITEM_REGISTRY)
                .addTag(ItemTags.LOGS_THAT_BURN, Registry.ITEM_REGISTRY)
                .addTag(ItemTags.LOGS, Registry.ITEM_REGISTRY)
                .addTag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL, Registry.ITEM_REGISTRY)
                .addRecipe(modRes("stripped_oak_wood_from_hollowed"))
                // Below: Both logs doesn't have the standard 16x16, take a look. You'll get why. It will be excluded
                .addCondition(w -> !(w.getId().toString().equals("terrestria:sakura") ||
                        w.getId().toString().equals("terrestria:yucca_palm")))
                .build();
        this.addEntry(stripped_hollow_log);

    }

    @Override
    public void onModSetup() {
        super.onModSetup();

        hollow_log.blocks.forEach((wood, block) -> {
            if (wood.getBlockOfThis("stripped_log") != null) {
                StrippableBlockRegistry.register(block, stripped_hollow_log.blocks.get(wood));

                boolean isStem = getLogName(wood.log).contains("stem");

                AxeBehaviors.AXE_BEHAVIORS.put(wood.log, (context, level, pos, state, face, horizontal) ->
                        HollowedLogBlock.hollow(level, pos, state, face, hollow_log.blocks.get(wood), isStem));

                AxeBehaviors.AXE_BEHAVIORS.put(wood.getBlockOfThis("stripped_log"), (context, level, pos, state, face, horizontal) ->
                        HollowedLogBlock.hollow(level, pos, state, face, stripped_hollow_log.blocks.get(wood), isStem));

                TermiteManager.Termite.addDegradable(block, stripped_hollow_log.blocks.get(wood));
            }
        });
    }

    private String getLogName(Block block) {
        String[] split = block.getDescriptionId().split("\\.");
        return split[2];
    }

    @Override
    // Recipes & Tags
    public void addDynamicServerResources(ServerDynamicResourcesHandler handler, ResourceManager manager) {
        super.addDynamicServerResources(handler, manager);

        hollow_log.blocks.forEach((wood, block) -> {
            if (wood.getBlockOfThis("stripped_log") != null) {
                // Variables
                ResourceLocation recipeLoc = ResType.RECIPES.getPath( "wilderwild:oak_planks_from_hollowed");
                ResourceLocation tagRLoc = EveryCompat.res(shortenedId() + "/" + wood.getNamespace() + "/" + "hollowed_" +
                        wood.getTypeName() + "_logs");
                ResourceLocation logTagRLoc = EveryCompat.res(shortenedId() + "/" + wood.getAppendableId() + "_logs");
                ResourceLocation newRecipeLoc = EveryCompat.res(wood.getTypeName() + "_planks_from_hollowed");
                boolean isTagEmpty = true;

                Block strippedBlock = stripped_hollow_log.blocks.get(wood);

                // TAGS ================================================================================================
                SimpleTagBuilder tagBuilder = SimpleTagBuilder.of(tagRLoc);
                SimpleTagBuilder logTagBuilder = SimpleTagBuilder.of(logTagRLoc);

                // Adding to tag's list
                if (block != null) {
                    tagBuilder.addEntry(block);
                    tagBuilder.addEntry(strippedBlock);

                    logTagBuilder.addEntry(block);
                    logTagBuilder.addEntry(strippedBlock);
                    isTagEmpty = false;
                }

                // Adding to the resources
                if (!isTagEmpty) {
                    handler.dynamicPack.addTag(tagBuilder, Registry.BLOCK_REGISTRY);
                    handler.dynamicPack.addTag(tagBuilder, Registry.ITEM_REGISTRY);

                    handler.dynamicPack.addTag(logTagBuilder, Registry.BLOCK_REGISTRY);
                    handler.dynamicPack.addTag(logTagBuilder, Registry.ITEM_REGISTRY);
                }

                // RECIPE ==============================================================================================
                try (InputStream recipeStream = manager.getResource(recipeLoc)
                        .orElseThrow(() -> new FileNotFoundException("ResourceLocation: " + recipeLoc)).open()) {
                    JsonObject recipe = RPUtils.deserializeJson(recipeStream);

                    // Editing the recipe
                    recipe.getAsJsonArray("ingredients").get(0).getAsJsonObject()
                            .addProperty("tag", tagRLoc.toString());

                    recipe.getAsJsonObject("result")
                            .addProperty("item", Utils.getID(wood.planks).toString());

                    // Adding to the resources
                    if (isTagEmpty) {
                        handler.dynamicPack.addJson(newRecipeLoc, recipe, ResType.RECIPES);
                    }

                } catch (IOException e) {
                    handler.getLogger().error("Failed to open the recipe file: ", e);
                }
            }
        });

    }
}