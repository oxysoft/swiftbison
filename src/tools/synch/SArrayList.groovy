package tools.synch

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * @author DancesWithOdin
 */
public final class SArrayList<E> extends ArrayList<E> implements List<E> {

	private final ReentrantReadWriteLock lock;
	private final ReentrantReadWriteLock.ReadLock r;
	private final ReentrantReadWriteLock.WriteLock w;

	public SArrayList() {
		lock = new ReentrantReadWriteLock(true);
		r = lock.readLock();
		w = lock.writeLock();
	}

	public SArrayList(int size) {
		super(size);
		lock = new ReentrantReadWriteLock(true);
		r = lock.readLock();
		w = lock.writeLock();
	}

	@Override
	public int size() {
		r.lock();
		try {
			return super.size();
		} finally {
			r.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		r.lock();
		try {
			return super.isEmpty();
		} finally {
			r.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		r.lock();
		try {
			return super.contains(o);
		} finally {
			r.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		r.lock();
		try {
			return super.iterator();
		} finally {
			r.unlock();
		}
	}

	@Override
	public Object[] toArray() {
		r.lock();
		try {
			return super.toArray();
		} finally {
			r.unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] ts) {
		r.lock();
		try {
			return super.toArray(ts);
		} finally {
			r.unlock();
		}
	}

	@Override
	public boolean add(E e) {
		w.lock();
		try {
			return super.add(e);
		} finally {
			w.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		w.lock();
		try {
			return super.remove(o);
		} finally {
			w.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> clctn) {
		r.lock();
		try {
			return super.containsAll(clctn);
		} finally {
			r.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> clctn) {
		w.lock();
		try {
			return super.addAll(clctn);
		} finally {
			w.unlock();
		}
	}

	@Override
	public boolean addAll(int i, Collection<? extends E> clctn) {
		w.lock();
		try {
			return super.addAll(i, clctn);
		} finally {
			w.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> clctn) {
		w.lock();
		try {
			return super.removeAll(clctn);
		} finally {
			w.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> clctn) {
		w.lock();
		try {
			return super.retainAll(clctn);
		} finally {
			w.unlock();
		}
	}

	@Override
	public void clear() {
		w.lock();
		try {
			super.clear();
		} finally {
			w.unlock();
		}
	}

	@Override
	public E get(int i) {
		r.lock();
		try {
			return super.get(i);
		} finally {
			r.unlock();
		}
	}

	@Override
	public E set(int i, E e) {
		w.lock();
		try {
			return super.set(i, e);
		} finally {
			w.unlock();
		}
	}

	@Override
	public void add(int i, E e) {
		w.lock();
		try {
			super.add(i, e);
		} finally {
			w.unlock();
		}
	}

	@Override
	public E remove(int i) {
		w.lock();
		try {
			return super.remove(i);
		} finally {
			w.unlock();
		}
	}

	@Override
	public int indexOf(Object o) {
		r.lock();
		try {
			return super.indexOf(o);
		} finally {
			r.unlock();
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		r.lock();
		try {
			return super.lastIndexOf(o);
		} finally {
			r.unlock();
		}
	}

	@Override
	public ListIterator<E> listIterator() {
		return super.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int i) {
		return super.listIterator(i);
	}
}
