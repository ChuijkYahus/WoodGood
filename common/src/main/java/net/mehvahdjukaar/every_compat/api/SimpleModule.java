package net.mehvahdjukaar.every_compat.api;

import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.dynamicpack.ClientDynamicResourcesHandler;
import net.mehvahdjukaar.every_compat.dynamicpack.ServerDynamicResourcesHandler;
import net.mehvahdjukaar.every_compat.type.StoneType;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleModule extends CompatModule {

    private final String shortId;
    private final Map<String, EntrySet<?>> entries = new LinkedHashMap<>();

    protected int bloat = 0;

    public SimpleModule(String modId, String shortId) {
        super(modId);
        this.shortId = shortId;
    }

    @Override
    public int bloatAmount() {
        return bloat;
    }

    public <T extends BlockType, S extends EntrySet<T>> S addEntry(S entryHolder) {
        var old = this.entries.put(entryHolder.getName(), entryHolder);
        if (old != null) {
            throw new UnsupportedOperationException(String.format("This module already has an entry set with name %s", entryHolder.getName()));
        }
        EveryCompat.addEntryType(entryHolder.getTypeClass(), entryHolder.getChildKey(this));
        return entryHolder;
    }

    public Collection<EntrySet<?>> getEntries() {
        return entries.values();
    }

    public EntrySet<?> getEntry(String name) {
        var e = entries.get(name);
        if (e == null)
            throw new UnsupportedOperationException(String.format("This module does not have entries of type %s", name));
        return e;
    }

    @Override
    public String shortenedId() {
        return shortId;
    }

    @Override
    public void addTranslations(ClientDynamicResourcesHandler clientDynamicResourcesHandler, AfterLanguageLoadEvent lang) {
        getEntries().forEach(e -> e.addTranslations(this, lang));
    }

    @Override
    public void registerWoodBlocks(Registrator<Block> registry, Collection<WoodType> woodTypes) {
        getEntries().forEach(e -> e.registerWoodBlocks(this, registry, woodTypes));
        AtomicInteger blockCount = new AtomicInteger();
        getEntries().forEach(e -> {
            blockCount.addAndGet(e.getBlockCount());
        });
        bloat += blockCount.get();
        if (blockCount.get() > 0)
            EveryCompat.LOGGER.info("{}: registered {} wood blocks", this, blockCount.get());
    }

    @Override
    public void registerLeavesBlocks(Registrator<Block> registry, Collection<LeavesType> leavesTypes) {
        getEntries().forEach(e -> e.registerLeavesBlocks(this, registry, leavesTypes));
        AtomicInteger blockCount = new AtomicInteger();
        getEntries().forEach(e -> {
            blockCount.addAndGet(e.getBlockCount());
        });
        bloat += blockCount.get();
        if (blockCount.get() > 0)
            EveryCompat.LOGGER.info("{}: registered {} leaves blocks", this, blockCount.get());
    }

    @Override
    public void registerStonesBlocks(Registrator<Block> registry, Collection<StoneType> leavesTypes) {
        getEntries().forEach(e -> e.registerStonesBlocks(this, registry, leavesTypes));
        AtomicInteger blockCount = new AtomicInteger();
        getEntries().forEach(e -> {
            blockCount.addAndGet(e.getBlockCount());
        });
        bloat += blockCount.get();
        if (blockCount.get() > 0)
            EveryCompat.LOGGER.info("{}: registered {} stone blocks", this, blockCount.get());
    }

    @Override
    public void registerItems(Registrator<Item> registry) {
        getEntries().forEach(e -> e.registerItems(this, registry));
    }

    @Override
    public void registerTiles(Registrator<BlockEntityType<?>> registry) {
        getEntries().forEach(e -> e.registerTiles(this, registry));
    }

    @Override
    public void addDynamicServerResources(ServerDynamicResourcesHandler handler, ResourceManager manager) {
        getEntries().forEach(e -> {
            e.generateLootTables(this, handler.dynamicPack, manager);
            e.generateRecipes(this, handler.dynamicPack, manager);
            e.generateTags(this, handler.dynamicPack, manager);
        });
    }

    @Override
    public void addDynamicClientResources(ClientDynamicResourcesHandler handler, ResourceManager manager) {
        getEntries().forEach(e -> {
            try {
                e.generateModels(this, handler, manager);
            } catch (Exception ex) {
                EveryCompat.LOGGER.error("Failed to generate models for entry set {}:", e, ex);
                if (PlatHelper.isDev()) throw ex;
            }
        });
        getEntries().forEach(e -> {
            try {
                e.generateTextures(this, handler, manager);
            } catch (Exception ex) {
                EveryCompat.LOGGER.error("Failed to generate textures for entry set {}:", e, ex);
                if (PlatHelper.isDev()) throw ex;
            }
        });
    }

    @Override
    public void registerBlockColors(ClientHelper.BlockColorEvent event) {
        getEntries().forEach(e -> e.registerBlockColors(event));
    }

    @Override
    public void registerItemColors(ClientHelper.ItemColorEvent event) {
        getEntries().forEach(e -> e.registerItemColors(event));
    }

    @Override
    public void onClientSetup() {
        getEntries().forEach(EntrySet::setRenderLayer);
    }

    @Override
    public void onModSetup() {
        getEntries().forEach(EntrySet::setupExistingTiles);
    }

    @Override
    public void registerBlockEntityRenderers(ClientHelper.BlockEntityRendererEvent event) {
        getEntries().forEach(e -> e.registerEntityRenderers(this, event));
    }

    public static void appendTileEntityBlocks(BlockEntityType<?> be, Collection<? extends Block> blocks) {
        be.validBlocks = new HashSet<>(be.validBlocks);
        be.validBlocks.addAll(blocks);
    }

    @Override
    public void registerItemsToExistingTabs(RegHelper.ItemToTabEvent event) {
        getEntries().forEach(e -> e.registerItemsToExistingTabs(this, event));
    }

    @Override
    public <T extends BlockType> List<Item> getAllItemsOfType(T type) {
        List<Item> l = new ArrayList<>();
        for (EntrySet<?> entrySet : entries.values()) {
            if (entrySet.getTypeClass().isAssignableFrom(type.getClass())) {
                var itemOfType = ((EntrySet<T>) entrySet).getItemForECTab(type);
                if (itemOfType != null && itemOfType != Items.AIR) l.add(itemOfType);
            }
        }
        return l;
    }


    public static BlockBehaviour.Properties addWoodProp(WoodType w, BlockBehaviour.Properties p) {
        if (w.canBurn()) p.ignitedByLava();
        p.mapColor(w.planks.defaultMapColor()).sound(w.getSound()).instrument(NoteBlockInstrument.BASS);
        return p;
    }

    public static BlockBehaviour.Properties addWoodPropNoFire(WoodType w, BlockBehaviour.Properties p) {
        p.mapColor(w.planks.defaultMapColor()).sound(w.getSound()).instrument(NoteBlockInstrument.BASS);
        return p;
    }

}
