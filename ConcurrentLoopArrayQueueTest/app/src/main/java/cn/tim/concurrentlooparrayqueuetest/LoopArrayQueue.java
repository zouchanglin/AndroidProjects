package cn.tim.concurrentlooparrayqueuetest;


import android.util.Log;

/**
 * 循环数组队列
 * @param <T>
 */
public class LoopArrayQueue<T> {
    private static final String TAG = "LoopArrayQueue";
    private final Object[] items;

    // 容量
    private final int capacity;
    private static final int DEFAULT_CAPACITY = 16;

    // 当前数据量
    private int size;

    // head队头索引 tail队尾索引
    private int head = 0;
    private int tail = 0;

    /**
     * 默认容量为16
     */
    public LoopArrayQueue(){
        this.items = new Object[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * 指定容量的初始化
     */
    public LoopArrayQueue(int capacity){
        items = new Object[capacity];
        this.capacity = capacity;
    }

    /**
     * 入队列
     */
    public void enqueue(T item){
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
    public T getByPosition(int position){
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
     * 从头打印全部元素
     */
    public void printQueueForDebug(){
        if(size == 0) return;
        for(int i = 0; i < size; i++){
            System.out.print(getByPosition(i) + ", ");
        }
        System.out.println();
    }

    /**
     * 返回当前size
     */
    public int size(){
        return this.size;
    }
}
