import com.lambda.client.event.events.PlayerTravelEvent
import com.lambda.client.module.Category
import com.lambda.client.module.modules.player.LagNotifier
import com.lambda.client.plugin.api.PluginModule
import com.lambda.client.util.threads.safeListener

internal object LongJump : PluginModule(
    name = "LongJump",
    category = Category.MISC,
    description = "Jump Longer",
    pluginMain = LongJumpController
) {
    private val mode by setting("Mode", Mode.PEAK)
    private val farSpeed by setting("Peak Speed", 1.0f, 0.0f..10.0f, 0.1f, { mode == Mode.PEAK }, description = "Speed When Falling")
    private val groundSpeed by setting("Ground Speed", 2.0f, 0.0f..10.0f, 0.1f, { mode == Mode.GROUND }, description = "Speed When Jumping")
    private val disableOnRubberband by setting("Rubberband disable", false)

    private var has = false

    private enum class Mode {
        PEAK,
        GROUND
    }

    init {
        onEnable {
            has = false
        }

        safeListener<PlayerTravelEvent> {
            if (LagNotifier.paused && disableOnRubberband) {
                disable()
            }

            when {
                mode == Mode.PEAK -> {
                    if (player.motionY <= 0 && !has) {
                        has = true
                        player.jumpMovementFactor = farSpeed
                    }
                    if (player.onGround) has = false
                }
                player.onGround -> {
                    player.jump()
                    player.motionX *= groundSpeed
                    player.motionY *= groundSpeed
                }
            }
        }
    }
}