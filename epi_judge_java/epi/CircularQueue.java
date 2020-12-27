package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.List;

public class CircularQueue {

    /*
    Pretty straight-forward. Just track the start and end.
    Let the start and end get displaced from the classic 0-end position,
    and just refill an array from the start if we need to resize.
     */
    public static class Queue {
        private int[] q;
        int start;
        int end;
        public Queue(int capacity) {
            q = new int[capacity];
            start = 0;
            end = 0;
        }

        public void enqueue(Integer x) {
            if (this.size() == q.length)
                this.expand();
            q[end++ % q.length] = x;
        }

        public Integer dequeue() {
            if (this.size() > 0) {
                return q[start++ % q.length];
            }
            return -1;
        }

        public int size() {
            return end - start;
        }

        private void expand() {
            int[] newQ = new int[q.length * 2];
            int newStart = 0;
            int newEnd = 0;
            while (this.size() != 0) {
                newQ[newEnd] = this.dequeue();
                newEnd++;
            }
            q = newQ;
            start = newStart;
            end = newEnd;
        }

        @Override
        public String toString() {
            // TODO - you fill in here.
            return super.toString();
        }
    }

    @EpiUserType(ctorParams = {String.class, int.class})
    public static class QueueOp {
        public String op;
        public int arg;

        public QueueOp(String op, int arg) {
            this.op = op;
            this.arg = arg;
        }

        @Override
        public String toString() {
            return op;
        }
    }

    @EpiTest(testDataFile = "circular_queue.tsv")
    public static void queueTester(List<QueueOp> ops) throws TestFailure {
        Queue q = new Queue(1);
        int opIdx = 0;
        for (QueueOp op : ops) {
            switch (op.op) {
                case "Queue":
                    q = new Queue(op.arg);
                    break;
                case "enqueue":
                    q.enqueue(op.arg);
                    break;
                case "dequeue":
                    int result = q.dequeue();
                    if (result != op.arg) {
                        throw new TestFailure()
                                .withProperty(TestFailure.PropertyName.STATE, q)
                                .withProperty(TestFailure.PropertyName.COMMAND, op)
                                .withMismatchInfo(opIdx, op.arg, result);
                    }
                    break;
                case "size":
                    int s = q.size();
                    if (s != op.arg) {
                        throw new TestFailure()
                                .withProperty(TestFailure.PropertyName.STATE, q)
                                .withProperty(TestFailure.PropertyName.COMMAND, op)
                                .withMismatchInfo(opIdx, op.arg, s);
                    }
                    break;
            }
            opIdx++;
        }
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "CircularQueue.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
