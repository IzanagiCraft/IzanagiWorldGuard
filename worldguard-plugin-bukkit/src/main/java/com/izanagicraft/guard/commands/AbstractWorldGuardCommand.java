package com.izanagicraft.guard.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.commands:AbstractWorldGuardCommand
 * <p>
 * An abstract class representing a WorldGuard-related command in Minecraft.
 * Commands extending this class provide functionality for executing commands, handling errors,
 * and providing tab-completion options.
 * Subclasses must implement the abstract methods for specific command behavior.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public abstract class AbstractWorldGuardCommand extends Command {

    /**
     * Constructs a new AbstractWorldGuardCommand with the given name.
     *
     * @param name The name of the command.
     */
    public AbstractWorldGuardCommand(@NotNull String name) {
        super(name);
    }

    /**
     * Executes the command when it is called in-game.
     *
     * @param sender       The sender of the command.
     * @param commandLabel The label used to call the command.
     * @param args         The arguments provided with the command.
     * @return Always returns false to indicate that the command did not handle execution.
     */
    @Override
    public boolean execute(@NotNull final CommandSender sender, @NotNull final String commandLabel, final @NotNull String[] args) {
        try {
            this.run(CommandSource.of(sender), commandLabel, this, args);
        } catch (Exception e) {
            e.printStackTrace();
            this.error(CommandSource.of(sender), commandLabel, this, args, e);
        }
        return false;
    }

    /**
     * Provides tab-completion options for the command.
     *
     * @param sender The sender of the tab-completion request.
     * @param alias  The alias used to call the command.
     * @param args   The arguments provided with the command.
     * @return A list of tab-completion options.
     */
    @Override
    public @NotNull List<String> tabComplete(@NotNull final CommandSender sender, @NotNull final String alias, final @NotNull String[] args) {
        return this.tabComplete(sender, alias, args, null);
    }

    /**
     * Provides tab-completion options for the command based on a specified location.
     *
     * @param sender   The sender of the tab-completion request.
     * @param alias    The alias used to call the command.
     * @param args     The arguments provided with the command.
     * @param location The location for which tab-completion options are requested (can be null).
     * @return A list of tab-completion options.
     */
    @Override
    public @NotNull List<String> tabComplete(@NotNull final CommandSender sender, @NotNull final String alias, final @NotNull String[] args, @Nullable final Location location) {
        try {
            if (args.length == 0) {
                // Shouldn't happen, but bail out early if it does so that args[0] can always be used
                return Collections.emptyList();
            }
            final List<String> options = tab(CommandSource.of(sender), alias, this, args);
            if (options == null) {
                return Collections.emptyList();
            } else {
                return options;
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Handles errors that occur during command execution.
     *
     * @param source       The command source.
     * @param commandLabel The label used to call the command.
     * @param command      The command that encountered an error.
     * @param args         The arguments provided with the command.
     * @param throwable    The throwable representing the error.
     */
    public abstract void error(final CommandSource source, final String commandLabel, final Command command, final String[] args, final Throwable throwable);

    /**
     * Executes the main logic of the command.
     *
     * @param source       The command source.
     * @param commandLabel The label used to call the command.
     * @param command      The command being executed.
     * @param args         The arguments provided with the command.
     */
    public abstract void run(final CommandSource source, final String commandLabel, final Command command, final String[] args);

    /**
     * Provides tab-completion options for the command.
     *
     * @param source       The command source.
     * @param commandLabel The label used to call the command.
     * @param cmd          The command for which tab-completion is requested.
     * @param args         The arguments provided with the command.
     * @return A list of tab-completion options.
     */
    public abstract List<String> tab(final CommandSource source, final String commandLabel, final Command cmd, final String[] args);

}
