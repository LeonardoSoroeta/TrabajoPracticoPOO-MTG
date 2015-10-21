package ar.edu.itba.Magic.Desktop;

import ar.edu.itba.Magic.Backend.MyGdxGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.foregroundFPS = 60;
		config.vSyncEnabled = false;
		config.fullscreen = false;
		config.resizable = false;
		config.title = "Magic";
		new LwjglApplication(new MyGdxGame(), config);
	}
	
}
