package com.redhat.service.bridge.cli.output;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.redhat.service.bridge.cli.config.Configuration;

@Singleton
public class OutputGeneratorFactoryImpl implements OutputGeneratorFactory {

    @Inject
    Configuration configuration;

    @Inject
    HumanOutputGenerator humanOutputGenerator;
    @Inject
    JsonOutputGenerator jsonOutputGenerator;

    @Override
    public OutputGenerator get() {
        switch (configuration.getOutputType()) {
            default:
            case HUMAN:
                return humanOutputGenerator;
            case JSON:
                return jsonOutputGenerator;
        }
    }
}
