package org.migor.strudel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by damoeb on 07.08.15.
 */

@org.springframework.data.elasticsearch.annotations.Document(indexName = "book", type = "book", indexStoreType = "memory", shards = 1, replicas = 0, refreshInterval = "-1")
public class Document {

    @Id
    private String id;
    private String name;
    private long size;
    private String mime;
//    @Field(type = FieldType.Object)
//    private Author author;
//    @Field(type = FieldType.Nested)
//    private Map<Integer, Collection<String>> buckets = new HashMap<Integer, Collection<String>>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getMime() {
        return mime;
    }
}
