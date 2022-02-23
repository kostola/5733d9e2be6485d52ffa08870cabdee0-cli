package com.redhat.service.bridge.cli.command;

import javax.inject.Inject;
import javax.inject.Singleton;

import picocli.CommandLine;

@Singleton
public class MainCommandFactory {

    @Inject
    CommandLine.IFactory factory;

    @Inject
    MainCommand command;

    public CommandLine build() {
        return new CommandLine(command, factory)
                .setCaseInsensitiveEnumValuesAllowed(true);
    }
}
