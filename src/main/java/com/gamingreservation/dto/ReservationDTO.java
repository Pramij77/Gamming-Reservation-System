package com.gamingreservation.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 235337482416066498L;

	private int gameId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")
	private LocalDateTime reservationDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")

	private LocalDateTime reservationStartTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")
	private LocalDateTime reservationEndTime;

	private int numberOfPlayers;

	private String playerNameOne;

	private String playerNameTwo;

	private String playerNameThree;

	private String playerNameFour;
	
	private int userId;

}
