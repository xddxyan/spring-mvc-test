package org.copycat.framework;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class DateTag extends TagSupport {
	private static final long serialVersionUID = 8006452093921121234L;
	private long value;
	private String pattern;

	public void setValue(long value) {
		this.value = value;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspTagException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = new Date(value);
		try {
			pageContext.getOut().write(format.format(date));
		} catch (IOException ex) {
			throw new JspTagException(
					"Fatal error:hello tag conld not write to JSP out");
		}
		return EVAL_PAGE;
	}

}
