package ru.serdjuk.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MyGdxGame extends Game {

    private World world;

    @Override
    public void create() {
        world = new World();
        setScreen(world);
    }

    @Override
    public void render() {
        Gdx.gl20.glClearColor(1, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }



    @Override
    public void dispose() {
        super.dispose();
    }
}
