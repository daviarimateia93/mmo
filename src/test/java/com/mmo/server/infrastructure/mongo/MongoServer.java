package com.mmo.server.infrastructure.mongo;

import java.io.IOException;
import java.util.Objects;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class MongoServer {

    private static MongodStarter starter = MongodStarter.getDefaultInstance();
    private static MongoServer instance;

    private MongodExecutable executable;
    private MongodProcess process;

    protected MongoServer() {
        start();
    }

    public static MongoServer getInstance() {
        if (Objects.isNull(instance)) {
            instance = new MongoServer();
        }

        return instance;
    }

    public void start() {
        if (Objects.nonNull(executable) && Objects.nonNull(process))
            return;

        start(0);
    }

    private void start(int counter) {
        if (counter > 3)
            return;

        try {
            executable = starter.prepare(ImmutableMongodConfig.builder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net("localhost", 27017, Network.localhostIsIPv6()))
                    .build());
            process = executable.start();
        } catch (IOException exception) {
            start(++counter);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void stop() {
        if (Objects.isNull(executable) && Objects.isNull(process))
            return;

        process.stop();
        executable.stop();
    }
}
