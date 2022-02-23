package com.redhat.service.bridge.cli.command;

import javax.inject.Inject;

import com.redhat.service.bridge.cli.config.Configuration;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

abstract class BaseCommand implements Runnable {

    @Spec
    CommandSpec spec;

    @Inject
    Configuration configuration;

    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}
