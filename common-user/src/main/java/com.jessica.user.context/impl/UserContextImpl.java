package com.jessica.user.context.impl;

import com.jessica.user.context.UserContext;
import org.springframework.stereotype.Component;

@Component
public class UserContextImpl implements UserContext {
	private static ThreadLocal<String> USER_CONTEXT_HOLDER = new ThreadLocal<>();

	@Override
	public String getUserName() {
		return USER_CONTEXT_HOLDER.get();
	}

	@Override
	public void reset(String userName) {
		USER_CONTEXT_HOLDER.set(userName);
	}
}
