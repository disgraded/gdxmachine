package com.disgraded.gdxmachine.framework.renderers

import com.disgraded.gdxmachine.framework.batches.MaskBatch
import com.disgraded.gdxmachine.framework.batches.SpriteBatch
import com.disgraded.gdxmachine.framework.batches.TextBatch
import com.disgraded.gdxmachine.framework.core.graphics.BatchManager
import com.disgraded.gdxmachine.framework.core.graphics.Drawable
import com.disgraded.gdxmachine.framework.core.graphics.Layer
import com.disgraded.gdxmachine.framework.core.graphics.Renderer
import com.disgraded.gdxmachine.framework.drawables.Mask
import com.disgraded.gdxmachine.framework.drawables.Sprite
import com.disgraded.gdxmachine.framework.drawables.Text

class Renderer2D : Renderer {

    private val batchManager = BatchManager()

    init {
        batchManager.addBatch(Sprite::class, SpriteBatch())
        batchManager.addBatch(Mask::class, MaskBatch())
        batchManager.addBatch(Text::class, TextBatch())
    }

    override fun render(drawableList: ArrayList<Drawable>, layer: Layer): Int {
        return batchManager.execute(drawableList, layer.camera.combined)
    }

    override fun dispose() {
        batchManager.dispose()
    }
}