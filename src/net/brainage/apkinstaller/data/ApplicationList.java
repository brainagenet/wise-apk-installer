/**
 * 
 */
package net.brainage.apkinstaller.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author ms29seo
 *
 */
public class ApplicationList implements List<ApplicationInfo>
{

    /**
     * 
     */
    private static ApplicationList singleton;

    /**
     * 
     */
    private ArrayList<ApplicationInfo> list;

    /**
     * 
     */
    private ApplicationList() {
        list = new ArrayList<ApplicationInfo>();
    }

    /**
     * @return
     */
    public static ApplicationList getInstance() {
        if ( singleton == null ) {
            singleton = new ApplicationList();
        }
        return singleton;
    }

    /**
     * @param object
     * @return
     * @see java.util.ArrayList#add(java.lang.Object)
     */
    public boolean add(ApplicationInfo object) {
        return list.add(object);
    }

    /**
     * @param index
     * @param object
     * @see java.util.ArrayList#add(int, java.lang.Object)
     */
    public void add(int index, ApplicationInfo object) {
        list.add(index, object);
    }

    /**
     * @param collection
     * @return
     * @see java.util.ArrayList#addAll(java.util.Collection)
     */
    public boolean addAll(Collection<? extends ApplicationInfo> collection) {
        return list.addAll(collection);
    }

    /**
     * @param index
     * @param collection
     * @return
     * @see java.util.ArrayList#addAll(int, java.util.Collection)
     */
    public boolean addAll(int index, Collection<? extends ApplicationInfo> collection) {
        return list.addAll(index, collection);
    }

    /**
     * 
     * @see java.util.ArrayList#clear()
     */
    public void clear() {
        list.clear();
    }

    /**
     * @return
     * @see java.util.ArrayList#clone()
     */
    public Object clone() {
        return list.clone();
    }

    /**
     * @param object
     * @return
     * @see java.util.ArrayList#contains(java.lang.Object)
     */
    public boolean contains(Object object) {
        return list.contains(object);
    }

    /**
     * @param collection
     * @return
     * @see java.util.AbstractCollection#containsAll(java.util.Collection)
     */
    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    /**
     * @param minimumCapacity
     * @see java.util.ArrayList#ensureCapacity(int)
     */
    public void ensureCapacity(int minimumCapacity) {
        list.ensureCapacity(minimumCapacity);
    }

    /**
     * @param o
     * @return
     * @see java.util.ArrayList#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        return list.equals(o);
    }

    /**
     * @param index
     * @return
     * @see java.util.ArrayList#get(int)
     */
    public ApplicationInfo get(int index) {
        return list.get(index);
    }

    /**
     * @return
     * @see java.util.ArrayList#hashCode()
     */
    public int hashCode() {
        return list.hashCode();
    }

    /**
     * @param object
     * @return
     * @see java.util.ArrayList#indexOf(java.lang.Object)
     */
    public int indexOf(Object object) {
        return list.indexOf(object);
    }

    /**
     * @return
     * @see java.util.ArrayList#isEmpty()
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * @return
     * @see java.util.ArrayList#iterator()
     */
    public Iterator<ApplicationInfo> iterator() {
        return list.iterator();
    }

    /**
     * @param object
     * @return
     * @see java.util.ArrayList#lastIndexOf(java.lang.Object)
     */
    public int lastIndexOf(Object object) {
        return list.lastIndexOf(object);
    }

    /**
     * @return
     * @see java.util.AbstractList#listIterator()
     */
    public ListIterator<ApplicationInfo> listIterator() {
        return list.listIterator();
    }

    /**
     * @param location
     * @return
     * @see java.util.AbstractList#listIterator(int)
     */
    public ListIterator<ApplicationInfo> listIterator(int location) {
        return list.listIterator(location);
    }

    /**
     * @param index
     * @return
     * @see java.util.ArrayList#remove(int)
     */
    public ApplicationInfo remove(int index) {
        return list.remove(index);
    }

    /**
     * @param object
     * @return
     * @see java.util.ArrayList#remove(java.lang.Object)
     */
    public boolean remove(Object object) {
        return list.remove(object);
    }

    /**
     * @param collection
     * @return
     * @see java.util.AbstractCollection#removeAll(java.util.Collection)
     */
    public boolean removeAll(Collection<?> collection) {
        return list.removeAll(collection);
    }

    /**
     * @param collection
     * @return
     * @see java.util.AbstractCollection#retainAll(java.util.Collection)
     */
    public boolean retainAll(Collection<?> collection) {
        return list.retainAll(collection);
    }

    /**
     * @param index
     * @param object
     * @return
     * @see java.util.ArrayList#set(int, java.lang.Object)
     */
    public ApplicationInfo set(int index, ApplicationInfo object) {
        return list.set(index, object);
    }

    /**
     * @return
     * @see java.util.ArrayList#size()
     */
    public int size() {
        return list.size();
    }

    /**
     * @param start
     * @param end
     * @return
     * @see java.util.AbstractList#subList(int, int)
     */
    public List<ApplicationInfo> subList(int start, int end) {
        return list.subList(start, end);
    }

    /**
     * @return
     * @see java.util.ArrayList#toArray()
     */
    public Object[] toArray() {
        return list.toArray();
    }

    /**
     * @param <T>
     * @param contents
     * @return
     * @see java.util.ArrayList#toArray(T[])
     */
    public <T> T[] toArray(T[] contents) {
        return list.toArray(contents);
    }

    /**
     * @return
     * @see java.util.AbstractCollection#toString()
     */
    public String toString() {
        return list.toString();
    }

    /**
     * 
     * @see java.util.ArrayList#trimToSize()
     */
    public void trimToSize() {
        list.trimToSize();
    }

}
