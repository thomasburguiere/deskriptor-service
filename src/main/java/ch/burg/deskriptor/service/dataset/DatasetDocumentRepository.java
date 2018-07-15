package ch.burg.deskriptor.service.dataset;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetDocumentRepository extends MongoRepository<DatasetDocument, String> {
}
