package model.entity;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseTmenu<M extends BaseTmenu<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setIconCls(java.lang.String iconCls) {
		set("iconCls", iconCls);
	}

	public java.lang.String getIconCls() {
		return get("iconCls");
	}

	public void setSeq(java.lang.Integer seq) {
		set("seq", seq);
	}

	public java.lang.Integer getSeq() {
		return get("seq");
	}

	public void setText(java.lang.String text) {
		set("text", text);
	}

	public java.lang.String getText() {
		return get("text");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}

	public java.lang.String getUrl() {
		return get("url");
	}

	public void setPid(java.lang.String pid) {
		set("pid", pid);
	}

	public java.lang.String getPid() {
		return get("pid");
	}

}
