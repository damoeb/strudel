package org.migor.strudel.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;

/**
 * Created by damoeb on 07.08.15.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "org/migor/strudel/repositories")
@EnableTransactionManagement
public class ElasticSearch {

    @Bean
    public Node getElasticSearchNode() {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("node.name", "orange11-node")
                .put("path.data", "data/index")
                .put("http.enabled", true)
                .build();

        return NodeBuilder.nodeBuilder()
                .settings(settings)
                .clusterName("strudel-cluster")
                .data(true).local(true).node();
    }

    @Bean
    public Client getElasticSearchClient() {
        return getElasticSearchNode().client();
    }

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchTemplate getElasticsearchTemplate() {
        return new org.springframework.data.elasticsearch.core.ElasticsearchTemplate(getElasticSearchClient());
    }

    @PreDestroy
    public void stopElasticSearch() {
        getElasticSearchNode().close();
    }
}
