package com.hcl.trading.service;

import com.hcl.trading.repository.ConfirmationToken;
import com.hcl.trading.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public
class ConfirmationTokenService {

	private final ConfirmationTokenRepository confirmationTokenRepository;

	void saveConfirmationToken(ConfirmationToken confirmationToken) {

		confirmationTokenRepository.save(confirmationToken);
	}

	void deleteConfirmationToken(Long id) {

		confirmationTokenRepository.deleteById(id);
	}


	Optional<ConfirmationToken> findConfirmationTokenByToken(String token) {

		return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
	}

}
