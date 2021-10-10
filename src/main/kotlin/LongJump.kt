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
    private val mode by setting("Mode", enumMode.Peak)
    private val farSpeed by setting("Peak Speed", 1.0f, 0.0f..10.0f, 0.1f, { mode.equals(enumMode.Peak) }, description = "Speed When Falling")
    private val groundSpeed by setting("Ground Speed", 2.0f, 0.0f..10.0f, 0.1f, { mode.equals(enumMode.Ground) }, description = "Speed When Jumping")
    private val disableOnRubberband by setting("Rubberband disable", false)

    var has = false;

    private enum class enumMode {

        Peak,
        Ground

    }

    init {

        onEnable {
            has = false
        }

        safeListener<PlayerTravelEvent> {

            if (LagNotifier.paused && disableOnRubberband) {

                disable()

            }

            if (mode.equals(enumMode.Peak)) {
                if (mc.player.motionY <= 0 && !has) {

                    has = true

                    mc.player.jumpMovementFactor = farSpeed

                }

                if (mc.player.onGround)
                    has = false

            } else if (mc.player.onGround) {

                mc.player.jump();
                mc.player.motionX *= groundSpeed
                mc.player.motionY *= groundSpeed

            }

        }
    }
}