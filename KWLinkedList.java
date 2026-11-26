/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Class KWLinkedList implements a double linked list and a ListIterator.
 * This is the course's KWLinkedList class. The project uses it instead of
 * java.util.LinkedList to store courses, tasks and sessions.
 * The last three methods (add, isEmpty, remove) were added for this project
 * and are built only on the course's own list iterator.
 **/
class KWLinkedList<E>
{
    // Data Fields
    private Node<E> head;
    private Node<E> tail;  // A reference to the end of the list.
    private int size;    // number of nodes
    // constructor – KWLinkedList
    public KWLinkedList()
    {
        // initially empty list
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Insert an object at the beginning of the list.
     * @param item - the item to be added
     */
    public void addFirst(E item)
    {
        Node<E> node = new Node<E>(item);
        if(head != null)
        {
            node.next = head;
            head.prev = node;
            head = node;
        }
        else
        {
            head = node;
            tail = node;
        }
        size++;
    } // end addFirst

    /**
     * Insert an object at the end of the list.
     * @param item - the item to be added
     */
    public void addLast(E item)
    {
        Node<E> node = new Node<E>(item);
        if(tail != null)
        {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        else
        {
            head = node;
            tail = node;
        }
        size++;
    } // end addLast

    /**
     * Get the first element of the list.
     * @return The first element of the list.
     */
    public E getFirst()
    {
        return head.data;
    }

    /**
     * Get the last element in the list.
     * @return The last element in the list.
     */
    public E getLast()
    {
        return tail.data;
    }

    /**
     * Return an Iterator to the list
     * @return an Itertor to the list
     */
    public Iterator<E> iterator()
    {
        KWListIter iterator = new KWListIter( );
        return iterator;
    }

    /**
     * Return a ListIterator to the list
     * @return a ListItertor to the list
     */
    public ListIterator<E> listIterator()
    {
        KWListIter iterator = new KWListIter( );
        return iterator;
    }

    /** Return a ListIterator that begins at index
     * @param index - The position the iteration is to begin
     * @return a ListIterator that begins at index
     */
    public ListIterator<E> listIterator(int index)
    {
        KWListIter iterator = new KWListIter(index);
        return iterator;
    }

    /**
     * Return a ListIterator that begins at the same
     * place as an existing ListIterator
     * @param iter - The other ListIterator
     * @return a ListIterator that is a copy of iter
     */
    public ListIterator <E> listIterator(ListIterator <E> iter)
    {
        KWListIter iterator = new KWListIter((KWListIter)iter);
        return iterator;
    }

    /**
     * Add an item at the specified index.
     * @param index The index at which the object is to be inserted
     * @param obj The object to be inserted
     * @throws IndexOutOfBoundsException if the index is out
     *         of range (i < 0 || i > size())
     */

    public void add(int index, E obj)
    {
        listIterator(index).add(obj);
    }

    /**
     * Get the element at position index.
     * @param index Position of item to be retrieved
     * @return The item at index
     */

    public E get(int index)
    {
        return listIterator(index).next();
    }

    /**
     * Return the size of the list
     * @return the size of the list
     */

    public int size()
    {
        return size;
    }

    // Inner Classes
    /**
     * A Node is the building block for a double-linked list.
     */
    private static class Node<E>
    {
        private E data;        // The data value
        private Node<E> next;  // The link to the next node
        private Node<E> prev;  // The link to the previous node


        /**
         * Construct a node with the given data value.
         * @param dataItem The data value
         */
        private Node(E dataItem)
        {
            data = dataItem;
            next = null;
            prev = null;
        }
        /**
         * Returns String equivalent of data
         * @return String equivalent of data
         */
        private String getData()
        {
            if(data == null)
                return "null";
            else
                return (String)data;
        }
    } //end class Node

    /** Inner class to implement the ListIterator interface. */
    private class KWListIter implements ListIterator<E>
    {
        private Node<E> nextItem;  // A reference to the next item
        private Node<E> lastItemReturned; //A reference to the last item returned.
        private int index = 0;  // The index of the current item.

        /**
         * Construct a KWListIter that will begin just before the first item of the list.
         * It will reference the first node of the list.
         */
        public KWListIter( )
        {
            lastItemReturned = null; // No item returned yet.
            index = 0;
            nextItem = head;  // Start at the beginning
        }

        /**
         * Construct a KWListIter that will reference the ith item.
         * @param i The index of the item to be referenced
         */
        public KWListIter(int i)
        {
            // Validate parameter i.
            if (i < 0 || i > size)
                throw new IndexOutOfBoundsException("Invalid index " + i);
            lastItemReturned = null; // No item returned yet.
            if (i == size) // Special case of last item
            {
                index = size;
                nextItem = null;
            }
            else
            {
                // Start at the beginning
                nextItem = head;
                for (index = 0; index < i; index++)
                    nextItem = nextItem.next;
            } //end else
        }

        /**
         * Construct a KWListIter that is a copy of another KWListIter
         * @param other The other KWListIter
         */
        public KWListIter(KWListIter other)
        {
            this.index = other.index;
            this.lastItemReturned = other.lastItemReturned;
            this.nextItem = other.nextItem;
        }

        /**
         * Indicate whether forward movement is valid.
         * @return true if call to next will not throw an exception
         */

        public boolean hasNext()
        {
            return (nextItem != null);
        }

        /** Move the iterator forward and return the next item.
         @return The next item in the list
         @throws NoSuchElementException if there is no such object
         */

        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        /**
         * Indicate whether backward movement is valid.
         * @return true if call to previous will not throw an exception
         */

        public boolean hasPrevious()
        {
            return ( (nextItem == null && size != 0) ||
                    (nextItem != null && nextItem.prev != null ));
        }

        /**
         * Return the index of the next item to be returned by next
         * @return the index of the next item to be returned by next
         */

        public int nextIndex()
        {
            return index;
        }

        /**
         * Return the index of the next item to be returned by previous
         * @return the index of the next item to be returned by previous
         */

        public int previousIndex()
        {
            return index - 1;
        }

        /**
         * Move the iterator backward and return the previous item.
         * @return The previous item in the list
         * @throws NoSuchElementException if there is no such object
         */

        public E previous()
        {
            if (!hasPrevious())
                throw new NoSuchElementException();
            if (nextItem == null) { // Iterator past the last element
                nextItem = tail;
            } else {
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        /**
         * Add a new item between the item that will be returned
         * by next and the item that will be returned by previous.
         * If previous is called after add, the element added is
         * returned.
         * @param obj The item to be inserted
         */

        public void add(E obj)
        {
            if (head == null)
            {
                // Add to an empty list.
                head = new Node<E>(obj);
                tail = head;
            }
            else if (nextItem == head)    // Insert a new node at front of the list.
            {
                Node<E> newNode = new Node<E>(obj); // Create a new node.
                newNode.next = nextItem; // Step 1 -  Link it to the nextItem.
                nextItem.prev = newNode; // Step 2 - Link nextItem to the new node.
                head = newNode; // Step 3 -  The new node is now the head.
            }
            else if (nextItem == null)   // Insert at tail.
            {
                Node<E> newNode = new Node<E>(obj); // Create a new node.
                tail.next = newNode; // Step 1 - Link the tail to the new node.
                newNode.prev = tail; // Step 2 - Link the new node to the tail.
                tail = newNode; // Step 3 - The new node is the new tail.

            }
            else  // Insert into the middle.
            {
                Node<E> newNode = new Node<E>(obj); // Create a new node.
                newNode.prev = nextItem.prev; // Step 1 - Link it to nextItem.prev.
                nextItem.prev.next = newNode; // Step 2
                newNode.next = nextItem; // Step 3 - Link it to the nextItem.
                nextItem.prev = newNode; // Step 4
            }
            // Increase size and index and set lastItemReturned.
            size++;
            index++;
            lastItemReturned = null;
        } // End of method add.


        /** Remove the last item returned. This can only be
         *  done once per call to next or previous.
         *  @throws IllegalStateException if next or previous
         *  was not called prior to calling this method
         */

        public void remove()
        {
            if(lastItemReturned == null) // can not remove
                throw new IllegalStateException( );
            else
            {
                if (head == tail) // Remove the only node
                {
                    head = tail = null;
                }
                else if (lastItemReturned == head) // Remove the first node
                {
                    head = head.next;
                    head.prev = null;
                }
                else if (lastItemReturned == tail) // Remove the last node
                {
                    tail = tail.prev;
                    tail.next = null;
                }
                else // Remove some in-between node
                {
                    lastItemReturned.prev.next = lastItemReturned.next;
                    lastItemReturned.next.prev = lastItemReturned.prev;
                }
                if (lastItemReturned != nextItem) {
                    // method next was called before calling remove
                    index--;
                }
                else {
                    // method previous was called before calling remove
                    // nextItem is same as lastItemReturned and this node
                    // is going to be deleted
                    nextItem = nextItem.next;
                }
                lastItemReturned = null;
                size--;
            } // end outer else
        } // end remove


        /** Replace the last item returned with a new value
         *  @param item The new value
         *  @throws IllegalStateException if next or previous
         *  was not called prior to calling this method
         */

        public void set(E item)
        {
            if(lastItemReturned == null)
                throw new IllegalStateException( );
            else{
                lastItemReturned.data = item;
                lastItemReturned = null;
            }
        }

    } //end class KWListIter


    /**
     * Method to find the index of the first occurrence of an item in the list
     * @param target The item being sought
     * @return The index of the first occurrence of the target item
     *         or -1 if the item is not found.
     */

    public int indexOf(E target)
    {
        int index = 0;
        Node<E> node = head;
        while (node != null)
        {
            if ( target.equals( node.data) )
                return index;
            else {
                node = node.next;
                index++;
            }
        }
        return -1; // target not found
    }



    /**
     * Method to find the index of the last occurrence of an item in the list
     * @param target The item being sought
     * @return The index of the last occurrence of the target item
     *         or -1, if the item is not found.
     */

    public int lastIndexOf(E target)
    {
        int index = size - 1;
        Node<E> node = tail;
        while (node != null)
        {
            if ( target.equals( node.data) )
                return index;
            else {
                node = node.prev;
                index--;
            }
        }
        return -1; // target not found
    }

    // ----- Methods added for this project (built on the course's iterator) -----

    /**
     * Insert an object at the end of the list (same as addLast).
     * @param item - the item to be added
     * @return true, always
     */
    public boolean add(E item)
    {
        addLast(item);
        return true;
    }

    /**
     * Check whether the list is empty.
     * @return true if the list has no elements
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Remove the element at position index, using the course's
     * ListIterator: move to the element with next(), then remove() it.
     * @param index Position of the item to be removed
     * @return The removed item
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public E remove(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index " + index);
        ListIterator<E> iter = listIterator(index);
        E removed = iter.next();
        iter.remove();
        return removed;
    }

}
