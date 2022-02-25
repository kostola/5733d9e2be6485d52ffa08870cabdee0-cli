package com.redhat.service.bridge.cli.command;

import com.redhat.service.bridge.cli.output.OutputType;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

@Command(name = "ob", mixinStandardHelpOptions = true, scope = ScopeType.INHERIT, version = "ob 1.0.0-SNAPSHOT", description = "ob made with jbang",
        subcommands = {
                BridgeCommand.class,
                ProcessorCommand.class
        }
)
public class MainCommand extends BaseCommand {

    @Option(names = {"-o", "--output-type"}, scope = ScopeType.INHERIT, defaultValue = "HUMAN",
            description = "Output type (values: ${COMPLETION-CANDIDATES}, default: ${DEFAULT-VALUE})")
    void setOutputType(OutputType outputType) {
        configuration.setOutputType(outputType);
    }

    @Option(names = {"-t", "--token"}, scope = ScopeType.INHERIT, description = "Access token")
    void setToken(String token) {
        configuration.setToken(token);
    }
}
