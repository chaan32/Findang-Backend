package com.chaan32.FindangBackend.repository;

import com.chaan32.FindangBackend.domain.InspectionRecord;
import com.chaan32.FindangBackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionRecordRepository extends JpaRepository<InspectionRecord, Long> {
    List<InspectionRecord> findByUser(User user);
}
