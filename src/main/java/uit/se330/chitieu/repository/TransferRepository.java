package uit.se330.chitieu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uit.se330.chitieu.entity.Transfer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID>, JpaSpecificationExecutor<Transfer> {
    @Query("SELECT t " +
            "FROM Transfer t " +
            "WHERE t.id = :id " +
            "AND " +
                "(t.sourceaccountid.userid = :userId " +
                "OR t.targetaccountid.userid = :userId)")
    Optional<Transfer> findByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);
}
