package ru.serdjuk.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import kotlin.math.cos
import kotlin.math.sin
import kotlin.reflect.KFunction3

class FBO {

    val bufferWidth = 512f
    val bufferHeight = 512f
    private val viewport = FitViewport(bufferWidth, bufferHeight, OrthographicCamera())
    val spriteIntoFbo = Sprite(Texture("badlogic.jpg")).also {
        it.setBounds(0f, 0f, 256f, 256f)
    }

    private val frameBufferBuilder =
            GLFrameBuffer.FrameBufferBuilder(bufferWidth.toInt(), bufferHeight.toInt()).also {
                it.addColorTextureAttachment(GL30.GL_RGBA, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE)
            }
    private val buffer = frameBufferBuilder.build()


    val region = TextureRegion(buffer.colorBufferTexture).also {
        it.flip(false, true)
    }

    var time = 0f

    fun render(batch: SpriteBatch) {

        buffer.begin()

        batch.projectionMatrix = viewport.camera.combined
        Gdx.gl20.glClearColor(1f, 1f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)
        spriteIntoFbo.draw(batch)
        batch.flush()       // +renderCall  если не сделать то спрайт отрисуется вне буфера

        buffer.end()


        move()

    }


    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    private fun move() {
        spriteIntoFbo.x = 1f + sin(time * 4f) / 2f * spriteIntoFbo.width + spriteIntoFbo.width / 2
        spriteIntoFbo.y = 1f + cos(time * 6) / 2f * spriteIntoFbo.height + spriteIntoFbo.height / 2
        time += 1f / 60f
    }

}