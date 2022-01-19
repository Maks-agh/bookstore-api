package com.bookstore.domain.report;

import javax.persistence.EntityManager;
import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReportRepository {

    private final EntityManager entityManager;

    List<ReportProjection> buildReport() {
        return entityManager.createQuery(
                "SELECT new com.bookstore.domain.report.ReportProjection(c.name, c.address, o.receivedAt, o.packedAt, o.sentAt, o.status, o.id) " +
                        "FROM OrderEntity AS o JOIN CustomerEntity AS c ON o.createdBy = c.id",
                ReportProjection.class
        ).getResultList();
    }
}
