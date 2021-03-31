package com.jessica.user.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.jessica.dynamodb.dto.BasicItem;
import com.jessica.dynamodb.utils.DynamoDBUtil;
import com.jessica.dynamodb.utils.KeyGenerationUtil;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@DynamoDBTable(tableName = DynamoDBUtil.TABLE_NAME_COMMON)
public class UserItem extends BasicItem {
	private static final String CLASS_NAME = "BasicItem";
	@DynamoDBIgnore
	private String userName;
	@DynamoDBAttribute
	private String password;
	@DynamoDBAttribute
	private String email;
	@DynamoDBAttribute
	private String imageName;

	@DynamoDBHashKey(attributeName = DynamoDBUtil.HASH_KEY)
	@Override
	public String getHashKey() {
		return KeyGenerationUtil.generateHashKey(this.getClass(), this.userName);
	}

	@Override
	public void setHashKey(String hashKey) {
		List<String> attributes = KeyGenerationUtil.parseHashKey(hashKey);
		this.userName = attributes.get(0);
	}

	@DynamoDBRangeKey(attributeName = DynamoDBUtil.RANGE_KEY)
	@Override
	public String getRangeKey() {
		return KeyGenerationUtil.generateRangeKey(this.userName);
	}

	@Override
	public void setRangeKey(String rangeKey) {
	}
}