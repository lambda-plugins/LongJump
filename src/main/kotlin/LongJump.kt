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
    private val farSpeed by setting("Speed", 1.0f, 0.0f..10.0f, 0.1f, description = "Long Jump Factor")
    private val disableOnRubberband by setting("Rubberband disable", false)

    init {
        safeListener<PlayerTravelEvent> {

            if (mc.player.motionY == 0.0030162615090425808) {

                mc.player.jumpMovementFactor = farSpeed

            }

            if (LagNotifier.paused && disableOnRubberband) {

                disable()

            }

        }
    }
}