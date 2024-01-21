package com.gamingreservation.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.gamingreservation.dao.ReservationRepository;
import com.gamingreservation.exception.RecordNotFoundException;
import com.gamingreservation.model.Reservation;

public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByReservationPKIDGameId_success() throws RecordNotFoundException {
        int gameId = 123;
        Reservation reservation = Reservation.builder().gameId(gameId)
                .numberOfPlayers(2).playerNameOne("Pramij").playerNameTwo("Priya").build();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        Mockito.when(reservationRepository.findAllByGameId(gameId)).thenReturn(reservations);

        List<Reservation> result = reservationService.getByReservationPKIDGameId(gameId);
        Assertions.assertEquals(1,result.size());
        Mockito.verify(reservationRepository).findAllByGameId(gameId);
    }
    @Test
    public void testGetByReservationPKIDGameId_failure() throws RecordNotFoundException{
        Reservation reservation = Reservation.builder().gameId(123)
                .numberOfPlayers(2).playerNameOne("Pramij").playerNameTwo("Priya").build();

        Mockito.when(reservationRepository.findAllByGameId(reservation.getGameId()))
                        .thenReturn(Collections.emptyList());

        Assertions.assertThrows(RecordNotFoundException.class, ()-> {
            reservationService.getByReservationPKIDGameId(reservation.getGameId());
        });
        Mockito.verify(reservationRepository).findAllByGameId(reservation.getGameId());
    }
    
  
    

}
