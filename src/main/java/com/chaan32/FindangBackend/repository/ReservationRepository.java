package com.chaan32.FindangBackend.repository;

import com.chaan32.FindangBackend.domain.Reservation;
import com.chaan32.FindangBackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> user(User user);

    List<Reservation> findByUser_UserId(Long userId);
}
