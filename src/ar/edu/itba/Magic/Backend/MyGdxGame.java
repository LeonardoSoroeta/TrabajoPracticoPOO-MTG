package ar.edu.itba.Magic.Backend;


import ar.edu.itba.Magic.Backend.Interfaces.Constants;
import ar.edu.itba.Magic.Frontend.MagicGameUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {
	public SpriteBatch batch;
	Texture img;
	BitmapFont standardFont;
	float time;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		img = new Texture("background.jpg");
		standardFont = new BitmapFont();
		//MYP maneja los eventos externos tal como el mouse o teclado
		MyInputProcessor MYP = new MyInputProcessor();
		Gdx.input.setInputProcessor(MYP);
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 600);
	    viewport = new FitViewport(800, 600, camera);
	}

	@Override
	public void render () {
		time+= Gdx.graphics.getDeltaTime();
		//batch.setTransformMatrix(camera.view);
		//batch.setProjectionMatrix(camera.projection);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0,800,600);
		standardFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),1,50);
		MagicGame.getInstance().update(Gdx.graphics.getDeltaTime());
		MagicGameUI.getInstance().draw(batch);
		batch.end();

	}
	
	@Override
    public void resize(int width, int height){
		 viewport.update(width, height,false);
	        camera.update();
	}
}
