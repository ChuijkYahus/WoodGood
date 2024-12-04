package net.mehvahdjukaar.every_compat.api;

import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.dynamicpack.ServerDynamicResourcesHandler;

import static net.mehvahdjukaar.every_compat.EveryCompat.MODULE_DISABLER;

/**
 * Use this to register new wood type blocks and module
 * To register wood types that aren't detected reference net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
 */
public class EveryCompatAPI {

    /**
     * Register a new compat module for your modded blocks
     *
     * @param module your module instance. Can be a custom implementation
     */
    public static synchronized void registerModule(CompatModule module) {
        if (MODULE_DISABLER.isModuleOn(module.modId)) { //maybe turn into supplier
            EveryCompat.ACTIVE_MODULES.add(module);
            EveryCompat.DEPENDENCIES.add(module.modId);
            EveryCompat.DEPENDENCIES.addAll(module.getAlreadySupportedMods());
            ServerDynamicResourcesHandler.INSTANCE.getPack().addNamespaces(module.getModId());
        }
    }

    //for each entry that you register you will need to add "block_type.everycomp.your_type" translation string to your lang file

    //example using simple module class
    /*

        SimpleModule mod = new SimpleModule("twigs", "tw");
        SimpleEntrySet<?, ?> e = SimpleEntrySet.builder(WoodType.class,"table", TwigsBlocks.OAK_TABLE, ()->WoodTypeRegistry.OAK_TYPE,
                        w -> new TableBlock(Utils.copyPropertySafe(w.planks).instabreak()))
                .addTag(ResourceLocation.parse("twigs:tables"), Registries.BLOCK)
                .useLootFromBase()
                .setTab(()->Twigs.ITEM_GROUP)
                .addTexture(ResourceLocation.parse("twigs:block/oak_table"))
                .addTexture(ResourceLocation.parse("twigs:block/oak_table_top"))
                .addTexture(ResourceLocation.parse("twigs:block/oak_table_bottom"))
                .build();
        mod.addEntry(e);

        WoodGoodAPI.registerModule(mod)

     */


    //register a custom non-detected wood type

    /*
    public static void init() {
        BlockSetAPI.addBlockTypeFinder(WoodType.class, WoodType.Finder
            .simple("my_mod", "cherry", "cherry_plank", "cherry_stem"));
    }
    */


}
