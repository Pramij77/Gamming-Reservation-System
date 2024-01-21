package com.gamingreservation.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trn_reserve")
@IdClass(ReservationPKId.class)
@Builder
public class Reservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8956582661503620730L;

	@Id
	@Column(name = "game_id", length = 2)
	private int gameId;

	@Id
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")
	@Column(name = "reservation_date")
	private LocalDateTime reservationDate;

	@Id
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")
	@Column(name = "start_time")
	private LocalDateTime reservationStartTime;

	@Id
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = { "yyyy-MM-dd'T'HH:mm" })
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Kolkata")
	@Column(name = "end_time")
	private LocalDateTime reservationEndTime;

	@Column(name = "no_of_players", length = 1)
	private int numberOfPlayers;

	@Column(name = "player_no_one")
	private String playerNameOne;

	@Column(name = "player_no_two")
	private String playerNameTwo;

	@Column(name = "player_no_three")
	private String playerNameThree;

	@Column(name = "player_no_four")
	private String playerNameFour;

	@ManyToOne
	@JoinColumn(name = "fk_user_id")
	private UserModel userModel;

}
