package com.gamingreservation.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mst_game")
public class Game {
	
    @Id
    @Column(name = "game_id", length = 2)
    private int gameId;

    @Column(name = "game_name")
    private String gameName;
}
