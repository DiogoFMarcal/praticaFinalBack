package com.diogo.backPraticaFinal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diogo.backPraticaFinal.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByEmail(String email);

	public Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findUserByEmailAndPassword(String email, String password);

	Optional<User> findByIban(String iban);


	@Query("select u from User u where u.id <> ?1 and u.email = ?2")
	public List<User> findUserWithDifferentIdAndSameEmail(Integer id, String email);

	@Query("select u from User u where u.id <> ?1 and u.iban = ?2")
	public List<User> findUserWithDifferentIdAndSameIban(Integer id, String iban);

}