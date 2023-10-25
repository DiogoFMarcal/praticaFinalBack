package com.diogo.backPraticaFinal.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.diogo.backPraticaFinal.dtos.user.LoginRequestDTO;
import com.diogo.backPraticaFinal.models.User;
import com.diogo.backPraticaFinal.models.Wallet;
import com.diogo.backPraticaFinal.repositories.UserRepository;

@Service
public class UserService {

	//############################################################################################################

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CountriesService countryService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenService tokenService;

	//############################################################################################################

	public User createUser(User user) {
		Optional<User> result = userRepository.findByEmail(user.getEmail());
		if(result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, 
					String.format("There is already a registed user with the given email"));
		}else if(!countryService.checkCountryExists(user.getCountry())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					String.format("Country not found."));
		} else if(!isValidEmail(user.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					String.format("Invalid email."));	
		}else if(!isValidPassword(user.getPassword()) ) { 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					String.format("Invalid password, must contain at least 6 characters, 1 special character and a capital letter."));	
		}else if(!isValidIbanByPattern(user.getIban()) ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					String.format("Invalid iban, the first two characters must be letters and the remaining 22 must be numbers."));	
		} if(!isIbanFree(user.getIban())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					String.format("Iban already taken."));	
		}

		//encrypt password
		user.setPassword(passwordEncoder.encode( user.getPassword()));
		User savedUser = userRepository.save(user);

		//create de user wallet
		walletService.create(new Wallet(savedUser));
		return savedUser;
	}

	public User login ( LoginRequestDTO loginUser) {


		Optional<User> result = userRepository.findByEmail(loginUser.getEmail());

		if(result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, 
					String.format("Invalid user credentials."));	
		}

		boolean validPassword = passwordEncoder.matches(loginUser.getPassword(), result.get().getPassword());

		if(!result.isPresent() || !validPassword) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, 
					String.format("Invalid user credentials."));	
		}

		return result.get();
	}

	public List<User> getAll() {

		return userRepository.findAll();
	}

	public User getUserByIban(String iban) {
		Optional<User> result = userRepository.findByIban(iban);
		if(result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Iban not found");
		}

		return result.get();
	}

	private boolean  isValidPassword(String password)  {

		if (password == null || password.length() <6) {
			return false;
		}

		String regex = "^(?=.*[A-Z])(?=.*[@#$%^&-+=()]).{6,}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(password);
		return m.matches();
	}

	private boolean isValidEmail(String email ) {

		if(email == null) return false;

		String regexPattern = "^(.+)@(\\S+\\.\\S+)$";
		return Pattern.compile(regexPattern)
				.matcher(email)
				.matches();
	}

	private boolean isValidIbanByPattern(String iban ) {

		if(iban == null) return false;

		String regexPattern = "^[A-Za-z]{2}\\d{22}$";
		return  Pattern.compile(regexPattern)
				.matcher(iban)
				.matches();	
	}

	private boolean isIbanFree(String iban) {
		//check if iban is already taken
		Optional<User> result = userRepository.findByIban(iban);

		return result.isEmpty();
	}

	public void editUser(String token, User user, @Valid User userToEdit) {

		userToEdit.setId(user.getId());

		//check if the email is already in use by some user that is not the user to edit
		List<User> usersWithSameEmailAndDiffId = userRepository.findUserWithDifferentIdAndSameEmail(userToEdit.getId(), userToEdit.getEmail());

		if(!usersWithSameEmailAndDiffId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use.");
		}

		//check if the iban is already in use for some user that is not the user to edit
		List<User> usersWithSameIbanAndDiffId = userRepository.findUserWithDifferentIdAndSameIban(userToEdit.getId(), userToEdit.getIban());

		if(!usersWithSameIbanAndDiffId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Iban already in use.");
		}

		//in this step the password is not being changed so we set the editPassword to the old one
		userToEdit.setPassword(user.getPassword());

		User editedUser = userRepository.save(userToEdit);	

		//set the edited user in the map that holds the users in token service
		tokenService.editUserInActiveSessionsByToken(token, editedUser);

	}

	public void changePassword(String token, User user, String oldPassword, String newPassword) {
		if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong old password");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		tokenService.editUserInActiveSessionsByToken(token, user);
	}
}