package com.gamingreservation.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamingreservation.dto.ReservationDTO;
import com.gamingreservation.model.Reservation;
import com.gamingreservation.service.ReservationService;

@RestController
@RequestMapping("/api")
public class GameReservationController {

	private static Logger log = LogManager.getLogger(GameReservationController.class);

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/all/timeslots")
	public ResponseEntity<List<ReservationDTO>> getAllTimeSlot() {
		log.info("GameReservationController :: getAllTimeSlot() start :");
		List<Reservation> reservations = null;
		try {
			reservations = reservationService.findAllTimeSlot();
			if (!reservations.isEmpty()) {
				// List<ReservationDTO> dtos = reservations.stream().map(x-> mapper.map(x,
				// ReservationDTO.class)).collect(Collectors.toList());
				List<ReservationDTO> dtos = Stream.of(reservations).flatMap(List::stream)
						.map(x -> mapper.map(x, ReservationDTO.class)).collect(Collectors.toList());
				log.info("GameReservationController :: getAllTimeSlot() end :");
				return new ResponseEntity<>(dtos, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error("GameReservationController :: getAllTimeSlot() error :", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/user/{userId}/reserve")
	public ResponseEntity<ReservationDTO> reserveTimeSlot(@RequestBody ReservationDTO reservationDTO,
			@PathVariable("userId") int userId) {
		log.info("GameReservationController :: reserveTimeSlot() start :");
		Optional<Reservation> timeSlot = Optional.empty();
		try {
			timeSlot = reservationService.findReservedTimeSlot(reservationDTO.getGameId(),
					reservationDTO.getReservationStartTime(), reservationDTO.getReservationDate());
			if (!timeSlot.isPresent()) {
				Reservation reservation = reservationService.reserveTimeSlotByUser(reservationDTO, userId);
				log.info("GameReservationController :: reserveTimeSlot() start :");
				return new ResponseEntity<>(mapper.map(reservation, ReservationDTO.class), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error("GameReservationController :: reserveTimeSlot() error :", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getByGameId/{gameId}")
	public ResponseEntity<List<ReservationDTO>> getByGameId(@PathVariable int gameId) {
		log.info("GameReservationController :: getByGameId() start :");
		List<Reservation> reservations = null;
		try {
			reservations = reservationService.getByReservationPKIDGameId(gameId);
			List<ReservationDTO> reservationDtos = reservations.stream()
					.map(slot -> mapper.map(slot, ReservationDTO.class)).collect(Collectors.toList());
			if (!reservationDtos.isEmpty()) {
				log.info("GameReservationController :: getByGameId() end :");
				return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			log.error("GameReservationController :: getByGameId() error :", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getDataByDate")
	public ResponseEntity<ReservationDTO> getDataByGameIdAndDate(@RequestParam("gameId") int gameId,
			@RequestParam("reservationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = {
					"yyyy-MM-dd'T'HH:mm" }) LocalDateTime reservationDate) {
		log.info("GameReservationController :: getDataByGameIdAndDate() start :");
		Optional<Reservation> reservation = Optional.empty();
		try {
			reservation = reservationService.findTimeSlot(gameId, reservationDate);
			if (reservation.isPresent()) {
				log.info("GameReservationController :: getDataByGameIdAndDate() end :");
				return new ResponseEntity<>(mapper.map(reservation, ReservationDTO.class), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error("GameReservationController :: getDataByGameIdAndDate() error :", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/update/timeslot")
	public ResponseEntity<ReservationDTO> updateReserveTimeSlot(@RequestBody ReservationDTO reservation) {
		log.info("GameReservationController :: updateReserveTimeSlot() start :");
		Optional<Reservation> reserve = Optional.empty();
		Optional<Reservation> resOptional = Optional.empty();

		try {
			if (reservation != null && reservation.getGameId() != 0) {
				reserve = reservationService.findTimeSlot(reservation.getGameId(), reservation.getReservationDate());

				resOptional = reservationService.findReservedTimeSlot(reservation.getGameId(),
						reservation.getReservationStartTime(), reservation.getReservationEndTime());

				try {
					if (reserve != null && !resOptional.isPresent()) {
						reservationService.updateTimeSlot(reservation.getReservationStartTime(),
								reservation.getReservationEndTime(), reservation.getGameId(),
								reservation.getReservationDate());
						Optional<Reservation> reservationPlayerInformation = reservationService
								.findTimeSlot(reservation.getGameId(), reservation.getReservationDate());
						Reservation reservation2 = reservationPlayerInformation.get();
						if (reservation.getNumberOfPlayers() == 2) {
							reservation2.setNumberOfPlayers(reservation.getNumberOfPlayers());
							reservation2.setPlayerNameOne(reservation.getPlayerNameOne());
							reservation2.setPlayerNameTwo(reservation.getPlayerNameTwo());
							reservation2.setPlayerNameThree("");
							reservation2.setPlayerNameFour("");

						} else {
							reservation2.setNumberOfPlayers(reservation.getNumberOfPlayers());
							reservation2.setPlayerNameOne(reservation.getPlayerNameOne());
							reservation2.setPlayerNameTwo(reservation.getPlayerNameTwo());
							reservation2.setPlayerNameThree(reservation.getPlayerNameThree());
							reservation2.setPlayerNameFour(reservation.getPlayerNameFour());
						}

						Reservation reservationA = mapper.map(reservation2, Reservation.class);
						@SuppressWarnings("unused")
						Reservation reservationB = reservationService.reserveTimeSlot(reservationA);
						Optional<Reservation> reservationC = reservationService.findTimeSlot(reservation.getGameId(),
								reservation.getReservationDate());
						log.info("GameReservationController :: updateReserveTimeSlot() end :");
						return new ResponseEntity<>(mapper.map(reservationC, ReservationDTO.class), HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
				} catch (Exception e1) {
					log.error("GameReservationController :: updateReserveTimeSlot() error1 :", e1);
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error("GameReservationController :: updateReserveTimeSlot() error :", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/delete/timeslot")
	public ResponseEntity<String> deleteReserveTimeSlot(
			@RequestParam("reservationEndTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = {
					"yyyy-MM-dd'T'HH:mm" }) LocalDateTime reservationEndTime,
			@RequestParam("gameId") int gameId,
			@RequestParam("reservationStartTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = {
					"yyyy-MM-dd'T'HH:mm" }) LocalDateTime reservationStartTime) {
		log.info("GameReservationController :: deleteReserveTimeSlot() start :");
		Optional<Reservation> reOptional = Optional.empty();
		try {
			reOptional = reservationService.findReservedTimeSlot(gameId, reservationStartTime, reservationStartTime);
			if (reOptional.isPresent()) {
				reservationService.deleteTimeSlot(reservationEndTime, gameId, reservationStartTime);
				log.info("GameReservationController :: deleteReserveTimeSlot() end :");
				return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No Content", HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("GameReservationController :: deleteReserveTimeSlot() error :", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
