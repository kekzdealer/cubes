package kurumi.utli;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL12C;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureLoader {

    /**
     * Create and load a texture to VRAM that can be used as a fallback texture in case others fail to load.<br><br>
     * The texture is a two by two grid in black and purple.
     * @return textureId of the created texture
     */
    public static int createFallbackTexture() {
        // Create raw data for the texture
        // black - purple
        // purple - black
        final byte[] colourData = {
                0, 0, 0, Byte.MAX_VALUE, 0, Byte.MAX_VALUE,
                Byte.MAX_VALUE, 0, Byte.MAX_VALUE, 0, 0, 0
        };
        // width = 2, height = 2, channels = 3 -> total buffer size required
        final ByteBuffer dataBuffer = BufferUtils.createByteBuffer(2 * 2 * 3);
        dataBuffer.put(colourData);
        dataBuffer.flip();

        // Create new empty texture buffer and grab it's id
        final int textureId = GL11C.glGenTextures();
        // Bind texture buffer to GL_TEXTURE_2D
        GL11C.glBindTexture(GL11C.GL_TEXTURE_2D, textureId);
        // Set texture wrapping for the activated buffer
        GL11C.glTexParameterf(GL11C.GL_TEXTURE_2D, GL11C.GL_TEXTURE_WRAP_S, GL12C.GL_CLAMP_TO_EDGE);
        GL11C.glTexParameterf(GL11C.GL_TEXTURE_2D, GL11C.GL_TEXTURE_WRAP_T, GL12C.GL_CLAMP_TO_EDGE);
        // Set texture filtering for the activated buffer
        GL11C.glTexParameterf(GL11C.GL_TEXTURE_2D, GL11C.GL_TEXTURE_MIN_FILTER, GL11C.GL_NEAREST);
        GL11C.glTexParameterf(GL11C.GL_TEXTURE_2D, GL11C.GL_TEXTURE_MAG_FILTER, GL11C.GL_NEAREST);
        // Write buffer to buffer bound to GL_TEXTURE_2D
        GL11C.glTexImage2D(GL11C.GL_TEXTURE_2D, 0, GL11C.GL_RGB, 2, 2, 0, GL11C.GL_RGB, GL11C.GL_UNSIGNED_BYTE, dataBuffer);
        // Unbind texture buffer
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return textureId;
    }

    private int loadTextureFromDisk(String path, String fileName) {
        // Initialized some buffers to capture return data
        final IntBuffer width = BufferUtils.createIntBuffer(1);
        final IntBuffer height = BufferUtils.createIntBuffer(1);
        final IntBuffer comp = BufferUtils.createIntBuffer(1);

        final ByteBuffer data = STBImage.stbi_load(path + fileName + ".png", width, height, comp, 4);
        if(data == null){
            System.err.println(STBImage.stbi_failure_reason() + " -> " + fileName);
        }

        // Create new empty texture buffer and grab it's id
        final int textureId = GL11C.glGenTextures();
        // Bind texture buffer to GL_TEXTURE_2D
        GL11C.glBindTexture(GL11C.GL_TEXTURE_2D, textureId);
        // Set texture wrapping for the activated buffer
        GL11C.glTexParameterf(GL11C.GL_TEXTURE_2D, GL11C.GL_TEXTURE_WRAP_S, GL12C.GL_CLAMP_TO_EDGE);
        GL11C.glTexParameterf(GL11C.GL_TEXTURE_2D, GL11C.GL_TEXTURE_WRAP_T, GL12C.GL_CLAMP_TO_EDGE);
        // Set texture filtering for the activated buffer
        GL11C.glTexParameterf(GL11C.GL_TEXTURE_2D, GL11C.GL_TEXTURE_MIN_FILTER, GL11C.GL_NEAREST);
        GL11C.glTexParameterf(GL11C.GL_TEXTURE_2D, GL11C.GL_TEXTURE_MAG_FILTER, GL11C.GL_NEAREST);
        // Write buffer to buffer bound to GL_TEXTURE_2D
        GL11C.glTexImage2D(GL11C.GL_TEXTURE_2D, 0, GL11C.GL_RGBA, width.get(), height.get(), 0, GL11C.GL_RGBA, GL11C.GL_UNSIGNED_BYTE, data);
        // Unbind texture buffer
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return textureId;
    }
}
