package dbdemo.mysql.repository;

import dbdemo.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByNameLike(String pattern);

    User readByName(String user);

    User getByCreatedateLessThan(Date date);
}
