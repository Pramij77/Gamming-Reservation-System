package com.gamingreservation.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReservationPKId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4166249055570400770L;

	@Column(name = "game_id", length = 2)
	private int gameId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")
	@Column(name = "reservation_date")
	private LocalDateTime reservationDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")
	@Column(name = "start_time")
	private LocalDateTime reservationStartTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")
	@Column(name = "end_time")
	private LocalDateTime reservationEndTime;
}
