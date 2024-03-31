package com.gardensmc.wings.fabric;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.command.Commands;
import com.gardensmc.wings.common.command.GardensCommand;
import com.gardensmc.wings.common.command.exception.InvalidArgumentException;
import com.gardensmc.wings.common.command.exception.NoPermissionException;
import com.gardensmc.wings.common.command.exception.PlayerNotFoundException;
import com.gardensmc.wings.common.player.PlayerMessageHandler;
import com.gardensmc.wings.fabric.config.FabricConfigWrapper;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import com.gardensmc.wings.fabric.schedule.FabricScheduler;
import com.gardensmc.wings.fabric.server.FabricServer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class WingsFabric implements ModInitializer {

    public static final File GARDENS_CONFIG_DIR = new File(FabricLoader.getInstance().getConfigDir().toFile(), "GardensWings");
    private static final String GENERIC_ARG = "arg";
    public static final Logger LOGGER = LoggerFactory.getLogger("GardensWings");

    @Override
    public void onInitialize() {
        GardensWings.wingsConfig = new FabricConfigWrapper();
        Commands.getCommands().forEach(gardensCommand -> CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> dispatcher.register(literal(gardensCommand.getName())
                        .executes(context -> executeCommand(gardensCommand, context))
                        .then(argument(GENERIC_ARG, greedyString())
                                .suggests((context, suggestionsBuilder) -> tabCompleter(gardensCommand, context, suggestionsBuilder))
                                .executes(context -> executeCommand(gardensCommand, context))
                        ))));
        ServerLifecycleEvents.SERVER_STARTED.register(this::initializeWithServer);
    }

    private void initializeWithServer(MinecraftServer server) {
        GardensWings.initialize(new FabricServer(server), FabricScheduler.INSTANCE);
    }

    private int executeCommand(GardensCommand gardensCommand, CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity serverPlayer = context.getSource().getPlayer();
        if (serverPlayer == null) {
            context.getSource().sendMessage(Text.of("Console cannot run this command!"));
        } else {
            String[] fullArgs = context.getInput().split(" "); // arg array with size >= 1
            String[] args = Arrays.copyOfRange(fullArgs, 1, fullArgs.length); // remove command name from arg
            FabricPlayer fabricPlayer = new FabricPlayer(serverPlayer);
            PlayerMessageHandler playerMessageHandler = new PlayerMessageHandler(fabricPlayer);
            try {
                gardensCommand.execute(fabricPlayer, args);
            } catch (InvalidArgumentException e) {
                playerMessageHandler.sendError("Invalid argument!");
            } catch (NoPermissionException e) {
                playerMessageHandler.sendError("You do not have permission to use this command!");
            } catch (PlayerNotFoundException e) {
                playerMessageHandler.sendError(String.format("Cannot find player '%s'", e.getUsername()));
            } catch (Exception e) {
                playerMessageHandler.sendError("An internal server error occurred");
                LOGGER.error("Failed to run command: " + gardensCommand.getName(), e);
            }
        }
        return 1;
    }

    private CompletableFuture<Suggestions> tabCompleter(GardensCommand gardensCommand, CommandContext<ServerCommandSource> context, SuggestionsBuilder suggestionsBuilder) {
        ServerPlayerEntity serverPlayer = context.getSource().getPlayer();
        if (serverPlayer == null) return null;
        int idx = suggestionsBuilder.getStart();
        String buffer = suggestionsBuilder.getInput().substring(idx);
        idx += buffer.length();

        String[] fullArgs = context.getInput().split(" ", -1); // let trailing spaces count as their own string
        String[] args = Arrays.copyOfRange(fullArgs, 1, fullArgs.length); // remove command name from arg
        String lastArg = fullArgs[fullArgs.length - 1];
        if (!lastArg.equals(" ")) {
            idx -= lastArg.length(); // don't increment index while current subcommand is in-progress
        }
        suggestionsBuilder = suggestionsBuilder.createOffset(idx); // offset to line up with current subcommand
        gardensCommand.getTabCompletion(new FabricPlayer(serverPlayer), args)
                .forEach(suggestionsBuilder::suggest);
        return suggestionsBuilder.buildFuture();
    }
}
