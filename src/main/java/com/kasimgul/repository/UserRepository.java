package com.kasimgul.repository;

import com.kasimgul.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    List<User> findAll();
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    List<User> findAll(Sort sort);
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    void delete(String s);
//
//    // Todo: Current user should be able to delete his/her own account
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    void delete(User entity);
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    void delete(Iterable<? extends User> entities);
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    void deleteAll();
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    Page<User> findAll(Pageable pageable);
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    Iterable<User> findAll(Iterable<String> strings);
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    long count();
}
