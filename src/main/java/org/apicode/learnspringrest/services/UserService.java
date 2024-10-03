package org.apicode.learnspringrest.services;

import org.hibernate.query.Query;
import org.apicode.learnspringrest.api.model.User;
import org.apicode.learnspringrest.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private Session session = HibernateUtil.getSession();

//    get requests
    public Optional<List<User>> getAllUsers() {
        Optional<List<User>> optional = Optional.empty();
        List<User> users;
        try {
            users = session.createQuery("from org.apicode.learnspringrest.api.model.User").list();
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return optional.of(users);
    }

    public Optional<User> getUserById(int id) {
        Optional<User> optional = Optional.empty();
        try {
            optional = Optional.of(session.get(User.class, id));
        }catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return optional;
    }

    public Optional<User> getUserByEmail(String email) {
        Optional<User> optional = Optional.empty();
        try {
            // Create a query to find the user by email
            Query query = session.createQuery("from org.apicode.learnspringrest.api.model.User where email = :email", User.class);
            query.setParameter("email", email);
            optional = Optional.ofNullable((User) query.uniqueResult()); // Fetch single result, or null if not found
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return optional;
    }


//    post requests
    public void createUser(User user) {
        try {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        }catch
        (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //put requests
    public Optional<User> updateUser(User user) {
        Optional<User> optional = Optional.empty();
        try {
            Transaction tx = session.beginTransaction();
            User existingUser = session.get(User.class, user.getId());
            if(existingUser != null) {
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                session.update(existingUser);
                optional = Optional.of(existingUser);
            }else {
                throw new RuntimeException("User not found with id: " + user.getId());
            }
            tx.commit();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return optional;
    }
}
