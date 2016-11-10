package org.copycat.framework;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class HumanTag extends TagSupport {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static final long serialVersionUID = 8006452093921121234L;
	private long value;

	public void setValue(long value) {
		this.value = value;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspTagException {
		String result = new String();
		long deltaTime = (System.currentTimeMillis() - value) / 1000;
		if (deltaTime > 259200) {
			result = format.format(new Date(value));
		} else if (deltaTime > 86400) {
			deltaTime = deltaTime / 86400;
			result = deltaTime + " 天前";
		} else if (deltaTime > 3600) {
			deltaTime = deltaTime / 3600;
			result = deltaTime + " 小时前";
		} else if (deltaTime > 60) {
			deltaTime = deltaTime / 60;
			result = deltaTime + " 分钟前";
		} else {
			result = deltaTime + " 秒前";
		}
		try {
			pageContext.getOut().write(result);
		} catch (IOException ex) {
			throw new JspTagException(
					"Fatal error:hello tag conld not write to JSP out");
		}
		return EVAL_PAGE;
	}

}
