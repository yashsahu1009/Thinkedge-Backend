package in.thinkedge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.thinkedge.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, String>{


}
