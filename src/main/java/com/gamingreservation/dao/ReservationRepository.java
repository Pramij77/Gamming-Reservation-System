package com.gamingreservation.dao;

import com.gamingreservation.model.Reservation;
import com.gamingreservation.model.ReservationPKId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationPKId> {

	List<Reservation> findAllByGameId(int gameId);

	@Query(value = "DELETE from Reservation r WHERE (r.reservationStartTime= :reservationStartTime) AND"
			+ "(r.gameId= :gameId) AND (r.reservationEndTime= :reservationEndTime)")
	void deleteByGameId(@Param("reservationStartTime") LocalDateTime reservationStartTime, @Param("gameId") int gameId,
			@Param("reservationEndTime") LocalDateTime reservationEndTime);

	@Query(value = "UPDATE Reservation u set u.reservationStartTime=?1, u.reservationEndTime=?2"
			+ "where (u.gameId=?3) AND (u.reservationDate=?4)")
	void updateByGameId(LocalDateTime reservationStartTime, LocalDateTime reservationEndTime, int gameId,
			LocalDateTime reservationDate);

	@Query(value = "SELECT e from Reservation e where (e.gameId= :gameId) AND (e.reservationStartTime= :reservationStartTime) AND"
			+ "(e.reservationEndTime= :reservationEndTime)")
	Optional<Reservation> findTimeSlotByGameId(@Param("gameId") int gameId,
			@Param("reservationStartTime") LocalDateTime reservationStartTime,
			@Param("reservationEndTime") LocalDateTime reservationEndTime);

	Optional<Reservation> findTimeSlotByGameIdAndReservationDate(int gameId, LocalDateTime reservationDate);

	@Query(value = "SELECT u from Reservation u WHERE (u.gameId=?1) AND (u.reservationStartTime=?2)")
	Optional<Reservation> findTimeSlotByGameIdStartTimeDate(int gameId, LocalDateTime reservationStartTime);
}
