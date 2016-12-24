package com.mantono.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Serializer<T> implements FileManager<T>
{
	private final File file;
	private final FileSaver saver;
	private final Class<?> thisClass;
	private final ReadLock readLock;
	private final WriteLock writeLock;
	private T data;

	public Serializer(final File file, final Class<?> classz)
	{
		this.file = file;
		this.saver = new FileSaver(file);
		this.thisClass = classz;
		if(!(classz instanceof Serializable))
			System.err.println("Class " + classz + " is not serializable.");
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
		this.writeLock = lock.writeLock();
		this.readLock = lock.readLock();
	}

	@Override
	public long save()
	{
		try
		{
			writeLock.lock();
			return saver.save(data);
		}
		finally
		{
			writeLock.unlock();
		}
	}

	@Override
	public long save(T obj)
	{
		try
		{
			writeLock.lock();
			this.data = obj;
			return save();
		}
		finally
		{
			writeLock.unlock();
		}
	}

	@Override
	public T load()
	{
		try
		{
			readLock.lock();

			if(file.exists())
			{

				try(FileInputStream fis = new FileInputStream(file);
						ObjectInputStream ois = new ObjectInputStream(fis);)
				{
					data = (T) ois.readObject();
				}
				catch(IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				data = (T) thisClass.newInstance();
			}
		}
		catch(InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		finally
		{
			readLock.unlock();
		}

		return data;
	}
}
