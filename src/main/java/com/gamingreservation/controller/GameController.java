package com.gamingreservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamingreservation.model.Game;
import com.gamingreservation.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@PostMapping("/saveGame")
	public ResponseEntity<Game> save(@RequestBody Game game){
		return new ResponseEntity<>(gameService.saveGame(game), HttpStatus.CREATED);
	}
}
