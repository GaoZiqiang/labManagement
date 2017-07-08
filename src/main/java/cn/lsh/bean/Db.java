package cn.lsh.bean;

import java.util.ArrayList;
import java.util.List;

public class Db {
	private static List<User> list = new ArrayList<User>();

	static {
		list.add(new User("aaa", "123"));
		list.add(new User("bbb", "456"));
		list.add(new User("ccc", "678"));
		list.add(new User("ddd", "789"));

	}

	public static List<User> getAllUser() {
		return list;
	}
}
