package com.gamingreservation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.gamingreservation.dto.ReservationDTO;
import com.gamingreservation.exception.RecordNotFoundException;
import com.gamingreservation.model.Reservation;

public interface ReservationService {
    List<Reservation> getByReservationPKIDGameId(int gameId) throws RecordNotFoundException;
    List<Reservation> findAllTimeSlot();
    Reservation reserveTimeSlot(Reservation reservation);
    Reservation reserveTimeSlotByUser(ReservationDTO  reservationDTO, int userId);
    boolean deleteTimeSlot(LocalDateTime reservationEndTime, int gameId, LocalDateTime reservationStartTime);
    boolean updateTimeSlot(LocalDateTime reservationStartTime,
                          LocalDateTime reservationEndTime, int gameId,
                          LocalDateTime reservationDate);

    Optional<Reservation> findTimeSLotByGameIdStartEndTime(int gameId,
                                                 LocalDateTime reservationStartTime,
                                                 LocalDateTime reservationEndTime);

    Optional<Reservation> findTimeSlot(int gameId, LocalDateTime reservationDate);
    Optional<Reservation> findReservedTimeSlot(int gameId, LocalDateTime reservationStartTime,
                                     LocalDateTime reservationDate);
}
