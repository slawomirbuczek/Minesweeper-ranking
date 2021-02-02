package minesweeper_ranking.service;

import lombok.AllArgsConstructor;
import minesweeper_ranking.dto.PlayerDto;
import minesweeper_ranking.exceptions.UserAlreadyExistsException;
import minesweeper_ranking.model.Player;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseMessage addPlayer(PlayerDto playerDto) {
        String username = playerDto.getUsername();

        if (playerRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        playerDto.setPassword(passwordEncoder.encode(playerDto.getPassword()));

        Player player = new ModelMapper().map(playerDto, Player.class);
        playerRepository.save(player);

        return new ResponseMessage("Registered successfully");
    }

}
