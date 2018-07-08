package com.swisscom.oce.elasticfart.order;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.store.HttpProxyFactory;
import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoTestConfig {

    @Bean
    public IRuntimeConfig mongodConfig() {

        final Command command = Command.MongoD;

        final IDownloadConfig downloadConfig = new DownloadConfigBuilder()
                .defaultsForCommand(command)
                .proxyFactory(new HttpProxyFactory("proxy.corproot.net", 8079))
                .build();


        return new RuntimeConfigBuilder()
                .defaults(command)
                .artifactStore(new ExtractedArtifactStoreBuilder()
                        .defaults(command)
                        .download(downloadConfig))
                .build();
    }
}