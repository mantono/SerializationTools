package com.mantono.serialization;

import java.io.Serializable;

/**
 * 
 * @author Anton &Ouml;sterberg
 *
 * @param <T> the type of the object that will be saved and/or loaded.
 */
public interface FileManager<T>
{
	/**
	 * 
	 * @return file size of saved file. -1 if it failed to save.
	 */
	long save();

	/**
	 * 
	 * @return file size of saved file. -1 if it failed to save.
	 */
	long save(T obj);

	/**
	 * 
	 * @return the object associated with the file for used by this instance of
	 * {@link FileManager}.
	 */
	T load();
}
