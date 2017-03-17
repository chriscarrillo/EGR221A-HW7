import datastructures.worklists.ArrayStack;
import datastructures.worklists.CircularArrayFIFOQueue;
import datastructures.worklists.ListFIFOQueue;
import egr221a.interfaces.worklists.FIFOWorkList;
import egr221a.interfaces.worklists.LIFOWorkList;
import egr221a.interfaces.worklists.WorkList;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by mhan on 3/12/2017.
 */
public class TestCheckPoint1 {

    private void sizeZeroTest(WorkList workList){
        Assert.assertTrue("Size must be 0", workList.size() == 0);
        Assert.assertFalse("Has work should be false", workList.hasWork());
        try{
            workList.peek();
            Assert.fail("Must throw NoSuchElementException");
        } catch (NoSuchElementException e) {
        }

        try{
            workList.next();
            Assert.fail("Must throw NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }
    private void addRemoveTest(FIFOWorkList<Integer> workList, int addNum, int removeNum, int repeatNum, boolean cleanUp) {
        int num = 1;
        List<Integer> list = new ArrayList<>();
        for(int data : workList) {
            list.add(data);
        }
//        System.out.println("list is " + list.toString());
        for(int i = 0; i < repeatNum ; i++) {
            for(int j = 0; j < addNum ; j++) {
                //System.out.println("add : " + j + " " + num);
                workList.add(num);
                list.add(num);
                Assert.assertTrue("peek results should be " + list.get(0), workList.peek().equals(list.get(0)));
                Assert.assertTrue("size should be " + list.size(), workList.size() == list.size());
                num++;
            }
//            System.out.println("after adding all list is " + list.toString());
//            System.out.println("after adding all work is " + workList.toString());
            for(int k = 0; k < removeNum; k++) {
//                System.out.println("before removing " + k + " :" + list.toString());
//                System.out.println("before removing " + k + " :" + workList.toString());
                int result = list.remove(0);
                Assert.assertTrue("next results should be " + result, workList.next().equals(result));
                Assert.assertTrue("size should be " + list.size(), workList.size() == list.size());

//                System.out.println("after removing " + k + " :" + list.toString());
//                System.out.println("after removing " + k + " :" + workList.toString());

                if(list.size() != 0) {
                    Assert.assertTrue("peek results should be " + list.get(0) + " but it is " + workList.peek(), workList.peek().equals(list.get(0)));
                    Assert.assertTrue("size should be " + list.size(), workList.size() == list.size());
                } else {
                    try {
                        workList.peek();
                        Assert.fail();
                    }catch (NoSuchElementException e){
                    }
                }
            }
//            System.out.println("after removing all list is " + list.toString());
//            System.out.println("after removing all work is " + workList.toString());
        }
        if(cleanUp) {
            while (list.size() != 0) {
                int result = list.remove(0);
                Assert.assertTrue("next results should be " + result, workList.next().equals(result));
                Assert.assertTrue("size should be " + list.size(), workList.size() == list.size());

                if (list.size() != 0) {
                    Assert.assertTrue("peek results should be " + list.get(0), workList.peek().equals(list.get(0)));
                    Assert.assertTrue("size should be " + list.size(), workList.size() == list.size());
                } else {
                    try {
                        workList.peek();
                        Assert.fail();
                    } catch (NoSuchElementException e) {
                    }
                }
            }
            sizeZeroTest(workList);
        }
    }

    private void addRemoveTest(LIFOWorkList<Integer> workList, int addNum, int removeNum, int repeatNum, boolean cleanUp) {
        int num = 1;
        Stack<Integer> stack = new Stack<>();
        for(int data : workList) {
            stack.push(data);
        }
        for(int i = 0; i < repeatNum ; i++) {
            for(int j = 0; j < addNum ; j++) {
                workList.add(num);
                stack.push(num);
                Assert.assertTrue("peek results should be " + stack.peek(), workList.peek() == stack.peek());
                Assert.assertTrue("size should be " + stack.size(), workList.size() == stack.size());
                num++;
            }

            for(int k = 0; k < removeNum; k++) {
                int result = stack.pop();
                Assert.assertTrue("next results should be " + result, workList.next() == result);
                Assert.assertTrue("size should be " + stack.size(), workList.size() == stack.size());

                if(stack.size() != 0) {
                    Assert.assertTrue("peek results should be " + stack.peek(), workList.peek() == stack.peek());
                    Assert.assertTrue("size should be " + stack.size(), workList.size() == stack.size());
                } else {
                    try {
                        workList.peek();
                        Assert.fail();
                    }catch (NoSuchElementException e){
                    }
                }
            }
        }
        if(cleanUp) {
            while (stack.size() != 0) {
                int result = stack.pop();
                Assert.assertTrue("next results should be " + result, workList.next() == result);
                Assert.assertTrue("size should be " + stack.size(), workList.size() == stack.size());

                if (stack.size() != 0) {
                    Assert.assertTrue("peek results should be " + stack.peek(), workList.peek() == stack.peek());
                    Assert.assertTrue("size should be " + stack.size(), workList.size() == stack.size());
                } else {
                    try {
                        workList.peek();
                        Assert.fail();
                    } catch (NoSuchElementException e) {
                    }
                }
            }
            sizeZeroTest(workList);
        }
    }


    @Test
    public void ListFIFOQueueTest1(){
        ListFIFOQueue lfq = new ListFIFOQueue();
        sizeZeroTest(lfq);
        sizeZeroTest(lfq);
    }

    @Test
    public void ListFIFOQueueTest2() {
        ListFIFOQueue lfq = new ListFIFOQueue();
        addRemoveTest(lfq, 1, 1, 10, true);
    }


    @Test
    public void ListFIFOQueueTest3() {
        ListFIFOQueue lfq = new ListFIFOQueue();
        addRemoveTest(lfq, 2, 1, 5, true);

    }

    @Test
    public void CircularArrayFIFOQueueTest1(){
        CircularArrayFIFOQueue caq = new CircularArrayFIFOQueue(10);

        sizeZeroTest(caq);
        sizeZeroTest(caq);
    }

    @Test
    public void CircularArrayFIFOQueueTest2(){
        CircularArrayFIFOQueue caq = new CircularArrayFIFOQueue(10);
        addRemoveTest(caq, 1, 1, 10, true);
    }


    @Test
    public void CircularArrayFIFOQueueTest3() {
        CircularArrayFIFOQueue caq = new CircularArrayFIFOQueue(10);
        addRemoveTest(caq, 2, 1, 5, true);

    }

    @Test
    public void CircularArrayFIFOQueueTest4(){
        CircularArrayFIFOQueue caq = new CircularArrayFIFOQueue(10);
        for(int j = 0 ; j < 2 ; j++) {
            addRemoveTest(caq, 10, 0, 1, false);
            for (int i = 0; i < 2; i++) {
                Assert.assertTrue(caq.size() == 10);
                Assert.assertTrue(caq.isFull());
                try {
                    caq.add(100);
                    Assert.fail();
                } catch (IllegalStateException e) {
                }
                addRemoveTest(caq, 0, 5, 1, false);
                Assert.assertTrue(caq.size() == 5);
                Assert.assertFalse(caq.isFull());

                addRemoveTest(caq, 2, 1, 4, false);
                caq.add(200);
            }
            caq.clear();
            sizeZeroTest(caq);
        }
    }


    @Test
    public void ArrayStackTest1(){
        ArrayStack as = new ArrayStack();

        sizeZeroTest(as);
        sizeZeroTest(as);
    }

    @Test
    public void ArrayStackTest2(){
        ArrayStack as = new ArrayStack();
        addRemoveTest(as, 10, 0, 1, false);
        Assert.assertTrue(as.size() == 10);

        Stack<Integer> stack = new Stack<>();
        for(int num = 1; num <= 10 ; num++) {
            stack.push(num);
        }

        try{
            as.add(20);
        }catch(IllegalStateException e){
            Assert.fail();
        }

        Assert.assertTrue(as.size() == 11);
        Assert.assertTrue(as.next().equals(20));

        Assert.assertTrue(as.size() == stack.size());
        Assert.assertTrue(as.size() == 10);

        Iterator<Integer> iterator = as.iterator();
        while(iterator.hasNext()) {
            Assert.assertTrue(stack.pop().equals(iterator.next()));
        }

        as.clear();
        sizeZeroTest(as);
    }

    @Test
    public void ArrayStackTest3(){
        ArrayStack as = new ArrayStack();
        addRemoveTest(as, 1, 1, 10, true);
    }

    @Test
    public void ArrayStackTest4() {
        ArrayStack as = new ArrayStack();
        addRemoveTest(as, 2, 1, 5, true);
    }
}

