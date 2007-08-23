/**
 * 
 */
package com.redv.blogmover.impl;

import com.redv.blogmover.Category;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class CategoryImpl implements Category {
	/**
	 * 
	 */
	private static final long serialVersionUID = -292753105141037427L;

	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.Category#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.Category#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Category category) {
		if (this.name == null && category.getName() == null) {
			return 0;
		} else if (this.name == null) {
			return 1;
		} else {
			return this.name.compareTo(category.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CategoryImpl other = (CategoryImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
