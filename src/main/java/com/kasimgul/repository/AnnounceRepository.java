package com.kasimgul.repository;

import com.kasimgul.domain.Announce;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "announces", path = "announces")
public interface AnnounceRepository extends MongoRepository<Announce, String> {

}
