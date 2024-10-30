package net.mehvahdjukaar.every_compat.modules.neoforge.create;

import com.simibubi.create.CreateClient;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.WindowBlock;
import com.simibubi.create.foundation.block.connected.*;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.every_compat.dynamicpack.ClientDynamicResourcesHandler;
import net.mehvahdjukaar.every_compat.dynamicpack.ServerDynamicResourcesHandler;
import net.mehvahdjukaar.every_compat.type.StoneTypeRegistry;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Objects;

// SUPPORT: v0.5.1+
public class CreateModule extends SimpleModule {

//    public final SimpleEntrySet<WoodType, Block> windows;
    public final SimpleEntrySet<WoodType, Block> windowPanes;
    //public final SimpleEntrySet<StoneType, Block> cut_andesite;
    //public final SimpleEntrySet<StoneType, Block> cut_andesite_stairs;
    //public final SimpleEntrySet<StoneType, Block> cut_andesite_slab;


    public CreateModule(String modId) {
        super(modId, "c");
        var tab = CreativeModeTabs.BUILDING_BLOCKS;

/*
        windows = SimpleEntrySet.builder(WoodType.class, "window",
                        getModBlock("oak_window"), () -> WoodTypeRegistry.OAK_TYPE, //AllPaletteBlocks.OAK_WINDOW
                        this::makeWindow)
                .addTag(BlockTags.IMPERMEABLE, Registries.BLOCK)
                .setTabKey(tab)
                .defaultRecipe()
                .setRenderType(RenderLayer.CUTOUT_MIPPED)
                .createPaletteFromOak(p -> p.remove(p.getDarkest()))
                .addTextureM(modRes("block/palettes/oak_window"), EveryCompat.res("block/palettes/oak_window_m"))
                .addTextureM(modRes("block/palettes/oak_window_connected"), EveryCompat.res("block/palettes/oak_window_connected_m"))
                .build();
        this.addEntry(windows);
*/

        windowPanes = SimpleEntrySet.builder(WoodType.class, "window_pane",
                        getModBlock("oak_window_pane"), () -> WoodTypeRegistry.OAK_TYPE, //AllPaletteBlocks.OAK_WINDOW_PANE
                        s -> new ConnectedGlassPaneBlock(Utils.copyPropertySafe(Blocks.GLASS_PANE)))
//                .addTag(Tags.Items.GLASS_PANES, Registries.BLOCK)
                .setTabKey(tab)
                .defaultRecipe()
                .setRenderType(RenderLayer.CUTOUT_MIPPED)
                .build();
        this.addEntry(windowPanes);

/*
        cut_andesite = SimpleEntrySet.builder(StoneType.class, "", "cut",
                        getModBlock("cut_andesite"), () -> StoneTypeRegistry.getValue(ResourceLocation.parse("andesite")),
                        stoneType -> new Block(Utils.copyPropertySafe(stoneType.stone)))
                .addTexture(modRes("block/palettes/stone_types/cut/andesite_cut"))
//                .setTabKey(() -> CreativeModeTabs.BUILDING_BLOCKS)
//                .defaultRecipe()
                .build();
        this.addEntry(cut_andesite);

        cut_andesite_stairs = SimpleEntrySet.builder(StoneType.class, "slab", "cut",
                        getModBlock("cut_andesite_slab"), () -> StoneTypeRegistry.getValue(ResourceLocation.parse("andesite")),
                        stoneType -> new StairBlock(stoneType.stone.defaultBlockState(), Utils.copyPropertySafe(stoneType.stone)))
                //TEXTURES: Using cut_andesite's from above
//                .setTabKey(() -> CreativeModeTabs.BUILDING_BLOCKS)
                .setRenderType(RenderLayer.CUTOUT_MIPPED)
//                .defaultRecipe()
                .build();

        this.addEntry(cut_andesite_stairs);

        cut_andesite_slab = SimpleEntrySet.builder(StoneType.class, "stairs", "cut",
                        getModBlock("cut_andesite_stairs"), () -> StoneTypeRegistry.getValue(ResourceLocation.parse("andesite")),
                        stoneType -> new SlabBlock(Utils.copyPropertySafe(stoneType.stone)))
                //TEXTURES: Using cut_andesite's from above
//                .setTabKey(() -> CreativeModeTabs.BUILDING_BLOCKS)
                .setRenderType(RenderLayer.CUTOUT_MIPPED)
//                .defaultRecipe()
                .build();
        this.addEntry(cut_andesite_slab);
*/

    }

