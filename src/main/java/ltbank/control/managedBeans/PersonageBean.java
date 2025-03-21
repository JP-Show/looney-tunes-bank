package ltbank.control.managedBeans;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import ltbank.model.personage.PersonaException;
import ltbank.model.personage.Personage;
import ltbank.model.personage.PersonageDAO;
import ltbank.model.personage.PersonageDTO;

@Stateless
public class PersonageBean {
	
	@EJB
	private PersonageDAO personageService;
	private final Argon2PasswordEncoder ARGONENCODER = new Argon2PasswordEncoder(16, 64, 2, 128000, 10);
	
	public void sendPersona(Personage personage) {
		if(personage == null) throw new PersonaException("personage is null");
		checkName(personage.getName());
		if(personage.getLastUpdate() == null) throw new  PersonaException("LastUpdate is null");
		if(personage.getMoney() == null) personage.setMoney(new BigDecimal(0));
		if(personage.getPassword() == null) throw new PersonaException("Your password is null");
		if(personage.getPassword().length() <= 15) throw new PersonaException("Your password is less than 15 character");
		if(personage.getPassword().length() > 100) throw new PersonaException("Your password is bigger than 100 character");
		
		try {
			String crypted = criptPassword(personage.getPassword());
			personage.setPassword(crypted);
			personageService.create(personage);			
		}catch (Exception e) {
			throw new  PersonaException("Something went wrong by creating a new character: \n" + e);
		}
	}
	public void updateName(String newName, long id, ZonedDateTime zdt, String password) {
		try {
			Personage currentPersonage = personageService.findById(id);
			if(!matchPassword(password,currentPersonage.getPassword()))
				throw new RuntimeException("Password nothing match");
			checkName(newName);
			personageService.updateName(newName, id, zdt );
		}catch(Exception e) {
			throw new PersonaException("Something went wrong while updating your name: " + e);
		}
	}
	public void deleteMySelf(long id ,String password) {
		try {
			Personage currentPersonage = personageService.findById(id);
			if(!matchPassword(password,currentPersonage.getPassword()))
				throw new RuntimeException("Password nothing match");
			personageService.deleteById(currentPersonage);
		}catch (Exception e) {
			throw new PersonaException("Something went wrong while deleting you: \n" + e);
		}
	}
	
	public Personage findPersoge(PersonageDTO pDTO) {
		if(pDTO == null) throw new PersonaException("PersonageDTO is null");
		Personage p = personageService.findById(pDTO.getId());
		if(p.getName() == pDTO.getName()) throw new PersonaException("Personage not match with PersonageDTO");
		return p;
	}
	
	public List<PersonageDTO> getAllPersonage() {
		List<PersonageDTO> personagesDTOList = new ArrayList<>();		
		try {
			List<Personage> personagesList = personageService.findAll();
			for (Personage personage : personagesList) {
				personagesDTOList.add(new PersonageDTO(personage.getId(), personage.getName()));
			}
		} catch (Exception e) {
			throw new PersonaException("Something went wrong while getting all Personage: \n" + e);
		}
		return personagesDTOList;
	}
	
	public Personage login(long id ,String password) {
		try {
			Personage p = personageService.findById(id);
			if(matchPassword(password, p.getPassword())) {
				return p;
			}
			else throw new PersonaException("Wrong password");
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	private void checkName(String name) {
		if(name == null) throw new  PersonaException("Your is null");
		if(name.length() > 20) throw new  PersonaException("Your name exceed 20 character");
		if(name.length() < 1)throw new  PersonaException("Please, type your name or something else");
	}
	private boolean matchPassword(CharSequence rawPassword, String enconderPassword) {
		boolean match = ARGONENCODER.matches(rawPassword, enconderPassword);
		return match;
	}
	private String criptPassword(String password) {
		String passwordEncrypted = ARGONENCODER.encode(password);
		return passwordEncrypted;
	}
}
