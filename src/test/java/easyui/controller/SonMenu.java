package easyui.controller;

import java.util.HashMap;
import java.util.Map;

public class SonMenu implements java.io.Serializable {
	private String id;
	private String text;
	private String iconCls;
	private Map<String, Object> attributes = new HashMap<String, Object>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "TreeMenu2 [id=" + id + ", text=" + text + ", iconCls="
				+ iconCls + ", attributes=" + attributes + "]";
	}

	
	
}
