package com.ssp.maintainance.oauth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository
public class OAuthDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public UserEntity getUserDetails(String username) {
	      Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
	      String userSQLQuery = "SELECT * FROM users WHERE USERNAME=?";
	      List<UserEntity> list = jdbcTemplate.query(userSQLQuery, new String[] { username }, new UserEntityExtractor());
	      
	      if (list.size() > 0) {
	         GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
	         grantedAuthoritiesList.add(grantedAuthority);
	         list.get(0).setGrantedAuthoritiesList(grantedAuthoritiesList);
	         return list.get(0);
	      }
	      return null;
	   }
	
	class UserEntityExtractor implements ResultSetExtractor<List<UserEntity>>{

		@Override
		public List<UserEntity> extractData(ResultSet rs)
				throws SQLException, DataAccessException {
			List<UserEntity> users = new ArrayList<UserEntity>();
			while (rs.next()) {	
				UserEntity user = new UserEntity();
		        user.setUsername(rs.getString("USERNAME"));
		        user.setPassword(rs.getString("PASSWORD"));
		        
		        users.add(user);
			}
			return users;
		}
		
	}
}
