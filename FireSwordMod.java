package dev.firesword;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class FireSwordMod implements ModInitializer {

    public static final String MOD_ID = "firesword";

    public static final ToolMaterial FIRE_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            3000,
            9.0f,
            8.0f,
            15,
            ItemTags.NETHERITE_TOOL_MATERIALS);

    public static final ResourceKey<Item> FIRE_SWORD_KEY =
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, "fire_sword"));

    public static final Item FIRE_SWORD = Registry.register(
            BuiltInRegistries.ITEM,
            FIRE_SWORD_KEY,
            new Item(new Item.Properties()
                    .setId(FIRE_SWORD_KEY)
                    .sword(FIRE_MATERIAL, 3.0f, -2.4f)));

    private static final float BURN_SECONDS = 8.0f;

    @Override
    public void onInitialize() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (blocked) return;
            if (source.getEntity() instanceof LivingEntity attacker
                    && attacker.getMainHandItem().is(FIRE_SWORD)) {
                entity.igniteForSeconds(BURN_SECONDS);
            }
        });
    }
}
