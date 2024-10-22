package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.ReservationModel;
import com.nc13.moviemates.entity.ReservationEntity;
import com.querydsl.core.Tuple;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReservationService {
    List<ReservationEntity> findAll();

   Optional<ReservationEntity> findById(Long id);

    Boolean save(ReservationModel reservation);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);

    Boolean update(List<ReservationModel> reservationList);

    Long deleteMany(List<Long> reservationIdList);

    List<Map<String, Object>> findReservationWithMovieByUserId(Long userId);

    List<Map<String, Object>> findReservationWithScheduleByUserId(Long userId);
}
