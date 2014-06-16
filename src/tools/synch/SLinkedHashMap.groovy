package tools.synch

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 *
 * @author DancesWithOdin
 */
public final class SLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements Map<K, V> {

	private final ReentrantReadWriteLock lock;
	private final ReentrantReadWriteLock.ReadLock r;
	private final ReentrantReadWriteLock.WriteLock w;

	public SLinkedHashMap() {
		lock = new ReentrantReadWriteLock(true);
		r = lock.readLock();
		w = lock.writeLock();
	}

	@Override
	public final void clear() {
		w.lock();
		try {
			super.clear();
		} finally {
			w.unlock();
		}
	}

	@Override
	public final boolean containsKey(Object o) {
		r.lock();
		try {
			return super.containsKey(o);
		} finally {
			r.unlock();
		}
	}

	@Override
	public final boolean containsValue(Object o) {
		r.lock();
		try {
			return super.containsValue(o);
		} finally {
			r.unlock();
		}
	}

	@Override
	public final Set<Map.Entry<K, V>> entrySet() {
		r.lock();
		try {
			return super.entrySet();
		} finally {
			r.unlock();
		}
	}

	@Override
	public final V get(Object k) {
		r.lock();
		try {
			return super.get(k);
		} finally {
			r.unlock();
		}
	}

	@Override
	public final boolean isEmpty() {
		r.lock();
		try {
			return super.isEmpty();
		} finally {
			r.unlock();
		}
	}

	@Override
	public final Set<K> keySet() {
		r.lock();
		try {
			return super.keySet();
		} finally {
			r.unlock();
		}
	}

	@Override
	public final V put(K k, V v) {
		w.lock();
		try {
			return super.put(k, v);
		} finally {
			w.unlock();
		}
	}

	@Override
	public final V remove(Object o) {
		w.lock();
		try {
			return super.remove(o);
		} finally {
			w.unlock();
		}
	}

	@Override
	public final int size() {
		r.lock();
		try {
			return super.size();
		} finally {
			r.unlock();
		}
	}
}
