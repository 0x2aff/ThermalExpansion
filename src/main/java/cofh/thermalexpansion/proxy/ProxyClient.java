package cofh.thermalexpansion.proxy;

import codechicken.lib.block.IParticleProvider;
import codechicken.lib.model.DummyBakedModel;
import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.model.blockbakery.BlockBakery;
import codechicken.lib.model.blockbakery.CCBakeryModel;
import codechicken.lib.model.blockbakery.IBlockStateKeyGenerator;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.texture.TextureUtils.IIconRegister;
import cofh.core.render.IconRegistry;
import cofh.core.render.RenderItemModular;
import cofh.thermalexpansion.block.CommonProperties;
import cofh.thermalexpansion.init.TEBlocksOld;
import cofh.thermalexpansion.init.TEItemsOld;
import cofh.thermalexpansion.item.TEAugments;
import cofh.thermalexpansion.item.TEFlorbs;
import cofh.thermalexpansion.item.tool.ItemToolBase;
import cofh.thermalexpansion.render.RenderDynamo;
import cofh.thermalexpansion.render.RenderFrame;
import cofh.thermalexpansion.render.entity.RenderEntityFlorb;
import cofh.thermalexpansion.render.item.ModelFlorb;
import cofh.thermalexpansion.render.item.SchematicBakedModel;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProxyClient extends Proxy {

	public static RenderItemModular rendererComponent = new RenderItemModular();

	private static DummyBakedModel DUMMY_MODEL = new DummyBakedModel();

	@Override
	public void registerRenderInformation() {

		//MinecraftForgeClient.registerItemRenderer(TEItems.itemDiagram, rendererComponent);
		//MinecraftForgeClient.registerItemRenderer(TEFlorbs.itemFlorb, rendererFlorb);

		//ItemRenderRegistry.addItemRenderer(TEItems.diagramSchematic, RenderSchematic.instance);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		//RenderStrongbox.registerRenderers();

		ModelLoaderRegistry.registerLoader(ModelFlorb.LoaderFlorb.INSTANCE);

		TEAugments.itemAugment.registerModelVariants();
		//TEItemsOld.itemCapacitor.registerModelVariants();

		registerToolModel(TEItemsOld.itemChiller, "chiller");
		registerToolModel(TEItemsOld.itemIgniter, "igniter");
		registerModedToolModel(TEItemsOld.itemPump, "pump");
		registerModedToolModel(TEItemsOld.itemTransfuser, "transfuser");

		//		final int accessCount = ISecurable.AccessMode.values().length;
		//		final ModelResourceLocation[] satchelLocations = new ModelResourceLocation[ItemSatchel.NAMES.length * accessCount];
		//		for (int meta = 0; meta < ItemSatchel.NAMES.length; meta++) {
		//			for (int access = 0; access < accessCount; access++) {
		//				satchelLocations[meta * accessCount + access] = getSatchelLocation(meta, ISecurable.AccessMode.values()[access]);
		//			}
		//		}
		//
		//		ModelLoader.setCustomMeshDefinition(TEItemsOld.itemSatchel, new ItemMeshDefinition() {
		//			@Override
		//			public ModelResourceLocation getModelLocation(ItemStack stack) {
		//
		//				return satchelLocations[stack.getMetadata() * accessCount + SecurityHelper.getAccess(stack).ordinal()];
		//			}
		//		});
		//		ModelLoader.registerItemVariants(TEItemsOld.itemSatchel, satchelLocations);

		ModelLoader.setCustomModelResourceLocation(TEFlorbs.itemFlorb, 0, ModelFlorb.MODEL_LOCATION);
		ModelLoader.setCustomModelResourceLocation(TEFlorbs.itemFlorb, 1, ModelFlorb.MAGMATIC_MODEL_LOCATION);

		ModelRegistryHelper.register(getDiagramLocation("schematic_override"), new SchematicBakedModel());

		//		ModelLoader.setCustomStateMapper(TEBlocksOld.blockGlass, new StateMapperBase() {
		//			@Override
		//			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		//
		//				return new ModelResourceLocation("thermalexpansion:glass", "type=" + state.getValue(BlockGlass.TYPES).getName());
		//			}
		//		});
		//		ModelLoader.setCustomStateMapper(TEBlocksOld.blockRockwool, new StateMapperBase() {
		//			@Override
		//			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		//
		//				return new ModelResourceLocation("thermalexpansion:rockwool", "color=" + state.getValue(BlockRockwool.COLOR).getName());
		//			}
		//		});
		//		ModelLoader.setCustomStateMapper(TEBlocksOld.blockWorkbench, new StateMapperBase() {
		//			@Override
		//			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		//
		//				return new ModelResourceLocation("thermalexpansion:workbench", "type=" + state.getValue(BlockWorkbench.TYPES).getName());
		//			}
		//		});

		//		registerBlockToBakery(TEBlocksOld.blockMachine, BlockMachine.Types.values());
		//		registerBlockToBakery(TEBlocksOld.blockDevice, BlockDevice.Types.values());
		//		registerBlockToBakery(TEBlocksOld.blockDynamo, RenderDynamo.instance, BlockDynamo.Types.values());
		//		registerBlockToBakery(TEBlocksOld.blockFrame, RenderFrame.instance, BlockFrame.Types.values());

		registerModelKeyGenerators();

		//ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(TEBlocksOld.blockSponge), new ItemMeshDefinition() {
		//		@Override public ModelResourceLocation getModelLocation (ItemStack stack){
		//
		//			boolean soaked = stack.getTagCompound() != null && stack.getTagCompound().hasKey("Fluid");
		//			return new ModelResourceLocation("thermalexpansion:sponge", "soaked=" + String.valueOf(soaked).toLowerCase() + ",type=" + BlockSponge.Types.values()[stack.getMetadata()].getName());
		//		}
		//	});

		//		for (int i = 0; i < BlockSponge.NAMES.length; i++) {
		//			ModelLoader.registerItemVariants(Item.getItemFromBlock(TEBlocksOld.blockSponge), new ModelResourceLocation("thermalexpansion:sponge", "soaked=false,type=" + BlockSponge.NAMES[i]));
		//			ModelLoader.registerItemVariants(Item.getItemFromBlock(TEBlocksOld.blockSponge), new ModelResourceLocation("thermalexpansion:sponge", "soaked=true,type=" + BlockSponge.NAMES[i]));
		//		}
		//
		//		for (EnumType type : EnumType.values()) {
		//			ModelResourceLocation location = new ModelResourceLocation("thermalexpansion:workbench", "type=" + type.getName().toLowerCase(Locale.US));
		//			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TEBlocksOld.blockWorkbench), type.ordinal(), location);
		//		}

		//		for (BlockGlass.Types type : BlockGlass.Types.values()) {
		//			ModelResourceLocation location = new ModelResourceLocation("thermalexpansion:glass", "type=" + type.getName().toLowerCase(Locale.US));
		//			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TEBlocksOld.blockGlass), type.ordinal(), location);
		//		}
		//
		//		for (EnumDyeColor color : EnumDyeColor.values()) {
		//			ModelResourceLocation location = new ModelResourceLocation("thermalexpansion:rockwool", "color=" + color.getName().toLowerCase(Locale.US));
		//			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TEBlocksOld.blockRockwool), color.ordinal(), location);
		//		}

		//		registerDummyModel(TEBlocksOld.blockStrongbox, BlockStrongbox.TYPES);
		//		ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(TEBlocksOld.blockStrongbox), RenderStrongbox.instance);

		//		registerDummyModel(TEBlocksOld.blockAirBarrier);
		//
		//		registerDummyModel(TEBlocksOld.blockAirForce, BlockAirForce.FACING);
		//
		//		registerDummyModel(TEBlocksOld.blockAirLight);
		//
		//		registerDummyModel(TEBlocksOld.blockAirSignal, BlockAirSignal.INTENSITY);

		RenderEntityFlorb.initialize();
	}

	public void initialize(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public static void registerBlockToBakery(Block block, IParticleProvider[] types) {

		IIconRegister register = block instanceof IIconRegister ? (IIconRegister) block : null;
		registerBlockToBakery(block, register, types);
	}

	//FIXME: Do per side particle grabbing.
	public static void registerBlockToBakery(Block block, IIconRegister iconRegister, IParticleProvider[] types) {

		for (IParticleProvider type : types) {
			IBakedModel model = new CCBakeryModel(type.getParticleTexture());
			String typeName = type.getTypeProperty().getName();
			ModelResourceLocation location = new ModelResourceLocation(block.getRegistryName(), typeName + "=" + type.getName());
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), type.meta(), location);
			ModelRegistryHelper.register(location, model);
		}
		if (iconRegister != null) {
			TextureUtils.addIconRegister(iconRegister);
		}
	}

	private void registerDummyModel(Block block, IProperty<?>... propertiesToIgnore) {

		final ModelResourceLocation normalLoc = new ModelResourceLocation(block.getRegistryName(), "normal");
		ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(propertiesToIgnore).build());
		ModelRegistryHelper.register(normalLoc, DUMMY_MODEL);
	}

	//	private ModelResourceLocation getSatchelLocation(int meta, ISecurable.AccessMode access) {
	//
	//		return new ModelResourceLocation("thermalexpansion:satchel", "latch=" + access.toString().toLowerCase() + ",type=" + ItemSatchel.NAMES[meta]);
	//	}

	private ModelResourceLocation getToolLocation(String name) {

		return new ModelResourceLocation("thermalexpansion:tool", "type=" + name.toLowerCase());
	}

	private ModelResourceLocation getDiagramLocation(String name) {

		return new ModelResourceLocation("thermalexpansion:diagram", "type=" + name.toLowerCase());
	}

	private void registerModedToolModel(final ItemToolBase item, String name) {

		final int OUTPUT = 1;
		final ModelResourceLocation input = getToolLocation(name + "input");
		final ModelResourceLocation output = getToolLocation(name + "output");

		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {

				return item.getMode(stack) == OUTPUT ? output : input;
			}
		});
		ModelLoader.registerItemVariants(item, input, output);
	}

	private void registerToolModel(Item item, String name) {

		registerToolModel(item, 0, name);
	}

	private void registerToolModel(ItemStack stack, String name) {

		registerToolModel(stack.getItem(), stack.getMetadata(), name);
	}

	private void registerToolModel(Item item, int metadata, String name) {

		final ModelResourceLocation location = getToolLocation(name);
		ModelLoader.setCustomModelResourceLocation(item, metadata, location);
	}

	private void registerModelKeyGenerators() {
		/*Items*/
		//        BlockBakery.registerItemKeyGenerator(Item.getItemFromBlock(TEBlocks.blockLight), new IItemStackKeyGenerator() {
		//            @Override
		//            public String generateKey(ItemStack stack) {
		//                StringBuilder builder = new StringBuilder();
		//                builder.append(stack.getMetadata());
		//                builder.append(",");
		//                builder.append(stack.getItem().getRegistryName().toString());
		//                builder.append(",");
		//                if (stack.hasTagCompound()) {
		//                    builder.append(stack.getTagCompound().getByte("Style"));
		//                }
		//                return builder.toString();
		//            }
		//        });

		//		BlockBakery.registerItemKeyGenerator(Item.getItemFromBlock(TEBlocksOld.blockTank), new IItemStackKeyGenerator() {
		//			@Override
		//			public String generateKey(ItemStack stack) {
		//
		//				StringBuilder builder = new StringBuilder();
		//				builder.append(stack.getMetadata());
		//				builder.append(",");
		//				builder.append(stack.getItem().getRegistryName().toString());
		//				builder.append(",");
		//				if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Fluid")) {
		//					FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("Fluid"));
		//					int level = (int) Math.min(TileTank.RENDER_LEVELS - 1, (long) fluid.amount * TileTank.RENDER_LEVELS / TileTank.CAPACITY[stack.getMetadata()]);
		//					builder.append(fluid.getFluid().getName());
		//					builder.append(",");
		//					builder.append(level);
		//				}
		//				return builder.toString();
		//			}
		//		});
		//FIXME, Possible json models: Dynamo,

        /*Blocks*/
		BlockBakery.registerBlockKeyGenerator(TEBlocksOld.blockDynamo, new IBlockStateKeyGenerator() {
			@Override
			public String generateKey(IExtendedBlockState state) {

				StringBuilder builder = new StringBuilder(state.getBlock().getRegistryName().toString());
				builder.append(",");
				builder.append(state.getValue(CommonProperties.FACING_PROPERTY));
				builder.append(",");
				builder.append(state.getValue(CommonProperties.ACTIVE_PROPERTY));
				builder.append(",");
				builder.append(state.getValue(CommonProperties.TYPE_PROPERTY));
				return builder.toString();
			}
		});
		//		BlockBakery.registerBlockKeyGenerator(TEBlocksOld.blockCell, new IBlockStateKeyGenerator() {
		//			@Override
		//			public String generateKey(IExtendedBlockState state) {
		//
		//				StringBuilder builder = new StringBuilder(state.getBlock().getRegistryName().toString());
		//				builder.append(",");
		//				Map<EnumFacing, TextureAtlasSprite> spriteMap = state.getValue(BlockBakeryProperties.LAYER_FACE_SPRITE_MAP);
		//				for (Entry<EnumFacing, TextureAtlasSprite> spriteEntry : spriteMap.entrySet()) {
		//					String tex = spriteEntry.getValue().getIconName();
		//					builder.append(spriteEntry.getKey()).append("=").append(tex.substring(tex.lastIndexOf("/") + 1));
		//					builder.append(",");
		//				}
		//				builder.append(state.getValue(CommonProperties.TYPE_PROPERTY));
		//				builder.append(",");
		//				builder.append(state.getValue(BlockCell.CHARGE_PROPERTY));
		//				builder.append(",");
		//				builder.append(state.getValue(CommonProperties.FACING_PROPERTY));
		//				builder.append(",");
		//				String tex = state.getValue(CommonProperties.ACTIVE_SPRITE_PROPERTY).getResourcePath();
		//				builder.append(tex.substring(tex.lastIndexOf("/") + 1));
		//				return builder.toString();
		//			}
		//		});
		//		BlockBakery.registerBlockKeyGenerator(TEBlocksOld.blockTank, new IBlockStateKeyGenerator() {
		//			@Override
		//			public String generateKey(IExtendedBlockState state) {
		//
		//				StringBuilder builder = new StringBuilder(state.getBlock().getRegistryName().toString());
		//				builder.append(",");
		//				int metadata = state.getBlock().getMetaFromState(state);
		//				FluidStack stack = state.getValue(BlockTank.FLUID_STACK_PROPERTY);
		//				if (stack != null) {
		//					builder.append(stack.getUnlocalizedName());
		//					builder.append("|");
		//					if (stack.getFluid().isGaseous()) {
		//						builder.append(32 + 192 * stack.amount / TileTank.CAPACITY[metadata]);
		//					} else {
		//						builder.append((int) Math.min(TileTank.RENDER_LEVELS - 1, (long) stack.amount * TileTank.RENDER_LEVELS / TileTank.CAPACITY[metadata]));
		//					}
		//					builder.append(",");
		//				}
		//				builder.append(state.getValue(BlockTank.MODE_PROPERTY));
		//				builder.append(",");
		//				builder.append(state.getValue(CommonProperties.TYPE_PROPERTY));
		//				return builder.toString();
		//			}
		//		});
		//        BlockBakery.registerBlockKeyGenerator(TEBlocks.blockTesseract, new IBlockStateKeyGenerator() {
		//            @Override
		//            public String generateKey(IExtendedBlockState state) {
		//                StringBuilder builder = new StringBuilder(state.getBlock().getRegistryName().toString());
		//                builder.append(",");
		//                builder.append(state.getValue(CommonProperties.ACTIVE_PROPERTY));
		//                builder.append(",");
		//                builder.append(state.getValue(BlockEnder.DISABLED_PROPERTY));
		//                return builder.toString();
		//            }
		//        });
		//        BlockBakery.registerBlockKeyGenerator(TEBlocks.blockPlate, new IBlockStateKeyGenerator() {
		//            @Override
		//            public String generateKey(IExtendedBlockState state) {
		//                StringBuilder builder = new StringBuilder(state.getBlock().getRegistryName().toString());
		//                builder.append(",");
		//                builder.append(state.getValue(BlockPlate.ALIGNMENT_PROPERTY));
		//                builder.append(",");
		//                builder.append(state.getValue(CommonProperties.FACING_PROPERTY));
		//                builder.append(",");
		//                builder.append(state.getValue(CommonProperties.TYPE_PROPERTY));
		//
		//                return builder.toString();
		//            }
		//        });
		//        BlockBakery.registerBlockKeyGenerator(TEBlocks.blockLight, new IBlockStateKeyGenerator() {
		//            @Override
		//            public String generateKey(IExtendedBlockState state) {
		//                StringBuilder builder = new StringBuilder(state.getBlock().getRegistryName().toString());
		//                builder.append(",");
		//                builder.append(state.getValue(CommonProperties.TYPE_PROPERTY));
		//                builder.append(",");
		//                builder.append(state.getValue(BlockLight.COLOUR_MULTIPLIER_PROPERTY));
		//                builder.append(",");
		//                builder.append(state.getValue(BlockLight.STYLE_PROPERTY));
		//                builder.append(",");
		//                builder.append(state.getValue(BlockLight.ALIGNMENT_PROPERTY));
		//                builder.append(",");
		//                builder.append(state.getValue(BlockLight.MODIFIED_PROPERTY));
		//                builder.append(",");
		//                builder.append(state.getValue(CommonProperties.ACTIVE_PROPERTY));
		//                return builder.toString();
		//            }
		//        });
		BlockBakery.registerBlockKeyGenerator(TEBlocksOld.blockFrame, new IBlockStateKeyGenerator() {
			@Override
			public String generateKey(IExtendedBlockState state) {

				return state.getBlock().getRegistryName().toString() + "|" + state.getBlock().getMetaFromState(state);
			}
		});
	}

	@Override
	@SideOnly (Side.CLIENT)
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

		IconRegistry.addIcon("IconConfigTesseract", "thermalexpansion:items/icons/icon_config_tesseract", event.getMap());
		IconRegistry.addIcon("IconRecvOnly", "thermalexpansion:items/icons/icon_recvonly", event.getMap());
		IconRegistry.addIcon("IconSendOnly", "thermalexpansion:items/icons/icon_sendonly", event.getMap());
		IconRegistry.addIcon("IconSendRecv", "thermalexpansion:items/icons/icon_sendrecv", event.getMap());
		IconRegistry.addIcon("IconBlocked", "thermalexpansion:items/icons/icon_blocked", event.getMap());
		IconRegistry.addIcon("IconSchematic", "thermalexpansion:items/diagram/schematic", event.getMap());
		IconRegistry.addIcon("IconSlotSchematic", "thermalexpansion:items/icons/icon_slotschematic", event.getMap());
		IconRegistry.addIcon("FlorbMask", "thermalexpansion:items/florb/florb_mask", event.getMap());
		IconRegistry.addIcon("FlorbOutline", "thermalexpansion:items/florb/florb_outline", event.getMap());

	}

	@Override
	@SideOnly (Side.CLIENT)
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

		RenderDynamo.initialize();
		RenderFrame.initialize();
	}

}
