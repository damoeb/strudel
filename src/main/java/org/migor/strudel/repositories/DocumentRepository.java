package org.migor.strudel.repositories;

import org.migor.strudel.domain.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by damoeb on 07.08.15.
 */
public interface DocumentRepository extends ElasticsearchCrudRepository<Document, String> {

}
