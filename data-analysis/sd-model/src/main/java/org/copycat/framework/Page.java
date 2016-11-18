package org.copycat.framework;

import org.springframework.ui.Model;

/**
 * 分页辅助类
 * 
 */
public class Page {
	private int number;
	private int total;
	private int limit;
	private int begin;
	private int end;
	private int size;

	/**
	 * 构造器
	 * 
	 * @param page
	 *            当前页数
	 * @param limit
	 *            每页数目
	 */
	public Page(int number, int limit) {
		this.number = number;
		this.limit = limit;
	}

	public Page(int number, int limit, Model model) {
		this.number = number;
		this.limit = limit;
		model.addAttribute("page", this);
	}

	/**
	 * 获取当前页数
	 * 
	 * @return
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * 获取总数目
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		size = (total / limit);
		if (total % limit > 0 || size == 0) {
			size++;
		}
		begin = number - 2;
		end = number + 2;
		if (begin < 1) {
			begin = 1;
			end = 5;
		}

		if (end > size) {
			end = size;
			if (end > 5) {
				begin = end - 4;
			}
		}
	}

	/**
	 * 获取每页数目
	 * 
	 * @return
	 */
	public int getLimit() {
		return this.limit;
	}

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 获取忽略的条目数
	 * 
	 * @return
	 */
	public int getOffset() {
		if (number == 0) {
			number = 1;
		}
		return (number - 1) * limit;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}
}
