///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.quarkus:quarkus-picocli:2.7.1.Final
//DEPS com.redhat.service.bridge.cli:logic:1.0.0-SNAPSHOT
//JAVAC_OPTIONS -parameters

//FILES reflection-config.json
//FILES resources-config.json

import javax.inject.Inject;

import com.redhat.service.bridge.cli.command.MainCommandFactory;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ob implements QuarkusApplication {

    @Inject
    MainCommandFactory commandFactory;

    @Override
    public int run(String... args) throws Exception {
        return commandFactory.build().execute(args);
    }
}
