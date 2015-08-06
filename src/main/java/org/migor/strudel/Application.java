/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.migor.strudel;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	private Node node;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void runElasticSearch() throws Exception {
		ImmutableSettings.Builder settings =
				ImmutableSettings.settingsBuilder();
		settings.put("node.name", "orange11-node");
		settings.put("path.data", "./.strudel/data/index");
		settings.put("http.enabled", false);
		node = NodeBuilder.nodeBuilder()
				.settings(settings)
				.clusterName("strudel-cluster")
				.data(true).local(true).node();
	}

	@PreDestroy
	public void stopElasticSearch() throws Exception {
		node.close();
	}

}
