package com.gamingreservation.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gamingreservation.dao.ReservationRepository;
import com.gamingreservation.dao.UserModelRepository;
import com.gamingreservation.dto.ReservationDTO;
import com.gamingreservation.exception.RecordNotFoundException;
import com.gamingreservation.model.Reservation;
import com.gamingreservation.model.UserModel;
import com.gamingreservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static Logger log = LogManager.getLogger(ReservationServiceImpl.class);

    private ReservationRepository reservationRepository;

    private UserModelRepository userModelRepository;

    private ModelMapper mapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserModelRepository userModelRepository, ModelMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.userModelRepository = userModelRepository;
        this.mapper = mapper;
    }

    /**
     * @param gameId
     * @return
     */
    @Override
    public List<Reservation> getByReservationPKIDGameId(int gameId) throws RecordNotFoundException {
        log.info("ReservationServiceImpl :: getByReservationPKIDGameId() start :");
        List<Reservation> reservations = reservationRepository.findAllByGameId(gameId);
        if (reservations.isEmpty()) {
            log.error("ReservationServiceImpl :: getByReservationPKIDGameId() error :");
            throw new RecordNotFoundException("Data is not available for this game Id : " + gameId);
        } else {
            log.info("ReservationServiceImpl :: getByReservationPKIDGameId() end :");
            return reservations;
        }

    }

    /**
     * @return
     */
    @Override
    public List<Reservation> findAllTimeSlot() {
        log.info("ReservationServiceImpl :: findAllTimeSlot() start :");
        List<Reservation> reservations = null;
        try {
            reservations = reservationRepository.findAll();
        } catch (Exception e) {
            log.error("ReservationServiceImpl :: findAllTimeSlot() error :", e);
        }
        log.info("ReservationServiceImpl :: findAllTimeSlot() start :");
        return reservations;
    }

    /**
     * @param reservation
     * @return
     */
    @Override
    public Reservation reserveTimeSlot(Reservation reservation) {
        Reservation reservation1 = null;
        try {
            reservation1 = reservationRepository.save(reservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservation1;
    }

    /**
     * @param reservationDTO
     * @param userId
     * @return
     */
    @Override
    public Reservation reserveTimeSlotByUser(ReservationDTO reservationDTO, int userId) {
        log.info("ReservationServiceImpl :: reserveTimeSlotByUser() start :");
        Optional<UserModel> userModel = userModelRepository.findById(userId);
        Reservation reservation = null;
        try {
            if (userModel.isPresent()) {
                UserModel userModel1 = userModel.get();
                reservation = mapper.map(reservationDTO, Reservation.class);
                reservation.setUserModel(userModel1);
            }
        } catch (Exception e) {
            log.error("ReservationServiceImpl :: reserveTimeSlotByUser() error :", e);
        }
        log.info("ReservationServiceImpl :: reserveTimeSlotByUser() start :");
        return reservationRepository.save(reservation);
    }

    /**
     * @param reservationEndTime
     * @param gameId
     * @param reservationStartTime
     * @return
     */
    @Override
    public boolean deleteTimeSlot(LocalDateTime reservationEndTime, int gameId, LocalDateTime reservationStartTime) {
        log.info("ReservationServiceImpl :: deleteTimeSlot() start :");
        try {
            reservationRepository.deleteByGameId(reservationStartTime, gameId, reservationEndTime);
        } catch (Exception e) {
            log.error("ReservationServiceImpl :: deleteTimeSlot() error :", e);
        }
        log.info("ReservationServiceImpl :: deleteTimeSlot() end :");
        return true;

    }

    /**
     * @param reservationStartTime
     * @param reservationEndTime
     * @param gameId
     * @param reservationDate
     * @return
     */
    @Override
    public boolean updateTimeSlot(LocalDateTime reservationStartTime, LocalDateTime reservationEndTime, int gameId,
                                  LocalDateTime reservationDate) {
        log.info("ReservationServiceImpl :: updateTimeSlot() start :");
        try {
            reservationRepository.updateByGameId(reservationStartTime, reservationEndTime, gameId, reservationDate);
        } catch (Exception e) {
            log.error("ReservationServiceImpl :: updateTimeSlot() error :", e);
        }
        log.info("ReservationServiceImpl :: updateTimeSlot() end :");
        return true;
    }

    /**
     * @param gameId
     * @param reservationStartTime
     * @param reservationEndTime
     * @return
     */
    @Override
    public Optional<Reservation> findTimeSLotByGameIdStartEndTime(int gameId, LocalDateTime reservationStartTime,
                                                                  LocalDateTime reservationEndTime) {
        log.info("ReservationServiceImpl :: findTimeSLotByGameIdStartEndTime() start :");
        Optional<Reservation> reservation = Optional.empty();
        try {
            reservation = reservationRepository.findTimeSlotByGameId(gameId, reservationStartTime, reservationEndTime);
        } catch (Exception e) {
            log.error("ReservationServiceImpl :: findTimeSLotByGameIdStartEndTime() error :", e);
        }
        log.info("ReservationServiceImpl :: findTimeSLotByGameIdStartEndTime() end :");
        return reservation;
    }

    /**
     * @param gameId
     * @param reservationDate
     * @return
     */
    @Override
    public Optional<Reservation> findTimeSlot(int gameId, LocalDateTime reservationDate) {
        log.info("ReservationServiceImpl :: findTimeSlot() start :");
        Optional<Reservation> reservation = Optional.empty();
        try {
            reservation = reservationRepository.findTimeSlotByGameIdAndReservationDate(gameId, reservationDate);
        } catch (Exception e) {
            log.error("ReservationServiceImpl :: findTimeSlot() error :", e);
        }
        log.info("ReservationServiceImpl :: findTimeSlot() end :");
        return reservation;
    }

    /**
     * @param gameId
     * @param reservationStartTime
     * @param reservationDate
     * @return
     */
    @Override
    public Optional<Reservation> findReservedTimeSlot(int gameId, LocalDateTime reservationStartTime,
                                                      LocalDateTime reservationDate) {
        log.info("ReservationServiceImpl :: findReservedTimeSlot() start :");
        Optional<Reservation> reservation = Optional.empty();
        try {
            reservation = reservationRepository.findTimeSlotByGameId(gameId, reservationStartTime, reservationDate);
        } catch (Exception e) {
            log.error("ReservationServiceImpl :: findReservedTimeSlot() error :", e);
        }
        log.info("ReservationServiceImpl :: findReservedTimeSlot() end :");
        return reservation;
    }
}
