package com.bagel.buzzierbees.common;

import com.bagel.buzzierbees.common.blocks.ModBlocks;
import com.bagel.buzzierbees.common.items.CureItem;
import com.bagel.buzzierbees.common.items.HoneyWandItem;

import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bagel.buzzierbees.common.blocks.ModTileEntities;
import com.bagel.buzzierbees.common.entities.ModEntities;
import com.bagel.buzzierbees.common.items.ModItems;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("buzzierbees")
public class BuzzierBees
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "buzzierbees";

    public BuzzierBees() {
    	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	ModTileEntities.TILE_ENTITY_TYPES.register(modEventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        addBrewingRecipes();
    }

    private void addBrewingRecipes() {
		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(Items.field_226638_pX_),
				Ingredient.fromItems(Items.SUGAR),
				new ItemStack(ModItems.CRYSTALLIZED_HONEY));
		
		//temporary Clover Honey recipe (until we get hive situation sorted out)
		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(Items.field_226638_pX_),
				Ingredient.fromItems(ModItems.CLOVER_HONEY_BOTTLE),
				new ItemStack(ModItems.CLOVER_HONEY_BOTTLE));
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(ModItems.CLOVER_HONEY_BOTTLE),
				Ingredient.fromItems(Items.POPPED_CHORUS_FRUIT),
				CureItem.getCure(new ItemStack(ModItems.CURE), null));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.SUGAR),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.SPEED));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.RABBIT_FOOT),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.JUMP_BOOST));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.BLAZE_POWDER),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.STRENGTH));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.SPIDER_EYE),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.POISON));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.GHAST_TEAR),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.REGENERATION));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.MAGMA_CREAM),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.FIRE_RESISTANCE));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.PUFFERFISH),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.WATER_BREATHING));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.GOLDEN_CARROT),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.NIGHT_VISION));

		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(CureItem.getCure(new ItemStack(ModItems.CURE), null).getItem()),
				Ingredient.fromItems(Items.PHANTOM_MEMBRANE),
				CureItem.getCure(new ItemStack(ModItems.CURE), Effects.SLOW_FALLING));
	}
    
    @OnlyIn(Dist.CLIENT)
    private void setupClient(final FMLClientSetupEvent event) {
    	ModItems.HONEY_WAND.addPropertyOverride(new ResourceLocation(MODID, "sticky"),
                (stack, world, entity) -> {
                	CompoundNBT nbt = stack.getOrCreateTag(); 
    				return entity != null && nbt.getBoolean(HoneyWandItem.STICKY_KEY) ? 1.0F : 0.0F;
                });
    	
    	ModEntities.registerRendering();
    	
    	RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTALLIZED_HONEY_BLOCK,RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(ModBlocks.CLOVER_HONEY_BLOCK,RenderType.func_228645_f_());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.WHITE_CLOVER,RenderType.func_228641_d_());
        RenderTypeLookup.setRenderLayer(ModBlocks.PINK_CLOVER,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.CARTWHEEL,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.VIOLET,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.COLUMBINE,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.JOLYCE,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLUEBELL,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.DAYBLOOM,RenderType.func_228641_d_());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_WHITE_CLOVER,RenderType.func_228641_d_());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_PINK_CLOVER,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_CARTWHEEL,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_VIOLET,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_COLUMBINE,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_JOLYCE,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_BLUEBELL,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_DAYBLOOM,RenderType.func_228641_d_());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.BIRD_OF_PARADISE,RenderType.func_228641_d_());

		RenderTypeLookup.setRenderLayer(ModBlocks.HIVE_DOOR,RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(ModBlocks.HIVE_TRAPDOOR,RenderType.func_228645_f_());

        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    /*private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }*/
}
