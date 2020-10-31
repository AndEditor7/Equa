package com.travall.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.travall.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.enableGLDebugOutput(true, System.err); // OpenGL debugger.
		config.disableAudio(true); // Audio disabled for quick start-up.
		config.useOpenGL3(true, 3, 2);
		config.setWindowedMode(800, 600);
		config.setTitle("Equa");
		new Lwjgl3Application(Main.main, config);
	}
}
