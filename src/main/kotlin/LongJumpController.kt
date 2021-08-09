import com.lambda.client.plugin.api.Plugin

internal object LongJumpController: Plugin() {

    override fun onLoad() {
        // Load any modules, commands, or HUD elements here
        modules.add(LongJump)
    }

    override fun onUnload() {
        // Here you can unregister threads etc...
    }
}