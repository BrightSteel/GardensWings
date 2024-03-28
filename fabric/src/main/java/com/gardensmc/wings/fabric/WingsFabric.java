package com.gardensmc.wings.fabric;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.command.Commands;
import com.gardensmc.wings.common.command.GardensCommand;
import com.gardensmc.wings.fabric.config.FabricConfig;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class WingsFabric implements ModInitializer {

    private static final String GENERIC_ARG = "arg";

    @Override
    public void onInitialize() {
        Commands.getCommands().forEach(gardensCommand -> CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> dispatcher.register(literal(gardensCommand.getName())
                        .executes(context -> executeCommand(gardensCommand, context))
                        .then(argument(GENERIC_ARG, greedyString())
                                .executes(context -> executeCommand(gardensCommand, context))
                        ))));

        GardensWings.wingsConfig = new FabricConfig();
    }

    private int executeCommand(GardensCommand gardensCommand, CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity serverPlayer = context.getSource().getPlayer();
        if (serverPlayer == null) {
            context.getSource().sendMessage(Text.of("Console cannot run this command!"));
        } else {
            String[] fullArgs = context.getInput().split(" "); // arg array with size >= 1
            String[] args = Arrays.copyOfRange(fullArgs, 1, fullArgs.length); // remove command name from arg
            gardensCommand.execute(new FabricPlayer(serverPlayer), args);
        }
        return 1;
    }
}
