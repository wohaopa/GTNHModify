package com.github.wohaopa.GTNHModify;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.config.GuiItemIconDumper;
import codechicken.nei.guihook.GuiContainerManager;

public class IconDumper extends GuiScreen {

    GuiScreen ori;
    List<ItemStack> items;

    private int iconSize;
    private int borderSize;
    private int boxSize;

    public IconDumper(GuiScreen ori, List<ItemStack> items) {
        this.ori = ori;
        this.items = items;

        this.iconSize = 32;
        borderSize = iconSize / 16;
        boxSize = iconSize + borderSize * 2;

        if (dir.exists()) {
            for (File f : dir.listFiles()) if (f.isFile()) f.delete();
        } else dir.mkdirs();
    }

    @Override
    protected void keyTyped(char c, int keycode) {
        if (keycode == Keyboard.KEY_ESCAPE || keycode == Keyboard.KEY_BACK) {
            if (Minecraft.getMinecraft().ingameGUI != null) Minecraft.getMinecraft().ingameGUI.getChatGUI()
                .printChatMessage(new ChatComponentText("退出"));
            Minecraft.getMinecraft()
                .displayGuiScreen(ori);
            return;
        }
        super.keyTyped(c, keycode);
    }

    @Override
    public void drawScreen(int mousex, int mousey, float frame) {
        drawItems();
        exportItems();
    }

    private int drawIndex;

    private void drawItems() {
        Dimension d = GuiDraw.displayRes();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, d.width * 16D / iconSize, d.height * 16D / iconSize, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        int rows = d.height / boxSize;
        int cols = d.width / boxSize;
        int fit = rows * cols;

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1, 1, 1, 1);

        final List<ItemStack> items1 = items;
        int drawIndex1 = drawIndex;

        for (int i = 0, size = items1.size(); drawIndex1 < size && i < fit; drawIndex1++, i++) {
            int x = i % cols * 18;
            int y = i / cols * 18;
            GuiContainerManager.drawItem(x + 1, y + 1, items1.get(drawIndex1));
        }
        drawIndex = drawIndex1;

        GL11.glFlush();
    }

    private final File dir = new File(Minecraft.getMinecraft().mcDataDir, "GTNHModify/dumps/icons");
    private int parseIndex;

    private void exportItems() {
        BufferedImage img = screenshot();
        int rows = img.getHeight() / boxSize;
        int cols = img.getWidth() / boxSize;
        int fit = rows * cols;

        final List<ItemStack> items1 = items;
        int parseIndex1 = parseIndex;

        for (int i = 0, size = items1.size(); parseIndex1 < size && i < fit; parseIndex1++, i++) {
            int x = i % cols * boxSize;
            int y = i / cols * boxSize;
            exportImage(
                dir,
                img.getSubimage(x + borderSize, y + borderSize, iconSize, iconSize),
                items1.get(parseIndex1));
        }
        parseIndex = parseIndex1;

        if (parseIndex >= items.size()) {
            if (Minecraft.getMinecraft().ingameGUI != null) Minecraft.getMinecraft().ingameGUI.getChatGUI()
                .printChatMessage(new ChatComponentText("成功！"));
            Minecraft.getMinecraft()
                .displayGuiScreen(ori);
        }
    }

    private void exportImage(File dir, BufferedImage img, ItemStack stack) {
        String name = stack.getDisplayName();
        name = GuiItemIconDumper.cleanFileName(name);
        File file = new File(dir, name + ".png");
        for (int i = 2; file.exists(); i++) file = new File(dir, name + '_' + i + ".png");
        try {
            ImageIO.write(img, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private IntBuffer pixelBuffer;
    private int[] pixelValues;

    private BufferedImage screenshot() {
        Framebuffer fb = Minecraft.getMinecraft()
            .getFramebuffer();
        Dimension mcSize = GuiDraw.displayRes();
        Dimension texSize = mcSize;

        if (OpenGlHelper.isFramebufferEnabled())
            texSize = new Dimension(fb.framebufferTextureWidth, fb.framebufferTextureHeight);

        int k = texSize.width * texSize.height;
        if (pixelBuffer == null || pixelBuffer.capacity() < k) {
            pixelBuffer = BufferUtils.createIntBuffer(k);
            pixelValues = new int[k];
        }

        GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        pixelBuffer.clear();

        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb.framebufferTexture);
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
        } else {
            GL11.glReadPixels(
                0,
                0,
                texSize.width,
                texSize.height,
                GL12.GL_BGRA,
                GL12.GL_UNSIGNED_INT_8_8_8_8_REV,
                pixelBuffer);
        }

        pixelBuffer.get(pixelValues);
        TextureUtil.func_147953_a(pixelValues, texSize.width, texSize.height);

        BufferedImage img = new BufferedImage(mcSize.width, mcSize.height, BufferedImage.TYPE_INT_ARGB);
        if (OpenGlHelper.isFramebufferEnabled()) {
            int yOff = texSize.height - mcSize.height;
            for (int y = 0; y < mcSize.height; ++y)
                for (int x = 0; x < mcSize.width; ++x) img.setRGB(x, y, pixelValues[(y + yOff) * texSize.width + x]);
        } else {
            img.setRGB(0, 0, texSize.width, height, pixelValues, 0, texSize.width);
        }

        return img;
    }

}
