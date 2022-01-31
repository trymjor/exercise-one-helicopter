package no.ntnu.helipeli.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.ntnu.helipeli.HeliPeli;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.width = HeliPeli.WIDTH;
		config.height = HeliPeli.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new HeliPeli(), config);
	}
}
