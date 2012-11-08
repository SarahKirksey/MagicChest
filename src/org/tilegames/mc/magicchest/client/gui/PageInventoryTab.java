package org.tilegames.mc.magicchest.client.gui;

import java.util.List;

import net.minecraft.src.ItemStack;
import net.minecraft.src.OpenGlHelper;

import org.lwjgl.opengl.GL11;
import org.tilegames.mc.magicchest.TileEntityMagicChest;

import MagicChest.common.MagicChest;

public class PageInventoryTab extends Page {
	private static final int SLOT_X = 8;
	private static final int SLOT_X_END = SLOT_X + 18 * 9;
	private static final int SLOT_Y = 18;
	private static final int SLOT_Y_END = SLOT_Y + 18 * 6;
	
	private int id;
    
    public PageInventoryTab (GuiPage gui, int id) {
        super (gui);
        this.id = id;
    }

    @Override
    public String getTitle () {
        return "Choose Item";
    }

    @Override
    public int getButtonId () {
        return Page.BASE_ID + id;
    }

    @Override
    public int getButtonTexture () {
        return 0;
    }

    
    @Override
    public void draw (int mouseX, int mouseY) {
    	GuiFilteringItemBrowser gui = (GuiFilteringItemBrowser) this.gui;
    	
    	/* Draw Background. */
    	gui.renderHelper.bindAndDrawBackgroundTexture ("Pages/FilteringItemBrowser.png");
    	
    	/* Reset selectedItemStack. */
    	ItemStack selectedItemStack = null;
    	
        /* Set Lightmap. */
        short var6 = 240;
        short var7 = 240;
        OpenGlHelper.setLightmapTextureCoords (OpenGlHelper.lightmapTexUnit, (float) var6 / 1.0F, (float) var7 / 1.0F);
        GL11.glColor4f (1.0F, 1.0F, 1.0F, 1.0F);
        
        /* Draw items. */
        List<ItemStack> items = GuiFilteringItemBrowser.items;
        int start = gui.row * 9;
        int end = (gui.row + 6) * 9;
        if (end > items.size ()) end = items.size ();
        int i = start;
        
    	for (int y = 18; y < 18 + 18 * 6; y += 18) {
    		for (int x = 8; x < 8 + 18 * 9; x += 18) {
        		ItemStack stack = null;
        		if (i < end) stack = items.get (i);
        		
        		/* Draw Item Stack. */
        		if (stack != null) {
	        		gui.setZLevel (100.0f);
	        		gui.renderHelper.drawItemStack (stack, x, y);
	        		gui.setZLevel (0.0f);
        		}
        		
        		/* Check hover status. */
                if (gui.renderHelper.pointInRectangle (x, y, 16, 16, mouseX, mouseY)) {
                    selectedItemStack = stack;
                    gui.renderHelper.drawHoverRectangle (x, y, 16, 16, 0x80FFFFFF);
                }
        		
        		++i;
        	}
        }
        
        /* Draw Tooltip. */
        if (selectedItemStack != null) { 
            gui.renderHelper.drawTooltip (selectedItemStack, mouseX - gui.offsetX, mouseY - gui.offsetY);
        }
    }

    
    @Override
    public boolean onClick (int x, int y, int button) {
    	if (button != 0) return false;
    	
    	GuiFilteringItemBrowser gui = (GuiFilteringItemBrowser) this.gui;
    	List<ItemStack> items = GuiFilteringItemBrowser.items;

    	x -= gui.offsetX;
    	y -= gui.offsetY;
    	
    	ItemStack stack = null;
    	if (x >= SLOT_X - 1 && x <= SLOT_X_END + 1 && y >= SLOT_Y - 1 && y <= SLOT_Y_END + 1) {
			int id = (y - SLOT_Y + 1) / 18 * 9 + (x - SLOT_X + 1) / 18 + gui.row * 9;
			if (id < items.size ()) {
				stack = items.get(id);
			}
    	}

    	if (stack != null) {
	    	int slot = gui.filteringSlot;
	    	if (slot < TileEntityMagicChest.INVENTORY_SIZE) {
	    		gui.chest.filteringCache.setSlotItem (slot, stack);
	    	}
	    	gui.getMinecraft ().thePlayer.openGui (MagicChest.instance, 0, gui.chest.worldObj, gui.chest.xCoord, gui.chest.yCoord, gui.chest.zCoord);
	    	return true;
    	}
    	
        return false;
    }

    @Override
    public boolean onKeyType (char character, int key) {
        return false;
    }

    
    
    
}
