package com.ssp.usermgmt.oauth;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {
	   private static final long serialVersionUID = 1L;
	   
	   public CustomUser(UserEntity user) {
	      super(user.getTenant_id()+OAuthDao.USER_SEPARATOR+user.getUsername(), user.getPassword(),(!user.isExpired()&!user.isPasswordExpired()&!user.isLocked()), !user.isExpired(),!user.isPasswordExpired(),!user.isLocked(),user.getGrantedAuthoritiesList());
	   }

}
