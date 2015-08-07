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

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.node.Node;
import org.migor.strudel.domain.Document;
import org.migor.strudel.repositories.DocumentRepository;
import org.migor.strudel.service.DocumentService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Component
public class Strudel implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DocumentService documentService;

    //    http://blog.trifork.com/2012/09/13/elasticsearch-beyond-big-data-running-elasticsearch-embedded/
    public void synchronize() throws Exception {

        List<String> ignores = Arrays.asList(".idea", "target", ".strudel", ".git");

        Files.walk(Paths.get(new File("").getAbsolutePath())).forEach(filePath -> {
            try {
                String path = filePath.toString();
                boolean isIgnored = ignores.stream().anyMatch(ignore -> path.endsWith(ignore) || path.contains(ignore + File.separator));
                if (!Files.isHidden(filePath) && !isIgnored) {
                    index(filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void index(Path path) {
        String pathAsString = path.toString();
        try {
            String mime = Files.probeContentType(path);
            long size = Files.size(path);
            String id = StringUtils.substring(DigestUtils.md5DigestAsHex((pathAsString + System.nanoTime()).getBytes()), 0, 6);
            Path name = path.getFileName();
            System.out.printf("%s %s %s %s\n", path.toString(), name, mime, id);

            Document entity = new Document();
            entity.setId(id);
            entity.setName(id);
            entity.setSize(size);
            entity.setMime(mime);

            documentService.index(entity);

//            documentRepository.save(entity);

            // todo factory to build supported file pojos
//            IndexRequest request = Requests.indexRequest()
//                    .source("id", id)
//                    .source("name", name)
//                    .source("mime", mime)
//                    .source("size", size)
//                    .source("content", "empty")
//                    ;
//            IndexResponse response = node.client().index(request).actionGet();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            synchronize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
