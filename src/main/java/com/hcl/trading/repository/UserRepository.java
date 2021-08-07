package com.hcl.trading.repository;

import com.hcl.trading.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on September, 2019
 *
 * @author kamer
 */
@Repository
public
interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);
	Optional<User> findByName(String name);

//	@Query("UPDATE Users u SET u.lastLogin=:lastLogin WHERE u.username = ?#{ principal?.username }")
//	@Modifying
//	@Transactional
//	void updateLastLogin(@Param("lastLogin") Date lastLogin);
}
