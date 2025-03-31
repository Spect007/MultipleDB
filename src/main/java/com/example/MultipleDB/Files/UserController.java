package com.example.MultipleDB.Files;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
        UserService userService;
        private DbType getDbType(String dbName) {
            if (dbName == null || dbName.trim().isEmpty()) {
                // Default data source
                return DbType.DB1;
            }
            try {
                return DbType.valueOf(dbName.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Fallback default if the value doesn't match any enum constant
                return DbType.DB1;
            }
        }
    
        @PostMapping
        public User createUser(@RequestBody User user,
                               @RequestHeader(name = "X-DB-Name", required = false) String dbName) {
            DbType dbType = getDbType(dbName);
            return userService.createUser(user, dbType);
        }
    
        @GetMapping
        public List<User> getAllUsers(@RequestHeader(name = "X-DB-Name", required = false) String dbName) {
            DbType dbType = getDbType(dbName);
            return userService.getAllUsers(dbType);
        }
    
        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable int id,
                                                @RequestHeader(name = "X-DB-Name", required = false) String dbName) {
            DbType dbType = getDbType(dbName);
            Optional<User> user = userService.getUserById(id, dbType);
            return user.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
    
        @PutMapping("/{id}")
        public User updateUser(@PathVariable int id,
                               @RequestBody User userDetails,
                               @RequestHeader(name = "X-DB-Name", required = false) String dbName) {
            DbType dbType = getDbType(dbName);
            return userService.updateUser(id, userDetails, dbType);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable int id,
                                               @RequestHeader(name = "X-DB-Name", required = false) String dbName) {
            DbType dbType = getDbType(dbName);
            userService.deleteUser(id, dbType);
            return ResponseEntity.noContent().build();
        }
}
