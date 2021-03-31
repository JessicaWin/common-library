package com.jessica.user.dao.impl;

import com.jessica.dynamodb.dao.impl.BasicOperationDaoImpl;
import com.jessica.user.dao.UserDao;
import com.jessica.user.dto.UserItem;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BasicOperationDaoImpl<UserItem> implements UserDao {
}
