package com.redhat.service.bridge.cli.command;

import javax.inject.Inject;

import com.redhat.service.bridge.cli.client.OpenBridgeClient;
import com.redhat.service.bridge.cli.output.OutputGeneratorFactoryImpl;
import org.eclipse.microprofile.rest.client.inject.RestClient;

abstract class BaseClientCommand extends BaseCommand {

    @Inject
    @RestClient
    OpenBridgeClient client;

    @Inject
    OutputGeneratorFactoryImpl outputGeneratorFactory;
}
