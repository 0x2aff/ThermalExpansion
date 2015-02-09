package cofh.thermalexpansion.gui.client.plate;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabInfo;
import cofh.core.gui.element.TabSecurity;
import cofh.core.render.IconRegistry;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementFluid;
import cofh.lib.gui.element.ElementIcon;
import cofh.lib.gui.element.ElementSimpleToolTip;
import cofh.thermalexpansion.block.plate.TilePlateTranslocate;
import cofh.thermalexpansion.core.TEProps;
import cofh.thermalexpansion.gui.container.ContainerTEBase;
import cofh.thermalfoundation.fluid.TFFluids;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiPlateTranslocate extends GuiBaseAdv {

	static final String TEX_PATH = TEProps.PATH_GUI + "Plate.png";
	static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);
	static final String INFO = "Translocates objects that touch it upon contact.\n\nDistance may be configured.\n\nWrench while sneaking to dismantle.";

	TilePlateTranslocate myTile;
	String playerName;

	ElementButton decDistance;
	ElementButton incDistance;

	ElementIcon plateTop;

	public GuiPlateTranslocate(InventoryPlayer inventory, TileEntity theTile) {

		super(new ContainerTEBase(inventory, theTile), TEXTURE);
		myTile = (TilePlateTranslocate) theTile;
		name = myTile.getInventoryName();
		playerName = inventory.player.getCommandSenderName();
	}

	@Override
	public void initGui() {

		super.initGui();

		addTab(new TabInfo(this, INFO));
		if (myTile.enableSecurity() && myTile.isSecured()) {
			addTab(new TabSecurity(this, myTile, playerName));
		}

		addElement(new ElementSimpleToolTip(this, 13, 20).setToolTip("Dist").setSize(24, 16).setTexture(TEX_DROP_RIGHT, 48, 16));

		addElement(new ElementFluid(this, 134, 32).setFluid(TFFluids.fluidEnder).setSize(16, 16));

		decDistance = new ElementButton(this, 10, 56, "decDistance", 176, 0, 176, 14, 176, 28, 14, 14, TEX_PATH).setToolTipLocalized(true);
		incDistance = new ElementButton(this, 26, 56, "incDistance", 190, 0, 190, 14, 190, 28, 14, 14, TEX_PATH).setToolTipLocalized(true);

		addElement(decDistance);
		addElement(incDistance);

		plateTop = new ElementIcon(this, 134, 32, IconRegistry.getIcon("PlateTop", myTile.getFacing()));
		addElement(plateTop);
	}

	@Override
	public void updateScreen() {

		super.updateScreen();

		if (!myTile.canAccess()) {
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {

		int change = 1;
		float pitch = 0.7F;

		byte curDistance = myTile.distance;

		if (buttonName.equalsIgnoreCase("decDistance")) {
			myTile.distance -= change;
			pitch -= 0.1F;
		} else if (buttonName.equalsIgnoreCase("incDistance")) {
			myTile.distance += change;
			pitch += 0.1F;
		}
		playSound("random.click", 1.0F, pitch);

		myTile.sendModePacket();

		myTile.distance = curDistance;
	}

	@Override
	protected void updateElementInformation() {

		if (myTile.distance > TilePlateTranslocate.MIN_DISTANCE) {
			decDistance.setActive();
		} else {
			decDistance.setDisabled();
		}
		if (myTile.distance < TilePlateTranslocate.MAX_DISTANCE) {
			incDistance.setActive();
		} else {
			incDistance.setDisabled();
		}
		plateTop.setIcon(IconRegistry.getIcon("PlateTop", myTile.getFacing()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		int xDistance = 26;

		String strDistance = String.format("%-8s", "" + myTile.distance);

		if (myTile.distance < 10) {
			xDistance += 6;
		}
		fontRendererObj.drawString(strDistance, xDistance, 47, 0x404040);

		super.drawGuiContainerForegroundLayer(x, y);
	}

	@Override
	protected void mouseClicked(int mX, int mY, int mouseButton) {

		if (134 <= mouseX && mouseX < 150 && 32 <= mouseY && mouseY < 48) {
			int facing = myTile.getFacing();
			float pitch = 0.7F;

			if (mouseButton == 1) {
				facing += 5;
				pitch -= 0.1F;
			} else {
				facing++;
				pitch += 0.1F;
			}
			facing %= 6;
			if (myTile.setFacing(facing)) {
				playSound("random.click", 1.0F, pitch);
				myTile.sendModePacket();
			}
		} else {
			super.mouseClicked(mX, mY, mouseButton);
		}
	}

}
