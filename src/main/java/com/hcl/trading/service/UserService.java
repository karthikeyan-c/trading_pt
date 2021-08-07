package com.hcl.trading.service;

import com.hcl.trading.repository.AuditUserRepository;
import com.hcl.trading.repository.UserRepository;
import com.hcl.trading.entity.AuditUserLogin;
import com.hcl.trading.entity.User;
import com.hcl.trading.repository.ConfirmationToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final ConfirmationTokenService confirmationTokenService;

	private final AuditUserRepository auditUserRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("insider1 validation");
		final Optional<User> optionalUser = userRepository.findByEmail(email);
		log.info("insider validation");
		return optionalUser.orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email)));
	}

	public void signUpUser(User user) {

		final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());

		user.setPassword(encryptedPassword);

		final User createdUser = userRepository.save(user);

		final ConfirmationToken confirmationToken = new ConfirmationToken(user);

		confirmationTokenService.saveConfirmationToken(confirmationToken);

//		sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
	}

	public User getUserDetails(String name) {
		final Optional<User> optionalUser = userRepository.findByName(name);
		return optionalUser.orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with name {0} cannot be found.", name)));
	}

	public void auditLogin(User userDetails, HttpSession session){
		AuditUserLogin auditUserLogin = new AuditUserLogin();
		log.info("userDetails.getId() " + userDetails.getId());
		auditUserLogin.setUserId(userDetails.getId());
		auditUserLogin.setSessionId(session.getId());
		auditUserLogin.setLoginDateTime(new Timestamp(System.currentTimeMillis()));
		auditUserLogin.setLoginStatus("Active");

		auditUserRepository.save(auditUserLogin);
		List<AuditUserLogin> all = auditUserRepository.findAll();
		log.info(" all audits "+ all.get(0));
	}

	public void auditLogout(String name){
		User userDetails = getUserDetails(name);
		AuditUserLogin auditUserLogin = auditUserRepository.findByUserId(userDetails.getId());
		auditUserLogin.setLogoutDateTime(new Timestamp(System.currentTimeMillis()));
		auditUserLogin.setLoginStatus("Loggedout");

		auditUserRepository.save(auditUserLogin);
		List<AuditUserLogin> all = auditUserRepository.findAll();
		log.info(" all audits "+ all.get(0));
	}
}
