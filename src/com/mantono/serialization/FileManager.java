package com.mantono.serialization;

import java.io.Serializable;

/**
 * 
 * @author Anton &Ouml;sterberg
 *
 * @param <T> the type of the object that will be saved and/or loaded.
 */
public interface FileManager<T> extends Runnable
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

	/**
	 * The default behavior is to call the {@link #save()} method unless
	 * overriden. This allows the object managed by this instance to be
	 * automatically saved on exit if added to a shutdown hook via
	 * {@link Runtime#addShutdownHook(Thread)} wrapped in a {@link Thread}.
	 */
	@Override
	default void run()
	{
		save();
	}
}
