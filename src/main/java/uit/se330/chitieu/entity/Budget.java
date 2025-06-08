package uit.se330.chitieu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "\"Budget\"")
public class Budget {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(name = "startdate", nullable = false)
    private LocalDate startdate;

    @NotNull
    @Column(name = "enddate", nullable = false)
    private LocalDate enddate;

    @NotNull
    @Column(name = "period", nullable = false)
    private Integer period;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "current", nullable = false)
    private BigDecimal current;

    @NotNull
    @Column(name = "budgetlimit", nullable = false)
    private BigDecimal budgetlimit;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User userid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryid")
    private Category categoryid;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        if (createdat == null) createdat = now;
        if (updatedat == null) updatedat = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedat = Instant.now();
    }
}