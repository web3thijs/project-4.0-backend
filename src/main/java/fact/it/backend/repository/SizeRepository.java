package fact.it.backend.repository;

import fact.it.backend.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface SizeRepository extends JpaRepository<Size, Long> {
    Page<Size> findAll(Pageable pageable);
    Size findSizeById(long id);
}
