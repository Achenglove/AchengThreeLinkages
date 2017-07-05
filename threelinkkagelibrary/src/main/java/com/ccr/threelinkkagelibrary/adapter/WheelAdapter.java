package com.ccr.threelinkkagelibrary.adapter;

/**
 * 在此写用途
 *
 * @Author: Acheng
 * @Email: 345887272@qq.com
 * @Date: 2017-05-25 15:12
 * @Version: V1.0 <描述当前版本功能>
 */

public interface WheelAdapter<T> {
	/**
	 * Gets items count
	 * @return the count of wheel items
	 */
	int getItemsCount();
	
	/**
	 * Gets a wheel item by index.
	 * @param index the item index
	 * @return the wheel item text or null
	 */
	T getItem(int index);
	
	/**
	 * Gets maximum item length. It is used to determine the wheel width.
	 * If -1 is returned there will be used the default wheel width.
	 * @param o
	 * @return the maximum item length or -1
     */
	int indexOf(T o);
}
