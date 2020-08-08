package com.ssp.maintainance.services.rdb.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.ssp.maintainance.services.MessageByLocaleService;

@Component
public class MessageByLocaleServiceImpl implements MessageByLocaleService{

	@Autowired
    private MessageSource messageSource;
	
	@Override
	public String getMessage(String id) {
		Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id,null,locale);
	}
	
	@Override
	public String getMessage(String id, Object[] params) {
		Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id,params,locale);
	}

}
