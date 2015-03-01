package com.palestone.enigma.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.palestone.enigma.EnigmaMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 800;

        TexturePacker.process("rawImages", "packedImages", "imagePack");
		new LwjglApplication(new EnigmaMain(), config);
	}
}
