package ch.burg.deskriptor.service.dataset;

import ch.burg.deskriptor.engine.model.Dataset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRepository extends MongoRepository<Dataset, String> {
}
