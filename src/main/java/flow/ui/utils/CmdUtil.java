package flow.ui.utils;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CmdUtil implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("flow_ui");

    @Override
    public void onInitializeClient() {
        LOGGER.info("FlowUI initialized");
    }
}
