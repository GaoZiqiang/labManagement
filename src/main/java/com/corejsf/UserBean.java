package com.corejsf;

import java.io.Serializable;

// or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
// or import javax.faces.bean.SessionScoped;

@Named("user") // or @ManagedBean(name="user")
@SessionScoped
public class UserBean implements Serializable {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String newValue) {
		id = newValue;
	}
}