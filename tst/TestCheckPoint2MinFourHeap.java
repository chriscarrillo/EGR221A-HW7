import datastructures.worklists.MinFourHeap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Created by mhan on 3/14/2017.
 */
public class TestCheckPoint2MinFourHeap {

    private void sizeZeroTest(MinFourHeap<Integer> heap){
        Assert.assertTrue("Size must be 0", heap.size() == 0);
        Assert.assertFalse("Has work should be false", heap.hasWork());
        try{
            heap.peek();
            Assert.fail("Must throw NoSuchElementException");
        } catch (NoSuchElementException e) {
        }

        try{
            heap.next();
            Assert.fail("Must throw NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }

    private void sizeNTestWithRandomValues(int n, int maxRange){
        MinFourHeap<Integer> heap = new MinFourHeap<>();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int[] array = Utils.randomArray(n, maxRange);

        for(int data : array) {
            heap.add(data);
            priorityQueue.add(data);
            Assert.assertTrue(priorityQueue.size() == heap.size());
            Assert.assertTrue(priorityQueue.peek().equals(heap.peek()));
        }

        Iterator<Integer> iterator = priorityQueue.iterator();
        while(iterator.hasNext()) {
            Assert.assertTrue(heap.hasWork());
            Assert.assertTrue(priorityQueue.peek().equals(heap.peek()));
            int removed = iterator.next();
            iterator.remove();
            Assert.assertTrue(heap.next().equals(removed));
            Assert.assertTrue( priorityQueue.size() == heap.size());
        }
        sizeZeroTest(heap);
    }

    @Test
    public void minFourHeapTest1(){
        MinFourHeap<Integer> heap = new MinFourHeap<>();
        sizeZeroTest(heap);
    }

    @Test
    public void minFourHeapTest2(){
        MinFourHeap<Integer> heap = new MinFourHeap<>();
        for(int i = 1 ; i <= 1024 ; i++ ){
            heap.add(i);
            Assert.assertTrue(heap.size() == 1);
            Assert.assertTrue(heap.next().equals(i));
        }
        sizeZeroTest(heap);
    }

    @Test
    public void minFourHeapTest3(){
        MinFourHeap<Integer> heap = new MinFourHeap<>();
        for(int i = 1 ; i <= 1024 ; i++ ){
            heap.add(i);
            Assert.assertTrue(heap.peek().equals(1));
        }
        for(int i = 1 ; i <= 1024 ; i++ ) {
            Assert.assertTrue(heap.next().equals(i));
            Assert.assertTrue(heap.size() == 1024 - i);
        }
        sizeZeroTest(heap);
    }

    @Test
    public void minFourHeapTest4(){
        sizeNTestWithRandomValues(15, 50);
    }

    @Test
    public void minFourHeapTest5(){
        sizeNTestWithRandomValues(30, 2);
    }

    @Test
    public void minFourHeapTest6(){
        sizeNTestWithRandomValues(300, 10);
    }

    @Test
    public void minFourHeapTest7(){
        sizeNTestWithRandomValues(1000, 50000);
    }

    @Test
    public void minFourHeapTest8(){
        sizeNTestWithRandomValues(2000, 2);
    }

    @Test
    public void minFourHeapTest9(){
        sizeNTestWithRandomValues(5000, 1000);
    }
}
