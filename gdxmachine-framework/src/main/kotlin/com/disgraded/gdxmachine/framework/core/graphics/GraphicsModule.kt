package com.disgraded.gdxmachine.framework.core.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.Scaling
import com.disgraded.gdxmachine.framework.core.Module
import com.disgraded.gdxmachine.framework.core.graphics.utils.Shader
import com.disgraded.gdxmachine.framework.core.resources.assets.ShaderData

class GraphicsModule : Module {

    var gpuCalls: Int = 0

    val api = GraphicsApi(this)

    private val layerMap = hashMapOf<String, Layer>()

    private val shaderMap = hashMapOf<String, Shader>()

    override fun load() { }

    override fun unload() = clear()

    fun update() {
        Gdx.gl.glClearColor(0f, 0f ,0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        var gpuCalls = 0
        for ((_, viewport) in layerMap.toList().sortedBy { it.second.priority }) {
            gpuCalls += viewport.render()
        }
        this.gpuCalls = gpuCalls
    }

    fun resize(width: Int, height: Int) {
        for ((_, viewport) in layerMap) viewport.update(width, height)
    }

    fun clear() {
        for ((_, viewport) in layerMap) viewport.dispose()
        layerMap.clear()
    }

    fun createLayer(key: String, width: Float, height: Float, scaling: Scaling): Layer {
        if (layerMap.containsKey(key)) throw RuntimeException("") // TODO: message
        layerMap[key] = Layer(key, width, height, scaling)
        layerMap[key]!!.update(Gdx.graphics.width, Gdx.graphics.height)
        return layerMap[key]!!
    }

    fun getLayer(key: String): Layer {
        if (!layerMap.containsKey(key)) throw RuntimeException("") // TODO: message
        return layerMap[key]!!
    }

    fun removeLayer(key: String) {
        if (!layerMap.containsKey(key)) throw RuntimeException("") // TODO: message
        layerMap[key]!!.dispose()
        layerMap.remove(key)
    }

    fun existLayer(key: String): Boolean = layerMap.containsKey(key)

    fun compileShader(key: String, vertex: ShaderData, fragment: ShaderData): Shader {
        if (shaderMap.containsKey(key)) throw RuntimeException("") // TODO: message
        shaderMap[key] = Shader(vertex, fragment)
        if (!shaderMap[key]!!.isCompiled) {
            throw RuntimeException("") // TODO: message
        }
        return shaderMap[key]!!
    }

    fun getShader(key: String): Shader {
        if (!shaderMap.containsKey(key)) throw RuntimeException("") // TODO: message
        return shaderMap[key]!!
    }

    fun removeShader(key: String) {
        if (!shaderMap.containsKey(key)) throw RuntimeException("") // TODO: message
        shaderMap[key]!!.dispose()
        shaderMap.remove(key)
    }

    fun existShader(key: String): Boolean = shaderMap.containsKey(key)
}