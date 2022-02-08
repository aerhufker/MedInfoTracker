// <<<<<<< MTW1
package com.medinfotracker.medinfotracker.models.data;

import com.medinfotracker.medinfotracker.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserName(String username);
}
// =======
// //package com.medinfotracker.medinfotracker.models.data;
// //
// //
// //import com.medinfotracker.medinfotracker.models.UserProfile;
// //import org.springframework.data.repository.CrudRepository;
// //
// //public interface UserRepository extends CrudRepository<UserProfile, Integer>{
// //
// //}
// >>>>>>> merging
