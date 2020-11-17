package ru.serdjuk.game

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.ExtendViewport
import kotlin.math.cos
import kotlin.math.sin

class World : ScreenAdapter() {
    private val fbo = FBO()
    private val batch = SpriteBatch()
    private val worldWidth = 2f
    private val worldHeight = 2f
    private val viewport = ExtendViewport(worldWidth, worldHeight, OrthographicCamera())

    private val fboSprite = Sprite(fbo.region).also {
        it.setBounds(0f, 0f, 1f, 1f)
    }
    private var time = 0f

    override fun render(delta: Float) {
        batch.begin()
        fbo.render(batch)                       // draw into fbo
        batch.projectionMatrix = viewport.camera.combined
        fboSprite.draw(batch)
        batch.end()

        move()
        super.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        fbo.resize(width, height)
        viewport.update(width, height, true)
    }


    private val tmpVector3 = Vector3()
    private fun move() {
        fboSprite.x = 1f + cos(time * 2f) / 2f
        fboSprite.y = 1f + sin(time * 3) / 2f
        time += 1f / 60f
    }


}