package model.entity;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseTree<M extends BaseTree<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setText(java.lang.String text) {
		set("text", text);
	}

	public java.lang.String getText() {
		return get("text");
	}

	public void setIconCls(java.lang.String iconCls) {
		set("iconCls", iconCls);
	}

	public java.lang.String getIconCls() {
		return get("iconCls");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}

	public java.lang.String getUrl() {
		return get("url");
	}

	public void setPlevel(java.lang.Integer plevel) {
		set("plevel", plevel);
	}

	public java.lang.Integer getPlevel() {
		return get("plevel");
	}

}