    private WindowBlock makeWindow(WoodType w) {
        return new WindowBlock(Utils.copyPropertySafe(Blocks.GLASS)
                .isValidSpawn((s, l, ps, t) -> false).isRedstoneConductor((s, l, ps) -> false)
                .isSuffocating((s, l, ps) -> false).isViewBlocking((s, l, ps) -> false), false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onClientSetup() {
        super.onClientSetup();
/*        windows.blocks.forEach((w, b) -> {
            String path = "block/" + shortenedId() + "/" + w.getNamespace() + "/palettes/" + w.getTypeName() + "_window";

            CTSpriteShiftEntry spriteShift = CTSpriteShifter.getCT(AllCTTypes.VERTICAL,
                    EveryCompat.res(path), EveryCompat.res(path + "_connected"));

            CreateClient.MODEL_SWAPPER.getCustomBlockModels().register(Utils.getID(b),
                    (model) -> new CTModel(model, new HorizontalCTBehaviour(spriteShift)));
            CreateClient.MODEL_SWAPPER.getCustomBlockModels().register(Utils.getID(windowPanes.blocks.get(w)),
                    (model) -> new CTModel(model, new GlassPaneCTBehaviour(spriteShift)));
        });*/
    }

    @Override
    // Recipes
    public void addDynamicServerResources(ServerDynamicResourcesHandler handler, ResourceManager manager) {
        super.addDynamicServerResources(handler, manager);
        if (!PlatHelper.isModLoaded("sawmill")) {
            for (WoodType w : WoodTypeRegistry.getTypes()) {
                if (w.getBlockOfThis("slab") != null)
                    sawRecipe(2, w.planks.asItem(), Objects.requireNonNull(w.getBlockOfThis("slab")).asItem(),null,  w, handler);

                if (w.getBlockOfThis("stairs") != null)
                    sawRecipe(1, w.planks.asItem(), Objects.requireNonNull(w.getBlockOfThis("stairs")).asItem(),null,  w, handler);

                sawRecipe(6, w.planks.asItem(), null, ResourceLocation.withDefaultNamespace("stick"), w, handler);
            }
        }

    }

    public void sawRecipe(int amount, Item input, Item output, ResourceLocation item, WoodType woodType, ServerDynamicResourcesHandler handler) {
        String blank = """
            {
                "type": "minecraft:stonecutting",
                "count": [amount],
                "ingredient": {
                    "item": "[input]"
                },
                "result": "[output]"
            }
            """;

        ResourceLocation resloc;
        String recipe;
        if (output != null) {
            recipe = blank.replace("[amount]", String.valueOf(amount))
                    .replace("[input]", Utils.getID(input).toString())
                    .replace("[output]", Utils.getID(output).toString());
            resloc = EveryCompat.res(
                    shortenedId() + "/" + woodType.getNamespace() + "/" + output + "_from_" + input + "_stonecutting");
        }
        else { // item != null
            recipe = blank.replace("[amount]", String.valueOf(amount))
                    .replace("[input]", Utils.getID(input).toString())
                    .replace("[output]", item.toString());
            resloc = EveryCompat.res(
                    shortenedId() + "/" + woodType.getNamespace() + "/" + item.getPath() + "_from_" + input + "_stonecutting");
        }

        handler.dynamicPack.addBytes(resloc, recipe.getBytes(), ResType.RECIPES);

    }
}