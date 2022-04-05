package com.ssp.usermgmt.oauth;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class OAuthDao {
	
	public static final char USER_SEPARATOR='_';
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public UserEntity getUserDetails(String username) {

		String[] usernameAndTenant = splitTenantAndPassword(username);
		if (usernameAndTenant == null || usernameAndTenant.length != 2) {
			throw new UsernameNotFoundException("Username and tenanat must be provided");
		}
				
		String userSQLQuery = "select u.tenant_id tenant_id, u.user_name user_name,u.password password, r.role role, u.expired expired, u.locked locked, u.password_expired password_expired from user u, roles r , userrole ur where u.user_name=? and u.tenant_id = ? and u.id=ur.user_id";
		List<UserEntity> list = jdbcTemplate.query(userSQLQuery, new String[] { usernameAndTenant[1], usernameAndTenant[0] }, new UserEntityExtractor());

		if (list.size() > 0) {
			Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>(list.size());
			for (UserEntity userEntity : list) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userEntity.getRole());
				grantedAuthoritiesList.add(grantedAuthority);
			}
			list.get(0).setGrantedAuthoritiesList(grantedAuthoritiesList);
			return list.get(0);
		}
		return null;
	}

	private String[] splitTenantAndPassword(String username) {
		String[] usernameAndTenant = new String[2];
		int index = username.indexOf(USER_SEPARATOR);
		
		if( index == -1) {
			throw new UsernameNotFoundException("Username and tenanat must be provided");
		}
		usernameAndTenant[0] = username.substring(0, index);
		usernameAndTenant[1] = username.substring(index+1,username.length());
		
		return usernameAndTenant;
	}
	
	class UserEntityExtractor implements ResultSetExtractor<List<UserEntity>> {

		@Override
		public List<UserEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<UserEntity> users = new ArrayList<UserEntity>();
			while (rs.next()) {
				UserEntity user = new UserEntity();
				user.setUsername(rs.getString("USER_NAME"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setTenant_id(rs.getString("TENANT_ID"));
				user.setRole(rs.getString("ROLE"));
				user.setExpired(rs.getBoolean("EXPIRED"));
				user.setLocked(rs.getBoolean("LOCKED"));
				user.setPasswordExpired(rs.getBoolean("PASSWORD_EXPIRED"));

				users.add(user);
			}
			return users;
		}

	}
}
