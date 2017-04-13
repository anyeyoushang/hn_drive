/*
 * @(#) TreeMenu1.java 2016-12-30
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package easyui.controller;

import java.util.ArrayList;
import java.util.List;

public class ParentMenu {
	private String id;
	private String text;
	private String iconCls;
	private List<SonMenu> children = new ArrayList<SonMenu>();
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
	public List<SonMenu> getChildren() {
		return children;
	}
	public void setChildren(List<SonMenu> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "TreeMenu1 [id=" + id + ", text=" + text + ", iconCls="
				+ iconCls + ", children=" + children + "]";
	}
	
}
