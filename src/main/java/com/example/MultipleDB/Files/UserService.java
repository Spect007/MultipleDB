package com.example.MultipleDB.Files;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    Repo userRepository;
    DbContextHolder DataSourceContextHolder;
    // @Transactional
    // public User createUser(User user, String dataSource) {
    //     try {
    //         DataSourceContextHolder.setDataSource(dataSource);
    //         return userRepository.save(user);
    //     } finally {
    //         DataSourceContextHolder.clear();
    //     }
    // }

    // // Get all users
    // public List<User> getAllUsers() {
    //     return userRepository.findAll();
    // }

    // // Get user by ID
    // public Optional<User> getUserById(int id) {
    //     return userRepository.findById(id);
    // }

    // // Update user
    // public User updateUser(int id, User userDetails) {
    //     return userRepository.findById(id)
    //             .map(user -> {
    //                 user.setVal(userDetails.getVal());
    //                 user.setVal2(userDetails.getVal2());
    //                 return userRepository.save(user);
    //             })
    //             .orElseGet(() -> {
    //                 userDetails.setId(id);
    //                 return userRepository.save(userDetails);
    //             });
    // }

    // // Delete user
    // public void deleteUser(int id) {
    //     userRepository.deleteById(id);
    // }



    @Transactional
    public User createUser(User user, DbType dataSourceType) {
        try {
            // Set the current data source before the operation
            DbContextHolder.setCurrentDb(dataSourceType);
            return userRepository.save(user);
        } finally {
            // Clear the context after the operation
            DbContextHolder.clear();
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(DbType dataSourceType) {
        try {
            DbContextHolder.setCurrentDb(dataSourceType);
            return userRepository.findAll();
        } finally {
            DbContextHolder.clear();
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(int id, DbType dataSourceType) {
        try {
            DbContextHolder.setCurrentDb(dataSourceType);
            return userRepository.findById(id);
        } finally {
            DbContextHolder.clear();
        }
    }

    @Transactional
    public User updateUser(int id, User userDetails, DbType dataSourceType) {
        try {
            DbContextHolder.setCurrentDb(dataSourceType);
            return userRepository.findById(id)
                    .map(user -> {
                        user.setVal(userDetails.getVal());
                        user.setVal2(userDetails.getVal2());
                        return userRepository.save(user);
                    })
                    .orElseGet(() -> {
                        userDetails.setId(id);
                        return userRepository.save(userDetails);
                    });
        } finally {
            DbContextHolder.clear();
        }
    }

    @Transactional
    public void deleteUser(int id, DbType dataSourceType) {
        try {
            DbContextHolder.setCurrentDb(dataSourceType);
            userRepository.deleteById(id);
        } finally {
            DbContextHolder.clear();
        }
    }
}
