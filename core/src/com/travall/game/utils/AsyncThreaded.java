package com.travall.game.utils;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

/** A light-weight single-treaded abstract class */
public abstract class AsyncThreaded<Packet> implements AsyncTask<Packet>, Disposable 
{
	/** The asynchronous executer. */
	protected final AsyncExecutor exe;
	
	protected AsyncResult<Packet> result;
	
	public AsyncThreaded(final String name) {
		this.exe = new AsyncExecutor(1, name);
	}
	
	/** The method that thread can execute. */
	public abstract Packet call() throws Exception;
	
	/** Get the packet after the thread is finished executing. */
	public Packet get() {
		return result == null ? null : result.get();
	}
	
	/** Check the thread is finished executing. */
	public boolean isDone() {
		return result == null ? true : result.isDone();
	}
	
	/** Clear anything regardless of the thread is done. */
	public void clear() {
		
	}
	
	/** Clear anything than dispose the thread. */
	public void dispose() {
		clear();
		exe.dispose();
		result = null;
	}
}
