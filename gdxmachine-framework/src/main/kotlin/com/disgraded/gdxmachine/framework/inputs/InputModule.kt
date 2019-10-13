package com.disgraded.gdxmachine.framework.inputs

import com.badlogic.gdx.Gdx
import com.disgraded.gdxmachine.framework.Module

class InputModule : Module {

    private lateinit var inputProcessor: InputProcessor
    lateinit var api: InputApi

    override fun load() {
        api = InputApi()
        inputProcessor = InputProcessor(api)
        Gdx.input.inputProcessor = inputProcessor
    }

    override fun unload() {
        Gdx.input.inputProcessor = null
        api.clear()
    }
}