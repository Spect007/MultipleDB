package com.example.MultipleDB.Files;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class DatabaseInterceptor implements HandlerInterceptor{
 @Override
    public boolean preHandle(HttpServletRequest request, 
                           HttpServletResponse response, 
                           Object handler) throws Exception {
        
        String dbParam = request.getHeader("X-DB-Name") != null 
            ? request.getHeader("X-DB-Name")
            : request.getParameter("db");
        
        if ("db2".equalsIgnoreCase(dbParam)) {
            DbContextHolder.setCurrentDb(DbType.DB2);
        } else {
            DbContextHolder.setCurrentDb(DbType.DB1);
        }
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, 
                              HttpServletResponse response, 
                              Object handler, 
                              Exception ex) throws Exception {
        DbContextHolder.clear();
    }



    public class DbTenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        return DbContextHolder.getCurrentDb() != null ? 
               DbContextHolder.getCurrentDb().name().toLowerCase() : "db1";
    }
    @Override public boolean validateExistingCurrentSessions() { return true; }
}

}
