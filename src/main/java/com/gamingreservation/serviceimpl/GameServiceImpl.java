package com.gamingreservation.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamingreservation.dao.GameRepository;
import com.gamingreservation.model.Game;
import com.gamingreservation.service.GameService;

@Service
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository repository;

	@Override
	public Game saveGame(Game game) {
		// TODO Auto-generated method stub
		return repository.save(game);
	}
}
