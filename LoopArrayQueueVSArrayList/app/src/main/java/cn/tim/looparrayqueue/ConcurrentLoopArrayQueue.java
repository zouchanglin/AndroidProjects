package cn.tim.looparrayqueue;

/**
 * 支持并发的循环数组队列
 * @param <T>
 */
public class ConcurrentLoopArrayQueue<T> extends LoopArrayQueue<T>{
    /**
     * 默认容量为16
     */
    public ConcurrentLoopArrayQueue(){
        super();
    }

    /**
     * 指定容量的初始化
     */
    public ConcurrentLoopArrayQueue(int capacity){
        super(capacity);
    }

    /**
     * 入队列
     */
    @Override
    public synchronized void enqueue(T item){
        if (size == capacity){
            if(size == 0) return;
            items[head] = item;
            head = (head + 1) % capacity;
            tail = (head + 1) % capacity;
        }else{
            items[tail] = item;
            tail = (tail + 1) % capacity;
            size++;
        }
    }

    /**
     * 根据索引位置获得元素
     */
    @Override
    public synchronized T getByPosition(int position){
        if(position > capacity - 1 || position > size){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(size < capacity){
            return (T) items[position];
        }else {
            return (T) items[((position + head) % size)];
        }
    }

    /**
     * 返回当前size
     */
    @Override
    public int size(){
        return this.size;
    }
}

