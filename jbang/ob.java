///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.quarkus:quarkus-picocli:2.7.1.Final
//DEPS com.redhat.service.bridge.cli:logic:1.0.0-SNAPSHOT
//JAVAC_OPTIONS -parameters

//FILES reflection-config.json
//FILES resources-config.json

import javax.inject.Inject;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;

import com.redhat.service.bridge.cli.command.MainCommand;

@QuarkusMain
public class ob implements QuarkusApplication {

    @Inject
    CommandLine.IFactory factory;

    @Inject
    MainCommand command;

    @Override
    public int run(String... args) throws Exception {
        return new CommandLine(command, factory)
                .execute(args);
    }
}
