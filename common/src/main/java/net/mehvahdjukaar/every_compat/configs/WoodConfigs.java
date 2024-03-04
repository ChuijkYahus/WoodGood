package net.mehvahdjukaar.every_compat.configs;

import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.world.level.ItemLike;

import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

//loaded after registry
public class WoodConfigs {

    private static final Map<Class<? extends BlockType>, Map<String, Supplier<Boolean>>> BLOCK_TYPE_CONFIGS = new HashMap<>();
    private static final Map<Class<? extends BlockType>, Map<String, Supplier<Boolean>>> CHILD_CONFIGS = new HashMap<>();

    public static ConfigSpec SPEC;

    public static void init() {
        String ss = PlatformHelper.isDev() && PlatformHelper.getEnv().isServer() ? "_s" : "";

        ConfigBuilder builder = ConfigBuilder.create(EveryCompat.res("wood_types" + ss), ConfigType.COMMON);

        builder.push("types");
        for (var reg : BlockSetAPI.getRegistries()) {
            builder.push(reg.typeName().replace(" ", "_"));
            for (var w : reg.getValues()) {
                if (!w.isVanilla()) {
                    String key = w.toString().replace(":", ".");
                    var config = builder.define(key, true);
                    var map = BLOCK_TYPE_CONFIGS.computeIfAbsent(reg.getType(), s -> new HashMap<>());
                    map.put(w.toString(), config);
                }
            }
            builder.pop();
        }
        builder.pop();

        builder.push("entries");
        for (var reg : BlockSetAPI.getRegistries()) {
            if(reg.getType() == WoodType.class || reg.getType() == LeavesType.class) {
                builder.push(reg.typeName().replace(" ", "_"));
                for (var c : EveryCompat.ENTRY_TYPES.getOrDefault(reg.getType(), Set.of())) {
                    String key = c.replace(":", ".");
                    var config = builder.define(key, true);
                    var map = CHILD_CONFIGS.computeIfAbsent(reg.getType(), s -> new HashMap<>());
                    map.put(c, config);
                }
                builder.pop();
            }
        }
        builder.pop();

        SPEC = builder.buildAndRegister();

        SPEC.loadFromFile(); //manually load late
    }

    public static <T extends BlockType> boolean isEntryEnabled(T w, Object o) {
        return isTypeEnabled(w, w.getChildKey(o));
    }

    public static <T extends BlockType> boolean isEntryEnabled(Class<T> typeClass, Object o) {
        var w = BlockSetAPI.getBlockTypeOf((ItemLike) o, typeClass);
        return isTypeEnabled(w, w.getChildKey(o));
    }

    public static <T extends BlockType> boolean isTypeEnabled(T w) {
        return isTypeEnabled(w, null);
    }

    public static <T extends BlockType> boolean isTypeEnabled(T w, @Nullable String childType) {
        try {
            if (childType != null && !CHILD_CONFIGS.get(w.getClass()).getOrDefault(childType, () -> true).get())
                return false;
            return BLOCK_TYPE_CONFIGS.get(w.getClass()).get(w.getId().toString()).get();
        } catch (Exception ignored) {
        }
        return true;
    }

}