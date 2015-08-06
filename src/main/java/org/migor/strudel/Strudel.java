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

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Component
public class Strudel {

    //    http://blog.trifork.com/2012/09/13/elasticsearch-beyond-big-data-running-elasticsearch-embedded/
    @PostConstruct
    public void synchronize() throws Exception {

        List<String> ignores = Arrays.asList(".idea", "target", ".strudel");

        Files.walk(Paths.get(new File("").getAbsolutePath())).parallel().forEach(filePath -> {
            try {
                String path = filePath.toString();
                boolean isIngored = ignores.stream().anyMatch(ignore -> path.endsWith(ignore) || path.contains(ignore + File.separator));
                if (!Files.isHidden(filePath) && !isIngored) {
                    System.out.println(filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
