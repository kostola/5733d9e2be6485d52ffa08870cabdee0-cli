package com.redhat.service.bridge.cli.command;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

@Command(name = "ob", mixinStandardHelpOptions = true, scope = ScopeType.INHERIT, version = "ob 1.0.0-SNAPSHOT", description = "ob made with jbang",
        subcommands = {
                BridgeCommand.class
        }
)
public class MainCommand extends BaseCommand {

    @Option(names = {"-t", "--token"}, scope = ScopeType.INHERIT, description = "Access token")
    void setToken(String token) {
        configuration.setToken(token);
    }
}
