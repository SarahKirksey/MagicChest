package org.tilegames.mc.magicchest.client.gui;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly (Side.CLIENT)
public class PageSoftware extends Page {

    public static final int BASE_ID = 107;
    
    private int id;
    
    public PageSoftware(GuiPage gui, int id) {
        super (gui);
        this.id = id;
    }
    

    @Override
    public String getTitle () {
        return "Software";
    }

    @Override
    public int getButtonId () {
        return BASE_ID + id;
    }
    
    @Override
    public int getButtonTexture () {
        return 16;
    }

    
    @Override
    public void draw (int mouseX, int mouseY) {
        gui.renderHelper.bindAndDrawBackgroundTexture ("Pages/Options.png");
        gui.renderHelper.drawDevelopingBanner (0);
    }

    @Override
    public boolean onClick (int x, int y, int button) {
        return false;
    }

    @Override
    public boolean onKeyType (char character, int key) {
        return false;
    }
    
}
